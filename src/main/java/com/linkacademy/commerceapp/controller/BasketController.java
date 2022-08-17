package com.linkacademy.commerceapp.controller;

import com.linkacademy.commerceapp.domain.entity.Basket;
import com.linkacademy.commerceapp.models.ItemRequest;
import com.linkacademy.commerceapp.service.BasketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("commerce/api/basket")
@AllArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @GetMapping
    public ResponseEntity<Basket> getBasket(@CookieValue(value = "buyerId", required = false) UUID buyerId) {
        if (buyerId == null || buyerId.toString().isBlank() || buyerId.toString().isEmpty()) {
            buyerId = UUID.randomUUID();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie","buyerId=" + buyerId +";Max-Age=604800; Path=/; Secure; HttpOnly");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(basketService.findOrCreateBasket(buyerId));
    }

    @PostMapping
    private ResponseEntity<Basket> addItemToBasket(
            @CookieValue(value = "buyerId", required = false) UUID buyerId,
            @RequestBody ItemRequest itemRequest) throws URISyntaxException {

        if (buyerId == null || buyerId.toString().isBlank() || buyerId.toString().isEmpty()) {
            buyerId = UUID.randomUUID();
        }

        Basket basket = basketService.addItem(buyerId,
                itemRequest.productId(),
                itemRequest.quantity());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie","buyerId=" + buyerId +";Max-Age=604800; Path=/; Secure; HttpOnly");

        return ResponseEntity
                .created(new URI("commerce/api/basket"))
                .headers(headers)
                .body(basket);
    }

    @DeleteMapping("/{productId}/{quantity}")
    private ResponseEntity<Basket> removeItemFromBasket(
            @CookieValue("buyerId") UUID buyerId,
            @PathVariable("productId") UUID productId,
            @PathVariable("quantity") long quantity) {
        return ResponseEntity.ok(basketService.removeItem(buyerId, productId, quantity));
    }
}
