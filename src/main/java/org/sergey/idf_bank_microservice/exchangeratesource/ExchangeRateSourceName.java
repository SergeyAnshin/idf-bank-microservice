package org.sergey.idf_bank_microservice.exchangeratesource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sergey.idf_bank_microservice.exchangerate.dto.AlphaVantageResponse;
import org.sergey.idf_bank_microservice.exchangerate.dto.ExchangeRateResponse;
import org.sergey.idf_bank_microservice.exchangerate.dto.TwelveDataResponse;

@AllArgsConstructor
@Getter
public enum ExchangeRateSourceName {
    ALPHA_VANTAGE(AlphaVantageResponse.class), TWELVE_DATA(TwelveDataResponse.class);

    private final Class<? extends ExchangeRateResponse> responseClass;

}
