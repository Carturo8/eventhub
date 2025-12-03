package com.carturo.eventhub.application.query.event;

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
class GetEventByIdQueryImplTest {

    @Mock
    private EventRepositoryPort eventRepositoryPort;

    @InjectMocks
    private GetEventByIdQueryImpl getEventByIdQuery;

    private Long existingEventId;
    private Long nonExistingEventId;
    private Event event;

    @BeforeEach
    void setUp() {
        existingEventId = 1L;
        nonExistingEventId = 99L;
        event = new Event(existingEventId, "Test Event", "Description", null, null, null, null, null);
    }

    @Test
    void getEventById_Successfully() {
        when(eventRepositoryPort.findById(existingEventId)).thenReturn(Optional.of(event));

        Optional<Event> result = getEventByIdQuery.get(existingEventId);

        assertTrue(result.isPresent());
        assertEquals(event, result.get());
        verify(eventRepositoryPort, times(1)).findById(existingEventId);
    }

    @Test
    void getEventById_WhenNotFound_ReturnsEmptyOptional() {
        when(eventRepositoryPort.findById(nonExistingEventId)).thenReturn(Optional.empty());

        Optional<Event> result = getEventByIdQuery.get(nonExistingEventId);

        assertFalse(result.isPresent());
        verify(eventRepositoryPort, times(1)).findById(nonExistingEventId);
    }

    @Test
    void getEventById_WithNullId_ReturnsEmptyOptionalAndDoesNotCallRepository() {
        Optional<Event> result = getEventByIdQuery.get(null);

        assertFalse(result.isPresent());
        verify(eventRepositoryPort, never()).findById(anyLong());
    }
}