package org.sergey.idf_bank_microservice.exchangeratesource;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sergey.idf_bank_microservice.currencypair.CurrencyPair;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;

import java.util.List;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_rate_source_name", columnNames = {"name"})
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "rate_source_id"))
})

public class ExchangeRateSource extends GeneralEntity {
    @Enumerated(EnumType.STRING)
    private ExchangeRateSourceName name;
    private String urlTemplate;
    private String apiKey;
    @Transient
    private String url;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "exchangeRateSources")
    private List<CurrencyPair> currencyPairs;
}
