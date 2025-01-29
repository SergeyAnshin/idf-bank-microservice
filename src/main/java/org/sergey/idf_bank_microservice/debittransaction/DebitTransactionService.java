package org.sergey.idf_bank_microservice.debittransaction;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.currencyconverter.CurrencyConverter;
import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;
import org.sergey.idf_bank_microservice.entitypersister.EntityPersistenceService;
import org.sergey.idf_bank_microservice.exchangerate.ExchangeRate;
import org.sergey.idf_bank_microservice.exchangerate.impl.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor

@Service
public class DebitTransactionService {
    private static final Logger logger = LoggerFactory.getLogger(DebitTransactionService.class);
    private final DebitTransactionRepository debitTransactionRepository;
    private final DebitTransactionMapper debitTransactionMapper;
    private final EntityPersistenceService<DebitTransaction> debitTransactionPersistenceService;
    private final CurrencyConverter currencyConverter;
    private final ExchangeRateService exchangeRateService;

    @Transactional
    public SuccessfulDebitTransactionDto register(DebitTransactionDto debitTransactionDto) {
        requireNonNull(debitTransactionDto, "DebitTransactionDto must not be null");
        DebitTransaction transaction = debitTransactionMapper.toDebitTransaction(debitTransactionDto);
        DebitTransaction persistedTransaction = debitTransactionPersistenceService.persist(transaction);
        try {
            updateTransactionWithConvertedAmount(persistedTransaction);
            DebitTransaction savedDebitTransaction = debitTransactionRepository.save(persistedTransaction);
            return debitTransactionMapper.toSuccessfulDebitTransactionDto(savedDebitTransaction);
        } catch (Exception e) {
            logger.error("Error registering debit transaction: {}", transaction);
            throw new TransactionRegistrationException("Failed to register debit transaction",
                                                       e,
                                                       DebitTransaction.class,
                                                       transaction);
        }
    }

    private void updateTransactionWithConvertedAmount(DebitTransaction persistedTransaction) {
        Currency accountCurrency = persistedTransaction.getClientBankAccount().getCurrency();
        Currency transactionCurrency = persistedTransaction.getCurrency();
        if (Objects.equals(accountCurrency.getAlphaCode(), transactionCurrency.getAlphaCode())) {
            persistedTransaction.setConvertedAmount(persistedTransaction.getAmount());
        } else {
            CurrencyPair currencyPair = CurrencyPair.builder()
                                                    .buyCurrency(accountCurrency)
                                                    .sellCurrency(transactionCurrency)
                                                    .build();
            ExchangeRate latestRate = exchangeRateService.findLatestRate(currencyPair);
            BigDecimal convertedAmount = currencyConverter.convert(persistedTransaction.getAmount(), latestRate);
            persistedTransaction.setConvertedAmount(convertedAmount);
        }
    }

}
