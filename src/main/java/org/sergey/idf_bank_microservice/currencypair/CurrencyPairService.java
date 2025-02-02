package org.sergey.idf_bank_microservice.currencypair;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSource;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSourceUrlsGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor

@Service
public class CurrencyPairService {
    private final CurrencyPairRepository currencyPairRepository;
    private final ExchangeRateSourceUrlsGenerator sourceUrlsGenerator;

    @Transactional
    public List<CurrencyPair> findAllWithRateSourceUrl() {
        BiFunction<ExchangeRateSource, CurrencyPair, ExchangeRateSource> resolveUrl
                = (rateSource, currencyPair) -> rateSource.toBuilder()
                                                          .url(sourceUrlsGenerator.generate(rateSource, currencyPair))
                                                          .build();
        Function<CurrencyPair, List<ExchangeRateSource>> getSourcesWithUrls
                = (currencyPair) -> currencyPair.getExchangeRateSources().stream()
                                                .map(source -> resolveUrl.apply(source, currencyPair)).toList();
        return currencyPairRepository.findAllWithRateSource()
                                     .stream()
                                     .peek(pair -> pair.setExchangeRateSources(getSourcesWithUrls.apply(pair)))
                                     .toList();
    }
}
