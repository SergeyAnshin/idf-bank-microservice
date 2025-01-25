package org.sergey.idf_bank_microservice.bankaccount;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;

@NoArgsConstructor
@Getter
@Setter

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "bank_account_id"))
})
public class BankAccount extends GeneralEntity {
    private int number;
}

