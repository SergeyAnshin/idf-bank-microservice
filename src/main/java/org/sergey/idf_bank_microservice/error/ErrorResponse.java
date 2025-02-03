package org.sergey.idf_bank_microservice.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import org.springframework.http.ProblemDetail;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(@JsonUnwrapped ProblemDetail problemDetail, List<ValidationError> validationErrors) {
}