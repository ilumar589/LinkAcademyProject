package com.linkacademy.commerceapp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends EntityWithUUID {
    private String name;
    private String description;
    private long price;
    private String pictureUrl;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private Brand brand;
    private long quantityInStock;
}
