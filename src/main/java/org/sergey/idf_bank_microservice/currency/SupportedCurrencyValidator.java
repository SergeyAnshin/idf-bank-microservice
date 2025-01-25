package org.sergey.idf_bank_microservice.currency;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor

@Component
public class SupportedCurrencyValidator implements ConstraintValidator<SupportedCurrency, String> {
    private final CurrencyRepository currencyRepository;

    @Override
    public boolean isValid(String currencyCode, ConstraintValidatorContext context) {
        if (nonNull(currencyCode)) {
            return currencyRepository.existsByAlphaCode(currencyCode);
        } else {
            return false;
        }
    }
}
