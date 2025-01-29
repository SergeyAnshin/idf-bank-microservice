package org.sergey.idf_bank_microservice.exchangerate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sergey.idf_bank_microservice.exchangerate.dto.ExchangeRateResponse;

@Mapper
public interface ExchangeRateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "buyCurrency.alphaCode", source = "buyCurrency")
    @Mapping(target = "sellCurrency.alphaCode", source = "sellCurrency")
    @Mapping(target = "buyRate", source = "buyRate")
    ExchangeRate toExchangeRate(ExchangeRateResponse exchangeRateResponse);

}
