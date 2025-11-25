package com.carturo.eventhub.domain.ports.in.command.event;

import com.carturo.eventhub.domain.model.Event;

public interface CreateEventUseCase {
    Event create(Event event);
}