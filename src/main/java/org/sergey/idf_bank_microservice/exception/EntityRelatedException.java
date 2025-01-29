package org.sergey.idf_bank_microservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntityRelatedException extends ApplicationException {
    private Class<? extends GeneralEntity> entityClass;

    public EntityRelatedException(String message, Throwable cause, Class<? extends GeneralEntity> entityClass) {
        super(message, cause);
        this.entityClass = entityClass;
    }
}
