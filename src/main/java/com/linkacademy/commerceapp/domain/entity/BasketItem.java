package com.linkacademy.commerceapp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BasketItem {
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @EqualsAndHashCode.Include
    private UUID id;

    @MapsId
    @OneToOne(mappedBy = "basketItem")
    @JoinColumn(name = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    private long quantity;

    public BasketItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
