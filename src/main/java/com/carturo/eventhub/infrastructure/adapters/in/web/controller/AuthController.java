package com.carturo.eventhub.infrastructure.adapters.in.web.controller;

import com.carturo.eventhub.domain.model.user.User;
import com.carturo.eventhub.domain.ports.in.command.auth.LoginUserUseCase;
import com.carturo.eventhub.domain.ports.in.command.auth.RegisterUserUseCase;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.response.AuthResponse;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.LoginRequest;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.RegisterRequest;
import com.carturo.eventhub.infrastructure.adapters.in.web.mapper.AuthWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final AuthWebMapper authWebMapper;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        User userToRegister = authWebMapper.toDomain(request);
        User registeredUser = registerUserUseCase.register(userToRegister);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        String jwt = loginUserUseCase.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(AuthResponse.builder().token(jwt).build());
    }
}