package org.sergey.idf_bank_microservice.exception;

import lombok.Getter;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;

@Getter
public class EntityNotExistsException extends EntityRelatedException {
    private String lookupFieldValue;

    public EntityNotExistsException(Class<? extends GeneralEntity> entityClass, String lookupFieldValue) {
        super(entityClass);
        this.lookupFieldValue = lookupFieldValue;
    }


}
