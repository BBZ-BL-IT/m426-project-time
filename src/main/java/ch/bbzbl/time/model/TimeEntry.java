package ch.bbzbl.time.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a time entry with a timestamp and an event type.
 */
public class TimeEntry {
    private LocalDateTime timestamp;
    private String eventType;

    /**
     * Constructs a new TimeEntry object with the specified timestamp and event type.
     *
     * @param timestamp The timestamp of the time entry.
     * @param eventType The event type of the time entry.
     */
    public TimeEntry(LocalDateTime timestamp, String eventType) {
        this.timestamp = timestamp;
        this.eventType = eventType;
    }

    /**
     * Returns the formatted timestamp of the time entry.
     *
     * @return The formatted timestamp.
     */
    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return timestamp.format(formatter);
    }

    /**
     * Sets the timestamp of the time entry.
     *
     * @param timestamp The timestamp to set.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the event type of the time entry.
     *
     * @return The event type.
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the event type of the time entry.
     *
     * @param eventType The event type to set.
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
