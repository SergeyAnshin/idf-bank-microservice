package org.sergey.idf_bank_microservice.debittransaction;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sergey.idf_bank_microservice.bankaccount.BankAccount;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;
import org.sergey.idf_bank_microservice.expensecategory.ExpenseCategory;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static jakarta.persistence.CascadeType.PERSIST;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "debit_transaction_id"))
})
public class DebitTransaction extends GeneralEntity {
    @ManyToOne(cascade = PERSIST, optional = false)
    @JoinColumn(name = "client_account_id", nullable = false, updatable = false)
    private BankAccount clientBankAccount;
    @ManyToOne(cascade = PERSIST, optional = false)
    @JoinColumn(name = "counterparty_account_id", nullable = false, updatable = false)
    private BankAccount counterpartyBankAccount;
    @ManyToOne(cascade = PERSIST, optional = false)
    @JoinColumn(name = "account_currency_id", nullable = false, updatable = false)
    private Currency currency;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    @ManyToOne(cascade = PERSIST, optional = false)
    @JoinColumn(name = "expense_category_id", nullable = false, updatable = false)
    private ExpenseCategory expenseCategory;
    private OffsetDateTime dateTime;
    @Column(name = "limit_exceeded")
    private boolean isLimitExceeded;

}
