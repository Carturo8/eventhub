ALTER TABLE events
    ADD CONSTRAINT fk_events_venue
        FOREIGN KEY (venue_id)
            REFERENCES venues (id)
            ON DELETE RESTRICT;

CREATE INDEX idx_events_venue_id ON events (venue_id);
CREATE INDEX idx_events_category ON events (category);
CREATE INDEX idx_events_status ON events (status);
CREATE INDEX idx_events_event_date ON events (event_date);
CREATE INDEX idx_venues_city ON venues (city);