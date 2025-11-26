package com.carturo.eventhub.application.query.event;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.ports.in.query.event.GetEventByIdQuery;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Transactional(readOnly = true)
public class GetEventByIdQueryImpl implements GetEventByIdQuery {

    private final EventRepositoryPort eventRepositoryPort;

    public GetEventByIdQueryImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public Optional<Event> get(Long id) {
        return eventRepositoryPort.findById(id);
    }
}