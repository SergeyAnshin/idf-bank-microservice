package org.sergey.idf_bank_microservice.currencyconverter;

import org.sergey.idf_bank_microservice.exchangerate.ExchangeRate;

import java.math.BigDecimal;

public interface CurrencyConverter {

    BigDecimal convert(BigDecimal amount, ExchangeRate latestRate);

}
