package com.linkacademy.commerceapp.models;

import java.util.UUID;

public record ItemRequest(UUID productId, long quantity) {
}
