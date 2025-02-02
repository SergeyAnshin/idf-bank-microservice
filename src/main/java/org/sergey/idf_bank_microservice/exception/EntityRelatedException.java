package org.sergey.idf_bank_microservice.exception;

import lombok.Getter;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;

@Getter
public class EntityRelatedException extends ApplicationException {
    private Class<? extends GeneralEntity> entityClass;

    public EntityRelatedException(String message) {
        super(message);
    }

    public EntityRelatedException(Class<? extends GeneralEntity> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityRelatedException(String message, Class<? extends GeneralEntity> entityClass) {
        super(message);
        this.entityClass = entityClass;
    }

    public EntityRelatedException(String message, Throwable cause, Class<? extends GeneralEntity> entityClass) {
        super(message, cause);
        this.entityClass = entityClass;
    }
}
