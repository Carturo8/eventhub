package com.carturo.eventhub.application.query.venue;

import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.ports.in.query.venue.GetVenueByIdQuery;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Transactional(readOnly = true)
public class GetVenueByIdQueryImpl implements GetVenueByIdQuery {

    private final VenueRepositoryPort venueRepositoryPort;

    public GetVenueByIdQueryImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public Optional<Venue> get(Long id) {
        return venueRepositoryPort.findById(id);
    }
}