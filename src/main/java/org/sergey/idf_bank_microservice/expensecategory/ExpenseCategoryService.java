package org.sergey.idf_bank_microservice.expensecategory;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor

@Service
public class ExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Cacheable("expenseCategories")
    public Optional<ExpenseCategory> findByValue(String value) {
        if (nonNull(value)) {
            return expenseCategoryRepository.findByValue(value);
        } else {
            return Optional.empty();
        }
    }

}
