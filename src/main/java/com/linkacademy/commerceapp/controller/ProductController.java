package com.linkacademy.commerceapp.controller;

import com.linkacademy.commerceapp.domain.entity.Product;
import com.linkacademy.commerceapp.models.ProductFilterOptions;
import com.linkacademy.commerceapp.models.ProductsFilter;
import com.linkacademy.commerceapp.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("commerce/api/public/catalog")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/product/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") UUID id) {
        Optional<Product> searchedProductOpt = productService.findById(id);
        return searchedProductOpt
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/products")
    public ResponseEntity<Page<Product>> findAllFiltered(
            @PageableDefault(size = 6, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestBody ProductsFilter productsFilter
    ) {
        Page<Product> searchedProducts = productService.findAllFiltered(pageable, productsFilter);
        return ResponseEntity.ok(searchedProducts);
    }

    @GetMapping("/products/filter")
    public ResponseEntity<ProductFilterOptions> findFilterOptions() {
        return ResponseEntity.ok(productService.findFilterOptions());
    }
}
