package com.carturo.eventhub.application.query.venue;

import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.model.venue.Venue;
import com.carturo.eventhub.domain.model.venue.VenueFilter;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
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
class ListVenuesQueryImplTest {

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private ListVenuesQueryImpl listVenuesQuery;

    private PageRequest pageRequest;
    private VenueFilter venueFilter;

    @BeforeEach
    void setUp() {
        pageRequest = new PageRequest(0, 10);
        venueFilter = new VenueFilter("Test City", null, null);
    }

    @Test
    void listVenues_Successfully() {
        Venue venue1 = new Venue(1L, "Venue A", "City A", 1000);
        Venue venue2 = new Venue(2L, "Venue B", "City B", 2000);
        List<Venue> venues = Arrays.asList(venue1, venue2);
        PageResult<Venue> expectedPageResult = new PageResult<>(venues, 0, 10, 2, 1);

        when(venueRepositoryPort.findAll(pageRequest, venueFilter)).thenReturn(expectedPageResult);

        PageResult<Venue> result = listVenuesQuery.list(pageRequest, venueFilter);

        assertNotNull(result);
        assertEquals(2, result.totalItems());
        assertEquals(venues, result.items());
        verify(venueRepositoryPort, times(1)).findAll(pageRequest, venueFilter);
    }

    @Test
    void listVenues_WhenNoVenuesFound_ReturnsEmptyPageResult() {
        PageResult<Venue> expectedPageResult = new PageResult<>(Collections.emptyList(), 0, 10, 0, 0);

        when(venueRepositoryPort.findAll(pageRequest, venueFilter)).thenReturn(expectedPageResult);

        PageResult<Venue> result = listVenuesQuery.list(pageRequest, venueFilter);

        assertNotNull(result);
        assertTrue(result.items().isEmpty());
        assertEquals(0, result.totalItems());
        verify(venueRepositoryPort, times(1)).findAll(pageRequest, venueFilter);
    }

    @Test
    void listVenues_WithNullPageRequest_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> listVenuesQuery.list(null, venueFilter));
        verify(venueRepositoryPort, never()).findAll(any(), any());
    }

    @Test
    void listVenues_WithNullVenueFilter_Successfully() {
        Venue venue1 = new Venue(1L, "Venue A", "City A", 1000);
        List<Venue> venues = Collections.singletonList(venue1);
        PageResult<Venue> expectedPageResult = new PageResult<>(venues, 0, 10, 1, 1);

        when(venueRepositoryPort.findAll(pageRequest, null)).thenReturn(expectedPageResult);

        PageResult<Venue> result = listVenuesQuery.list(pageRequest, null);

        assertNotNull(result);
        assertEquals(1, result.totalItems());
        assertEquals(venues, result.items());
        verify(venueRepositoryPort, times(1)).findAll(pageRequest, null);
    }
}