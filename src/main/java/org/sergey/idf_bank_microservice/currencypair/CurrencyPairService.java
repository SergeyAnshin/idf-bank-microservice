package org.sergey.idf_bank_microservice.currencypair;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSourceUrlsGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor

@Service
public class CurrencyPairService {
    private final CurrencyPairRepository currencyPairRepository;
    private final ExchangeRateSourceUrlsGenerator sourceUrlsGenerator;

    @Transactional
    public List<CurrencyPair> findAllWithRateSourceUrl() {
        Consumer<CurrencyPair> setExchangeRateSourceUrls
                = (currencyPair) -> currencyPair.getExchangeRateSources()
                                                .forEach(source -> source.setUrl(sourceUrlsGenerator.generate(source,
                                                                                                              currencyPair)));
        return currencyPairRepository.findAllWithRateSource()
                                     .stream()
                                     .peek(setExchangeRateSourceUrls)
                                     .toList();
    }
}
