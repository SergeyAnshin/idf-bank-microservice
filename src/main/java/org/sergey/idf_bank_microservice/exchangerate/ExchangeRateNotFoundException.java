package org.sergey.idf_bank_microservice.exchangerate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;
import org.sergey.idf_bank_microservice.exception.ApplicationException;

@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateNotFoundException extends ApplicationException {
    private final CurrencyPair currencyPair;

    public ExchangeRateNotFoundException(String message, CurrencyPair currencyPair) {
        super(message);
        this.currencyPair = currencyPair;
    }

}
