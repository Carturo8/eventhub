package com.carturo.eventhub.repository.inmemory;

import com.carturo.eventhub.entity.VenueEntity;
import com.carturo.eventhub.repository.domain.VenueRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("inmemory")
public class InMemoryVenueRepository implements VenueRepository {

    private final Map<Long, VenueEntity> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public VenueEntity save(VenueEntity venue) {
        if (venue.getId() == null) {
            venue.setId(idCounter.getAndIncrement());
        }
        storage.put(venue.getId(), venue);
        return venue;
    }

    @Override
    public List<VenueEntity> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<VenueEntity> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }
}