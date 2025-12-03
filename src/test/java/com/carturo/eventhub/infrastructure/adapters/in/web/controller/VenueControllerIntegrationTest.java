package com.carturo.eventhub.infrastructure.adapters.in.web.controller;

import com.carturo.eventhub.AbstractIntegrationTest;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.VenueRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VenueControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VenueRepositoryPort venueRepositoryPort;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        venueRepositoryPort.findAll(new PageRequest(0, 100)).items().forEach(venue -> venueRepositoryPort.delete(venue.getId()));
    }

    @Test
    void createVenue_AsAdmin_ShouldReturnCreatedVenue() throws Exception {
        VenueRequest venueRequest = new VenueRequest(
                "New Venue",
                "New City",
                2000
        );

        mockMvc.perform(post("/api/venues")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venueRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Venue"))
                .andExpect(jsonPath("$.city").value("New City"));
    }

    @Test
    void createVenue_AsUser_ShouldReturnForbidden() throws Exception {
        VenueRequest venueRequest = new VenueRequest(
                "New Venue",
                "New City",
                2000
        );

        mockMvc.perform(post("/api/venues")
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venueRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getVenues_AsUser_ShouldReturnVenuesPage() throws Exception {
        venueRepositoryPort.save(new Venue(null, "Venue 1", "City A", 1000));
        venueRepositoryPort.save(new Venue(null, "Venue 2", "City B", 2000));

        mockMvc.perform(get("/api/venues")
                        .with(user("user").roles("USER"))
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(2))
                .andExpect(jsonPath("$.items[0].name").value("Venue 1"))
                .andExpect(jsonPath("$.items[1].name").value("Venue 2"));
    }

    @Test
    void getVenueById_AsUser_ShouldReturnVenue() throws Exception {
        Venue savedVenue = venueRepositoryPort.save(new Venue(null, "Single Venue", "Single City", 1500));

        mockMvc.perform(get("/api/venues/{id}", savedVenue.getId())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedVenue.getId()))
                .andExpect(jsonPath("$.name").value("Single Venue"));
    }

    @Test
    void getVenueById_WhenNotFound_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/venues/{id}", 999L)
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateVenue_AsAdmin_ShouldReturnUpdatedVenue() throws Exception {
        Venue savedVenue = venueRepositoryPort.save(new Venue(null, "Original Venue", "Original City", 1000));

        VenueRequest updatedRequest = new VenueRequest(
                "Updated Venue",
                "Updated City",
                1200
        );

        mockMvc.perform(put("/api/venues/{id}", savedVenue.getId())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedVenue.getId()))
                .andExpect(jsonPath("$.name").value("Updated Venue"))
                .andExpect(jsonPath("$.city").value("Updated City"));
    }

    @Test
    void updateVenue_AsUser_ShouldReturnForbidden() throws Exception {
        Venue savedVenue = venueRepositoryPort.save(new Venue(null, "Original Venue", "Original City", 1000));

        VenueRequest updatedRequest = new VenueRequest(
                "Updated Venue",
                "Updated City",
                1200
        );

        mockMvc.perform(put("/api/venues/{id}", savedVenue.getId())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteVenue_AsAdmin_ShouldReturnNoContent() throws Exception {
        Venue savedVenue = venueRepositoryPort.save(new Venue(null, "Venue to Delete", "City", 500));

        mockMvc.perform(delete("/api/venues/{id}", savedVenue.getId())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/venues/{id}", savedVenue.getId())
                        .with(user("user").roles("USER")))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteVenue_AsUser_ShouldReturnForbidden() throws Exception {
        Venue savedVenue = venueRepositoryPort.save(new Venue(null, "Venue to Delete", "City", 500));

        mockMvc.perform(delete("/api/venues/{id}", savedVenue.getId())
                        .with(user("user").roles("USER")))
                .andExpect(status().isForbidden());
    }
}