package org.sergey.idf_bank_microservice.debittransaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitTransactionRepository extends JpaRepository<DebitTransaction, Long> {

}
