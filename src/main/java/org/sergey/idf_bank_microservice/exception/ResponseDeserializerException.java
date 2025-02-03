package org.sergey.idf_bank_microservice.exception;

import lombok.Getter;

@Getter
public class ResponseDeserializerException extends ApplicationException {

    public ResponseDeserializerException(String message) {
        super(message);
    }
}
