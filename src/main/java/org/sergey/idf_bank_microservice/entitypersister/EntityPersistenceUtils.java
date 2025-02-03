package org.sergey.idf_bank_microservice.entitypersister;

import org.sergey.idf_bank_microservice.entity.GeneralEntity;

public class EntityPersistenceUtils {

    public static boolean nonPersist(GeneralEntity entity) {
        return entity.getId() <= 0;
    }

}
