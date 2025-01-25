package org.sergey.idf_bank_microservice.expensecategory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor

@Component
public class ExistingExpenseCategoryValidator implements ConstraintValidator<ExistingExpenseCategory, String> {
    private final ExpenseCategoryService expenseCategoryService;

    @Override
    public boolean isValid(String expenseCategory, ConstraintValidatorContext context) {
        if (nonNull(expenseCategory)) {
            return expenseCategoryService.existsByName(expenseCategory);
        } else {
            return false;
        }
    }

}
