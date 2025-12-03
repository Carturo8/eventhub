package com.carturo.eventhub.application.usecase.event;

import com.carturo.eventhub.application.exception.ResourceNotFoundException;
import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
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
class DeleteEventUseCaseImplTest {

    @Mock
    private EventRepositoryPort eventRepositoryPort;

    @InjectMocks
    private DeleteEventUseCaseImpl deleteEventUseCase;

    private Long existingEventId;
    private Long nonExistingEventId;

    @BeforeEach
    void setUp() {
        existingEventId = 1L;
        nonExistingEventId = 99L;
    }

    @Test
    void deleteEvent_Successfully() {
        when(eventRepositoryPort.findById(existingEventId)).thenReturn(Optional.of(new Event()));
        doNothing().when(eventRepositoryPort).delete(existingEventId);

        assertDoesNotThrow(() -> deleteEventUseCase.delete(existingEventId));

        verify(eventRepositoryPort, times(1)).findById(existingEventId);
        verify(eventRepositoryPort, times(1)).delete(existingEventId);
    }

    @Test
    void deleteEvent_WhenEventNotFound_ThrowsResourceNotFoundException() {
        when(eventRepositoryPort.findById(nonExistingEventId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deleteEventUseCase.delete(nonExistingEventId));

        verify(eventRepositoryPort, times(1)).findById(nonExistingEventId);
        verify(eventRepositoryPort, never()).delete(anyLong());
    }

    @Test
    void deleteEvent_WithNullId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> deleteEventUseCase.delete(null));

        verify(eventRepositoryPort, never()).findById(anyLong());
        verify(eventRepositoryPort, never()).delete(anyLong());
    }
}