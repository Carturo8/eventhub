package com.carturo.eventhub.infrastructure.adapters.out.jpa.repository;

import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueJpaRepository extends JpaRepository<VenueEntity, Long>, JpaSpecificationExecutor<VenueEntity> {
    boolean existsByNameIgnoreCase(String name);
}