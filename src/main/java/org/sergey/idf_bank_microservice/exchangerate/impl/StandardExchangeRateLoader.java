package org.sergey.idf_bank_microservice.exchangerate.impl;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRate;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRateApiClient;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRatesLoader;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor

@Component
public class StandardExchangeRateLoader implements ExchangeRatesLoader {
    private static final Logger logger = LoggerFactory.getLogger(StandardExchangeRateLoader.class);
    private final ExecutorService currencyPairExecutorService;
    private final ExchangeRateApiClient apiClient;

    @PreDestroy
    public void shutdown() {
        currencyPairExecutorService.shutdown();
        try {
            boolean isTerminated = currencyPairExecutorService.awaitTermination(2, TimeUnit.MINUTES);
            if (isTerminated) {
                logger.info("Executor service for currency pair fetch tasks terminated");
            } else {
                logger.warn("Executor service for currency pair fetch tasks not terminate in the expected time");
                currencyPairExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Executor service shutdown was interrupted", e);
        }
    }

    @Override
    public List<ExchangeRate> load(List<CurrencyPair> currencyPairs) {
        List<CompletableFuture<Optional<ExchangeRate>>> futureExchangeRates = currencyPairs.stream()
                .map((this::createRateFetchTask))
                .toList();

        CompletableFuture.allOf(futureExchangeRates.toArray(new CompletableFuture[0])).join();

        return futureExchangeRates.stream()
                .map(this::getExchangeRate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private CompletableFuture<Optional<ExchangeRate>> createRateFetchTask(CurrencyPair currencyPair) {
        return CompletableFuture
                .supplyAsync(() -> loadRatesForCurrencyPair(currencyPair, currencyPair.getExchangeRateSources()),
                             currencyPairExecutorService)
                .exceptionally((ex) -> {
                    logger.error("Error loading exchange rate for {}: {}", currencyPair, ex.getMessage());
                    return Optional.empty();
                });
    }

    private Optional<ExchangeRate> getExchangeRate(CompletableFuture<Optional<ExchangeRate>> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Failed to retrieve exchange rate: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    private Optional<ExchangeRate> loadRatesForCurrencyPair(CurrencyPair currencyPair,
                                                            List<ExchangeRateSource> rateSources) {
        for (ExchangeRateSource source : rateSources) {
            Optional<ExchangeRate> optionalRate = apiClient.fetchExchangeRate(source, currencyPair);
            if (optionalRate.isPresent()) {
                logger.info("Successfully fetched exchange rate for {} from {}: {}",
                            currencyPair,
                            source.getUrl(),
                            optionalRate.get().getBuyRate());
                return optionalRate;
            } else {
                logger.warn("Failed to fetch exchange rate for {} from {}", currencyPair, source.getUrl());
            }
        }
        logger.error("Exchange rate for currency pair {} not loaded {}", currencyPair, OffsetDateTime.now());
        return Optional.empty();
    }
}

