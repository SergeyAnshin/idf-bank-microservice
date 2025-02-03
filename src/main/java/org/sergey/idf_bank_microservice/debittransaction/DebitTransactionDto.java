package org.sergey.idf_bank_microservice.debittransaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.sergey.idf_bank_microservice.currency.validation.AlphabeticCurrencyCode;
import org.sergey.idf_bank_microservice.currency.validation.SupportedCurrency;
import org.sergey.idf_bank_microservice.expensecategory.ExistingExpenseCategory;
import org.sergey.idf_bank_microservice.validation.OffsetDateTimeFormat;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class DebitTransactionDto {
    @JsonIgnore
    private long id;

    @JsonProperty("account_from")
    @Positive(groups = { NewDebitTransactionChecks.class, TransactionsWithExceededLimitGroup.class })
    private int accountFrom;

    @JsonProperty("account_to")
    @Positive(groups = { NewDebitTransactionChecks.class })
    private int accountTo;

    @JsonProperty("currency_shortname")
    @NotNull(groups = NewDebitTransactionChecks.class, message = "{validator.RequiredField.message}")
    @Size(min = 3, max = 3)
    @AlphabeticCurrencyCode(groups = NewDebitTransactionChecks.class)
    @SupportedCurrency(groups = NewDebitTransactionChecks.class)
    private String currencyShortname;

    @JsonProperty("sum")
    @NotNull(groups = NewDebitTransactionChecks.class, message = "{validator.RequiredField.message}")
    @Positive(groups = NewDebitTransactionChecks.class)
    private BigDecimal sum;

    @JsonProperty("expense_category")
    @NotNull(groups = NewDebitTransactionChecks.class, message = "{validator.RequiredField.message}")
    @ExistingExpenseCategory(groups = NewDebitTransactionChecks.class)
    private String expenseCategory;

    @JsonProperty("datetime")
    @NotNull(groups = NewDebitTransactionChecks.class, message = "{validator.RequiredField.message}")
    @OffsetDateTimeFormat(groups = NewDebitTransactionChecks.class)
    private String dateTime;

    @JsonProperty("limit_sum")
    private BigDecimal limitSum;

    @JsonProperty("limit_datetime")
    private String limitDateTime;

    @JsonProperty("limit_currency_shortname")
    private String limitCurrencyShortname;
}
