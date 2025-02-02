package org.sergey.idf_bank_microservice.expenselimit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sergey.idf_bank_microservice.bankaccount.BankAccount;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.debittransaction.DebitTransaction;
import org.sergey.idf_bank_microservice.expensecategory.ExpenseCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.time.OffsetDateTime.now;
import static java.time.OffsetDateTime.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class ExpenseLimitServiceTest {
    private ExpenseCategory productCategory;
    private BankAccount clientBankAccount;
    private Currency counterpartyCurrency;
    private BankAccount counterpartyBankAccount;
    @Autowired
    private ExpenseLimitService limitService;

    @BeforeEach
    void setUp() {
        productCategory = ExpenseCategory.builder().id(1).value("product").build();
        Currency clientCurrency = Currency.builder().id(3).alphaCode("KZT").build();
        clientBankAccount = BankAccount.builder().id(1).number(7984616).currency(clientCurrency).build();
        counterpartyCurrency = Currency.builder().id(1).alphaCode("USD").build();
        counterpartyBankAccount = BankAccount.builder().id(2).number(3596478).currency(counterpartyCurrency).build();
    }

    @Test
    void givenFirstTransaction_whenCheckLimitExceeded_thenLimitIsNotExceeded() {
        List<DebitTransaction> prevTransactions = List.of();
        assertFalse(limitService.isLimitExceeded(createLimit(1000, LocalDate.of(2022, 1, 1)),
                                                 createTransaction(500, LocalDate.of(2022, 1, 2)),
                                                 prevTransactions));
    }

    private ExpenseLimit createLimit(double value, LocalDate dateTime) {
        return ExpenseLimit.builder()
                           .createdAt(of(dateTime, LocalTime.now(), ZoneOffset.UTC))
                           .bankAccount(clientBankAccount)
                           .expenseCategory(productCategory)
                           .value(valueOf(value))
                           .build();
    }

    private DebitTransaction createTransaction(double convertedAmount, LocalDate date) {
        return DebitTransaction.builder()
                               .id(System.currentTimeMillis())
                               .createdAt(now())
                               .clientBankAccount(clientBankAccount)
                               .counterpartyBankAccount(counterpartyBankAccount)
                               .currency(counterpartyCurrency)
                               .amount(valueOf(convertedAmount / 0.51838))
                               .convertedAmount(valueOf(convertedAmount))
                               .expenseCategory(productCategory)
                               .dateTime(of(date, LocalTime.now(), ZoneOffset.UTC))
                               .build();
    }


    @Test
    void givenPrevTransactionsSum500AndCurrentSum600WithLimit1000_whenCheckLimitExceeded_thenLimitIsExceeded() {
        List<DebitTransaction> prevTransactions = List.of(createTransaction(500, LocalDate.of(2022, 1, 2)));
        assertTrue(limitService.isLimitExceeded(createLimit(1000, LocalDate.of(2022, 1, 10)),
                                                createTransaction(600, LocalDate.of(2022, 1, 3)),
                                                prevTransactions));
    }

    @Test
    void givenPrevTransactionsSum1100AndCurrentSum100WithLimit2000_whenCheckLimitExceeded_thenLimitIsNotExceeded() {
        List<DebitTransaction> prevTransactions = List.of(createTransaction(500, LocalDate.of(2022, 1, 2)),
                                                          createTransaction(600, LocalDate.of(2022, 1, 3)));
        assertFalse(limitService.isLimitExceeded(createLimit(2000, LocalDate.of(2022, 1, 10)),
                                                 createTransaction(100, LocalDate.of(2022, 1, 11)),
                                                 prevTransactions));
    }

    @Test
    void givenPrevTransactionsSum1200AndCurrentSum700WithLimit2000_whenCheckLimitExceeded_thenLimitIsNotExceeded() {
        List<DebitTransaction> prevTransactions = List.of(createTransaction(500, LocalDate.of(2022, 1, 2)),
                                                          createTransaction(600, LocalDate.of(2022, 1, 3)),
                                                          createTransaction(100, LocalDate.of(2022, 1, 11)));
        assertFalse(limitService.isLimitExceeded(createLimit(2000, LocalDate.of(2022, 1, 10)),
                                                 createTransaction(700, LocalDate.of(2022, 1, 12)),
                                                 prevTransactions));
    }

    @Test
    void givenPrevTransactionsSum1900AndCurrentSum100WithLimit2000_whenCheckLimitExceeded_thenLimitIsNotExceeded() {
        List<DebitTransaction> prevTransactions = List.of(createTransaction(500, LocalDate.of(2022, 1, 2)),
                                                          createTransaction(600, LocalDate.of(2022, 1, 3)),
                                                          createTransaction(100, LocalDate.of(2022, 1, 11)),
                                                          createTransaction(700, LocalDate.of(2022, 1, 12)));
        assertFalse(limitService.isLimitExceeded(createLimit(2000, LocalDate.of(2022, 1, 10)),
                                                 createTransaction(100, LocalDate.of(2022, 1, 13)),
                                                 prevTransactions));
    }

    @Test
    void givenPrevTransactionsSum2000AndCurrentSum100WithLimit2000_whenCheckLimitExceeded_thenLimitIsExceeded() {
        List<DebitTransaction> prevTransactions = List.of(createTransaction(500, LocalDate.of(2022, 1, 2)),
                                                          createTransaction(600, LocalDate.of(2022, 1, 3)),
                                                          createTransaction(100, LocalDate.of(2022, 1, 11)),
                                                          createTransaction(700, LocalDate.of(2022, 1, 12)),
                                                          createTransaction(100, LocalDate.of(2022, 1, 13)));
        assertTrue(limitService.isLimitExceeded(createLimit(2000, LocalDate.of(2022, 1, 10)),
                                                createTransaction(100, LocalDate.of(2022, 1, 13)),
                                                prevTransactions));
    }

    @Test
    void givenPrevTransactionsSum0AndCurrentSum500WithLimit1000_whenCheckLimitExceeded_thenLimitIsNotExceeded() {
        List<DebitTransaction> prevTransactions = List.of();
        assertFalse(limitService.isLimitExceeded(createLimit(1000, LocalDate.of(2022, 2, 1)),
                                                 createTransaction(500, LocalDate.of(2022, 1, 2)),
                                                 prevTransactions));
    }

    @Test
    void givenPrevTransactionsSum500AndCurrentSum100WithLimit1000_whenCheckLimitExceeded_thenLimitIsNotExceeded() {
        List<DebitTransaction> prevTransactions = List.of(createTransaction(500, LocalDate.of(2022, 1, 2)));
        assertFalse(limitService.isLimitExceeded(createLimit(1000, LocalDate.of(2022, 2, 1)),
                                                 createTransaction(100, LocalDate.of(2022, 1, 3)),
                                                 prevTransactions));
    }

    @Test
    void givenPrevTransactionsSum600AndCurrentSum100WithLimit400_whenCheckLimitExceeded_thenLimitIsExceeded() {
        List<DebitTransaction> prevTransactions = List.of(createTransaction(500, LocalDate.of(2022, 1, 2)),
                                                          createTransaction(100, LocalDate.of(2022, 1, 3)));

        assertTrue(limitService.isLimitExceeded(createLimit(400, LocalDate.of(2022, 2, 10)),
                                                createTransaction(100, LocalDate.of(2022, 1, 11)),
                                                prevTransactions));
    }

    @Test
    void givenPrevTransactionsSum700AndCurrentSum100WithLimit400_whenCheckLimitExceeded_thenLimitIsExceeded() {
        List<DebitTransaction> prevTransactions = List.of(createTransaction(500, LocalDate.of(2022, 1, 2)),
                                                          createTransaction(100, LocalDate.of(2022, 1, 3)),
                                                          createTransaction(100, LocalDate.of(2022, 1, 11)));
        assertTrue(limitService.isLimitExceeded(createLimit(400, LocalDate.of(2022, 2, 10)),
                                                createTransaction(100, LocalDate.of(2022, 1, 12)),
                                                prevTransactions));
    }
}