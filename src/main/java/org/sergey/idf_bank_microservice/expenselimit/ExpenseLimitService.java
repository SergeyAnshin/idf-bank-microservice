package org.sergey.idf_bank_microservice.expenselimit;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.currencyconverter.ConversionData;
import org.sergey.idf_bank_microservice.currencyconverter.CurrencyConverter;
import org.sergey.idf_bank_microservice.debittransaction.DebitTransaction;
import org.sergey.idf_bank_microservice.entitypersister.EntityPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.sergey.idf_bank_microservice.currency.CurrencyUtils.haveSameAlphaCode;

@RequiredArgsConstructor

@Service
public class ExpenseLimitService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseLimitService.class);
    public final ExpenseLimitMapper limitMapper;
    public final ExpenseLimitRepository limitRepository;
    public final EntityPersistenceService<ExpenseLimit> limitPersistenceService;
    public final CurrencyConverter currencyConverter;

    @Transactional
    public ExpenseLimitDto setExpenseLimit(ExpenseLimitDto expenseLimitDto) {
        try {
            ExpenseLimit expenseLimit = limitMapper.toExpenseLimit(expenseLimitDto);
            ExpenseLimit persistedLimit = limitPersistenceService.persist(expenseLimit);

            updateExpenseLimitWithConvertedValue(persistedLimit, persistedLimit.getValue());

            Optional<ExpenseLimit> optionalExistingLimit = findExistingExpenseLimit(persistedLimit);
            ExpenseLimit existingLimit = optionalExistingLimit.orElseGet(() -> limitRepository.save(persistedLimit));
            return limitMapper.toExpenseLimitDto(existingLimit);
        } catch (Exception e) {
            logger.error("Error occurred while setting expense limit: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred while setting the expense limit.", e);
        }
    }

    private void updateExpenseLimitWithConvertedValue(@NotNull ExpenseLimit persistedLimit,
                                                      @NotNull @Min(0) BigDecimal limit) {
        Currency accountCurrency = persistedLimit.getBankAccount().getCurrency();
        ConversionData conversionData = new ConversionData(accountCurrency, persistedLimit.getCurrency(), limit);

        if (haveSameAlphaCode(accountCurrency, persistedLimit.getCurrency())) {
            persistedLimit.setValue(limit);
        } else {
            BigDecimal convertedLimit = currencyConverter.convert(conversionData);
            persistedLimit.setValue(convertedLimit);
        }
    }

    private Optional<ExpenseLimit> findExistingExpenseLimit(@NotNull ExpenseLimit expenseLimit) {
        return limitRepository.findBy(expenseLimit.getCreatedAt(),
                                      expenseLimit.getValue(),
                                      expenseLimit.getExpenseCategory().getId(),
                                      expenseLimit.getBankAccount().getId());
    }

    public boolean isLimitExceeded(@NotNull ExpenseLimit expenseLimit,
                                   @NotNull DebitTransaction currentTransaction,
                                   @NotNull List<DebitTransaction> previousTransactions) {
        BigDecimal prevTransactionsSum
                = previousTransactions.stream()
                                      .filter(transaction -> Objects.equals(transaction.getExpenseCategory(),
                                                                            expenseLimit.getExpenseCategory()))
                                      .map(DebitTransaction::getConvertedAmount)
                                      .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalSum = prevTransactionsSum.add(currentTransaction.getConvertedAmount());
        return totalSum.compareTo(expenseLimit.getValue()) > 0;
    }

    public Optional<ExpenseLimit> findLastLimitBy(@Min(1) long bankAccountId, @Min(1) long expenseCategoryId) {
        return limitRepository.findLastLimitBy(bankAccountId, expenseCategoryId);
    }
}
