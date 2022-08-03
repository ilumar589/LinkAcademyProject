package com.linkacademy.commerceapp.domain.repository;

import com.linkacademy.commerceapp.domain.entity.Basket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasketRepository extends CrudRepository<Basket, UUID> {
    Optional<Basket> findBasketByBuyerId(UUID buyerId);
}
