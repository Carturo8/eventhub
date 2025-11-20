package com.carturo.eventhub.service;

import com.carturo.eventhub.dto.request.VenueRequest;
import com.carturo.eventhub.dto.response.VenueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenueService {
    VenueResponse create(VenueRequest request);
    Page<VenueResponse> findAll(Pageable pageable);
    VenueResponse findById(Long id);
    VenueResponse update(Long id, VenueRequest request);
    void delete(Long id);
}