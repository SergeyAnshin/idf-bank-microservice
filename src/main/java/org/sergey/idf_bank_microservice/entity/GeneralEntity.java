package org.sergey.idf_bank_microservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString

@MappedSuperclass
public abstract class GeneralEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createdAt;

}
