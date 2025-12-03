package com.carturo.eventhub.application.query.event;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.ports.in.query.event.ListEventsQuery;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class ListEventsQueryImpl implements ListEventsQuery {

    private final EventRepositoryPort eventRepositoryPort;

    public ListEventsQueryImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public PageResult<Event> list(PageRequest pageRequest) {
        if (pageRequest == null) {
            throw new IllegalArgumentException("Page request must not be null");
        }
        return eventRepositoryPort.findAll(pageRequest);
    }
}