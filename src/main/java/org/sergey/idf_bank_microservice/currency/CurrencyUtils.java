package org.sergey.idf_bank_microservice.currency;

import org.springframework.validation.annotation.Validated;

import java.util.Objects;

import static java.util.Objects.isNull;

@Validated
public class CurrencyUtils {

    public static boolean haveSameAlphaCode(Currency firstCurrency, Currency secondCurrency) {
        if (isNull(firstCurrency) || isNull(secondCurrency)) {
            return false;
        }
        return Objects.equals(firstCurrency.getAlphaCode(), secondCurrency.getAlphaCode());
    }

}
