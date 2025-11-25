package com.carturo.eventhub.application.usecase.event;

import com.carturo.eventhub.domain.ports.in.command.event.DeleteEventUseCase;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import com.carturo.eventhub.infrastructure.exception.ResourceNotFoundException;

public class DeleteEventUseCaseImpl implements DeleteEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public DeleteEventUseCaseImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public void delete(Long id) {
        boolean exists = eventRepositoryPort.findById(id).isPresent();
        if (!exists) {
            throw new ResourceNotFoundException("Event not found");
        }
        eventRepositoryPort.delete(id);
    }
}