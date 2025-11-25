package com.carturo.eventhub.application.usecase.event;

import com.carturo.eventhub.domain.model.Event;
import com.carturo.eventhub.domain.ports.in.command.event.UpdateEventUseCase;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;

public class UpdateEventUseCaseImpl implements UpdateEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public UpdateEventUseCaseImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public Event update(Long id, Event event) {

        Event existing = eventRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        existing.setName(event.getName());
        existing.setDescription(event.getDescription());
        existing.setCategory(event.getCategory());
        existing.setDate(event.getDate());
        existing.setVenue(event.getVenue());

        return eventRepositoryPort.save(existing);
    }
}