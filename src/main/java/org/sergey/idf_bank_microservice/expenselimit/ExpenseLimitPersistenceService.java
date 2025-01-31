package org.sergey.idf_bank_microservice.expenselimit;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.bankaccount.BankAccount;
import org.sergey.idf_bank_microservice.bankaccount.BankAccountPersistenceService;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.currency.CurrencyPersistenceService;
import org.sergey.idf_bank_microservice.entitypersister.BaseEntityPersistenceService;
import org.sergey.idf_bank_microservice.expensecategory.ExpenseCategory;
import org.sergey.idf_bank_microservice.expensecategory.ExpenseCategoryPersistenceService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class ExpenseLimitPersistenceService extends BaseEntityPersistenceService<ExpenseLimit> {
    private final ExpenseCategoryPersistenceService expenseCategoryPersistenceService;
    private final CurrencyPersistenceService currencyPersistenceService;
    private final BankAccountPersistenceService bankAccountPersistenceService;

    @Override
    public ExpenseLimit persist(ExpenseLimit expenseLimit) {
        ExpenseCategory expenseCategory = expenseCategoryPersistenceService.persist(expenseLimit.getExpenseCategory());
        Currency accountCurrency = currencyPersistenceService.persist(expenseLimit.getCurrency());
        BankAccount bankAccount = bankAccountPersistenceService.persist(expenseLimit.getBankAccount());
        return expenseLimit.toBuilder()
                           .expenseCategory(expenseCategory)
                           .currency(accountCurrency)
                           .bankAccount(bankAccount)
                           .build();
    }
}
