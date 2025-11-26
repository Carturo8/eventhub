package com.carturo.eventhub.application.usecase.venue;

import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.ports.in.command.venue.UpdateVenueUseCase;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import com.carturo.eventhub.infrastructure.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UpdateVenueUseCaseImpl implements UpdateVenueUseCase {

    private final VenueRepositoryPort venueRepositoryPort;

    public UpdateVenueUseCaseImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public Venue update(Long id, Venue venue) {
        if (id == null || venue == null) {
            throw new IllegalArgumentException("Venue data is invalid");
        }

        Venue existing = venueRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

        existing.setName(venue.getName());
        existing.setCity(venue.getCity());
        existing.setCapacity(venue.getCapacity());

        return venueRepositoryPort.save(existing);
    }
}