package org.sergey.idf_bank_microservice.exchangerate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("""
            SELECT er
            FROM ExchangeRate er
            WHERE er.buyCurrency.id = :buyCurrencyId
                AND er.sellCurrency.id = :sellCurrencyId
            ORDER BY er.createdAt
            LIMIT 1
            """)
    Optional<ExchangeRate> findLatestRate(@Param("buyCurrencyId") long buyCurrencyId,
                                          @Param("sellCurrencyId") long sellCurrencyId);

}
