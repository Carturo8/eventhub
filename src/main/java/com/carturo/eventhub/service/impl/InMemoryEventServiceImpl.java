package com.carturo.eventhub.service.impl;

import com.carturo.eventhub.dto.EventDTO;
import com.carturo.eventhub.repository.InMemoryEventRepository;
import com.carturo.eventhub.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryEventServiceImpl implements EventService {

    private final InMemoryEventRepository eventRepository;

    public InMemoryEventServiceImpl(InMemoryEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventDTO create(EventDTO event) {
        return eventRepository.save(event);
    }

    @Override
    public List<EventDTO> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public EventDTO findById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public EventDTO update(Long id, EventDTO updatedEvent) {
        EventDTO existing = findById(id);
        if (existing != null) {
            updatedEvent.setId(id);
            return eventRepository.save(updatedEvent);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        eventRepository.delete(id);
    }
}