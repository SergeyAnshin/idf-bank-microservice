package org.sergey.idf_bank_microservice.debittransaction;

import lombok.Getter;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;
import org.sergey.idf_bank_microservice.exception.EntityRelatedException;

@Getter
public class TransactionRegistrationException extends EntityRelatedException {
    private final DebitTransaction debitTransaction;

    public TransactionRegistrationException(String message,
                                            Throwable cause,
                                            Class<? extends GeneralEntity> entityClass,
                                            DebitTransaction debitTransaction) {
        super(message, cause, entityClass);
        this.debitTransaction = debitTransaction;
    }

}
