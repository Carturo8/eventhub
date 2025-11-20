package com.carturo.eventhub.repository.inmemory;

import com.carturo.eventhub.entity.EventEntity;
import com.carturo.eventhub.repository.domain.EventRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("inmemory")
public class InMemoryEventRepository implements EventRepository {

    private final Map<Long, EventEntity> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public EventEntity save(EventEntity event) {
        if (event.getId() == null) {
            event.setId(idCounter.getAndIncrement());
        }
        storage.put(event.getId(), event);
        return event;
    }

    @Override
    public List<EventEntity> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<EventEntity> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }
}