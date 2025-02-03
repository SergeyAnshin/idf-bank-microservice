package org.sergey.idf_bank_microservice.bankaccount;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.entitypersister.BaseEntityPersistenceService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor

@Component
public class BankAccountPersistenceService extends BaseEntityPersistenceService<BankAccount> {
    private final BankAccountRepository bankAccountRepository;

    @Override
    @Transactional
    public BankAccount persist(BankAccount bankAccount) {
        return super.persist(bankAccount, bankAccountRepository::findByNumber, bankAccount.getNumber());
    }
}
