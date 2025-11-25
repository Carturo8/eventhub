package com.carturo.eventhub.infrastructure.adapters.in.web;

import com.carturo.eventhub.domain.model.Event;
import com.carturo.eventhub.domain.ports.in.command.event.CreateEventUseCase;
import com.carturo.eventhub.domain.ports.in.command.event.DeleteEventUseCase;
import com.carturo.eventhub.domain.ports.in.command.event.UpdateEventUseCase;
import com.carturo.eventhub.domain.ports.in.query.event.GetEventByIdQuery;
import com.carturo.eventhub.domain.ports.in.query.event.ListEventsQuery;
import com.carturo.eventhub.domain.ports.in.query.event.SearchEventsQuery;
import com.carturo.eventhub.domain.ports.in.query.venue.GetVenueByIdQuery;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.EventRequest;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.response.EventResponse;
import com.carturo.eventhub.infrastructure.adapters.in.web.mapper.EventWebMapper;
import com.carturo.eventhub.infrastructure.exception.ResourceNotFoundException;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    private final ListEventsQuery listEventsQuery;
    private final SearchEventsQuery searchEventsQuery;
    private final GetVenueByIdQuery getVenueByIdQuery;
    private final EventWebMapper mapper;

    public EventController(
            CreateEventUseCase createEventUseCase,
            UpdateEventUseCase updateEventUseCase,
            DeleteEventUseCase deleteEventUseCase,
            GetEventByIdQuery getEventByIdQuery,
            ListEventsQuery listEventsQuery,
            SearchEventsQuery searchEventsQuery,
            GetVenueByIdQuery getVenueByIdQuery,
            EventWebMapper mapper
    ) {
        this.createEventUseCase = createEventUseCase;
        this.updateEventUseCase = updateEventUseCase;
        this.deleteEventUseCase = deleteEventUseCase;
        this.getEventByIdQuery = getEventByIdQuery;
        this.listEventsQuery = listEventsQuery;
        this.searchEventsQuery = searchEventsQuery;
        this.getVenueByIdQuery = getVenueByIdQuery;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Create a new event",
            description = "Creates a new event.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Event created successfully",
                            content = @Content(schema = @Schema(implementation = EventResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid event data", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(@Valid @RequestBody EventRequest request) {
        Event event = mapper.toDomain(request);
        var venue = getVenueByIdQuery.get(request.venueId());
        if (venue == null) throw new ResourceNotFoundException("Venue not found");
        event.setVenue(venue);
        Event created = createEventUseCase.create(event);
        return mapper.toResponse(created);
    }

    @Operation(
            summary = "Get events (paged)",
            description = "Retrieves a paginated list of events with optional filters.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Events retrieved",
                            content = @Content(schema = @Schema(implementation = EventResponse.class)))
            }
    )
    @GetMapping
    public Page<EventResponse> getEvents(
            @Parameter(description = "Filter by venue city (optional)") @RequestParam(required = false) String city,
            @Parameter(description = "Filter by category (optional)") @RequestParam(required = false) String category,
            @Parameter(description = "Filter by start date (yyyy-MM-dd) (optional)") @RequestParam(required = false) LocalDate startDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageRequest pageRequest = new PageRequest(page, size);

        var pageResult = (city != null || category != null || startDate != null)
                ? searchEventsQuery.search(city, category, startDate, pageRequest)
                : listEventsQuery.list(pageRequest);

        List<EventResponse> content = pageResult.items().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(content,
                org.springframework.data.domain.PageRequest.of(pageResult.page(), pageResult.size()),
                pageResult.totalItems());
    }

    @Operation(
            summary = "Get event by ID",
            description = "Retrieves an event by its id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event found",
                            content = @Content(schema = @Schema(implementation = EventResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public EventResponse getEventById(@PathVariable Long id) {
        Event event = getEventByIdQuery.get(id);
        if (event == null) throw new ResourceNotFoundException("Event not found");
        return mapper.toResponse(event);
    }

    @Operation(
            summary = "Update an event",
            description = "Updates an existing event by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event updated",
                            content = @Content(schema = @Schema(implementation = EventResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Event not found or venue not found", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public EventResponse updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequest request) {
        Event event = mapper.toDomain(request);
        var venue = getVenueByIdQuery.get(request.venueId());
        if (venue == null) throw new ResourceNotFoundException("Venue not found");
        event.setVenue(venue);
        Event updated = updateEventUseCase.update(id, event);
        return mapper.toResponse(updated);
    }

    @Operation(
            summary = "Delete an event",
            description = "Deletes an event by id.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Event deleted"),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long id) {
        deleteEventUseCase.delete(id);
    }
}