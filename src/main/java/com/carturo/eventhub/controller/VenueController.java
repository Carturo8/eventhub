package com.carturo.eventhub.controller;

import com.carturo.eventhub.dto.VenueDTO;
import com.carturo.eventhub.exception.ApiError;
import com.carturo.eventhub.service.VenueService;
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

@Tag(name = "Venues", description = "Endpoints for managing venues")
@RestController
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @Operation(
            summary = "Create a new venue",
            description = "Registers a new venue in the in-memory catalog",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Venue created successfully",
                            content = @Content(schema = @Schema(implementation = VenueDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid venue data",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @PostMapping
    public ResponseEntity<?> createVenue(@Valid @RequestBody VenueDTO venue) {
        VenueDTO createdVenue = venueService.create(venue);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVenue);
    }

    @Operation(
            summary = "Get all venues",
            description = "Retrieves all venues stored in memory",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully",
                            content = @Content(schema = @Schema(implementation = VenueDTO.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        return ResponseEntity.ok(venueService.findAll());
    }

    @Operation(
            summary = "Get venue by ID",
            description = "Retrieves a venue by its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Venue found",
                            content = @Content(schema = @Schema(implementation = VenueDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Venue not found",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getVenueById(@PathVariable Long id) {
        VenueDTO venue = venueService.findById(id);
        if (venue == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(404, "Venue not found"));
        }
        return ResponseEntity.ok(venue);
    }

    @Operation(
            summary = "Update a venue",
            description = "Updates the details of an existing venue",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Venue updated successfully",
                            content = @Content(schema = @Schema(implementation = VenueDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Venue not found",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueDTO updatedVenue) {
        VenueDTO venue = venueService.update(id, updatedVenue);
        if (venue == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(404, "Venue not found"));
        }
        return ResponseEntity.ok(venue);
    }

    @Operation(
            summary = "Delete a venue",
            description = "Removes a venue from the catalog by its ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Venue not found",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVenue(@PathVariable Long id) {
        VenueDTO venue = venueService.findById(id);
        if (venue == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(404, "Venue not found"));
        }
        venueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}