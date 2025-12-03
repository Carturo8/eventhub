package com.carturo.eventhub.infrastructure.config;

import com.carturo.eventhub.application.query.event.GetEventByIdQueryImpl;
import com.carturo.eventhub.application.query.event.ListEventsQueryImpl;
import com.carturo.eventhub.application.query.event.SearchEventsQueryImpl;
import com.carturo.eventhub.application.query.venue.GetVenueByIdQueryImpl;
import com.carturo.eventhub.application.query.venue.ListVenuesQueryImpl;
import com.carturo.eventhub.application.usecase.event.CreateEventUseCaseImpl;
import com.carturo.eventhub.application.usecase.event.DeleteEventUseCaseImpl;
import com.carturo.eventhub.application.usecase.event.UpdateEventUseCaseImpl;
import com.carturo.eventhub.application.usecase.venue.CreateVenueUseCaseImpl;
import com.carturo.eventhub.application.usecase.venue.DeleteVenueUseCaseImpl;
import com.carturo.eventhub.application.usecase.venue.UpdateVenueUseCaseImpl;
import com.carturo.eventhub.domain.ports.in.command.event.CreateEventUseCase;
import com.carturo.eventhub.domain.ports.in.command.event.DeleteEventUseCase;
import com.carturo.eventhub.domain.ports.in.command.event.UpdateEventUseCase;
import com.carturo.eventhub.domain.ports.in.command.venue.CreateVenueUseCase;
import com.carturo.eventhub.domain.ports.in.command.venue.DeleteVenueUseCase;
import com.carturo.eventhub.domain.ports.in.command.venue.UpdateVenueUseCase;
import com.carturo.eventhub.domain.ports.in.query.event.GetEventByIdQuery;
import com.carturo.eventhub.domain.ports.in.query.event.ListEventsQuery;
import com.carturo.eventhub.domain.ports.in.query.event.SearchEventsQuery;
import com.carturo.eventhub.domain.ports.in.query.venue.GetVenueByIdQuery;
import com.carturo.eventhub.domain.ports.in.query.venue.ListVenuesQuery;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeansConfig {

    // ===== Event UseCases =====
    @Bean
    public CreateEventUseCase createEventUseCase(EventRepositoryPort eventRepositoryPort, MeterRegistry meterRegistry) {
        return new CreateEventUseCaseImpl(eventRepositoryPort, meterRegistry);
    }

    @Bean
    public UpdateEventUseCase updateEventUseCase(EventRepositoryPort eventRepositoryPort) {
        return new UpdateEventUseCaseImpl(eventRepositoryPort);
    }

    @Bean
    public DeleteEventUseCase deleteEventUseCase(EventRepositoryPort eventRepositoryPort) {
        return new DeleteEventUseCaseImpl(eventRepositoryPort);
    }

    // ===== Event Queries =====
    @Bean
    public GetEventByIdQuery getEventByIdQuery(EventRepositoryPort eventRepositoryPort) {
        return new GetEventByIdQueryImpl(eventRepositoryPort);
    }

    @Bean
    public ListEventsQuery listEventsQuery(EventRepositoryPort eventRepositoryPort) {
        return new ListEventsQueryImpl(eventRepositoryPort);
    }

    @Bean
    public SearchEventsQuery searchEventsQuery(EventRepositoryPort eventRepositoryPort) {
        return new SearchEventsQueryImpl(eventRepositoryPort);
    }

    // ===== Venue UseCases =====
    @Bean
    public CreateVenueUseCase createVenueUseCase(VenueRepositoryPort venueRepositoryPort) {
        return new CreateVenueUseCaseImpl(venueRepositoryPort);
    }

    @Bean
    public UpdateVenueUseCase updateVenueUseCase(VenueRepositoryPort venueRepositoryPort) {
        return new UpdateVenueUseCaseImpl(venueRepositoryPort);
    }

    @Bean
    public DeleteVenueUseCase deleteVenueUseCase(VenueRepositoryPort venueRepositoryPort) {
        return new DeleteVenueUseCaseImpl(venueRepositoryPort);
    }

    // ===== Venue Queries =====
    @Bean
    public GetVenueByIdQuery getVenueByIdQuery(VenueRepositoryPort venueRepositoryPort) {
        return new GetVenueByIdQueryImpl(venueRepositoryPort);
    }

    @Bean
    public ListVenuesQuery listVenuesQuery(VenueRepositoryPort venueRepositoryPort) {
        return new ListVenuesQueryImpl(venueRepositoryPort);
    }
}