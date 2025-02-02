package org.sergey.idf_bank_microservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDeserializerException extends ApplicationException {

    public ResponseDeserializerException(String message) {
        super(message);
    }
}
