package org.sergey.idf_bank_microservice.expenselimit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface ExpenseLimitRepository extends JpaRepository<ExpenseLimit, Long> {

    @Query("""
            SELECT el
            FROM ExpenseLimit el
            WHERE el.createdAt = :createdAt
                AND el.value = :value
                AND el.expenseCategory.id = :categoryId
                AND el.bankAccount.id = :bankAccountId
            """)
    Optional<ExpenseLimit> findBy(@Param("createdAt") OffsetDateTime createdAt,
                                  @Param("value") BigDecimal value,
                                  @Param("categoryId") Long categoryId,
                                  @Param("bankAccountId") Long bankAccountId);

    @Query("""
            SELECT el
            FROM ExpenseLimit el
            WHERE el.bankAccount.id = :bankAccountId
                AND el.expenseCategory.id = :expenseCategoryId
            ORDER BY el.createdAt DESC
            LIMIT 1
            """)
    Optional<ExpenseLimit> findLastLimitBy(long bankAccountId, long expenseCategoryId);
}
