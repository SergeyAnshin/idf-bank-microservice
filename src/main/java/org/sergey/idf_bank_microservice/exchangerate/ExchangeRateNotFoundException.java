package org.sergey.idf_bank_microservice.exchangerate;

import lombok.Getter;
import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;
import org.sergey.idf_bank_microservice.exception.EntityRelatedException;

@Getter
public class ExchangeRateNotFoundException extends EntityRelatedException {
    private CurrencyPair currencyPair;

    public ExchangeRateNotFoundException(String message) {
        super(message);
    }

    public ExchangeRateNotFoundException(String message, CurrencyPair currencyPair) {
        super(message);
        this.currencyPair = currencyPair;
    }

    public ExchangeRateNotFoundException(String message,
                                         Class<? extends GeneralEntity> entityClass,
                                         CurrencyPair currencyPair) {
        super(message, entityClass);
        this.currencyPair = currencyPair;
    }
}
