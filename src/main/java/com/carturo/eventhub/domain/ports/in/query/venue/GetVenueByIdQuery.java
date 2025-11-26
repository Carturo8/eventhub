package com.carturo.eventhub.domain.ports.in.query.venue;

import com.carturo.eventhub.domain.model.venue.Venue;
import java.util.Optional;

public interface GetVenueByIdQuery {
    Optional<Venue> get(Long id);
}