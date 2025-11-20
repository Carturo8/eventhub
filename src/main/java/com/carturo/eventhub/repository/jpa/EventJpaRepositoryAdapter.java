package com.carturo.eventhub.repository.jpa;

import com.carturo.eventhub.entity.EventEntity;
import com.carturo.eventhub.repository.domain.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("dev")
@RequiredArgsConstructor
public class EventJpaRepositoryAdapter implements EventRepository {

    private final SpringDataEventRepository repository;

    @Override
    public EventEntity save(EventEntity event) {
        return repository.save(event);
    }

    @Override
    public List<EventEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<EventEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    interface SpringDataEventRepository extends JpaRepository<EventEntity, Long> {
        boolean existsByNameIgnoreCase(String name);
    }
}