package com.carturo.eventhub.domain.ports.in.query.event;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.model.event.EventFilter;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.model.pagination.PageRequest;

public interface SearchEventsQuery {
    PageResult<Event> search(EventFilter filter, PageRequest pageRequest);
}