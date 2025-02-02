package org.sergey.idf_bank_microservice.expensecategory;

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
        @AttributeOverride(name = "id", column = @Column(name = "expense_category_id"))
})
public class ExpenseCategory extends GeneralEntity {
    @Column(name = "val")
    private String value;

}
