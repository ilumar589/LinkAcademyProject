package com.linkacademy.commerceapp.models;

import com.linkacademy.commerceapp.domain.entity.CommerceRole;

public record SignupRequest(String email, String password, CommerceRole role) {
    public SignupRequest(String email, String password) {
        this(email, password, CommerceRole.ROLE_USER);
    }
}
