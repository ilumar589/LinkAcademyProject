package com.linkacademy.commerceapp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Basket extends EntityWithUUID {
    @Type(type = "pg-uuid")
    private UUID buyerId;
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private Set<BasketItem> items = new HashSet<>();
}
