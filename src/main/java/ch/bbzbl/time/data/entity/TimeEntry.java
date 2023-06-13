package ch.bbzbl.time.data.entity;

import ch.bbzbl.time.security.AuthenticatedUser;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a time entry with a timestamp, event type, and user.
 */
public class TimeEntry {
    private LocalDateTime timestamp;
    private String eventType;
    @ManyToOne
    private String user;

    /**
     * Constructs a new TimeEntry object with the specified timestamp, event type, and user.
     *
     * @param timestamp The timestamp of the time entry.
     * @param eventType The event type of the time entry.
     * @param user      The user associated with the time entry.
     */
    public TimeEntry(LocalDateTime timestamp, String eventType, String user) {
        this.timestamp = timestamp;
        this.eventType = eventType;
        this.user = user;
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

    /**
     * Returns the user associated with the time entry.
     *
     * @return The user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user associated with the time entry.
     *
     * @param user The user to set.
     */
    public void setUser(String user) {
        this.user = user;
    }
}
