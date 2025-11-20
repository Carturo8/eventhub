package com.carturo.eventhub.controller;

import com.carturo.eventhub.docs.VenueControllerDoc;
import com.carturo.eventhub.dto.request.VenueRequest;
import com.carturo.eventhub.dto.response.VenueResponse;
import com.carturo.eventhub.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController implements VenueControllerDoc {

    private final VenueService venueService;

    @Override
    @PostMapping
    public VenueResponse createVenue(@RequestBody VenueRequest request) {
        return venueService.create(request);
    }

    @Override
    @GetMapping
    public Page<VenueResponse> getVenues(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        return venueService.findAll(pageable);
    }

    @Override
    @GetMapping("/{id}")
    public VenueResponse getVenueById(@PathVariable Long id) {
        return venueService.findById(id);
    }

    @Override
    @PutMapping("/{id}")
    public VenueResponse updateVenue(@PathVariable Long id, @RequestBody VenueRequest request) {
        return venueService.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id) {
        venueService.delete(id);
    }
}