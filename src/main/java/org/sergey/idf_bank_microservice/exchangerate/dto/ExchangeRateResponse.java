package org.sergey.idf_bank_microservice.exchangerate.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode
public abstract class ExchangeRateResponse {
    private String buyCurrency;
    private String sellCurrency;
    private BigDecimal buyRate;
}
