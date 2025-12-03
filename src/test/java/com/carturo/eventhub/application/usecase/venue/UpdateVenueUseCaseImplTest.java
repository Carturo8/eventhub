package com.carturo.eventhub.application.usecase.venue;

import com.carturo.eventhub.application.exception.ResourceNotFoundException;
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
class UpdateVenueUseCaseImplTest {

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private UpdateVenueUseCaseImpl updateVenueUseCase;

    private Venue existingVenue;
    private Venue updatedVenueInfo;

    @BeforeEach
    void setUp() {
        existingVenue = new Venue(1L, "Old Venue Name", "Old City", 1000);
        updatedVenueInfo = new Venue(null, "New Venue Name", "New City", 1500);
    }

    @Test
    void updateVenue_Successfully() {
        when(venueRepositoryPort.findById(1L)).thenReturn(Optional.of(existingVenue));
        when(venueRepositoryPort.save(any(Venue.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Venue result = updateVenueUseCase.update(1L, updatedVenueInfo);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Venue Name", result.getName());
        assertEquals("New City", result.getCity());
        assertEquals(1500, result.getCapacity());

        verify(venueRepositoryPort, times(1)).findById(1L);
        verify(venueRepositoryPort, times(1)).save(existingVenue);
    }

    @Test
    void updateVenue_WhenVenueNotFound_ThrowsResourceNotFoundException() {
        when(venueRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> updateVenueUseCase.update(1L, updatedVenueInfo));

        verify(venueRepositoryPort, times(1)).findById(1L);
        verify(venueRepositoryPort, never()).save(any(Venue.class));
    }

    @Test
    void updateVenue_WithNullId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> updateVenueUseCase.update(null, updatedVenueInfo));
        verify(venueRepositoryPort, never()).findById(anyLong());
        verify(venueRepositoryPort, never()).save(any(Venue.class));
    }

    @Test
    void updateVenue_WithNullVenue_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> updateVenueUseCase.update(1L, null));
        verify(venueRepositoryPort, never()).findById(anyLong());
        verify(venueRepositoryPort, never()).save(any(Venue.class));
    }
}