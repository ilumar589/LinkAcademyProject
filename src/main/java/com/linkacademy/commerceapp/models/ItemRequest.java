package com.linkacademy.commerceapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ItemRequest {
    private final UUID productId;
    private final long quantity;
}
