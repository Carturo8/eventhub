package com.carturo.eventhub.application.usecase.event;

import com.carturo.eventhub.application.exception.DuplicateResourceException;
import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEventUseCaseImplTest {

    @Mock
    private EventRepositoryPort eventRepositoryPort;

    @InjectMocks
    private CreateEventUseCaseImpl createEventUseCase;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event(1L, "Test Event", "Test Description", null, null, null, null, null);
    }

    @Test
    void createEvent_Successfully() {
        when(eventRepositoryPort.existsByNameIgnoreCase("Test Event")).thenReturn(false);
        when(eventRepositoryPort.save(event)).thenReturn(event);

        Event createdEvent = createEventUseCase.create(event);

        assertNotNull(createdEvent);
        assertEquals("Test Event", createdEvent.getName());
        verify(eventRepositoryPort, times(1)).existsByNameIgnoreCase("Test Event");
        verify(eventRepositoryPort, times(1)).save(event);
    }

    @Test
    void createEvent_WhenEventAlreadyExists_ThrowsDuplicateResourceException() {
        when(eventRepositoryPort.existsByNameIgnoreCase("Test Event")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> createEventUseCase.create(event));

        verify(eventRepositoryPort, times(1)).existsByNameIgnoreCase("Test Event");
        verify(eventRepositoryPort, never()).save(any(Event.class));
    }

    @Test
    void createEvent_WithNullEvent_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> createEventUseCase.create(null));
        verify(eventRepositoryPort, never()).existsByNameIgnoreCase(anyString());
        verify(eventRepositoryPort, never()).save(any(Event.class));
    }

    @Test
    void createEvent_WithNullEventName_ThrowsIllegalArgumentException() {
        event.setName(null);
        assertThrows(IllegalArgumentException.class, () -> createEventUseCase.create(event));
        verify(eventRepositoryPort, never()).existsByNameIgnoreCase(anyString());
        verify(eventRepositoryPort, never()).save(any(Event.class));
    }
}