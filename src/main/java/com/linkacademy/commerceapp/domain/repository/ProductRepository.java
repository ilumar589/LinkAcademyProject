package com.linkacademy.commerceapp.domain.repository;

import com.linkacademy.commerceapp.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
}