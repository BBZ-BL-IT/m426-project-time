package ch.bbzbl.time.data.service;

import ch.bbzbl.time.data.entity.TimeEntry;
import ch.bbzbl.time.views.home.HomeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeEntryService {

    private List<TimeEntry> timeEntries;
    private HomeView view;
    private DateTimeFormatter formatter;

    public TimeEntryService() {
        timeEntries = new ArrayList<>();
        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    }

    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }

    public String getFormattedDateTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(formatter);
    }

    public boolean clockIn() {
        if (!timeEntries.isEmpty() && timeEntries.get(timeEntries.size() - 1).getEventType().equals("out")) {
            view.displayNotification("Cannot clock in without clocking out.");
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        TimeEntry timeEntry = new TimeEntry(currentTime, "in", "John Doe");
        timeEntries.add(timeEntry);

        return true;
    }

    public boolean clockOut() {
        if (timeEntries.isEmpty() || timeEntries.get(timeEntries.size() - 1).getEventType().equals("out")) {
            view.displayNotification("Cannot clock out without clocking in.");
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        TimeEntry timeEntry = new TimeEntry(currentTime, "out", "John Doe");
        timeEntries.add(timeEntry);

        return true;
    }

    public boolean deleteLastEntry() {
        if (!timeEntries.isEmpty()) {
            timeEntries.remove(timeEntries.size() - 1);
            return true;
        }
        return false;
    }

    public void updateButtonStates(Button clockIn, Button clockOut, Button deleteEntry) {
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

    public String getRemainingWorkTime() {
        if (!timeEntries.isEmpty() && timeEntries.get(timeEntries.size() - 1).getEventType().equals("in")) {
            LocalDateTime currentTime = LocalDateTime.now();
            TimeEntry lastEntry = timeEntries.get(timeEntries.size() - 1);

            LocalDateTime clockInTime = LocalDateTime.parse(lastEntry.getTimestamp(), formatter);

            long workMinutes = Duration.between(clockInTime, currentTime).toMinutes();
            long remainingMinutes = 480 - workMinutes;

            if (remainingMinutes > 0) {
                long remainingHours = remainingMinutes / 60;
                long remainingMinutesModulo = remainingMinutes % 60;
                return "Remaining work time: " + String.format("%02d:%02d", remainingHours, remainingMinutesModulo);
            } else {
                return "You can leave now!";
            }
        } else {
            return "";
        }
    }

}

