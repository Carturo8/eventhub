package com.carturo.eventhub.infrastructure.adapters.out.jpa.repository;

import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {
    boolean existsByNameIgnoreCase(String name);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"venue"})
    Page<EventEntity> findAll(@NonNull Pageable pageable);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"venue"})
    Page<EventEntity> findAll(@NonNull Specification<EventEntity> spec, @NonNull Pageable pageable);
}