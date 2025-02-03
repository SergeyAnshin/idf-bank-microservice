package org.sergey.idf_bank_microservice.expenselimit;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sergey.idf_bank_microservice.bankaccount.BankAccount;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;
import org.sergey.idf_bank_microservice.expensecategory.ExpenseCategory;

import java.math.BigDecimal;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_created_at_val_expense_category_id_bank_account_id",
                          columnNames = { "creates_at", "val", "expense_category_id", "bank_account_id" })
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "expense_limit_id"))
})
public class ExpenseLimit extends GeneralEntity {
    @Column(name = "val")
    private BigDecimal value = BigDecimal.valueOf(1000);
    @ManyToOne(optional = false)
    @JoinColumn(name = "expense_category_id", nullable = false, updatable = false)
    private ExpenseCategory expenseCategory;
    @Transient
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;
}
