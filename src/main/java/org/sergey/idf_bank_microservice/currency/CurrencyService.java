package org.sergey.idf_bank_microservice.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    @Cacheable("currencies")
    public Optional<Currency> findByAlphaCode(String currencyCode) {
        return currencyRepository.findByAlphaCode(currencyCode);
    }

}
