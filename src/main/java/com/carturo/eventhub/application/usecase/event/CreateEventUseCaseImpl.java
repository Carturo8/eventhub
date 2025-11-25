package com.carturo.eventhub.application.usecase.event;

import com.carturo.eventhub.domain.model.Event;
import com.carturo.eventhub.domain.ports.in.command.event.CreateEventUseCase;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;

public class CreateEventUseCaseImpl implements CreateEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public CreateEventUseCaseImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public Event create(Event event) {

        if (eventRepositoryPort.existsByNameIgnoreCase(event.getName())) {
            throw new IllegalArgumentException("Event name already exists");
        }

        return eventRepositoryPort.save(event);
    }
}