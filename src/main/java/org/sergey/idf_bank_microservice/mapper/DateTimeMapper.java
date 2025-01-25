package org.sergey.idf_bank_microservice.mapper;

import org.mapstruct.Mapper;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface DateTimeMapper {

    default OffsetDateTime toOffsetDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        return OffsetDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"));
    }

}
