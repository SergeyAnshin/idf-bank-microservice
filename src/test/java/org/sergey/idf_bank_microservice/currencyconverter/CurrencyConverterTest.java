package org.sergey.idf_bank_microservice.currencyconverter;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRateNotFoundException;
import org.sergey.idf_bank_microservice.exchangerate.impl.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CurrencyConverterTest {
    @MockitoBean
    private ExchangeRateService exchangeRateService;
    @Autowired
    private SimpleCurrencyConverter currencyConverter;

    @Test
    void givenNullArg_whenConvertCurrency_thenThrowConstraintViolationException() {
        Currency currency = Currency.builder().id(2).build();
        BigDecimal amount = BigDecimal.valueOf(1);
        assertAll(() -> {
            assertThrows(ConstraintViolationException.class,
                         () -> currencyConverter.convert(new ConversionData(null, currency, amount)));
            assertThrows(ConstraintViolationException.class,
                         () -> currencyConverter.convert(new ConversionData(currency, null, amount)));
            assertThrows(ConstraintViolationException.class,
                         () -> currencyConverter.convert(new ConversionData(currency, currency, null)));
        });
    }

    @Test
    void givenAmountLessThan0_whenConvertCurrency_thenThrowConstraintViolationException() {
        Currency currency = Currency.builder().id(2).build();
        assertThrows(ConstraintViolationException.class,
                     () -> currencyConverter.convert(new ConversionData(currency, currency, BigDecimal.valueOf(-1))));
    }

    @Test
    void givenCurrencyIdLessThan1_whenConvertCurrency_thenThrowConstraintViolationException() {
        Currency currency = Currency.builder().id(-1).build();
        assertThrows(ConstraintViolationException.class,
                     () -> currencyConverter.convert(new ConversionData(currency, currency, BigDecimal.valueOf(3))));
    }

    @Test
    void givenValidDara_whenLatestRateIsNull_thenThrowExchangeRateNotFoundException() {
        Currency buyCurrency = Currency.builder().id(1).build();
        Currency sellCurrency = Currency.builder().id(2).build();
        Mockito.when(exchangeRateService.findLatestRateBy(buyCurrency.getId(), sellCurrency.getId())).thenReturn(
                Optional.empty());
        assertThrows(ExchangeRateNotFoundException.class,
                     () -> currencyConverter.convert(new ConversionData(buyCurrency,
                                                                        sellCurrency,
                                                                        BigDecimal.valueOf(3))));
    }

}