package org.sergey.idf_bank_microservice.currencyconverter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.sergey.idf_bank_microservice.currency.Currency;

import java.math.BigDecimal;

public record ConversionData(@NotNull(groups = { CurrencyConversionGroup.class }) @Valid Currency buyCurrency,
                             @NotNull(groups = { CurrencyConversionGroup.class }) @Valid Currency sellCurrency,
                             @NotNull(groups = { CurrencyConversionGroup.class })
                             @Min(value = 0, groups = { CurrencyConversionGroup.class }) BigDecimal amount) {
}
