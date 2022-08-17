package com.linkacademy.commerceapp.models;

import com.linkacademy.commerceapp.domain.entity.Basket;
import com.linkacademy.commerceapp.domain.entity.Product;

public record BasketProductTuple(Basket basket, Product product) {
}
