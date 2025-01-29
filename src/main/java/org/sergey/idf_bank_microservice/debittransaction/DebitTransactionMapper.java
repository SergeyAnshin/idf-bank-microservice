package org.sergey.idf_bank_microservice.debittransaction;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sergey.idf_bank_microservice.mapper.DateTimeMapper;

@Mapper(uses = {DateTimeMapper.class})
public interface DebitTransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "clientBankAccount.number", source = "accountFrom")
    @Mapping(target = "counterpartyBankAccount.number", source = "accountTo")
    @Mapping(target = "currency.alphaCode", source = "currencyShortname")
    @Mapping(target = "amount", source = "sum")
    @Mapping(target = "convertedAmount", ignore = true)
    @Mapping(target = "expenseCategory.value", source = "expenseCategory")
    DebitTransaction toDebitTransaction(DebitTransactionDto debitTransactionDto);

    SuccessfulDebitTransactionDto toSuccessfulDebitTransactionDto(DebitTransaction debitTransactionDto);

}
