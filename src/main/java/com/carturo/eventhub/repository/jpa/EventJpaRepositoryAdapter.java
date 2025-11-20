package com.carturo.eventhub.repository.jpa;

import com.carturo.eventhub.entity.EventEntity;
import com.carturo.eventhub.repository.domain.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Profile("dev")
@RequiredArgsConstructor
public class EventJpaRepositoryAdapter implements EventRepository {

    private final EventJpaRepository repository;

    @Override
    public EventEntity save(EventEntity event) {
        return repository.save(event);
    }

    @Override
    public Page<EventEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<EventEntity> findById(Long id) {
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

    @Override
    public Page<EventEntity> search(String city, String category, LocalDate startDate, Pageable pageable) {
        return repository.findAll(EventSpecifications.withFilters(city, category, startDate), pageable);
    }
}