package com.carturo.eventhub.domain.ports.in.command.venue;

import com.carturo.eventhub.domain.model.venue.Venue;

public interface UpdateVenueUseCase {
    Venue update(Long id, Venue venue);
}