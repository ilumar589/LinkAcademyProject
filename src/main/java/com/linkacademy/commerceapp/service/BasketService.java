package com.linkacademy.commerceapp.service;

import com.linkacademy.commerceapp.domain.entity.Basket;
import com.linkacademy.commerceapp.domain.entity.BasketItem;
import com.linkacademy.commerceapp.domain.entity.Product;
import com.linkacademy.commerceapp.domain.repository.BasketRepository;
import com.linkacademy.commerceapp.domain.repository.ProductRepository;
import com.linkacademy.commerceapp.models.BasketProductTuple;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    public void addItem(UUID basketId, UUID productId, int quantity) {
        BasketProductTuple basketProductTuple = validateSentBasketInfo(basketId, productId);

        Basket basket = basketProductTuple.getBasket();

        Set<BasketItem> basketItems = basket.getItems();
        basketItems = CollectionUtils.isEmpty(basketItems) ? new HashSet<>() : basketItems;

        if (basketItems
                .stream()
                .noneMatch(basketItem -> basketItem.getProduct().getId().equals(productId))) {

            basketItems.add(new BasketItem(basketProductTuple.getProduct(), quantity));
        }

        basketItems
                .stream()
                .filter(basketItem -> basketItem.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(basketItem -> basketItem.setQuantity(basketItem.getQuantity() + quantity));

        basketRepository.save(basket);
    }

    public void removeItem(UUID basketId, UUID productId, int quantity) {
        BasketProductTuple basketProductTuple = validateSentBasketInfo(basketId, productId);

        Basket basket = basketProductTuple.getBasket();
        Set<BasketItem> basketItems = basket.getItems();

        basketItems = CollectionUtils.isEmpty(basketItems) ? new HashSet<>() : basketItems;

        Set<BasketItem> finalBasketItems = basketItems;
        basketItems
                .stream()
                .filter(basketItem -> basketItem.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(basketItem -> {
                    basketItem.setQuantity(basketItem.getQuantity() - quantity);
                    if (basketItem.getQuantity() == 0) {
                        finalBasketItems.remove(basketItem);
                    }
                });

        basketRepository.save(basket);
    }

    private BasketProductTuple validateSentBasketInfo(UUID basketId, UUID productId) {
        Optional<Basket> optionalBasket = basketRepository.findById(basketId);
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalBasket.isEmpty()) {
            throw new RuntimeException("No basket found with id " + basketId);
        }

        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("No product found with id " + productId);
        }

        return new BasketProductTuple(optionalBasket.get(), optionalProduct.get());
    }
}
