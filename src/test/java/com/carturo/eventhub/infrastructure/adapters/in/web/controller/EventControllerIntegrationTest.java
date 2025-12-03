package com.carturo.eventhub.infrastructure.adapters.in.web.controller;

import com.carturo.eventhub.AbstractIntegrationTest;
import com.carturo.eventhub.domain.model.event.EventCategory;
import com.carturo.eventhub.domain.model.event.EventStatus;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.EventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EventControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepositoryPort eventRepositoryPort;

    @Autowired
    private VenueRepositoryPort venueRepositoryPort;

    private Venue testVenue;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        eventRepositoryPort.findAll(new PageRequest(0, 100)).items().forEach(event -> eventRepositoryPort.delete(event.getId()));
        venueRepositoryPort.findAll(new PageRequest(0, 100)).items().forEach(venue -> venueRepositoryPort.delete(venue.getId()));

        testVenue = new Venue(null, "Test Venue", "Test City", 1000);
        testVenue = venueRepositoryPort.save(testVenue);
    }

    @Test
    void createEvent_AsAdmin_ShouldReturnCreatedEvent() throws Exception {
        EventRequest eventRequest = new EventRequest(
                "New Event",
                "Description for new event",
                EventCategory.MUSIC,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
                testVenue.getId()
        );

        mockMvc.perform(post("/api/events")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Event"))
                .andExpect(jsonPath("$.venue.id").value(testVenue.getId()));
    }

    @Test
    void createEvent_AsUser_ShouldReturnForbidden() throws Exception {
        EventRequest eventRequest = new EventRequest(
                "New Event",
                "Description for new event",
                EventCategory.MUSIC,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
                testVenue.getId()
        );

        mockMvc.perform(post("/api/events")
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getEvents_AsUser_ShouldReturnEventsPage() throws Exception {
        eventRepositoryPort.save(new com.carturo.eventhub.domain.model.event.Event(
                null, "Event 1", "Desc 1", EventCategory.MUSIC, LocalDate.now(), LocalDate.now().plusDays(1), testVenue, EventStatus.ACTIVE));
        eventRepositoryPort.save(new com.carturo.eventhub.domain.model.event.Event(
                null, "Event 2", "Desc 2", EventCategory.SPORTS, LocalDate.now(), LocalDate.now().plusDays(2), testVenue, EventStatus.ACTIVE));

        mockMvc.perform(get("/api/events")
                        .with(user("user").roles("USER"))
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(2))
                .andExpect(jsonPath("$.items[0].name").value("Event 1"))
                .andExpect(jsonPath("$.items[1].name").value("Event 2"));
    }

    @Test
    void getEventById_AsUser_ShouldReturnEvent() throws Exception {
        com.carturo.eventhub.domain.model.event.Event savedEvent = eventRepositoryPort.save(new com.carturo.eventhub.domain.model.event.Event(
                null, "Single Event", "Single Desc", EventCategory.MUSIC, LocalDate.now(), LocalDate.now().plusDays(1), testVenue, EventStatus.ACTIVE));

        mockMvc.perform(get("/api/events/{id}", savedEvent.getId())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedEvent.getId()))
                .andExpect(jsonPath("$.name").value("Single Event"));
    }

    @Test
    void getEventById_WhenNotFound_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/events/{id}", 999L)
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateEvent_AsAdmin_ShouldReturnUpdatedEvent() throws Exception {
        com.carturo.eventhub.domain.model.event.Event savedEvent = eventRepositoryPort.save(new com.carturo.eventhub.domain.model.event.Event(
                null, "Original Event", "Original Desc", EventCategory.MUSIC, LocalDate.now(), LocalDate.now().plusDays(1), testVenue, EventStatus.ACTIVE));

        EventRequest updatedRequest = new EventRequest(
                "Updated Event",
                "Updated Description",
                EventCategory.SPORTS,
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(6),
                testVenue.getId()
        );

        mockMvc.perform(put("/api/events/{id}", savedEvent.getId())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedEvent.getId()))
                .andExpect(jsonPath("$.name").value("Updated Event"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void updateEvent_AsUser_ShouldReturnForbidden() throws Exception {
        com.carturo.eventhub.domain.model.event.Event savedEvent = eventRepositoryPort.save(new com.carturo.eventhub.domain.model.event.Event(
                null, "Original Event", "Original Desc", EventCategory.MUSIC, LocalDate.now(), LocalDate.now().plusDays(1), testVenue, EventStatus.ACTIVE));

        EventRequest updatedRequest = new EventRequest(
                "Updated Event",
                "Updated Description",
                EventCategory.SPORTS,
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(6),
                testVenue.getId()
        );

        mockMvc.perform(put("/api/events/{id}", savedEvent.getId())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteEvent_AsAdmin_ShouldReturnNoContent() throws Exception {
        com.carturo.eventhub.domain.model.event.Event savedEvent = eventRepositoryPort.save(new com.carturo.eventhub.domain.model.event.Event(
                null, "Event to Delete", "Desc", EventCategory.MUSIC, LocalDate.now(), LocalDate.now().plusDays(1), testVenue, EventStatus.ACTIVE));

        mockMvc.perform(delete("/api/events/{id}", savedEvent.getId())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/events/{id}", savedEvent.getId())
                        .with(user("user").roles("USER")))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEvent_AsUser_ShouldReturnForbidden() throws Exception {
        com.carturo.eventhub.domain.model.event.Event savedEvent = eventRepositoryPort.save(new com.carturo.eventhub.domain.model.event.Event(
                null, "Event to Delete", "Desc", EventCategory.MUSIC, LocalDate.now(), LocalDate.now().plusDays(1), testVenue, EventStatus.ACTIVE));

        mockMvc.perform(delete("/api/events/{id}", savedEvent.getId())
                        .with(user("user").roles("USER")))
                .andExpect(status().isForbidden());
    }
}