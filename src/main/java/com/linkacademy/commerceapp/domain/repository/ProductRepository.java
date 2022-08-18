package com.linkacademy.commerceapp.domain.repository;

import com.linkacademy.commerceapp.domain.entity.Brand;
import com.linkacademy.commerceapp.domain.entity.Product;
import com.linkacademy.commerceapp.domain.entity.Type;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    @Query("select distinct product.type from Product as product")
    Set<Type> findDistinctProductTypes();

    @Query("select distinct product.brand from Product as product")
    Set<Brand> findDistinctProductBrands();
}