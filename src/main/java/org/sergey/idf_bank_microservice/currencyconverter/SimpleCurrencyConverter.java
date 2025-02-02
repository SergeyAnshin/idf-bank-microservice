package org.sergey.idf_bank_microservice.currencyconverter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.exchangerate.impl.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@RequiredArgsConstructor

@Component
@Validated
public class SimpleCurrencyConverter implements CurrencyConverter {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCurrencyConverter.class);
    private final ExchangeRateService exchangeRateService;

    @Override
    @Validated(CurrencyConversionGroup.class)
    public BigDecimal convert(@Valid ConversionData conversionData) {
        long buyCurrencyId = conversionData.buyCurrency().getId();
        long sellCurrencyId = conversionData.sellCurrency().getId();
        return exchangeRateService.findLatestRateBy(buyCurrencyId, sellCurrencyId)
                                  .orElseThrow(() -> {
                                      logger.error(
                                              "Exchange rate for buyCurrency with id = {} and sellCurrency with id = {} not found",
                                              buyCurrencyId,
                                              conversionData.sellCurrency().getId());
                                      return new CurrencyConversionException("Exchange rate not found");
                                  })
                                  .getBuyRate()
                                  .multiply(conversionData.amount());
    }
}

