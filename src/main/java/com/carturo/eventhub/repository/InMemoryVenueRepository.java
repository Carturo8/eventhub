package com.carturo.eventhub.repository;

import com.carturo.eventhub.dto.VenueDTO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryVenueRepository implements BaseRepository<VenueDTO> {

    private final List<VenueDTO> venues = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public VenueDTO save(VenueDTO venue) {
        if (venue.getId() == null) {
            venue.setId(idCounter.getAndIncrement());
            venues.add(venue);
        } else {
            delete(venue.getId());
            venues.add(venue);
        }
        return venue;
    }

    @Override
    public List<VenueDTO> findAll() {
        return new ArrayList<>(venues);
    }

    @Override
    public Optional<VenueDTO> findById(Long id) {
        return venues.stream().filter(v -> v.getId().equals(id)).findFirst();
    }

    @Override
    public void delete(Long id) {
        venues.removeIf(v -> v.getId().equals(id));
    }
}