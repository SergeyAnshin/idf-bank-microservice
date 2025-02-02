package org.sergey.idf_bank_microservice.exchangeratesource;

import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;

public interface ExchangeRateSourceUrlsGenerator {

    String generate(ExchangeRateSource rateSource, CurrencyPair currencyPair);

}
