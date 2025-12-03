package com.carturo.eventhub.domain.model.event;

import java.time.LocalDate;

public class EventFilter {

    private String city;
    private EventCategory category;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long venueId;
    private EventStatus status;

    public EventFilter() {}

    public EventFilter(String city, EventCategory category, LocalDate startDate, LocalDate endDate,
                       Long venueId, EventStatus status) {
        this.city = city;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venueId = venueId;
        this.status = status;
    }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public EventCategory getCategory() { return category; }
    public void setCategory(EventCategory category) { this.category = category; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }

    public static EventFilter empty() {
        return new EventFilter(null, null, null, null, null, null);
    }
}