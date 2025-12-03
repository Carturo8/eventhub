package com.carturo.eventhub.domain.ports.in.command.auth;

public interface LoginUserUseCase {
    String login(String username, String password);
}