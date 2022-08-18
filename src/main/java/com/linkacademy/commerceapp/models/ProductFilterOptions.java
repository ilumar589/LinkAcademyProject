package com.linkacademy.commerceapp.models;

import com.linkacademy.commerceapp.domain.entity.Brand;
import com.linkacademy.commerceapp.domain.entity.Type;

import java.util.Set;

public record ProductFilterOptions(Set<Type> types, Set<Brand> brands) {
}
