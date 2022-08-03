package com.linkacademy.commerceapp.models;

import com.linkacademy.commerceapp.domain.entity.Basket;
import com.linkacademy.commerceapp.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BasketProductTuple {
    private final Basket basket;
    private final Product product;
}
