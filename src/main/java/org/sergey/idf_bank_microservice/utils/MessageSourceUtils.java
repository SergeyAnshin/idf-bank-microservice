package org.sergey.idf_bank_microservice.utils;

import org.sergey.idf_bank_microservice.entity.GeneralEntity;
import org.sergey.idf_bank_microservice.exception.ApplicationException;

public class MessageSourceUtils {

    public static String resolveMessageSourceCode(String prefix, Class<? extends GeneralEntity> entityClass,
                                                  Class<? extends ApplicationException> exceptionClass, String suffix) {
        return String.join(".", prefix, entityClass.getSimpleName(), exceptionClass.getSimpleName(), suffix);
    }

    public static String resolveMessageSourceCode(String prefix, Class<?> exceptionClass, String suffix) {
        return String.join(".", prefix, exceptionClass.getSimpleName(), suffix);
    }

}
