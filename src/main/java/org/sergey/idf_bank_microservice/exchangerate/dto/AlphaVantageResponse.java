package org.sergey.idf_bank_microservice.exchangerate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.sergey.idf_bank_microservice.exchangerate.deserializer.AlphaVantageResponseDeserializer;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)

@Component
@JsonDeserialize(using = AlphaVantageResponseDeserializer.class)
public class AlphaVantageResponse extends ExchangeRateResponse {}
