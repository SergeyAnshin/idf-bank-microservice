package org.sergey.idf_bank_microservice.exchangerate;

import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSource;

import java.util.Optional;

public interface ExchangeRateApiClient {

    Optional<ExchangeRate> fetchExchangeRate(ExchangeRateSource exchangeRateSource, CurrencyPair currencyPair);

}
