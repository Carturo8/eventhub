package com.carturo.eventhub.service.impl;

import com.carturo.eventhub.dto.EventDTO;
import com.carturo.eventhub.service.EventService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EventServiceImpl implements EventService {

    private final List<EventDTO> events = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public EventDTO create(EventDTO event) {
        event.setId(idCounter.getAndIncrement());
        events.add(event);
        return event;
    }

    @Override
    public List<EventDTO> findAll() {
        return events;
    }

    @Override
    public EventDTO findById(Long id) {
        Optional<EventDTO> event = events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        return event.orElse(null);
    }

    @Override
    public EventDTO update(Long id, EventDTO updatedEvent) {
        EventDTO existingEvent = findById(id);
        if (existingEvent != null) {
            existingEvent.setName(updatedEvent.getName());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setDate(updatedEvent.getDate());
            existingEvent.setVenueId(updatedEvent.getVenueId());
        }
        return existingEvent;
    }

    @Override
    public void delete(Long id) {
        events.removeIf(e -> e.getId().equals(id));
    }
}