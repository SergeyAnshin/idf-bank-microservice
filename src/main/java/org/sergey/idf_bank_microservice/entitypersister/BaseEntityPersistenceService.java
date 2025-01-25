package org.sergey.idf_bank_microservice.entitypersister;

import org.sergey.idf_bank_microservice.exception.EntityNotFoundException;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static org.sergey.idf_bank_microservice.entitypersister.EntityPersistenceUtils.nonPersist;

public abstract class BaseEntityPersistenceService<E extends GeneralEntity> implements EntityPersistenceService<E> {

    @Override
    @Transactional
    public <P> E persist(E entity, Function<P, Optional<E>> findFunction, P findFunctionParam) {
        if (nonNull(entity) && nonPersist(entity)) {
            Optional<E> existingEntity = findFunction.apply(findFunctionParam);
            if (existingEntity.isPresent()) {
                return existingEntity.get();
            } else {
                throw new EntityNotFoundException(
                        String.format("Entity with type %s not found by params %s", entity.getClass().getSimpleName(),
                                      findFunctionParam));
            }
        }
        return entity;
    }
}
