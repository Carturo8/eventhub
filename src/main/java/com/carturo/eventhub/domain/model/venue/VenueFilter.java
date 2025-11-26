package com.carturo.eventhub.domain.model.venue;

public class VenueFilter {

    private String city;
    private Integer minCapacity;
    private Integer maxCapacity;

    public VenueFilter() {}

    public VenueFilter(String city, Integer minCapacity, Integer maxCapacity) {
        this.city = city;
        this.minCapacity = minCapacity;
        this.maxCapacity = maxCapacity;
    }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Integer getMinCapacity() { return minCapacity; }
    public void setMinCapacity(Integer minCapacity) { this.minCapacity = minCapacity; }

    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }

    public static VenueFilter empty() {
        return new VenueFilter(null, null, null);
    }
}