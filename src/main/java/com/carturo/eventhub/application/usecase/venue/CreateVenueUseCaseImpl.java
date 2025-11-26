package com.carturo.eventhub.application.usecase.venue;

import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.ports.in.command.venue.CreateVenueUseCase;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import com.carturo.eventhub.infrastructure.exception.DuplicateResourceException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CreateVenueUseCaseImpl implements CreateVenueUseCase {

    private final VenueRepositoryPort venueRepositoryPort;

    public CreateVenueUseCaseImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public Venue create(Venue venue) {
        if (venue == null || venue.getName() == null) {
            throw new IllegalArgumentException("Venue data is invalid");
        }
        if (venueRepositoryPort.existsByNameIgnoreCase(venue.getName())) {
            throw new DuplicateResourceException("Venue name already exists");
        }
        return venueRepositoryPort.save(venue);
    }
}