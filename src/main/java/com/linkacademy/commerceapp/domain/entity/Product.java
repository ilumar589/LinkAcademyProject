package com.linkacademy.commerceapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Product extends EntityWithUUID {
    @EqualsAndHashCode.Include
    private String name;
    private String description;
    private long price;
    private String pictureUrl;
    private Type type;
    private Brand brand;
    private long quantityInStock;
    @JsonIgnore
    @OneToOne(mappedBy = "product")
    private BasketItem basketItem;
}
