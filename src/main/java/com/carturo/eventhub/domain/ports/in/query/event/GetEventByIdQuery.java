package com.carturo.eventhub.domain.ports.in.query.event;

import com.carturo.eventhub.domain.model.event.Event;
import java.util.Optional;

public interface GetEventByIdQuery {
    Optional<Event> get(Long id);
}