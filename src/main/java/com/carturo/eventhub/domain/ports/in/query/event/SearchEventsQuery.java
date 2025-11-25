package com.carturo.eventhub.domain.ports.in.query.event;

import com.carturo.eventhub.domain.model.Event;

import java.time.LocalDate;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.model.pagination.PageRequest;

public interface SearchEventsQuery {
    PageResult<Event> search(String city, String category, LocalDate startDate, PageRequest pageRequest);
}