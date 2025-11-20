package com.carturo.eventhub.service.impl;

import com.carturo.eventhub.dto.request.EventRequest;
import com.carturo.eventhub.dto.response.EventResponse;
import com.carturo.eventhub.entity.EventEntity;
import com.carturo.eventhub.entity.VenueEntity;
import com.carturo.eventhub.exception.DuplicateResourceException;
import com.carturo.eventhub.exception.ResourceNotFoundException;
import com.carturo.eventhub.mapper.EventMapper;
import com.carturo.eventhub.repository.domain.EventRepository;
import com.carturo.eventhub.repository.domain.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements com.carturo.eventhub.service.EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final EventMapper eventMapper;

    @Override
    public EventResponse create(EventRequest request) {
        validateEventNameUniqueOnCreate(request.name());
        VenueEntity venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + request.venueId()));
        EventEntity entity = eventMapper.toEntity(request);
        entity.setVenue(venue);
        EventEntity saved = eventRepository.save(entity);
        return eventMapper.toResponse(saved);
    }

    @Override
    public Page<EventResponse> findAll(Pageable pageable) {
        Page<EventEntity> page = eventRepository.findAll(pageable);
        return page.map(eventMapper::toResponse);
    }

    public Page<EventResponse> search(String city, String category, java.time.LocalDate startDate, Pageable pageable) {
        Page<EventEntity> page = eventRepository.search(city, category, startDate, pageable);
        return page.map(eventMapper::toResponse);
    }

    @Override
    public EventResponse findById(Long id) {
        EventEntity entity = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        return eventMapper.toResponse(entity);
    }

    @Override
    public EventResponse update(Long id, EventRequest request) {
        EventEntity existing = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        if (!existing.getName().equalsIgnoreCase(request.name())) {
            validateEventNameUniqueOnCreate(request.name());
        }
        VenueEntity venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + request.venueId()));
        existing.setName(request.name());
        existing.setDescription(request.description());
        existing.setCategory(request.category());
        existing.setDate(request.date());
        existing.setVenue(venue);
        EventEntity saved = eventRepository.save(existing);
        return eventMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        EventEntity existing = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        eventRepository.delete(existing.getId());
    }

    private void validateEventNameUniqueOnCreate(String name) {
        if (eventRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Event with name '" + name + "' already exists");
        }
    }
}