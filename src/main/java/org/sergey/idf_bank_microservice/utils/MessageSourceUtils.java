package org.sergey.idf_bank_microservice.utils;

import org.sergey.idf_bank_microservice.entity.GeneralEntity;
import org.sergey.idf_bank_microservice.exception.ApplicationException;

import static java.util.Objects.nonNull;

public class MessageSourceUtils {

    private static String createCode(String... parts) {
        return String.join(".", parts);
    }

    public static String resolveMessageSourceCode(String prefix, Class<? extends GeneralEntity> entityClass,
                                                  Class<? extends ApplicationException> exceptionClass, String suffix) {
        if (nonNull(prefix) && nonNull(entityClass) && nonNull(exceptionClass) && nonNull(suffix)) {
            return createCode(prefix, entityClass.getSimpleName(), exceptionClass.getSimpleName(), suffix);
        } else {
            throw new IllegalArgumentException("Parameter cannot be null");
        }
    }

    public static String resolveMessageSourceCode(String prefix, Class<?> exceptionClass, String suffix) {
        if (nonNull(prefix) && nonNull(exceptionClass) && nonNull(suffix)) {
            return createCode(prefix, exceptionClass.getSimpleName(), suffix);
        } else {
            throw new IllegalArgumentException("Parameter cannot be null");
        }
    }

}
