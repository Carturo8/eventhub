package com.carturo.eventhub.domain.model.event;

import com.carturo.eventhub.domain.model.venue.Venue;
import java.time.LocalDate;

public class Event {

    private Long id;
    private String name;
    private String description;
    private EventCategory category;
    private LocalDate eventDate;
    private Venue venue;
    private EventStatus status;

    public Event() {
        this.status = EventStatus.ACTIVE;
    }

    public Event(Long id, String name, String description, EventCategory category,
                 LocalDate eventDate, Venue venue, EventStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.eventDate = eventDate;
        this.venue = venue;
        this.status = status == null ? EventStatus.ACTIVE : status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public EventCategory getCategory() { return category; }
    public void setCategory(EventCategory category) { this.category = category; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }
}