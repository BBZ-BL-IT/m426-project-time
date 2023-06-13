package ch.bbzbl.time.views.home;

import ch.bbzbl.time.data.entity.TimeEntry;
import ch.bbzbl.time.data.service.TimeEntryService;
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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@PageTitle("Home")
@Route(value = "Home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {

    private Button clockIn;
    private Button clockOut;
    private Button deleteEntry;
    private Grid<TimeEntry> timeEntryGrid;
    private Label remainingHoursLabel;

    private TimeEntryService timeEntryService;

    private ScheduledExecutorService executorService;

    public HomeView() {
        timeEntryService = new TimeEntryService();

        clockIn = new Button("Clock In");
        clockIn.addClickListener(e -> clockIn());

        clockOut = new Button("Clock Out");
        clockOut.addClickListener(e -> clockOut());

        deleteEntry = new Button("Delete Last Entry");
        deleteEntry.addClickListener(e -> deleteLastEntry());

        timeEntryGrid = new Grid<>(TimeEntry.class);
        timeEntryGrid.setColumns("timestamp", "eventType", "user");
        timeEntryGrid.setItems(timeEntryService.getTimeEntries());

        remainingHoursLabel = new Label();
        remainingHoursLabel.getStyle().set("color", "rgb(255, 0, 0)");
        remainingHoursLabel.getStyle().set("font-weight", "bold");
        remainingHoursLabel.getStyle().set("font-size", "18px");

        updateButtonStates();

        setMargin(true);

        add(clockIn, clockOut, deleteEntry, timeEntryGrid, remainingHoursLabel);
    }

    private void updateButtonStates() {
        timeEntryService.updateButtonStates(clockIn, clockOut, deleteEntry);
    }

    private void clockIn() {
        if (timeEntryService.clockIn()) {
            displayNotification("Clocked in at " + timeEntryService.getFormattedDateTime());
            updateButtonStates();
            displayRemainingWorkTime();
            timeEntryGrid.getDataProvider().refreshAll();
        }
    }

    private void clockOut() {
        if (timeEntryService.clockOut()) {
            displayNotification("Clocked out at " + timeEntryService.getFormattedDateTime());
            updateButtonStates();
            displayRemainingWorkTime();
            timeEntryGrid.getDataProvider().refreshAll();
        }
    }

    private void deleteLastEntry() {
        if (timeEntryService.deleteLastEntry()) {
            updateButtonStates();
            displayRemainingWorkTime();
            timeEntryGrid.getDataProvider().refreshAll();
        }
    }

    private void displayRemainingWorkTime() {
        remainingHoursLabel.setText(timeEntryService.getRemainingWorkTime());
    }

    private void scheduleRemainingWorkTimeUpdateTask() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::displayRemainingWorkTime, 0, 1, TimeUnit.MINUTES);
    }

    private void cancelRemainingWorkTimeUpdateTask() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    public void displayNotification(String message) {
        Notification notification = new Notification(message);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.setDuration(5000);
        notification.open();
    }
}
