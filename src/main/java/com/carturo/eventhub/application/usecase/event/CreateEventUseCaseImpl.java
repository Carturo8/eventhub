package com.carturo.eventhub.application.usecase.event;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.ports.in.command.event.CreateEventUseCase;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import com.carturo.eventhub.application.exception.DuplicateResourceException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CreateEventUseCaseImpl implements CreateEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public CreateEventUseCaseImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public Event create(Event event) {
        if (event == null || event.getName() == null) {
            throw new IllegalArgumentException("Event data is invalid");
        }
        if (eventRepositoryPort.existsByNameIgnoreCase(event.getName())) {
            throw new DuplicateResourceException("Event name already exists");
        }
        return eventRepositoryPort.save(event);
    }
}