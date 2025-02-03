package org.sergey.idf_bank_microservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.Objects.nonNull;

public class OffsetDateTimeFormatValidator implements ConstraintValidator<OffsetDateTimeFormat, String> {

    @Override
    public boolean isValid(String dateTime, ConstraintValidatorContext context) {
        if (nonNull(dateTime)) {
            try {
                OffsetDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"));
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
