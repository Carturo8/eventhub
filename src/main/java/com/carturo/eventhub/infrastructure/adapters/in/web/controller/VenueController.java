package com.carturo.eventhub.infrastructure.adapters.in.web.controller;

import com.carturo.eventhub.application.exception.ResourceNotFoundException;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.model.venue.VenueFilter;
import com.carturo.eventhub.domain.ports.in.command.venue.CreateVenueUseCase;
import com.carturo.eventhub.domain.ports.in.command.venue.DeleteVenueUseCase;
import com.carturo.eventhub.domain.ports.in.command.venue.UpdateVenueUseCase;
import com.carturo.eventhub.domain.ports.in.query.venue.GetVenueByIdQuery;
import com.carturo.eventhub.domain.ports.in.query.venue.ListVenuesQuery;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.VenueRequest;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.response.PageResponse;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.response.VenueResponse;
import com.carturo.eventhub.infrastructure.adapters.in.web.mapper.VenueWebMapper;
import com.carturo.eventhub.infrastructure.adapters.in.web.validation.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueResponse createVenue(@Validated(ValidationGroups.Create.class) @RequestBody VenueRequest request) {
        Venue venue = mapper.toDomain(request);
        Venue created = createVenueUseCase.create(venue);
        return mapper.toResponse(created);
    }

    @GetMapping
    public PageResponse<VenueResponse> getVenues(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageRequest pageRequest = new PageRequest(page, size);
        VenueFilter filter = new VenueFilter(city, minCapacity, maxCapacity);
        var pageResult = listVenuesQuery.list(pageRequest, filter);

        List<VenueResponse> content = pageResult.items().stream()
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
    public VenueResponse getVenueById(@PathVariable Long id) {
        return getVenueByIdQuery.get(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with ID: " + id));
    }



    @PutMapping("/{id}")
    public VenueResponse updateVenue(@PathVariable Long id, @Validated(ValidationGroups.Update.class) @RequestBody VenueRequest request) {
        Venue venue = mapper.toDomain(request);
        Venue updated = updateVenueUseCase.update(id, venue);
        return mapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVenue(@PathVariable Long id) {
        deleteVenueUseCase.delete(id);
    }
}