package com.carturo.eventhub.domain.ports.out;

import com.carturo.eventhub.domain.model.Event;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;

import java.time.LocalDate;
import java.util.Optional;

public interface EventRepositoryPort {
    Event save(Event event);
    Optional<Event> findById(Long id);
    PageResult<Event> findAll(PageRequest pageRequest);
    PageResult<Event> search(String city, String category, LocalDate startDate, PageRequest pageRequest);
    boolean existsByNameIgnoreCase(String name);
    void delete(Long id);
}