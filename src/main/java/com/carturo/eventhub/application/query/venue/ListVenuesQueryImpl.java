package com.carturo.eventhub.application.query.venue;

import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.model.venue.VenueFilter;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.ports.in.query.venue.ListVenuesQuery;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class ListVenuesQueryImpl implements ListVenuesQuery {

    private final VenueRepositoryPort venueRepositoryPort;

    public ListVenuesQueryImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public PageResult<Venue> list(PageRequest pageRequest, VenueFilter filter) {
        if (pageRequest == null) {
            throw new IllegalArgumentException("Page request must not be null");
        }
        return venueRepositoryPort.findAll(pageRequest, filter);
    }
}