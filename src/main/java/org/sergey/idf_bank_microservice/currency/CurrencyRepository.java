package org.sergey.idf_bank_microservice.currency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByAlphaCode(String alphaCode);

    boolean existsByAlphaCode(String alphaCode);

}

