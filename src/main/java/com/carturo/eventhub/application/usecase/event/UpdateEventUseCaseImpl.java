package com.carturo.eventhub.application.usecase.event;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.ports.in.command.event.UpdateEventUseCase;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import com.carturo.eventhub.infrastructure.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UpdateEventUseCaseImpl implements UpdateEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public UpdateEventUseCaseImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public Event update(Long id, Event event) {
        if (id == null || event == null) {
            throw new IllegalArgumentException("Event data is invalid");
        }

        Event existing = eventRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        existing.setName(event.getName());
        existing.setDescription(event.getDescription());
        existing.setCategory(event.getCategory());
        existing.setEventDate(event.getEventDate());
        existing.setVenue(event.getVenue());
        existing.setStatus(event.getStatus());

        return eventRepositoryPort.save(existing);
    }
}