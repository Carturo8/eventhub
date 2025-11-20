package com.carturo.eventhub.service.impl;

import com.carturo.eventhub.dto.request.VenueRequest;
import com.carturo.eventhub.dto.response.VenueResponse;
import com.carturo.eventhub.entity.VenueEntity;
import com.carturo.eventhub.exception.DuplicateResourceException;
import com.carturo.eventhub.exception.ResourceNotFoundException;
import com.carturo.eventhub.mapper.VenueMapper;
import com.carturo.eventhub.repository.domain.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements com.carturo.eventhub.service.VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Override
    public VenueResponse create(VenueRequest request) {
        validateVenueNameUniqueOnCreate(request.name());
        VenueEntity entity = venueMapper.toEntity(request);
        VenueEntity saved = venueRepository.save(entity);
        return venueMapper.toResponse(saved);
    }

    @Override
    public Page<VenueResponse> findAll(Pageable pageable) {
        Page<VenueEntity> page = venueRepository.findAll(pageable);
        return page.map(venueMapper::toResponse);
    }

    @Override
    public VenueResponse findById(Long id) {
        VenueEntity entity = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));
        return venueMapper.toResponse(entity);
    }

    @Override
    public VenueResponse update(Long id, VenueRequest request) {
        VenueEntity existing = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));
        if (!existing.getName().equalsIgnoreCase(request.name())) {
            validateVenueNameUniqueOnCreate(request.name());
        }
        existing.setName(request.name());
        existing.setCity(request.city());
        existing.setCapacity(request.capacity());
        VenueEntity saved = venueRepository.save(existing);
        return venueMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        VenueEntity existing = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));
        venueRepository.delete(existing.getId());
    }

    private void validateVenueNameUniqueOnCreate(String name) {
        if (venueRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Venue with name '" + name + "' already exists");
        }
    }
}