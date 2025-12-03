package com.carturo.eventhub.domain.ports.in.query.venue;

import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.model.venue.VenueFilter;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;

public interface ListVenuesQuery {
    PageResult<Venue> list(PageRequest pageRequest, VenueFilter filter);

    default PageResult<Venue> list(PageRequest pageRequest) {
        return list(pageRequest, VenueFilter.empty());
    }
}