package com.linkacademy.commerceapp.service;

import com.linkacademy.commerceapp.domain.entity.Basket;
import com.linkacademy.commerceapp.domain.entity.BasketItem;
import com.linkacademy.commerceapp.domain.entity.Product;
import com.linkacademy.commerceapp.domain.repository.BasketRepository;
import com.linkacademy.commerceapp.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    public Optional<Basket> findByBuyerId(UUID buyerId) {
        return basketRepository.findBasketByBuyerId(buyerId);
    }

    public Basket addItem(UUID buyerId, UUID productId, long quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("No product found with id " + productId);
        }

        Basket basket = findOrCreateBasket(buyerId);

        Set<BasketItem> basketItems = basket.getItems();


        if (basketItems
                .stream()
                .noneMatch(basketItem -> basketItem.getProduct().getId().equals(productId))) {

            BasketItem basketItem = new BasketItem(optionalProduct.get(), basket, quantity);
            basketItems.add(basketItem);
        } else {
            basketItems
                    .stream()
                    .filter(basketItem -> basketItem.getProduct().getId().equals(productId))
                    .findFirst()
                    .ifPresent(basketItem -> basketItem.setQuantity(basketItem.getQuantity() + quantity));
        }

        basketRepository.save(basket);

        return basket;
    }

    public Basket removeItem(UUID buyerId, UUID productId, long quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("No product found with id " + productId);
        }

        Basket basket = findOrCreateBasket(buyerId);

        Set<BasketItem> basketItems = basket.getItems();

        basketItems
                .stream()
                .filter(basketItem -> basketItem.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(basketItem -> {
                    basketItem.setQuantity(basketItem.getQuantity() - quantity);
                    if (basketItem.getQuantity() == 0) {
                        basketItems.remove(basketItem);
                    }
                });

        basketRepository.save(basket);

        return basket;
    }

    public Basket findOrCreateBasket(UUID buyerId) {
        Optional<Basket> optionalBasket = basketRepository.findBasketByBuyerId(buyerId);
        return optionalBasket.orElseGet(() -> basketRepository.save(new Basket(buyerId, Collections.emptySet())));
    }
}
