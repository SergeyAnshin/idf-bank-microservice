package org.sergey.idf_bank_microservice.exchangerate.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.sergey.idf_bank_microservice.exchangerate.dto.TwelveDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;

import static org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSourceName.TWELVE_DATA;

public class TwelveDataResponseDeserializer extends StdDeserializer<TwelveDataResponse> {
    private static final Logger logger = LoggerFactory.getLogger(TwelveDataResponseDeserializer.class);

    protected TwelveDataResponseDeserializer() {
        super(TwelveDataResponse.class);
    }

    @Override
    public TwelveDataResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);

            String[] currencyPair = node.get("symbol").asText().split("/");
            String buyCurrency = currencyPair[0];
            String sellCurrency = currencyPair[1];
            BigDecimal rate = node.get("rate").decimalValue();

            return TwelveDataResponse.builder()
                    .buyCurrency(buyCurrency)
                    .sellCurrency(sellCurrency)
                    .buyRate(rate)
                    .build();
        } catch (IOException e) {
            logger.warn("Error deserializing response from {}", TWELVE_DATA.name());
            return null;
        }
    }
}
