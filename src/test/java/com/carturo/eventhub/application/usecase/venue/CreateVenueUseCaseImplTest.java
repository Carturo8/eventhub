package com.carturo.eventhub.application.usecase.venue;

import com.carturo.eventhub.application.exception.DuplicateResourceException;
import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateVenueUseCaseImplTest {

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private CreateVenueUseCaseImpl createVenueUseCase;

    private Venue venue;

    @BeforeEach
    void setUp() {
        venue = new Venue(null, "Test Venue", "Test City", 1000);
    }

    @Test
    void createVenue_Successfully() {
        when(venueRepositoryPort.existsByNameIgnoreCase("Test Venue")).thenReturn(false);
        when(venueRepositoryPort.save(venue)).thenReturn(new Venue(1L, "Test Venue", "Test City", 1000));

        Venue createdVenue = createVenueUseCase.create(venue);

        assertNotNull(createdVenue);
        assertEquals(1L, createdVenue.getId());
        assertEquals("Test Venue", createdVenue.getName());
        verify(venueRepositoryPort, times(1)).existsByNameIgnoreCase("Test Venue");
        verify(venueRepositoryPort, times(1)).save(any(Venue.class));
    }

    @Test
    void createVenue_WhenVenueAlreadyExists_ThrowsDuplicateResourceException() {
        when(venueRepositoryPort.existsByNameIgnoreCase("Test Venue")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> createVenueUseCase.create(venue));

        verify(venueRepositoryPort, times(1)).existsByNameIgnoreCase("Test Venue");
        verify(venueRepositoryPort, never()).save(any(Venue.class));
    }

    @Test
    void createVenue_WithNullVenue_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> createVenueUseCase.create(null));
        verify(venueRepositoryPort, never()).existsByNameIgnoreCase(anyString());
        verify(venueRepositoryPort, never()).save(any(Venue.class));
    }

    @Test
    void createVenue_WithNullVenueName_ThrowsIllegalArgumentException() {
        venue.setName(null);
        assertThrows(IllegalArgumentException.class, () -> createVenueUseCase.create(venue));
        verify(venueRepositoryPort, never()).existsByNameIgnoreCase(anyString());
        verify(venueRepositoryPort, never()).save(any(Venue.class));
    }
}