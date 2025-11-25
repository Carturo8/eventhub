package com.carturo.eventhub.application.query.venue;

import com.carturo.eventhub.domain.model.Venue;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.ports.in.query.venue.ListVenuesQuery;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;

public class ListVenuesQueryImpl implements ListVenuesQuery {

    private final VenueRepositoryPort venueRepositoryPort;

    public ListVenuesQueryImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public PageResult<Venue> list(PageRequest pageRequest) {
        return venueRepositoryPort.findAll(pageRequest);
    }
}