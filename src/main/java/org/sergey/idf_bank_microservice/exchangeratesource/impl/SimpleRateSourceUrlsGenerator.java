package org.sergey.idf_bank_microservice.exchangeratesource.impl;

import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSource;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSourceUrlsGenerator;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class SimpleRateSourceUrlsGenerator implements ExchangeRateSourceUrlsGenerator {

    @Override
    public String generate(ExchangeRateSource rateSource, CurrencyPair currencyPair) {
        if (nonNull(rateSource) && nonNull(currencyPair) && nonNull(currencyPair.getExchangeRateSources())
                && nonNull(currencyPair.getBuyCurrency()) && nonNull(currencyPair.getSellCurrency())) {
            return createUrl(rateSource.getUrlTemplate(),
                             currencyPair.getBuyCurrency().getAlphaCode(),
                             currencyPair.getSellCurrency().getAlphaCode(),
                             rateSource.getApiKey());
        } else {
            return "";
        }
    }


    private String createUrl(String template, String buyCurrency, String sellCurrency, String apiKey) {
        return String.format(template, buyCurrency, sellCurrency, apiKey);
    }
}
