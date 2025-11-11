package com.carturo.eventhub.service.impl;

import com.carturo.eventhub.dto.VenueDTO;
import com.carturo.eventhub.service.VenueService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VenueServiceImpl implements VenueService {

    private final List<VenueDTO> venues = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public VenueDTO create(VenueDTO venue) {
        venue.setId(idCounter.getAndIncrement());
        venues.add(venue);
        return venue;
    }

    @Override
    public List<VenueDTO> findAll() {
        return venues;
    }

    @Override
    public VenueDTO findById(Long id) {
        Optional<VenueDTO> venue = venues.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();
        return venue.orElse(null);
    }

    @Override
    public VenueDTO update(Long id, VenueDTO updatedVenue) {
        VenueDTO existingVenue = findById(id);
        if (existingVenue != null) {
            existingVenue.setName(updatedVenue.getName());
            existingVenue.setLocation(updatedVenue.getLocation());
            existingVenue.setCapacity(updatedVenue.getCapacity());
        }
        return existingVenue;
    }

    @Override
    public void delete(Long id) {
        venues.removeIf(v -> v.getId().equals(id));
    }
}