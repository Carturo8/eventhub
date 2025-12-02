package com.carturo.eventhub.domain.ports.in.command.auth;

import com.carturo.eventhub.domain.model.user.User;

public interface RegisterUserUseCase {
    User register(User user);
}