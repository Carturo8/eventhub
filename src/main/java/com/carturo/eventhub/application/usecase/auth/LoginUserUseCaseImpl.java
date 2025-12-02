package com.carturo.eventhub.application.usecase.auth;

import com.carturo.eventhub.domain.ports.in.command.auth.LoginUserUseCase;
import com.carturo.eventhub.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class LoginUserUseCaseImpl implements LoginUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // TODO: Obtener los roles reales del UserDetails si es necesario, o del User de dominio
        return jwtUtil.generateToken(userDetails.getUsername(), new HashSet<>(Collections.singletonList("USER")));
    }
}