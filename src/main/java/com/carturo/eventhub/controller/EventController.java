package com.carturo.eventhub.controller;

import com.carturo.eventhub.dto.EventDTO;
import com.carturo.eventhub.exception.ApiError;
import com.carturo.eventhub.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Events", description = "Endpoints for managing events")
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(
            summary = "Create a new event",
            description = "Registers a new event in the in-memory catalog",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Event created successfully",
                            content = @Content(schema = @Schema(implementation = EventDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid event data",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventDTO event) {
        EventDTO createdEvent = eventService.create(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @Operation(
            summary = "Get all events",
            description = "Retrieves the list of all events stored in memory",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully",
                            content = @Content(schema = @Schema(implementation = EventDTO.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @Operation(
            summary = "Get event by ID",
            description = "Retrieves an event by its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event found",
                            content = @Content(schema = @Schema(implementation = EventDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        EventDTO event = eventService.findById(id);
        if (event == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(404, "Event not found"));
        }
        return ResponseEntity.ok(event);
    }

    @Operation(
            summary = "Update an event",
            description = "Updates the details of an existing event",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event updated successfully",
                            content = @Content(schema = @Schema(implementation = EventDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO updatedEvent) {
        EventDTO event = eventService.update(id, updatedEvent);
        if (event == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(404, "Event not found"));
        }
        return ResponseEntity.ok(event);
    }

    @Operation(
            summary = "Delete an event",
            description = "Removes an event from the catalog by its ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Event not found",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        EventDTO event = eventService.findById(id);
        if (event == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(404, "Event not found"));
        }
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}