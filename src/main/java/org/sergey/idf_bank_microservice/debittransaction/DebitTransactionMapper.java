package org.sergey.idf_bank_microservice.debittransaction;


import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sergey.idf_bank_microservice.mapper.DateTimeMapper;

import java.util.List;

@DecoratedWith(DebitTransactionMapperDecorator.class)
@Mapper(uses = { DateTimeMapper.class })
public interface DebitTransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "clientBankAccount.number", source = "accountFrom")
    @Mapping(target = "counterpartyBankAccount.number", source = "accountTo")
    @Mapping(target = "currency.alphaCode", source = "currencyShortname")
    @Mapping(target = "amount", source = "sum")
    @Mapping(target = "convertedAmount", ignore = true)
    @Mapping(target = "expenseCategory.value", source = "expenseCategory")
    @Mapping(target = "isLimitExceeded", ignore = true)
    DebitTransaction toDebitTransaction(DebitTransactionDto debitTransactionDto);

    @InheritInverseConfiguration
    DebitTransactionDto toDebitTransactionDto(DebitTransaction debitTransaction);

    List<DebitTransactionDto> toDebitTransactionDtoList(List<DebitTransaction> debitTransactionList);

    SuccessfulDebitTransactionDto toSuccessfulDebitTransactionDto(DebitTransaction debitTransactionDto);

}
