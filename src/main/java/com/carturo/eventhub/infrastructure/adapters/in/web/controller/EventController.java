package com.carturo.eventhub.infrastructure.adapters.in.web.controller;

import com.carturo.eventhub.application.exception.ResourceNotFoundException;
import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.model.event.EventCategory;
import com.carturo.eventhub.domain.model.event.EventFilter;
import com.carturo.eventhub.domain.model.event.EventStatus;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.ports.in.command.event.CreateEventUseCase;
import com.carturo.eventhub.domain.ports.in.command.event.DeleteEventUseCase;
import com.carturo.eventhub.domain.ports.in.command.event.UpdateEventUseCase;
import com.carturo.eventhub.domain.ports.in.query.event.GetEventByIdQuery;
import com.carturo.eventhub.domain.ports.in.query.event.SearchEventsQuery;
import com.carturo.eventhub.domain.ports.in.query.venue.GetVenueByIdQuery;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.EventRequest;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.response.EventResponse;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.response.PageResponse;
import com.carturo.eventhub.infrastructure.adapters.in.web.mapper.EventWebMapper;
import com.carturo.eventhub.infrastructure.adapters.in.web.validation.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final CreateEventUseCase createEventUseCase;
    private final UpdateEventUseCase updateEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final GetEventByIdQuery getEventByIdQuery;
    private final SearchEventsQuery searchEventsQuery;
    private final GetVenueByIdQuery getVenueByIdQuery;
    private final EventWebMapper mapper;

    public EventController(
            CreateEventUseCase createEventUseCase,
            UpdateEventUseCase updateEventUseCase,
            DeleteEventUseCase deleteEventUseCase,
            GetEventByIdQuery getEventByIdQuery,
            SearchEventsQuery searchEventsQuery,
            GetVenueByIdQuery getVenueByIdQuery,
            EventWebMapper mapper
    ) {
        this.createEventUseCase = createEventUseCase;
        this.updateEventUseCase = updateEventUseCase;
        this.deleteEventUseCase = deleteEventUseCase;
        this.getEventByIdQuery = getEventByIdQuery;
        this.searchEventsQuery = searchEventsQuery;
        this.getVenueByIdQuery = getVenueByIdQuery;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public EventResponse createEvent(@Validated(ValidationGroups.Create.class) @RequestBody EventRequest request) {
        Event event = mapper.toDomain(request);

        var venue = getVenueByIdQuery.get(request.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with ID: " + request.getVenueId()));
        event.setVenue(venue);

        Event created = createEventUseCase.create(event);
        return mapper.toResponse(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public PageResponse<EventResponse> getEvents(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long venueId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageRequest pageRequest = new PageRequest(page, size);

        EventCategory eventCategory = null;
        if (category != null && !category.isBlank()) {
            try {
                eventCategory = EventCategory.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid event category: " + category);
            }
        }

        EventStatus eventStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                eventStatus = EventStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid event status: " + status);
            }
        }

        EventFilter filter = new EventFilter(city, eventCategory, startDate, endDate, venueId, eventStatus);
        var pageResult = searchEventsQuery.search(filter, pageRequest);

        List<EventResponse> content = pageResult.items().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                pageResult.page(),
                pageResult.size(),
                pageResult.totalItems(),
                pageResult.totalPages(),
                pageResult.page() >= pageResult.totalPages() - 1
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public EventResponse getEventById(@PathVariable Long id) {
        return getEventByIdQuery.get(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EventResponse updateEvent(@PathVariable Long id, @Validated(ValidationGroups.Update.class) @RequestBody EventRequest request) {
        Event event = mapper.toDomain(request);

        var venue = getVenueByIdQuery.get(request.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with ID: " + request.getVenueId()));
        event.setVenue(venue);

        Event updated = updateEventUseCase.update(id, event);
        return mapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteEvent(@PathVariable Long id) {
        deleteEventUseCase.delete(id);
    }
}