package com.linkacademy.commerceapp.controller;

import com.linkacademy.commerceapp.domain.entity.Product;
import com.linkacademy.commerceapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("commerce/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") UUID id) {
        Optional<Product> searchedProductOpt = productService.findById(id);
        return searchedProductOpt
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> searchedProducts = productService.findAll();

        if (searchedProducts == null || searchedProducts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(searchedProducts);
    }
}
