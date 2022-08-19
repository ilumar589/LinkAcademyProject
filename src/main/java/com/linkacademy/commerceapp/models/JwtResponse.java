package com.linkacademy.commerceapp.models;

import java.util.List;
import java.util.UUID;

public record JwtResponse(String token, UUID userId, String email, List<String> roles) {
}
