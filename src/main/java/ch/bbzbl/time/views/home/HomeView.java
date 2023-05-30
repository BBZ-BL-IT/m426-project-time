package ch.bbzbl.time.views.home;

import ch.bbzbl.time.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Home")
@Route(value = "Home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {

    private Button clockIn;
    private Button clockOut;
    private Button deleteEntry;
    private Grid<TimeEntry> timeEntryGrid;
    private List<TimeEntry> timeEntries;
    private Label remainingHoursLabel;

    /**
     * View for home.
     */
    public HomeView() {
        timeEntries = new ArrayList<>();

        clockIn = new Button("Clock In");
        clockIn.addClickListener(e -> clockIn());

        clockOut = new Button("Clock Out");
        clockOut.addClickListener(e -> clockOut());

        deleteEntry = new Button("Delete Entry");
        deleteEntry.addClickListener(e -> deleteEntry());

        timeEntryGrid = new Grid<>(TimeEntry.class);
        timeEntryGrid.setColumns("timestamp", "eventType");
        timeEntryGrid.setItems(timeEntries);

        remainingHoursLabel = new Label();
        remainingHoursLabel.getStyle().set("color", "rgb(152, 251, 152)");
        remainingHoursLabel.getStyle().set("font-weight", "bold");
        remainingHoursLabel.getStyle().set("font-size", "18px");

        updateButtonStates();

        setMargin(true);

        add(clockIn, clockOut, deleteEntry, timeEntryGrid, remainingHoursLabel);
    }

    /**
     * Update the state of clockIn, clockOut, and deleteEntry buttons based on timeEntries.
     */
    private void updateButtonStates() {
        if (timeEntries.isEmpty()) {
            clockOut.setEnabled(false);
            clockIn.setEnabled(true);
            deleteEntry.setEnabled(false);
        } else if (timeEntries.get(timeEntries.size() - 1).getEventType().equals("out")) {
            clockIn.setEnabled(true);
            clockOut.setEnabled(false);
            deleteEntry.setEnabled(true);
        } else if (timeEntries.get(timeEntries.size() - 1).getEventType().equals("in")) {
            clockIn.setEnabled(false);
            clockOut.setEnabled(true);
            deleteEntry.setEnabled(false);
        }
    }

    /**
     * clockIn
     * Creates a time stamp with the current time and adds a "Clock In" entry to the list.
     */
    private void clockIn() {
        LocalDateTime currentTime = LocalDateTime.now();
        TimeEntry timeEntry = new TimeEntry(currentTime, "in");

        if (validateTimeEntry(timeEntry)) {
            timeEntries.add(timeEntry);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String formattedDateTime = currentTime.format(formatter);

            Notification notification = new Notification("Clocked in at: " + formattedDateTime);
            notification.open();
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.setDuration(10000); // Set duration to 10 seconds

            updateButtonStates();
            timeEntryGrid.getDataProvider().refreshAll();
            displayRemainingWorkTime();
        } else {
            Notification notification = new Notification("Invalid time entry!");
            notification.open();
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.setDuration(10000); // Set duration to 10 seconds
        }
    }

    /**
     * clockOut
     * Creates a time stamp with the current time and adds a "Clock Out" entry to the list.
     */
    private void clockOut() {
        LocalDateTime currentTime = LocalDateTime.now();
        TimeEntry timeEntry = new TimeEntry(currentTime, "out");

        if (validateTimeEntry(timeEntry)) {
            timeEntries.add(timeEntry);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String formattedDateTime = currentTime.format(formatter);

            Notification notification = new Notification("Clocked out at: " + formattedDateTime);
            notification.open();
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.setDuration(10000); // Set duration to 10 seconds

            updateButtonStates();
            timeEntryGrid.getDataProvider().refreshAll();
            displayRemainingWorkTime();
        } else {
            Notification notification = new Notification("Invalid time entry!");
            notification.open();
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.setDuration(10000); // Set duration to 10 seconds
        }
    }

    /**
     * deleteEntry
     * Deletes the last time entry from the list.
     */
    private void deleteEntry() {
        if (!timeEntries.isEmpty()) {
            timeEntries.remove(timeEntries.size() - 1);
            Notification notification = new Notification("Last entry deleted");
            notification.open();
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.setDuration(10000); // Set duration to 10 seconds
            updateButtonStates();
            timeEntryGrid.getDataProvider().refreshAll();
            displayRemainingWorkTime();
        }
    }

    /**
     * Validates a new time entry to ensure that the same event type is not repeated consecutively.
     * Returns true if the entry is valid, false otherwise.
     */
    private boolean validateTimeEntry(TimeEntry newEntry) {
        if (!timeEntries.isEmpty()) {
            TimeEntry lastEntry = timeEntries.get(timeEntries.size() - 1);
            return !lastEntry.getEventType().equals(newEntry.getEventType());
        }
        return true;
    }

    /**
     * Displays the remaining work time based on the last clock-in time and the current time.
     */
    private void displayRemainingWorkTime() {
        if (!timeEntries.isEmpty() && timeEntries.get(timeEntries.size() - 1).getEventType().equals("in")) {
            LocalDateTime currentTime = LocalDateTime.now();
            TimeEntry lastEntry = timeEntries.get(timeEntries.size() - 1);

            LocalDateTime clockInTime = LocalDateTime.parse(lastEntry.getTimestamp(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            LocalDateTime clockOutTime = currentTime;

            long workMinutes = java.time.Duration.between(clockInTime, clockOutTime).toMinutes();
            long remainingMinutes = 480 - workMinutes; // Assuming 8-hour workday (8 hours = 480 minutes)

            long remainingHours = remainingMinutes / 60;
            long remainingMinutesModulo = remainingMinutes % 60;

            String remainingTime = String.format("%02d:%02d", remainingHours, remainingMinutesModulo);

            remainingHoursLabel.setText("Remaining work time: " + remainingTime);
        } else {
            remainingHoursLabel.setText("");
        }
    }

    /**
     * Inner class representing a time entry.
     */
    public static class TimeEntry {
        private LocalDateTime timestamp;
        private String eventType;

        public TimeEntry(LocalDateTime timestamp, String eventType) {
            this.timestamp = timestamp;
            this.eventType = eventType;
        }

        public String getTimestamp() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            return timestamp.format(formatter);
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }
    }
}