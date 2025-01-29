package org.sergey.idf_bank_microservice.exchangerate.impl;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;
import org.sergey.idf_bank_microservice.currencypair.CurrencyPairService;
import org.sergey.idf_bank_microservice.entitypersister.EntityPersistenceService;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRate;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRateRepository;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRatesLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor

@Service
public class ExchangeRateService {
    private final ExchangeRatesLoader exchangeRatesLoader;
    private final ExchangeRateRepository exchangeRateRepository;
    private final EntityPersistenceService<ExchangeRate> ratePersistenceService;
    private final CurrencyPairService currencyPairService;

    @Scheduled(cron = "${exchange-rate-fetcher.cron-fetch-schedule}")
    @Transactional
    public void loadExchangeRates() {
        List<CurrencyPair> currencyPairs = currencyPairService.findAllWithRateSourceUrl();
        List<ExchangeRate> newExchangeRates = exchangeRatesLoader.load(currencyPairs);
        List<ExchangeRate> persistedRates = ratePersistenceService.persist(newExchangeRates);
        exchangeRateRepository.saveAll(persistedRates);
    }

}
