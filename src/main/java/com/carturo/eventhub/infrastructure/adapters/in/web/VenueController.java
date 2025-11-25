package com.carturo.eventhub.infrastructure.adapters.in.web;

import com.carturo.eventhub.domain.model.Venue;
import com.carturo.eventhub.domain.ports.in.command.venue.CreateVenueUseCase;
import com.carturo.eventhub.domain.ports.in.command.venue.DeleteVenueUseCase;
import com.carturo.eventhub.domain.ports.in.command.venue.UpdateVenueUseCase;
import com.carturo.eventhub.domain.ports.in.query.venue.GetVenueByIdQuery;
import com.carturo.eventhub.domain.ports.in.query.venue.ListVenuesQuery;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.VenueRequest;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.response.VenueResponse;
import com.carturo.eventhub.infrastructure.adapters.in.web.mapper.VenueWebMapper;
import com.carturo.eventhub.infrastructure.exception.ResourceNotFoundException;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final CreateVenueUseCase createVenueUseCase;
    private final UpdateVenueUseCase updateVenueUseCase;
    private final DeleteVenueUseCase deleteVenueUseCase;
    private final GetVenueByIdQuery getVenueByIdQuery;
    private final ListVenuesQuery listVenuesQuery;
    private final VenueWebMapper mapper;

    public VenueController(
            CreateVenueUseCase createVenueUseCase,
            UpdateVenueUseCase updateVenueUseCase,
            DeleteVenueUseCase deleteVenueUseCase,
            GetVenueByIdQuery getVenueByIdQuery,
            ListVenuesQuery listVenuesQuery,
            VenueWebMapper mapper
    ) {
        this.createVenueUseCase = createVenueUseCase;
        this.updateVenueUseCase = updateVenueUseCase;
        this.deleteVenueUseCase = deleteVenueUseCase;
        this.getVenueByIdQuery = getVenueByIdQuery;
        this.listVenuesQuery = listVenuesQuery;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Create a new venue",
            description = "Creates a new venue.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Venue created successfully",
                            content = @Content(schema = @Schema(implementation = VenueResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid venue data", content = @Content)
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueResponse createVenue(@Valid @RequestBody VenueRequest request) {
        Venue venue = mapper.toDomain(request);
        Venue created = createVenueUseCase.create(venue);
        return mapper.toResponse(created);
    }

    @Operation(
            summary = "Get venues (paged)",
            description = "Retrieves a paginated list of venues.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Venues retrieved",
                            content = @Content(schema = @Schema(implementation = VenueResponse.class)))
            }
    )
    @GetMapping
    public Page<VenueResponse> getVenues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageRequest pageRequest = new PageRequest(page, size);

        var pageResult = listVenuesQuery.list(pageRequest);

        List<VenueResponse> content = pageResult.items().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(content,
                org.springframework.data.domain.PageRequest.of(pageResult.page(), pageResult.size()),
                pageResult.totalItems());
    }

    @Operation(
            summary = "Get venue by ID",
            description = "Retrieves a venue by its id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Venue found",
                            content = @Content(schema = @Schema(implementation = VenueResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public VenueResponse getVenueById(@PathVariable Long id) {
        Venue venue = getVenueByIdQuery.get(id);
        if (venue == null) throw new ResourceNotFoundException("Venue not found");
        return mapper.toResponse(venue);
    }

    @Operation(
            summary = "Update a venue",
            description = "Updates an existing venue by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Venue updated",
                            content = @Content(schema = @Schema(implementation = VenueResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public VenueResponse updateVenue(@PathVariable Long id, @Valid @RequestBody VenueRequest request) {
        Venue venue = mapper.toDomain(request);
        Venue updated = updateVenueUseCase.update(id, venue);
        return mapper.toResponse(updated);
    }

    @Operation(
            summary = "Delete a venue",
            description = "Deletes a venue by id.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Venue deleted"),
                    @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVenue(@PathVariable Long id) {
        deleteVenueUseCase.delete(id);
    }
}