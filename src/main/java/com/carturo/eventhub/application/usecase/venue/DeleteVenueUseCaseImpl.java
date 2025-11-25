package com.carturo.eventhub.application.usecase.venue;

import com.carturo.eventhub.domain.ports.in.command.venue.DeleteVenueUseCase;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import com.carturo.eventhub.infrastructure.exception.ResourceNotFoundException;

public class DeleteVenueUseCaseImpl implements DeleteVenueUseCase {

    private final VenueRepositoryPort venueRepositoryPort;

    public DeleteVenueUseCaseImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public void delete(Long id) {
        boolean exists = venueRepositoryPort.findById(id).isPresent();
        if (!exists) {
            throw new ResourceNotFoundException("Venue not found");
        }
        venueRepositoryPort.delete(id);
    }
}