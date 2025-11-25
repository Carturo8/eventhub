package com.carturo.eventhub.domain.ports.in.query.venue;

import com.carturo.eventhub.domain.model.Venue;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;

public interface ListVenuesQuery {
    PageResult<Venue> list(PageRequest pageRequest);
}