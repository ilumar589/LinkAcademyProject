package com.linkacademy.commerceapp.models;

import com.linkacademy.commerceapp.domain.entity.Brand;
import com.linkacademy.commerceapp.domain.entity.Type;

import java.util.Collections;
import java.util.Set;

public record ProductsFilter(Set<Type> types, Set<Brand> brands) {

    public ProductsFilter {
        types = Collections.emptySet();
        brands = Collections.emptySet();
    }
}
