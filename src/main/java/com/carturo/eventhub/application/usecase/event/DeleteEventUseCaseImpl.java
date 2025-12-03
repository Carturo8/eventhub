package com.carturo.eventhub.application.usecase.event;

import com.carturo.eventhub.domain.ports.in.command.event.DeleteEventUseCase;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import com.carturo.eventhub.application.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DeleteEventUseCaseImpl implements DeleteEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public DeleteEventUseCaseImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Event ID must not be null");
        }
        if (eventRepositoryPort.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Event not found");
        }
        eventRepositoryPort.delete(id);
    }
}