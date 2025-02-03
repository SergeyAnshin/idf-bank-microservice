package org.sergey.idf_bank_microservice.exchangerate;

import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;

public class ExchangeRateFetchException extends RuntimeException {
    private final CurrencyPair currencyPair;
    private final String exchangeRateSourceUrl;

    public ExchangeRateFetchException(Throwable cause, CurrencyPair currencyPair, String exchangeRateSourceUrl) {
        super(cause);
        this.currencyPair          = currencyPair;
        this.exchangeRateSourceUrl = exchangeRateSourceUrl;
    }

    public ExchangeRateFetchException(String message, CurrencyPair currencyPair) {
        super(message);
        this.currencyPair          = currencyPair;
        this.exchangeRateSourceUrl = "";
    }
}
