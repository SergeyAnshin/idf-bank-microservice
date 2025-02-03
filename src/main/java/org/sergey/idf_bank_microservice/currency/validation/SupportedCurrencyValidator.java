package org.sergey.idf_bank_microservice.currency.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.currency.CurrencyService;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor

@Component
public class SupportedCurrencyValidator implements ConstraintValidator<SupportedCurrency, String> {
    private final CurrencyService currencyService;

    @Override
    public boolean isValid(String currencyCode, ConstraintValidatorContext context) {
        if (nonNull(currencyCode)) {
            return currencyService.findByAlphaCode(currencyCode).isPresent();
        } else {
            return false;
        }
    }
}
