package com.carturo.eventhub.repository.jpa;

import com.carturo.eventhub.entity.VenueEntity;
import com.carturo.eventhub.repository.domain.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("dev")
@RequiredArgsConstructor
public class VenueJpaRepositoryAdapter implements VenueRepository {

    private final SpringDataVenueRepository repository;

    @Override
    public VenueEntity save(VenueEntity venue) {
        return repository.save(venue);
    }

    @Override
    public Page<VenueEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<VenueEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return repository.existsByNameIgnoreCase(name);
    }

    interface SpringDataVenueRepository extends JpaRepository<VenueEntity, Long> {
        boolean existsByNameIgnoreCase(String name);
    }
}