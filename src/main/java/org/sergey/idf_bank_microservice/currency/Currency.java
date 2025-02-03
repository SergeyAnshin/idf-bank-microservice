package org.sergey.idf_bank_microservice.currency;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sergey.idf_bank_microservice.entity.GeneralEntity;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "currency_id"))
})
public class Currency extends GeneralEntity {
    @Column(unique = true)
    private String alphaCode;

}
