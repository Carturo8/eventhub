package com.carturo.eventhub.service.impl;

import com.carturo.eventhub.dto.VenueDTO;
import com.carturo.eventhub.repository.InMemoryVenueRepository;
import com.carturo.eventhub.service.VenueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryVenueServiceImpl implements VenueService {

    private final InMemoryVenueRepository venueRepository;

    public InMemoryVenueServiceImpl(InMemoryVenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public VenueDTO create(VenueDTO venue) {
        return venueRepository.save(venue);
    }

    @Override
    public List<VenueDTO> findAll() {
        return venueRepository.findAll();
    }

    @Override
    public VenueDTO findById(Long id) {
        return venueRepository.findById(id).orElse(null);
    }

    @Override
    public VenueDTO update(Long id, VenueDTO updatedVenue) {
        VenueDTO existing = findById(id);
        if (existing != null) {
            updatedVenue.setId(id);
            return venueRepository.save(updatedVenue);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        venueRepository.delete(id);
    }
}