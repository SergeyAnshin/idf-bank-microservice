package org.sergey.idf_bank_microservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExchangeRateFetcherConfig {
    @Value("${exchange-rate-fetcher.executor-service.currency-pair.pool-size}")
    private int currencyPairPoolSize;
    @Value("${exchange-rate-fetcher.executor-service.exchange-rate-source.pool-size}")
    private int exchangeRateSourcePoolSize;

    @Bean
    public ExecutorService currencyPairExecutorService() {
        return Executors.newFixedThreadPool(currencyPairPoolSize);
    }

    @Bean
    public ExecutorService exchangeRateSourceExecutorService() {
        return Executors.newFixedThreadPool(exchangeRateSourcePoolSize);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
