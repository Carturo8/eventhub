package com.carturo.eventhub.domain.ports.out;

import com.carturo.eventhub.domain.model.Venue;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;

import java.util.Optional;

public interface VenueRepositoryPort {
    Venue save(Venue venue);
    Optional<Venue> findById(Long id);
    PageResult<Venue> findAll(PageRequest pageRequest);
    boolean existsByNameIgnoreCase(String name);
    void delete(Long id);
}