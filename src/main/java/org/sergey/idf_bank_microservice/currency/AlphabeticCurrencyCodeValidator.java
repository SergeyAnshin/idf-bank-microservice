package org.sergey.idf_bank_microservice.currency;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Currency;
import java.util.function.Predicate;

public class AlphabeticCurrencyCodeValidator implements ConstraintValidator<AlphabeticCurrencyCode, String> {

    @Override
    public boolean isValid(String currencyCode, ConstraintValidatorContext context) {
        Predicate<Currency> currencyPredicate = currency -> currency.getCurrencyCode().equals(currencyCode);
        return Currency.getAvailableCurrencies().stream().anyMatch(currencyPredicate);
    }
}
