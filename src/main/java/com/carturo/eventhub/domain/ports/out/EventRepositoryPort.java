package com.carturo.eventhub.domain.ports.out;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.model.event.EventFilter;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;

import java.util.Optional;

public interface EventRepositoryPort {
    Event save(Event event);
    Optional<Event> findById(Long id);
    PageResult<Event> findAll(PageRequest pageRequest);
    PageResult<Event> search(EventFilter filter, PageRequest pageRequest);
    boolean existsByNameIgnoreCase(String name);
    void delete(Long id);
}