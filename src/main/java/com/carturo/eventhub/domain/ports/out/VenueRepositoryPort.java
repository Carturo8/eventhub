package com.carturo.eventhub.domain.ports.out;

import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.model.venue.VenueFilter;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;

import java.util.Optional;

public interface VenueRepositoryPort {
    Venue save(Venue venue);
    Optional<Venue> findById(Long id);
    PageResult<Venue> findAll(PageRequest pageRequest, VenueFilter filter);
    default PageResult<Venue> findAll(PageRequest pageRequest) {
        return findAll(pageRequest, VenueFilter.empty());
    }
    boolean existsByNameIgnoreCase(String name);
    void delete(Long id);
}