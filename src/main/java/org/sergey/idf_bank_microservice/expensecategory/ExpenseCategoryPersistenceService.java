package org.sergey.idf_bank_microservice.expensecategory;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.entitypersister.BaseEntityPersistenceService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor

@Component
public class ExpenseCategoryPersistenceService extends BaseEntityPersistenceService<ExpenseCategory> {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Override
    @Transactional
    public ExpenseCategory persist(ExpenseCategory expenseCategory) {
        return super.persist(expenseCategory, expenseCategoryRepository::findByName, expenseCategory.getName());
    }
}
