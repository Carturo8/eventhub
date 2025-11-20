package com.carturo.eventhub.repository.jpa;

import com.carturo.eventhub.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueJpaRepository extends JpaRepository<VenueEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}