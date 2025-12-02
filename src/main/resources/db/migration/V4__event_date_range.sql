-- V4: Refactor Event date to support a date range
-- This migration replaces the single event_date column with start_date and end_date
-- to allow events to span multiple days, which is a core business requirement change.

-- Step 1: Drop the existing event_date column from the events table.
-- We are dropping this column as it is being replaced by a more flexible date range.
ALTER TABLE events
    DROP COLUMN event_date;

-- Step 2: Add the new start_date column.
-- This column will store the starting date of the event. It cannot be null.
ALTER TABLE events
    ADD COLUMN start_date DATE NOT NULL;

-- Step 3: Add the new end_date column.
-- This column will store the ending date of the event. It cannot be null.
ALTER TABLE events
    ADD COLUMN end_date DATE NOT NULL;