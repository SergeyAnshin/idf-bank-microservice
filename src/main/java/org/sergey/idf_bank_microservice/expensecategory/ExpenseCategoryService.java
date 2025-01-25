package org.sergey.idf_bank_microservice.expensecategory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.sergey.idf_bank_microservice.entitypersister.EntityPersistenceUtils.nonPersist;

@RequiredArgsConstructor

@Service
public class ExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public boolean existsByName(String name) {
        return expenseCategoryRepository.existsByName(name);
    }

    @Transactional
    public ExpenseCategory persist(ExpenseCategory expenseCategory) {
        if (nonNull(expenseCategory) && nonPersist(expenseCategory)) {
            Optional<ExpenseCategory> existingExpenseCategory = expenseCategoryRepository.findByName(
                    expenseCategory.getName());
            return existingExpenseCategory.orElseGet(() -> expenseCategoryRepository.save(expenseCategory));
        }
        return expenseCategory;
    }
}
