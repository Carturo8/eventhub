package com.carturo.eventhub.service;

import com.carturo.eventhub.dto.EventDTO;
import java.util.List;

public interface EventService {
    EventDTO create(EventDTO event);
    List<EventDTO> findAll();
    EventDTO findById(Long id);
    EventDTO update(Long id, EventDTO event);
    void delete(Long id);
}