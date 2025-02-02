package org.sergey.idf_bank_microservice.exchangerate.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.sergey.idf_bank_microservice.exchangerate.dto.AlphaVantageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

import static java.util.Objects.nonNull;
import static org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSourceName.ALPHA_VANTAGE;

@Component
public class AlphaVantageResponseDeserializer extends StdDeserializer<AlphaVantageResponse> {
    private static final Logger logger = LoggerFactory.getLogger(AlphaVantageResponseDeserializer.class);

    protected AlphaVantageResponseDeserializer() {
        super(AlphaVantageResponse.class);
    }

    @Override
    public AlphaVantageResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            JsonNode rateNode = node.get("Realtime Currency Exchange Rate");

            if (nonNull(rateNode)) {
                String buyCurrency = rateNode.get("1. From_Currency Code").asText();
                String sellCurrency = rateNode.get("3. To_Currency Code").asText();
                BigDecimal rate = rateNode.get("5. Exchange Rate").decimalValue();
                return AlphaVantageResponse.builder()
                        .buyCurrency(buyCurrency)
                        .sellCurrency(sellCurrency)
                        .buyRate(rate)
                        .build();
            } else {
                logger.warn("Invalid deserialization scheme or limit exceeded for {}", ALPHA_VANTAGE.name());
                return null;
            }
        } catch (IOException e) {
            logger.warn("Error deserializing response from {}", ALPHA_VANTAGE.name());
            return null;
        }
    }
}
