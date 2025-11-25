package com.carturo.eventhub.domain.ports.in.command.venue;

import com.carturo.eventhub.domain.model.Venue;

public interface CreateVenueUseCase {
    Venue create(Venue venue);
}