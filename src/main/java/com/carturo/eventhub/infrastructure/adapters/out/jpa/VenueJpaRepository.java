package com.carturo.eventhub.infrastructure.adapters.out.jpa;

import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueJpaRepository extends JpaRepository<VenueEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}