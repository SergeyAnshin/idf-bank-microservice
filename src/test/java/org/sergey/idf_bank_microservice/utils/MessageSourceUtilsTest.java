package org.sergey.idf_bank_microservice.utils;

import org.junit.jupiter.api.Test;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;
import org.sergey.idf_bank_microservice.exception.EntityNotExistsException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.sergey.idf_bank_microservice.utils.MessageSourceUtils.resolveMessageSourceCode;

@SpringBootTest
@ActiveProfiles("test")
class MessageSourceUtilsTest {

    @Test
    void resolveMessageSourceCode_shouldReturnCorrectMessageSourceCode_whenPassNotApplicationExceptionWithValidParams() {
        String expected = "problem-detail.RuntimeException.detail";
        String actual = resolveMessageSourceCode("problem-detail", RuntimeException.class, "detail");
        assertEquals(expected, actual);
    }

    @Test
    void resolveMessageSourceCode_shouldReturnCorrectMessageSourceCode_whenPassEntityNotExistsExceptionWithValidParams() {
        EntityNotExistsException testException = new EntityNotExistsException(GeneralEntity.class, "id");
        String expected = "problem-detail.GeneralEntity.EntityNotExistsException.detail";
        String actual = resolveMessageSourceCode("problem-detail", GeneralEntity.class,
                                                 testException.getClass(), "detail");
        assertEquals(expected, actual);
    }

    @Test
    void resolveMessageSourceCode_throwsIllegalArgumentException_whenPassNullArguments() {
        assertAll(() -> {
            assertThrows(IllegalArgumentException.class,
                         () -> resolveMessageSourceCode(null, RuntimeException.class, "detail"));
            assertThrows(IllegalArgumentException.class,
                         () -> resolveMessageSourceCode("problem-detail", null, "detail"));
            assertThrows(IllegalArgumentException.class,
                         () -> resolveMessageSourceCode("problem-detail", RuntimeException.class, null));
        });
    }
}