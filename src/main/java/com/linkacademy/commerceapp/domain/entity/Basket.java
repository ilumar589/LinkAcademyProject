package com.linkacademy.commerceapp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true ,onlyExplicitlyIncluded = true)
public class Basket extends EntityWithUUID {
    @Type(type = "pg-uuid")
    @EqualsAndHashCode.Include
    private UUID buyerId;
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private Set<BasketItem> items = new HashSet<>();
}
