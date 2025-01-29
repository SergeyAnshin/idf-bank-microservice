package org.sergey.idf_bank_microservice.error;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.debittransaction.TransactionRegistrationException;
import org.sergey.idf_bank_microservice.exception.EntityNotExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@RequiredArgsConstructor

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ErrorMapper errorMapper;
    private final MessageSource messageSource;

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleEntityExceptions(RuntimeException ex, WebRequest request) {
        logger.error("An unexpected error occurred: ", ex);
        ErrorResponse errorResponse = errorMapper.toErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.warn("Validation failed: ", ex);
        ErrorResponse errorResponse = errorMapper.toErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(EntityNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotExistsException(EntityNotExistsException ex,
                                                                        WebRequest request) {
        logger.info("Entity not exists: ", ex);
        ErrorResponse errorResponse = errorMapper.toErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(TransactionRegistrationException.class)
    public ResponseEntity<ErrorResponse> handleTransactionRegistrationException(TransactionRegistrationException ex,
                                                                                WebRequest request) {
        ErrorResponse errorResponse = errorMapper.toErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

}
