package org.sergey.idf_bank_microservice.currencyconverter;

import lombok.Getter;
import org.sergey.idf_bank_microservice.exception.ApplicationException;

@Getter
public class CurrencyConversionException extends ApplicationException {

    public CurrencyConversionException(String message) {
        super(message);
    }

    public CurrencyConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
