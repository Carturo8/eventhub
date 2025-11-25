package com.carturo.eventhub.domain.model;

import java.time.LocalDate;

public class Event {

    private Long id;
    private String name;
    private String description;
    private String category;
    private LocalDate date;
    private Venue venue;

    public Event() {}

    public Event(Long id, String name, String description, String category, LocalDate date, Venue venue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.date = date;
        this.venue = venue;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }
}