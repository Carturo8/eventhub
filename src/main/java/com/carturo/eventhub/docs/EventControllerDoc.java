package com.carturo.eventhub.docs;

import com.carturo.eventhub.dto.request.EventRequest;
import com.carturo.eventhub.dto.response.EventResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public interface EventControllerDoc {

    @Operation(
            summary = "Create a new event",
            description = "Creates a new event.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Event created successfully",
                            content = @Content(schema = @Schema(implementation = EventResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid event data", content = @Content)
            }
    )
    EventResponse createEvent(EventRequest request);

    @Operation(
            summary = "Get events (paged)",
            description = "Retrieves a paginated list of events with optional filters.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Events retrieved",
                            content = @Content(schema = @Schema(implementation = EventResponse.class)))
            }
    )
    Page<EventResponse> getEvents(
            @Parameter(description = "Filter by venue city (optional)") String city,
            @Parameter(description = "Filter by category (optional)") String category,
            @Parameter(description = "Filter by start date (yyyy-MM-dd) (optional)")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    );

    @Operation(
            summary = "Get event by ID",
            description = "Retrieves an event by its id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event found",
                            content = @Content(schema = @Schema(implementation = EventResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
            }
    )
    EventResponse getEventById(Long id);

    @Operation(
            summary = "Update an event",
            description = "Updates an existing event by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event updated",
                            content = @Content(schema = @Schema(implementation = EventResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
            }
    )
    EventResponse updateEvent(Long id, EventRequest request);

    @Operation(
            summary = "Delete an event",
            description = "Deletes an event by id.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Event deleted"),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
            }
    )
    void deleteEvent(Long id);
}