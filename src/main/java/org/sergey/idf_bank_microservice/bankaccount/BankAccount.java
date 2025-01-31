package org.sergey.idf_bank_microservice.bankaccount;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.sergey.idf_bank_microservice.currency.Currency;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "bank_account_id"))
})
public class BankAccount extends GeneralEntity {
    private int number;
    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_id", nullable = false, updatable = false)
    private Currency currency;
}
