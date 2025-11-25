package com.carturo.eventhub.domain.ports.in.query.venue;

import com.carturo.eventhub.domain.model.Venue;

public interface GetVenueByIdQuery {
    Venue get(Long id);
}