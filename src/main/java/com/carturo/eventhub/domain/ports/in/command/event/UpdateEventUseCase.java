package com.carturo.eventhub.domain.ports.in.command.event;

import com.carturo.eventhub.domain.model.event.Event;

public interface UpdateEventUseCase {
    Event update(Long id, Event event);
}