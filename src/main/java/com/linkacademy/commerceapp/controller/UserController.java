package com.linkacademy.commerceapp.controller;

import com.linkacademy.commerceapp.models.JwtResponse;
import com.linkacademy.commerceapp.security.SecurityUserDetails;
import com.linkacademy.commerceapp.security.jwt.JWTUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("commerce/api/user/")
@AllArgsConstructor
public class UserController {

    private final JWTUtils jwtUtils;

    @GetMapping
    public ResponseEntity<JwtResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUserDetails currentUser = (SecurityUserDetails) authentication;

        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roles = currentUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), currentUser.getUsername(), roles));
    }
}
