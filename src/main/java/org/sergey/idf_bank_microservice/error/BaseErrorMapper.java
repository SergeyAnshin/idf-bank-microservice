package org.sergey.idf_bank_microservice.error;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.exception.EntityNotExistsException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.sergey.idf_bank_microservice.utils.MessageSourceUtils.resolveMessageSourceCode;
import static org.sergey.idf_bank_microservice.utils.WebRequestUtils.extractUri;

@RequiredArgsConstructor

@Component
public class BaseErrorMapper implements ErrorMapper {
    private final MessageSource messageSource;

    @Override
    public ErrorResponse toErrorResponse(MethodArgumentNotValidException ex, HttpStatusCode status) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(ex.getBody().getTitle());
        problemDetail.setDetail(ex.getBody().getDetail());
        problemDetail.setInstance(ex.getBody().getInstance());
        List<ValidationError> validationErrorList = toValidationErrorList(ex);
        return ErrorResponse.builder().problemDetail(problemDetail).validationErrors(validationErrorList).build();
    }

    @Override
    public ErrorResponse toErrorResponse(EntityNotExistsException ex, WebRequest request, HttpStatus status) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        String titleCode = resolveMessageSourceCode(MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_PREFIX, ex.getEntityClass(),
                                                    ex.getClass(), MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_TITLE_SUFFIX);
        String title = messageSource.getMessage(titleCode, null, request.getLocale());
        problemDetail.setTitle(title);
        String detailCode = resolveMessageSourceCode(MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_PREFIX, ex.getEntityClass(),
                                                     ex.getClass(), MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_DETAIL_SUFFIX);
        String detail = messageSource.getMessage(detailCode, new String[]{ex.getLookupFieldValue()},
                                                 request.getLocale());
        problemDetail.setDetail(detail);
        problemDetail.setInstance(extractUri(request));
        return ErrorResponse.builder().problemDetail(problemDetail).build();
    }

    @Override
    public ErrorResponse toErrorResponse(RuntimeException ex, HttpStatus status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        String titleCode = resolveMessageSourceCode(MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_PREFIX, ex.getClass(),
                                                    MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_TITLE_SUFFIX);
        String title = messageSource.getMessage(titleCode, null, request.getLocale());
        problemDetail.setTitle(title);
        String detailCode = resolveMessageSourceCode(MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_PREFIX, ex.getClass(),
                                                     MESSAGE_SOURCE_CODE_PROBLEM_DETAIL_DETAIL_SUFFIX);
        String detail = messageSource.getMessage(detailCode, null, request.getLocale());
        problemDetail.setDetail(detail);
        problemDetail.setInstance(extractUri(request));
        return ErrorResponse.builder().problemDetail(problemDetail).build();

    }

    private List<ValidationError> toValidationErrorList(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors().stream().map(this::toValidationError).toList();
    }

    private ValidationError toValidationError(MessageSourceResolvable messageSourceResolvable) {
        String[] codes = messageSourceResolvable.getCodes();
        String base = null;
        if (nonNull(codes)) {
            String[] code = codes[0].split("\\.");
            base = code[code.length - 1];
        }
        return ValidationError.builder().base(base).error(messageSourceResolvable.getDefaultMessage()).build();
    }

}
