package com.carturo.eventhub.repository;

import com.carturo.eventhub.dto.EventDTO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryEventRepository implements BaseRepository<EventDTO> {

    private final List<EventDTO> events = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public EventDTO save(EventDTO event) {
        if (event.getId() == null) {
            event.setId(idCounter.getAndIncrement());
            events.add(event);
        } else {
            delete(event.getId());
            events.add(event);
        }
        return event;
    }

    @Override
    public List<EventDTO> findAll() {
        return new ArrayList<>(events);
    }

    @Override
    public Optional<EventDTO> findById(Long id) {
        return events.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    @Override
    public void delete(Long id) {
        events.removeIf(e -> e.getId().equals(id));
    }
}