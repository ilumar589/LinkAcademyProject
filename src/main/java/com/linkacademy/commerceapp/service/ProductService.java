package com.linkacademy.commerceapp.service;

import com.linkacademy.commerceapp.domain.entity.Brand;
import com.linkacademy.commerceapp.domain.entity.Product;
import com.linkacademy.commerceapp.domain.entity.Type;
import com.linkacademy.commerceapp.domain.repository.ProductRepository;
import com.linkacademy.commerceapp.models.ProductsFilter;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return StreamSupport
                .stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Product> findAllFiltered(Pageable pageable, ProductsFilter productsFilter) {
        return productRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>(3);
            searchByName(root, criteriaBuilder, productsFilter.productName()).ifPresent(predicates::add);
            hasTypes(root, productsFilter.types()).ifPresent(predicates::add);
            hasBrands(root, productsFilter.brands()).ifPresent(predicates::add);

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        }, pageable);
    }

    private Optional<Predicate> searchByName(Root<Product> root, CriteriaBuilder criteriaBuilder, String productName) {
        if (StringUtils.isEmpty(productName)) {
            return Optional.empty();
        }

        return Optional.of(criteriaBuilder.like(root.get("name"), productName.trim().toLowerCase() + "%"));
    }

    private Optional<Predicate> hasTypes(Root<Product> root, Set<Type> types) {
        if (CollectionUtils.isEmpty(types)) {
            return Optional.empty();
        }

        return Optional.of(root.get("type").in(types));
    }

    private Optional<Predicate> hasBrands(Root<Product> root, Set<Brand> brands) {
        if (CollectionUtils.isEmpty(brands)) {
            return Optional.empty();
        }

        return Optional.of(root.get("brand").in(brands));
    }
}
