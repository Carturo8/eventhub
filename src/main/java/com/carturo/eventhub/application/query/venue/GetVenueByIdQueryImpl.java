package com.carturo.eventhub.application.query.venue;

import com.carturo.eventhub.domain.model.Venue;
import com.carturo.eventhub.domain.ports.in.query.venue.GetVenueByIdQuery;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import com.carturo.eventhub.infrastructure.exception.ResourceNotFoundException;

public class GetVenueByIdQueryImpl implements GetVenueByIdQuery {

    private final VenueRepositoryPort venueRepositoryPort;

    public GetVenueByIdQueryImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public Venue get(Long id) {
        return venueRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
    }
}