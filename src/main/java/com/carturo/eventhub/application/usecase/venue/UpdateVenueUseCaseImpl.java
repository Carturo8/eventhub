package com.carturo.eventhub.application.usecase.venue;

import com.carturo.eventhub.domain.model.Venue;
import com.carturo.eventhub.domain.ports.in.command.venue.UpdateVenueUseCase;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;

public class UpdateVenueUseCaseImpl implements UpdateVenueUseCase {

    private final VenueRepositoryPort venueRepositoryPort;

    public UpdateVenueUseCaseImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public Venue update(Long id, Venue venue) {

        Venue existing = venueRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venue not found"));

        existing.setName(venue.getName());
        existing.setCity(venue.getCity());
        existing.setCapacity(venue.getCapacity());

        return venueRepositoryPort.save(existing);
    }
}