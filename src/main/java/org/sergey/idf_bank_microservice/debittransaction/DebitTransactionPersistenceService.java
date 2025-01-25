package org.sergey.idf_bank_microservice.debittransaction;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.bankaccount.BankAccount;
import org.sergey.idf_bank_microservice.bankaccount.BankAccountPersistenceService;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.currency.CurrencyPersistenceService;
import org.sergey.idf_bank_microservice.entitypersister.BaseEntityPersistenceService;
import org.sergey.idf_bank_microservice.expensecategory.ExpenseCategory;
import org.sergey.idf_bank_microservice.expensecategory.ExpenseCategoryPersistenceService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor

@Component
public class DebitTransactionPersistenceService extends BaseEntityPersistenceService<DebitTransaction> {
    private final BankAccountPersistenceService bankAccountPersistenceService;
    private final CurrencyPersistenceService currencyPersistenceService;
    private final ExpenseCategoryPersistenceService expenseCategoryPersistenceService;

    @Override
    @Transactional
    public DebitTransaction persist(DebitTransaction debitTransaction) {
        BankAccount clientBankAccount = bankAccountPersistenceService.persist(debitTransaction.getClientBankAccount());
        BankAccount counterpartyBankAccount = bankAccountPersistenceService.persist(
                debitTransaction.getCounterpartyBankAccount());
        Currency accountCurrency = currencyPersistenceService.persist(debitTransaction.getAccountCurrency());
        ExpenseCategory expenseCategory = expenseCategoryPersistenceService.persist(
                debitTransaction.getExpenseCategory());
        return debitTransaction.toBuilder()
                               .clientBankAccount(clientBankAccount)
                               .counterpartyBankAccount(counterpartyBankAccount)
                               .accountCurrency(accountCurrency)
                               .expenseCategory(expenseCategory)
                               .build();
    }


}
