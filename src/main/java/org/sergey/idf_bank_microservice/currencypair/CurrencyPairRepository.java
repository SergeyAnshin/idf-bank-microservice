package org.sergey.idf_bank_microservice.currencypair;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {

    @Query("SELECT cp FROM CurrencyPair cp")
    @EntityGraph(value = "CurrencyPair.Currency&ExchangeRateSource")
    List<CurrencyPair> findAllWithRateSource();

}
