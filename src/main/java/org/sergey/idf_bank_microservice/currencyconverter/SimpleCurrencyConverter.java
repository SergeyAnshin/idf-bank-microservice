package org.sergey.idf_bank_microservice.currencyconverter;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor

@Component
public class SimpleCurrencyConverter implements CurrencyConverter {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCurrencyConverter.class);

    @Override
    public BigDecimal convert(BigDecimal amount, ExchangeRate latestRate) {
        requireNonNull(amount, "Amount must not be null");
        requireNonNull(latestRate, "CurrencyPair must not be null");

        BigDecimal buyRate = latestRate.getBuyRate();
        if (isNull(buyRate)) {
            logger.error("Buy rate is null for ExchangeRate: {}", latestRate);
            throw new IllegalArgumentException("Buy rate must not be null");
        }
        return amount.multiply(latestRate.getBuyRate());
    }
}
