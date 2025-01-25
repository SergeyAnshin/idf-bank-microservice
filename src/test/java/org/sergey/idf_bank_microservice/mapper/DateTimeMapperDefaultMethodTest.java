package org.sergey.idf_bank_microservice.mapper;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateTimeMapperDefaultMethodTest {
    private final DateTimeMapper dateTimeMapper = new DateTimeMapper() {};

    @Test
    void toOffsetDateTime_shouldParse_whenValidDateTimeString() {
        String validDateTime = "2025-01-25 22:16:00+03";
        OffsetDateTime result = dateTimeMapper.toOffsetDateTime(validDateTime);
        assertEquals(OffsetDateTime.parse(validDateTime,
                                          DateTimeFormatter.ofPattern(DateTimeMapper.BASE_OFFSET_DATE_TIME_PATTERN)),
                     result);
    }

    @Test
    void toOffsetDateTime_shouldReturnMinOffsetDateTime_whenDateTimeIsNull() {
        OffsetDateTime result = dateTimeMapper.toOffsetDateTime(null);
        assertEquals(OffsetDateTime.MIN, result);
    }

    @Test
    void toOffsetDateTime_shouldThrowDateTimeParseException_whenDateTimeIsInvalid() {
        String invalidDateTime = "2025-01-25 22:16:00+03:00";
        assertThrows(DateTimeParseException.class, () -> dateTimeMapper.toOffsetDateTime(invalidDateTime));
    }

}