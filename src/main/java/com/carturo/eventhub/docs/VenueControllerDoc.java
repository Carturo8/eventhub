package com.carturo.eventhub.docs;

import com.carturo.eventhub.dto.request.VenueRequest;
import com.carturo.eventhub.dto.response.VenueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

public interface VenueControllerDoc {

    @Operation(
            summary = "Create a new venue",
            description = "Creates a new venue.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Venue created successfully",
                            content = @Content(schema = @Schema(implementation = VenueResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid venue data", content = @Content)
            }
    )
    VenueResponse createVenue(VenueRequest request);

    @Operation(
            summary = "Get venues (paged)",
            description = "Retrieves a paginated list of venues.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Venues retrieved",
                            content = @Content(schema = @Schema(implementation = VenueResponse.class)))
            }
    )
    Page<VenueResponse> getVenues(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    );

    @Operation(
            summary = "Get venue by ID",
            description = "Retrieves a venue by its id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Venue found",
                            content = @Content(schema = @Schema(implementation = VenueResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
            }
    )
    VenueResponse getVenueById(Long id);

    @Operation(
            summary = "Update a venue",
            description = "Updates an existing venue.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Venue updated",
                            content = @Content(schema = @Schema(implementation = VenueResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
            }
    )
    VenueResponse updateVenue(Long id, VenueRequest request);

    @Operation(
            summary = "Delete a venue",
            description = "Deletes a venue by id.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Venue deleted"),
                    @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
            }
    )
    void deleteVenue(Long id);
}