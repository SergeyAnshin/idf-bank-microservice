package org.sergey.idf_bank_microservice.exchangerate;

import org.sergey.idf_bank_microservice.exchangerate.dto.ExchangeRateResponse;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSource;

public interface ExchangeRateResponseClassResolver {

    Class<? extends ExchangeRateResponse> resolve(ExchangeRateSource exchangeRateSource);

}
