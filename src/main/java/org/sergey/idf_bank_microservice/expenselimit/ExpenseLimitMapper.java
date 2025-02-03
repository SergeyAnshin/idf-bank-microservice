package org.sergey.idf_bank_microservice.expenselimit;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ExpenseLimitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "value", source = "limit")
    @Mapping(target = "expenseCategory.value", source = "expenseCategory")
    @Mapping(target = "currency.alphaCode", source = "currencyShortname")
    @Mapping(target = "bankAccount.number", source = "accountTo")
    ExpenseLimit toExpenseLimit(ExpenseLimitDto expenseLimitDto);

    @InheritInverseConfiguration
    ExpenseLimitDto toExpenseLimitDto(ExpenseLimit debitTransactionDto);

}
