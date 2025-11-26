package com.carturo.eventhub.application.query.event;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.model.event.EventFilter;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.ports.in.query.event.SearchEventsQuery;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SearchEventsQueryImpl implements SearchEventsQuery {

    private final EventRepositoryPort eventRepositoryPort;

    public SearchEventsQueryImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public PageResult<Event> search(EventFilter filter, PageRequest pageRequest) {
        return eventRepositoryPort.search(filter, pageRequest);
    }
}