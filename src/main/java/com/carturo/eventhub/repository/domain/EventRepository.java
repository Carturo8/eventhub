package com.carturo.eventhub.repository.domain;

import com.carturo.eventhub.entity.EventEntity;

import java.util.List;
import java.util.Optional;

public interface EventRepository {
    EventEntity save(EventEntity event);
    List<EventEntity> findAll();
    Optional<EventEntity> findById(Long id);
    void delete(Long id);
}