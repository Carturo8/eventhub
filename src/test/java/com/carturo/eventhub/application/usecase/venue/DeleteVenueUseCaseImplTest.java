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
class DeleteVenueUseCaseImplTest {

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private DeleteVenueUseCaseImpl deleteVenueUseCase;

    private Long existingVenueId;
    private Long nonExistingVenueId;

    @BeforeEach
    void setUp() {
        existingVenueId = 1L;
        nonExistingVenueId = 99L;
    }

    @Test
    void deleteVenue_Successfully() {
        when(venueRepositoryPort.findById(existingVenueId)).thenReturn(Optional.of(new Venue()));
        doNothing().when(venueRepositoryPort).delete(existingVenueId);

        assertDoesNotThrow(() -> deleteVenueUseCase.delete(existingVenueId));

        verify(venueRepositoryPort, times(1)).findById(existingVenueId);
        verify(venueRepositoryPort, times(1)).delete(existingVenueId);
    }

    @Test
    void deleteVenue_WhenVenueNotFound_ThrowsResourceNotFoundException() {
        when(venueRepositoryPort.findById(nonExistingVenueId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deleteVenueUseCase.delete(nonExistingVenueId));

        verify(venueRepositoryPort, times(1)).findById(nonExistingVenueId);
        verify(venueRepositoryPort, never()).delete(anyLong());
    }

    @Test
    void deleteVenue_WithNullId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> deleteVenueUseCase.delete(null));

        verify(venueRepositoryPort, never()).findById(anyLong());
        verify(venueRepositoryPort, never()).delete(anyLong());
    }
}