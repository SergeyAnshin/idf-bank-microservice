package org.sergey.idf_bank_microservice.entitypersister;

import org.sergey.idf_bank_microservice.entity.GeneralEntity;

import java.util.Optional;
import java.util.function.Function;

public interface EntityPersistenceService<E extends GeneralEntity> {

    E persist(E entity);

    <P> E persist(E entity, Function<P, Optional<E>> findFunction, P findFunctionParam);

}
