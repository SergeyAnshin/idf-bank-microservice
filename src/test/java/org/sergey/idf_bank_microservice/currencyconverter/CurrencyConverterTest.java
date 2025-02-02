package org.sergey.idf_bank_microservice.currencyconverter;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.exchangerate.impl.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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
    void givenValidDara_whenLatestRateIsNull_thenThrowCurrencyConversionException() {
        Currency buyCurrency = Currency.builder().id(1).build();
        Currency sellCurrency = Currency.builder().id(2).build();
        when(exchangeRateService.findLatestRateBy(buyCurrency.getId(),
                                                  sellCurrency.getId())).thenReturn(Optional.empty());
        assertThrows(CurrencyConversionException.class,
                     () -> currencyConverter.convert(new ConversionData(buyCurrency,
                                                                        sellCurrency,
                                                                        BigDecimal.valueOf(3))));
    }

}