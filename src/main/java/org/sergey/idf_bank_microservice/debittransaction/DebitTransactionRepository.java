package org.sergey.idf_bank_microservice.debittransaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebitTransactionRepository extends JpaRepository<DebitTransaction, Long> {

    List<DebitTransaction> findAllByClientBankAccountId(long id);

}
