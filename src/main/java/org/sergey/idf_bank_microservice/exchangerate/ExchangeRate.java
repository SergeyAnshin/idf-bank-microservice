package org.sergey.idf_bank_microservice.exchangerate;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;

import java.math.BigDecimal;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_created_at_buy_sell_currency_id",
                columnNames = {"creates_at", "buy_currency_id", "sell_currency_id"})
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "exchange_rate_id"))
})
public class ExchangeRate extends GeneralEntity {
    @ManyToOne
    @JoinColumn(name = "buy_currency_id", nullable = false, updatable = false)
    private Currency buyCurrency;
    @ManyToOne
    @JoinColumn(name = "sell_currency_id", nullable = false, updatable = false)
    private Currency sellCurrency;
    private BigDecimal buyRate;
}
