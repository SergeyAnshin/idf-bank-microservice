package org.sergey.idf_bank_microservice.debittransaction;

import org.sergey.idf_bank_microservice.expenselimit.ExpenseLimit;
import org.sergey.idf_bank_microservice.mapper.DateTimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
public abstract class DebitTransactionMapperDecorator implements DebitTransactionMapper {
    @Autowired
    private DebitTransactionMapper delegate;
    @Autowired
    private DateTimeMapper dateTimeMapper;

    @Override
    public DebitTransactionDto toDebitTransactionDto(DebitTransaction debitTransaction) {
        DebitTransactionDto debitTransactionDto = delegate.toDebitTransactionDto(debitTransaction);
        fillLimitData(debitTransaction, debitTransactionDto);
        return debitTransactionDto;
    }

    @Override
    public List<DebitTransactionDto> toDebitTransactionDtoList(List<DebitTransaction> debitTransactionList) {
        return debitTransactionList.stream().map(this::toDebitTransactionDto).toList();
    }

    protected void fillLimitData(DebitTransaction debitTransaction, DebitTransactionDto debitTransactionDto) {
        debitTransaction.getClientBankAccount()
                        .getExpenseLimits()
                        .stream()
                        .distinct()
                        .sorted(Comparator.comparing(ExpenseLimit::getCreatedAt).reversed())
                        .filter(limit -> Objects.equals(limit.getExpenseCategory(),
                                                        debitTransaction.getExpenseCategory())
                                && limit.getCreatedAt().isBefore(debitTransaction.getDateTime()))
                        .findFirst()
                        .ifPresent(limit -> {
                            debitTransactionDto.setLimitSum(limit.getValue());
                            debitTransactionDto.setLimitDateTime(dateTimeMapper.toStringOffsetDateTime(limit.getCreatedAt()));
                            debitTransactionDto.setLimitCurrencyShortname(debitTransaction.getClientBankAccount()
                                                                                          .getCurrency()
                                                                                          .getAlphaCode());
                        });
    }
}
