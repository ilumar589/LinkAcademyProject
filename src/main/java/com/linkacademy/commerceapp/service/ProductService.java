package com.linkacademy.commerceapp.service;

import com.linkacademy.commerceapp.domain.entity.Product;
import com.linkacademy.commerceapp.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
}
