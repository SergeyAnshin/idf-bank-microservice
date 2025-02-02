package org.sergey.idf_bank_microservice.exchangerate;

import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;

import java.util.List;

public interface ExchangeRatesLoader {

    List<ExchangeRate> load(List<CurrencyPair> currencyPairs);

}
