package com.carturo.eventhub.service;

import com.carturo.eventhub.dto.request.EventRequest;
import com.carturo.eventhub.dto.response.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface EventService {
    EventResponse create(EventRequest request);
    Page<EventResponse> findAll(Pageable pageable);
    EventResponse findById(Long id);
    EventResponse update(Long id, EventRequest request);
    void delete(Long id);
    Page<EventResponse> search(String city, String category, LocalDate startDate, Pageable pageable);
}