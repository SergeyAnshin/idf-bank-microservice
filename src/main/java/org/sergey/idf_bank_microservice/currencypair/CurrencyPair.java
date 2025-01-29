package org.sergey.idf_bank_microservice.currencypair;

import jakarta.persistence.*;
import lombok.*;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;
import org.sergey.idf_bank_microservice.exchangeratesource.ExchangeRateSource;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_buy_sell_currency_id", columnNames = {"buy_currency_id", "sell_currency_id"})
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "currency_pair_id"))
})
@NamedEntityGraph(
        name = "CurrencyPair.Currency&ExchangeRateSource",
        attributeNodes = {
                @NamedAttributeNode(value = "exchangeRateSources"),
                @NamedAttributeNode(value = "buyCurrency"),
                @NamedAttributeNode(value = "sellCurrency"),
        }
)
public class CurrencyPair extends GeneralEntity {
    @ManyToOne
    @JoinColumn(name = "buy_currency_id", nullable = false, updatable = false)
    private Currency buyCurrency;
    @ManyToOne
    @JoinColumn(name = "sell_currency_id", nullable = false, updatable = false)
    private Currency sellCurrency;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "currencyPairs")
    private List<ExchangeRateSource> exchangeRateSources;

}
