package org.sergey.idf_bank_microservice.mapper;

import org.mapstruct.Mapper;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.nonNull;

@Mapper
public interface DateTimeMapper {
    String BASE_OFFSET_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ssX";

    default String toStringOffsetDateTime(OffsetDateTime dateTime) {
        if (nonNull(dateTime)) {
            return dateTime.toString().replace('T', ' ');
        } else {
            return "";
        }
    }

    default OffsetDateTime toOffsetDateTime(String dateTime) {
        if (nonNull(dateTime)) {
            return OffsetDateTime.parse(dateTime, DateTimeFormatter.ofPattern(BASE_OFFSET_DATE_TIME_PATTERN));
        } else {
            return OffsetDateTime.MIN;
        }
    }
}
