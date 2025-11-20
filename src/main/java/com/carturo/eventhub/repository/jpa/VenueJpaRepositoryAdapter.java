package com.carturo.eventhub.repository.jpa;

import com.carturo.eventhub.entity.VenueEntity;
import com.carturo.eventhub.repository.domain.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public List<VenueEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<VenueEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    interface SpringDataVenueRepository extends JpaRepository<VenueEntity, Long> {
    }
}