package com.carturo.eventhub.application.usecase.event;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.ports.in.command.event.CreateEventUseCase;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import com.carturo.eventhub.application.exception.DuplicateResourceException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateEventUseCaseImpl implements CreateEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;
    private final Counter createdEventsCounter;

    public CreateEventUseCaseImpl(EventRepositoryPort eventRepositoryPort, MeterRegistry meterRegistry) {
        this.eventRepositoryPort = eventRepositoryPort;
        this.createdEventsCounter = meterRegistry.counter("events.created.total");
    }

    @Override
    public Event create(Event event) {
        if (event == null || event.getName() == null) {
            throw new IllegalArgumentException("Event data is invalid");
        }
        if (eventRepositoryPort.existsByNameIgnoreCase(event.getName())) {
            throw new DuplicateResourceException("Event name already exists");
        }
        createdEventsCounter.increment();
        return eventRepositoryPort.save(event);
    }
}