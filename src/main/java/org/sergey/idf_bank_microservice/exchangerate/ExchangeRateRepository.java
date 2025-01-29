package org.sergey.idf_bank_microservice.exchangerate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
}
