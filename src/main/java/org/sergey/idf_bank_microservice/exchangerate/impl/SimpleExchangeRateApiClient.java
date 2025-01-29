package org.sergey.idf_bank_microservice.exchangerate.impl;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRate;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRateApiClient;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRateMapper;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRateResponseClassResolver;
import org.sergey.idf_bank_microservice.exchangerate.dto.ExchangeRateResponse;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@RequiredArgsConstructor

@Component
public class SimpleExchangeRateApiClient implements ExchangeRateApiClient {
    private final ExchangeRateResponseClassResolver responseClassResolver;
    private final ExchangeRateMapper exchangeRateMapper;
    private final WebClient webClient;

    @Override
    public Optional<ExchangeRate> fetchExchangeRate(ExchangeRateSource exchangeRateSource, CurrencyPair currencyPair) {
        try {
            Class<? extends ExchangeRateResponse> responseClass = responseClassResolver.resolve(exchangeRateSource);
            ExchangeRateResponse exchangeRateResponse = webClient.get()
                    .uri(exchangeRateSource.getUrl())
                    .retrieve()
                    .bodyToMono(responseClass)
                    .block();
            return Optional.of(exchangeRateMapper.toExchangeRate(exchangeRateResponse));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
