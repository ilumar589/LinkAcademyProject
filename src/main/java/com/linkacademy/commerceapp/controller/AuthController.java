package com.linkacademy.commerceapp.controller;

import com.linkacademy.commerceapp.domain.entity.Role;
import com.linkacademy.commerceapp.domain.entity.User;
import com.linkacademy.commerceapp.domain.repository.RoleRepository;
import com.linkacademy.commerceapp.domain.repository.UserRepository;
import com.linkacademy.commerceapp.models.JwtResponse;
import com.linkacademy.commerceapp.models.LoginRequest;
import com.linkacademy.commerceapp.models.MessageResponse;
import com.linkacademy.commerceapp.models.SignupRequest;
import com.linkacademy.commerceapp.security.SecurityUserDetails;
import com.linkacademy.commerceapp.security.jwt.JWTUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("commerce/api/auth/")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JWTUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),userDetails.getUsername(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        Optional<Role> sentRole = roleRepository.findByCommerceRole(signUpRequest.role());

        if (sentRole.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Sent role is invalid!"));

        }

        // Create new user's account
        User user = new User(signUpRequest.email(),
                encoder.encode(signUpRequest.password()),
                Collections.singleton(sentRole.get()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
