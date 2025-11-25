package com.carturo.eventhub.application.query.event;

import com.carturo.eventhub.domain.model.Event;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.ports.in.query.event.SearchEventsQuery;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;

import java.time.LocalDate;

public class SearchEventsQueryImpl implements SearchEventsQuery {

    private final EventRepositoryPort eventRepositoryPort;

    public SearchEventsQueryImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public PageResult<Event> search(String city, String category, LocalDate startDate, PageRequest pageRequest) {
        return eventRepositoryPort.search(city, category, startDate, pageRequest);
    }
}