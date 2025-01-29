package org.sergey.idf_bank_microservice.exchangerate.impl;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRateResponseClassResolver;
import org.sergey.idf_bank_microservice.exchangerate.dto.ExchangeRateResponse;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSource;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class EnumFieldResponseClassResolver implements ExchangeRateResponseClassResolver {

    @Override
    public Class<? extends ExchangeRateResponse> resolve(ExchangeRateSource exchangeRateSource) {
        return exchangeRateSource.getName().getResponseClass();
    }
}
