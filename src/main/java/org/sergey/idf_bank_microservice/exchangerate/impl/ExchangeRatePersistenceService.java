package org.sergey.idf_bank_microservice.exchangerate.impl;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.currency.CurrencyPersistenceService;
import org.sergey.idf_bank_microservice.entitypersister.BaseEntityPersistenceService;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor

@Component
public class ExchangeRatePersistenceService extends BaseEntityPersistenceService<ExchangeRate> {
    private final CurrencyPersistenceService currencyPersistenceService;

    @Override
    @Transactional
    public ExchangeRate persist(ExchangeRate exchangeRate) {
        Currency buyCurrency = currencyPersistenceService.persist(exchangeRate.getBuyCurrency());
        Currency sellCurrency = currencyPersistenceService.persist(exchangeRate.getSellCurrency());
        return exchangeRate.toBuilder().buyCurrency(buyCurrency).sellCurrency(sellCurrency).build();
    }
}
