package com.carturo.eventhub.domain.ports.out;

import com.carturo.eventhub.domain.model.user.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByUsername(String username);
}