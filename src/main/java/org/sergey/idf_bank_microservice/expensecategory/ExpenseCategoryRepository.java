package org.sergey.idf_bank_microservice.expensecategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    boolean existsByName(String name);

    Optional<ExpenseCategory> findByName(String name);

}

