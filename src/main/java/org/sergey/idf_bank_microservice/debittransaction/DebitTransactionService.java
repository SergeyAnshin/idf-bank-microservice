package org.sergey.idf_bank_microservice.debittransaction;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.currencyconverter.ConversionData;
import org.sergey.idf_bank_microservice.currencyconverter.CurrencyConverter;
import org.sergey.idf_bank_microservice.entitypersister.EntityPersistenceService;
import org.sergey.idf_bank_microservice.expenselimit.ExpenseLimit;
import org.sergey.idf_bank_microservice.expenselimit.ExpenseLimitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.sergey.idf_bank_microservice.currency.CurrencyUtils.haveSameAlphaCode;

@RequiredArgsConstructor

@Service
public class DebitTransactionService {
    private static final Logger logger = LoggerFactory.getLogger(DebitTransactionService.class);
    private final DebitTransactionRepository transactionRepository;
    private final DebitTransactionMapper debitTransactionMapper;
    private final EntityPersistenceService<DebitTransaction> debitTransactionPersistenceService;
    private final CurrencyConverter currencyConverter;
    private final ExpenseLimitService expenseLimitService;

    @Transactional
    public SuccessfulDebitTransactionDto register(DebitTransactionDto debitTransactionDto) {
        DebitTransaction transaction = debitTransactionMapper.toDebitTransaction(debitTransactionDto);
        DebitTransaction persistedTransaction = debitTransactionPersistenceService.persist(transaction);
        try {
            convertTransactionAmount(persistedTransaction);
            applyLimitExceededStatus(persistedTransaction);
            DebitTransaction savedDebitTransaction = transactionRepository.save(persistedTransaction);
            return debitTransactionMapper.toSuccessfulDebitTransactionDto(savedDebitTransaction);
        } catch (Exception e) {
            logger.error("Error registering debit transaction: {}", transaction);
            throw new TransactionRegistrationException("Failed to register debit transaction",
                                                       e,
                                                       DebitTransaction.class,
                                                       transaction);
        }
    }

    private void convertTransactionAmount(DebitTransaction persistedTransaction) {
        Currency accountCurrency = persistedTransaction.getClientBankAccount().getCurrency();
        Currency transactionCurrency = persistedTransaction.getCurrency();
        if (haveSameAlphaCode(accountCurrency, transactionCurrency)) {
            persistedTransaction.setConvertedAmount(persistedTransaction.getAmount());
        } else {
            ConversionData conversionData = new ConversionData(accountCurrency,
                                                               transactionCurrency,
                                                               persistedTransaction.getAmount());
            BigDecimal convertedAmount = currencyConverter.convert(conversionData);
            persistedTransaction.setConvertedAmount(convertedAmount);
        }
    }

    private void applyLimitExceededStatus(DebitTransaction persistedTransaction) {
        long bankAccountId = persistedTransaction.getClientBankAccount().getId();
        long expenseCategoryId = persistedTransaction.getExpenseCategory().getId();
        ExpenseLimit expenseLimit
                = expenseLimitService.findLastLimitBy(bankAccountId, expenseCategoryId)
                                     .orElseThrow(() -> {
                                         logger.error("Expense limit not found for accountId: {} and categoryId: {}",
                                                      bankAccountId,
                                                      expenseCategoryId);
                                         return new IllegalArgumentException("Expense limit not found");
                                     });
        boolean isLimitExceeded
                = expenseLimitService.isLimitExceeded(expenseLimit,
                                                      persistedTransaction,
                                                      transactionRepository.findAllByClientBankAccountId(bankAccountId));
        persistedTransaction.setLimitExceeded(isLimitExceeded);
    }

    public List<DebitTransactionDto> findByExceededLimit(DebitTransactionDto debitTransactionDto) {
        List<DebitTransaction> transactions
                = transactionRepository.findByExceededLimit(debitTransactionDto.getAccountFrom());
        return debitTransactionMapper.toDebitTransactionDtoList(transactions);
    }
}
