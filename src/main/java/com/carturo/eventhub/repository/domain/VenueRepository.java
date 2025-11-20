package com.carturo.eventhub.repository.domain;

import com.carturo.eventhub.entity.VenueEntity;

import java.util.List;
import java.util.Optional;

public interface VenueRepository {
    VenueEntity save(VenueEntity venue);
    List<VenueEntity> findAll();
    Optional<VenueEntity> findById(Long id);
    void delete(Long id);
}