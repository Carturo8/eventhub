package com.carturo.eventhub.repository.domain;

import com.carturo.eventhub.entity.VenueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VenueRepository {
    VenueEntity save(VenueEntity venue);
    Page<VenueEntity> findAll(Pageable pageable);
    Optional<VenueEntity> findById(Long id);
    void delete(Long id);
    boolean existsByNameIgnoreCase(String name);
}