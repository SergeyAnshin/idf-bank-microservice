package org.sergey.idf_bank_microservice.debittransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.sergey.idf_bank_microservice.currency.validation.AlphabeticCurrencyCode;
import org.sergey.idf_bank_microservice.currency.validation.SupportedCurrency;
import org.sergey.idf_bank_microservice.expensecategory.ExistingExpenseCategory;
import org.sergey.idf_bank_microservice.validation.OffsetDateTimeFormat;

import java.math.BigDecimal;

public record DebitTransactionDto(@JsonProperty("account_from")
                                  @Positive(groups = NewDebitTransactionChecks.class)
                                  int accountFrom,
                                  @JsonProperty("account_to")
                                  @Positive(groups = NewDebitTransactionChecks.class)
                                  int accountTo,
                                  @JsonProperty("currency_shortname")
                                  @NotNull(groups = NewDebitTransactionChecks.class,
                                          message = "{validator.RequiredField.message}")
                                  @Size(min = 3, max = 3)
                                  @AlphabeticCurrencyCode(groups = NewDebitTransactionChecks.class)
                                  @SupportedCurrency(groups = NewDebitTransactionChecks.class)
                                  String currencyShortname,
                                  @JsonProperty("sum")
                                  @NotNull(groups = NewDebitTransactionChecks.class,
                                          message = "{validator.RequiredField.message}")
                                  @Positive(groups = NewDebitTransactionChecks.class)
                                  BigDecimal sum,
                                  @JsonProperty("expense_category")
                                  @NotNull(groups = NewDebitTransactionChecks.class,
                                          message = "{validator.RequiredField.message}")
                                  @ExistingExpenseCategory(groups = NewDebitTransactionChecks.class)
                                  String expenseCategory,
                                  @JsonProperty("datetime")
                                  @NotNull(groups = NewDebitTransactionChecks.class,
                                          message = "{validator.RequiredField.message}")
                                  @OffsetDateTimeFormat(groups = NewDebitTransactionChecks.class)
                                  String dateTime) {
}
