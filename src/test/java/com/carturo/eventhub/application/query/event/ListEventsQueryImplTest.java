package com.carturo.eventhub.application.query.event;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListEventsQueryImplTest {

    @Mock
    private EventRepositoryPort eventRepositoryPort;

    @InjectMocks
    private ListEventsQueryImpl listEventsQuery;

    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        pageRequest = new PageRequest(0, 10);
    }

    @Test
    void listEvents_Successfully() {
        Event event1 = new Event(1L, "Event A", "Desc A", null, null, null, null, null);
        Event event2 = new Event(2L, "Event B", "Desc B", null, null, null, null, null);
        List<Event> events = Arrays.asList(event1, event2);
        PageResult<Event> expectedPageResult = new PageResult<>(events, 0, 10, 2, 1);

        when(eventRepositoryPort.findAll(pageRequest)).thenReturn(expectedPageResult);

        PageResult<Event> result = listEventsQuery.list(pageRequest);

        assertNotNull(result);
        assertEquals(2, result.totalItems());
        assertEquals(events, result.items());
        verify(eventRepositoryPort, times(1)).findAll(pageRequest);
    }

    @Test
    void listEvents_WhenNoEventsFound_ReturnsEmptyPageResult() {
        PageResult<Event> expectedPageResult = new PageResult<>(Collections.emptyList(), 0, 10, 0, 0);

        when(eventRepositoryPort.findAll(pageRequest)).thenReturn(expectedPageResult);

        PageResult<Event> result = listEventsQuery.list(pageRequest);

        assertNotNull(result);
        assertTrue(result.items().isEmpty());
        assertEquals(0, result.totalItems());
        verify(eventRepositoryPort, times(1)).findAll(pageRequest);
    }

    @Test
    void listEvents_WithNullPageRequest_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> listEventsQuery.list(null));
        verify(eventRepositoryPort, never()).findAll(any());
    }
}