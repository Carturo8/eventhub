package com.carturo.eventhub.application.query.event;

import com.carturo.eventhub.domain.model.Event;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.ports.in.query.event.ListEventsQuery;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;

public class ListEventsQueryImpl implements ListEventsQuery {

    private final EventRepositoryPort eventRepositoryPort;

    public ListEventsQueryImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public PageResult<Event> list(PageRequest pageRequest) {
        return eventRepositoryPort.findAll(pageRequest);
    }
}