package org.sergey.idf_bank_microservice.currency;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.sergey.idf_bank_microservice.currency.CurrencyUtils.haveSameAlphaCode;

@SpringBootTest
class CurrencyUtilsTest {

    @Test
    void givenSameAlphaCodes_whenCurrenciesCompared_thenReturnsTrue() {
        Currency currencyUsd = Currency.builder().alphaCode("USD").build();
        assertTrue(haveSameAlphaCode(currencyUsd, currencyUsd));
    }

    @Test
    void givenDifferentAlphaCodes_whenCurrenciesCompared_thenReturnsFalse() {
        Currency currencyUsd = Currency.builder().alphaCode("USD").build();
        Currency currencyEur = Currency.builder().alphaCode("EUR").build();
        assertFalse(haveSameAlphaCode(currencyUsd, currencyEur));
    }

    @Test
    void givenOneNullCurrency_whenCurrenciesCompared_thenFalse() {
        Currency currencyUsd = Currency.builder().alphaCode("USD").build();
        assertFalse(haveSameAlphaCode(currencyUsd, null));
    }

    @Test
    void givenBothNullCurrencies_whenCurrenciesCompared_thenFalse() {
        assertFalse(haveSameAlphaCode(null, null));
    }

    @Test
    void givenEmptyAlphaCodes_whenCurrenciesCompared_thenReturnsTrue() {
        Currency currencyUsd = Currency.builder().alphaCode("").build();
        Currency currencyEur = Currency.builder().alphaCode("").build();
        assertTrue(haveSameAlphaCode(currencyUsd, currencyEur));
    }

    @Test
    void givenNullAlphaCodes_whenCurrenciesCompared_thenFalse() {
        Currency currencyUsd = Currency.builder().build();
        Currency currencyEur = Currency.builder().build();
        assertTrue(haveSameAlphaCode(currencyUsd, currencyEur));
    }

}