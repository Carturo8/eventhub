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
class UpdateEventUseCaseImplTest {

    @Mock
    private EventRepositoryPort eventRepositoryPort;

    @InjectMocks
    private UpdateEventUseCaseImpl updateEventUseCase;

    private Event existingEvent;
    private Event updatedEventInfo;

    @BeforeEach
    void setUp() {
        existingEvent = new Event(1L, "Old Name", "Old Description", null, null, null, null, null);
        updatedEventInfo = new Event(null, "New Name", "New Description", null, null, null, null, null);
    }

    @Test
    void updateEvent_Successfully() {
        when(eventRepositoryPort.findById(1L)).thenReturn(Optional.of(existingEvent));
        when(eventRepositoryPort.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event result = updateEventUseCase.update(1L, updatedEventInfo);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Name", result.getName());
        assertEquals("New Description", result.getDescription());

        verify(eventRepositoryPort, times(1)).findById(1L);
        verify(eventRepositoryPort, times(1)).save(existingEvent);
    }

    @Test
    void updateEvent_WhenEventNotFound_ThrowsResourceNotFoundException() {
        when(eventRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> updateEventUseCase.update(1L, updatedEventInfo));

        verify(eventRepositoryPort, times(1)).findById(1L);
        verify(eventRepositoryPort, never()).save(any(Event.class));
    }

    @Test
    void updateEvent_WithNullId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> updateEventUseCase.update(null, updatedEventInfo));
        verify(eventRepositoryPort, never()).findById(anyLong());
        verify(eventRepositoryPort, never()).save(any(Event.class));
    }

    @Test
    void updateEvent_WithNullEvent_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> updateEventUseCase.update(1L, null));
        verify(eventRepositoryPort, never()).findById(anyLong());
        verify(eventRepositoryPort, never()).save(any(Event.class));
    }
}