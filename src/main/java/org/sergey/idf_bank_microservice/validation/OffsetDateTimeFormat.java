package org.sergey.idf_bank_microservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented

@Constraint(validatedBy = {OffsetDateTimeFormatValidator.class})
public @interface OffsetDateTimeFormat {

    String message() default "{validator.OffsetDateTimeFormat.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
