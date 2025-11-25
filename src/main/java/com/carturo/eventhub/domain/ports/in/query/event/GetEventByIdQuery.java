package com.carturo.eventhub.domain.ports.in.query.event;

import com.carturo.eventhub.domain.model.Event;

public interface GetEventByIdQuery {
    Event get(Long id);
}