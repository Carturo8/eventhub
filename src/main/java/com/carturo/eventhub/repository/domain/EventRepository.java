package com.carturo.eventhub.repository.domain;

import com.carturo.eventhub.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface EventRepository {
    EventEntity save(EventEntity event);
    Page<EventEntity> findAll(Pageable pageable);
    Optional<EventEntity> findById(Long id);
    void delete(Long id);
    boolean existsByNameIgnoreCase(String name);
    Page<EventEntity> search(String city, String category, LocalDate startDate, Pageable pageable);
}