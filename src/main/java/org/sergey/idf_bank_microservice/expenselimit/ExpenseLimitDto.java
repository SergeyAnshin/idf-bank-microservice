package org.sergey.idf_bank_microservice.expenselimit;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.sergey.idf_bank_microservice.currency.validation.AlphabeticCurrencyCode;
import org.sergey.idf_bank_microservice.currency.validation.SupportedCurrency;
import org.sergey.idf_bank_microservice.expensecategory.ExistingExpenseCategory;

import java.math.BigDecimal;

public record ExpenseLimitDto(long id,
                              @NotNull(message = "{validator.RequiredField.message}")
                              @Positive
                              BigDecimal limit,
                              @JsonProperty("expense_category")
                              @NotNull(message = "{validator.RequiredField.message}")
                              @ExistingExpenseCategory
                              String expenseCategory,
                              @JsonProperty("currency_shortname")
                              @NotNull(message = "{validator.RequiredField.message}")
                              @Size(min = 3, max = 3)
                              @AlphabeticCurrencyCode
                              @SupportedCurrency
                              String currencyShortname,
                              @JsonProperty("account_to")
                              @Positive
                              int accountTo) {
}