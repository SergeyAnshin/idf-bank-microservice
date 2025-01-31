package org.sergey.idf_bank_microservice.exchangerate.impl;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;
import org.sergey.idf_bank_microservice.currencypair.CurrencyPairService;
import org.sergey.idf_bank_microservice.entitypersister.EntityPersistenceService;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRate;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRateNotFoundException;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRateRepository;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRatesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor

@Service
public class ExchangeRateService {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);
    private final ExchangeRatesLoader exchangeRatesLoader;
    private final ExchangeRateRepository rateRepository;
    private final EntityPersistenceService<ExchangeRate> ratePersistenceService;
    private final CurrencyPairService currencyPairService;

    @Scheduled(cron = "${exchange-rate-fetcher.cron-fetch-schedule}")
    @Transactional
    public void loadExchangeRates() {
        List<CurrencyPair> currencyPairs = currencyPairService.findAllWithRateSourceUrl();
        List<ExchangeRate> newExchangeRates = exchangeRatesLoader.load(currencyPairs);
        List<ExchangeRate> persistedRates = ratePersistenceService.persist(newExchangeRates);
        rateRepository.saveAll(persistedRates);
    }

    public ExchangeRate findLatestRate(CurrencyPair currencyPair) {
        requireNonNull(currencyPair, "CurrencyPair must not be null");

        long buyCurrencyId = Optional.ofNullable(currencyPair.getBuyCurrency())
                                     .map(Currency::getId)
                                     .orElseThrow(() -> {
                                         logger.error("Buy currency id is null for CurrencyPair: {}", currencyPair);
                                         return new IllegalArgumentException("Buy currency id must not be null");
                                     });

        long sellCurrencyId = Optional.ofNullable(currencyPair.getSellCurrency())
                                      .map(Currency::getId)
                                      .orElseThrow(() -> {
                                          logger.error("Sell currency id is null for CurrencyPair: {}", currencyPair);
                                          return new IllegalArgumentException("Sell currency id must not be null");
                                      });

        Optional<ExchangeRate> latestRate = rateRepository.findLatestRate(buyCurrencyId, sellCurrencyId);

        if (latestRate.isPresent()) {
            return latestRate.get();
        } else {
            logger.error("No exchange rate found for CurrencyPair: {}", currencyPair);
            throw new ExchangeRateNotFoundException("No current exchange rate for CurrencyPair", currencyPair);
        }
    }

    public Optional<ExchangeRate> findLatestRateBy(@Min(1) long buyCurrencyId, @Min(1) long sellCurrencyId) {
        return rateRepository.findLatestRate(buyCurrencyId, sellCurrencyId);
    }
}
