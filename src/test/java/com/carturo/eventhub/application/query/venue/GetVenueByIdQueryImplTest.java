package com.carturo.eventhub.application.query.venue;

import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetVenueByIdQueryImplTest {

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private GetVenueByIdQueryImpl getVenueByIdQuery;

    private Long existingVenueId;
    private Long nonExistingVenueId;
    private Venue venue;

    @BeforeEach
    void setUp() {
        existingVenueId = 1L;
        nonExistingVenueId = 99L;
        venue = new Venue(existingVenueId, "Test Venue", "Test City", 1000);
    }

    @Test
    void getVenueById_Successfully() {
        when(venueRepositoryPort.findById(existingVenueId)).thenReturn(Optional.of(venue));

        Optional<Venue> result = getVenueByIdQuery.get(existingVenueId);

        assertTrue(result.isPresent());
        assertEquals(venue, result.get());
        verify(venueRepositoryPort, times(1)).findById(existingVenueId);
    }

    @Test
    void getVenueById_WhenNotFound_ReturnsEmptyOptional() {
        when(venueRepositoryPort.findById(nonExistingVenueId)).thenReturn(Optional.empty());

        Optional<Venue> result = getVenueByIdQuery.get(nonExistingVenueId);

        assertFalse(result.isPresent());
        verify(venueRepositoryPort, times(1)).findById(nonExistingVenueId);
    }

    @Test
    void getVenueById_WithNullId_ReturnsEmptyOptionalAndDoesNotCallRepository() {
        Optional<Venue> result = getVenueByIdQuery.get(null);

        assertFalse(result.isPresent());
        verify(venueRepositoryPort, never()).findById(anyLong());
    }
}