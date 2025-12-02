package com.carturo.eventhub.infrastructure.adapters.out.jpa.adapter;

import com.carturo.eventhub.domain.model.user.User;
import com.carturo.eventhub.domain.ports.out.UserRepositoryPort;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.mapper.UserEntityMapper;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {
        return userEntityMapper.toDomain(userJpaRepository.save(userEntityMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userEntityMapper::toDomain);
    }
}