package org.sergey.idf_bank_microservice.exchangerate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.sergey.idf_bank_microservice.exchangerate.deserializer.TwelveDataResponseDeserializer;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)

@Component
@JsonDeserialize(using = TwelveDataResponseDeserializer.class)
public class TwelveDataResponse extends ExchangeRateResponse {}
