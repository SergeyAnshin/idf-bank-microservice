package org.sergey.idf_bank_microservice.error;

import org.sergey.idf_bank_microservice.exception.EntityNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

public interface ErrorMapper {
    String MESSAGE_SOURCE_CODE_UNEXPECTED_ERROR = "";
    String MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_PREFIX = "problem-detail";
    String MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_TITLE_SUFFIX = "title";
    String MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_DETAIL_SUFFIX = "detail";

    ErrorResponse toErrorResponse(MethodArgumentNotValidException ex, HttpStatusCode status);

    ErrorResponse toErrorResponse(EntityNotExistsException ex, WebRequest request, HttpStatus httpStatus);

    ErrorResponse toErrorResponse(RuntimeException ex, HttpStatus httpStatus, WebRequest request);

}
