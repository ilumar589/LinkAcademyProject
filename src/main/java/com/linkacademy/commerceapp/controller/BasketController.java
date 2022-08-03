package com.linkacademy.commerceapp.controller;

import com.linkacademy.commerceapp.domain.entity.Basket;
import com.linkacademy.commerceapp.models.ItemRequest;
import com.linkacademy.commerceapp.service.BasketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("commerce/api/basket")
@AllArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @GetMapping
    public ResponseEntity<Basket> getBasket(@CookieValue("buyerId") UUID buyerId,  HttpServletResponse httpServletResponse) {
        if (buyerId == null || buyerId.toString().isBlank() || buyerId.toString().isEmpty()) {
            UUID buyerIdentity = UUID.randomUUID();
            buyerId = buyerIdentity;
            httpServletResponse.addCookie(new Cookie("buyerId", buyerIdentity.toString()));
        }

        return ResponseEntity.ok(basketService.findOrCreateBasket(buyerId));
    }

    @PostMapping
    private ResponseEntity<Basket> addItemToBasket(
            @CookieValue("buyerId") UUID buyerId,
            HttpServletResponse httpServletResponse,
            @RequestBody ItemRequest itemRequest) throws URISyntaxException {

        if (buyerId == null || buyerId.toString().isBlank() || buyerId.toString().isEmpty()) {
            UUID buyerIdentity = UUID.randomUUID();
            buyerId = buyerIdentity;
            httpServletResponse.addCookie(new Cookie("buyerId", buyerIdentity.toString()));
        }

        Basket basket = basketService.addItem(buyerId,
                itemRequest.getProductId(),
                itemRequest.getQuantity());

        return ResponseEntity
                .created(new URI("commerce/api/basket"))
                .body(basket);
    }

    @DeleteMapping
    private ResponseEntity<Basket> removeItemFromBasket(
            @CookieValue("buyerId") UUID buyerId,
            @RequestBody ItemRequest itemRequest) throws URISyntaxException {
        return ResponseEntity
                .created(new URI("commerce/api/basket"))
                .body(basketService.addItem(buyerId,
                        itemRequest.getProductId(),
                        itemRequest.getQuantity()));
    }
}
