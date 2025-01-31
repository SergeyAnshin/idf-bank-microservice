package org.sergey.idf_bank_microservice.currencyconverter;

import jakarta.validation.Valid;

import java.math.BigDecimal;

public interface CurrencyConverter {

    BigDecimal convert(@Valid ConversionData conversionData);

}
