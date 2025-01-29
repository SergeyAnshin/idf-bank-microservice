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
public class EntityNotExistsException extends EntityRelatedException {
    private String lookupFieldValue;

    public EntityNotExistsException(Class<? extends GeneralEntity> entityClass, String lookupFieldValue) {
        super(entityClass);
        this.lookupFieldValue = lookupFieldValue;
    }
}
