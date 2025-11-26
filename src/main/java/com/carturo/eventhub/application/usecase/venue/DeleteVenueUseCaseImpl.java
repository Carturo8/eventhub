package com.carturo.eventhub.application.usecase.venue;

import com.carturo.eventhub.domain.ports.in.command.venue.DeleteVenueUseCase;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import com.carturo.eventhub.infrastructure.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DeleteVenueUseCaseImpl implements DeleteVenueUseCase {

    private final VenueRepositoryPort venueRepositoryPort;

    public DeleteVenueUseCaseImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Venue ID must not be null");
        }
        if (venueRepositoryPort.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Venue not found");
        }
        venueRepositoryPort.delete(id);
    }
}