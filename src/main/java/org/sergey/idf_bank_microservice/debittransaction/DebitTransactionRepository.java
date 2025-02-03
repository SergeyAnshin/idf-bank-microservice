package org.sergey.idf_bank_microservice.debittransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebitTransactionRepository extends JpaRepository<DebitTransaction, Long> {

    List<DebitTransaction> findAllByClientBankAccountId(long id);

    @Query("""
            SELECT dt
            FROM DebitTransaction dt
            JOIN FETCH dt.clientBankAccount ba
            JOIN FETCH dt.counterpartyBankAccount cba
            JOIN FETCH dt.expenseCategory ec
            JOIN FETCH dt.currency c
            JOIN FETCH ba.expenseLimits el
            WHERE dt.clientBankAccount.number = :bankAccountNumber
                AND dt.isLimitExceeded = TRUE
            """)
    List<DebitTransaction> findByExceededLimit(@Param("bankAccountNumber") long bankAccountNumber);

}
