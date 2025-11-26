CREATE TABLE venues
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    city     VARCHAR(255) NOT NULL,
    capacity INT          NOT NULL,
    CONSTRAINT unique_venue_name UNIQUE (name)
);

CREATE TABLE events
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    category    VARCHAR(50)  NOT NULL,
    event_date  DATE         NOT NULL,
    status      VARCHAR(50)  NOT NULL,
    venue_id    BIGINT       NOT NULL,
    CONSTRAINT unique_event_name UNIQUE (name)
);