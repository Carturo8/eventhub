package com.carturo.eventhub.service;

import com.carturo.eventhub.dto.VenueDTO;
import java.util.List;

public interface VenueService {
    VenueDTO create(VenueDTO venue);
    List<VenueDTO> findAll();
    VenueDTO findById(Long id);
    VenueDTO update(Long id, VenueDTO venue);
    void delete(Long id);
}