package org.sergey.idf_bank_microservice.entitypersister;

import org.sergey.idf_bank_microservice.entity.GeneralEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface EntityPersistenceService<E extends GeneralEntity> {

    <P> E persist(E entity, Function<P, Optional<E>> findFunction, P findFunctionParam);

    @Transactional
    default List<E> persist(List<E> entities) {
        return entities.stream().map(this::persist).toList();
    }

    E persist(E entity);

}
