package com.linkacademy.commerceapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ItemRequest {
    private final UUID productId;
    private final long quantity;
}
