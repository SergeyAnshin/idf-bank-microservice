package org.sergey.idf_bank_microservice.currency;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.entitypersister.BaseEntityPersistenceService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor

@Component
public class CurrencyPersistenceService extends BaseEntityPersistenceService<Currency> {
    private final CurrencyRepository currencyRepository;

    @Override
    @Transactional
    public Currency persist(Currency currency) {
        return super.persist(currency, currencyRepository::findByAlphaCode, currency.getAlphaCode());
    }
}
