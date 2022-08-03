package com.linkacademy.commerceapp.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EntityWithUUID {
    @Id
    @Type(type = "pg-uuid")
    @EqualsAndHashCode.Include
    private UUID id;

    public EntityWithUUID() {
        this.id = UUID.randomUUID();
    }
}
