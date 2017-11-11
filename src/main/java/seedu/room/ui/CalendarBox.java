package seedu.room.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import seedu.room.logic.Logic;
import seedu.room.model.event.ReadOnlyEvent;

//@@author Haozhe321

/**
 * Create a CalendarBox Object to pass to the CalendarBoxPanel to be displayed
 */
public class CalendarBox {

    private ArrayList<AnchorPaneNode> allCalendarDays;
    private VBox view;
    private Text calendarTitle;
    private GridPane calendar;
    private GridPane dayLabels;
    private HBox titleBar;
    private YearMonth currentYearMonth;
    private final Color yellow = Color.web("#CA9733");
    private Logic logic;
    private Text[] dayNames = new Text[]{ new Text("Sunday"), new Text("Monday"), new Text("Tuesday"),
            new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
            new Text("Saturday") };


    /**
     * Create a month-based calendar filled with dates and events
     * @param yearMonth the month of the calendar to create the calendar
     * @param logic containing the events to populate
     */
    public CalendarBox(YearMonth yearMonth, Logic logic) {
        this.logic = logic;
        currentYearMonth = yearMonth;
        allCalendarDays = new ArrayList<>(35);

        //create the skeleton of the calendar, i.e. grids and days
        makeCalendarSkeleton();

        // Create the navigation tool of the calendar, i.e. title, previous/next month buttons
        makeCalendarNavigationTool();

        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth, logic.getFilteredEventList());

        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
        VBox.setMargin(titleBar, new Insets(0, 0, 10, 0));
    }


    /**
     * Set the days of the calendar to correspond to the appropriate date, with the events populated
     * @param yearMonth year and month of month to render
     * @param eventList list of events to populate
     */
    public void populateCalendar(YearMonth yearMonth, ObservableList<ReadOnlyEvent> eventList) {
        HashMap<LocalDate, ArrayList<ReadOnlyEvent>> hashEvents = new HashMap<LocalDate, ArrayList<ReadOnlyEvent>>();
        hashEvents = eventsHashMap(eventList);

        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }

        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            ap.setId("calendarCell");
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }

            ap.getChildren().clear();
            //make today's date light up
            if (calendarDate.equals(LocalDate.now())) {
                ap.lightUpToday();
            } else {
                ap.revertBackground();
            }
            addDates(calendarDate, ap);

            if (hashEvents.containsKey(calendarDate)) {
                ArrayList<ReadOnlyEvent> eventInADay = hashEvents.get(calendarDate);

                int numEvents = 0;
                String allEventTitle = "";
                //go through the list of events and add them to the grid
                for (ReadOnlyEvent actualEvent: eventInADay) {

                    //if number of events is already more than 2, populate only 2 and tell users there are more events
                    if (numEvents == 2) {
                        allEventTitle = allEventTitle + "and more...";
                        break;
                    }
                    String eventTitle = actualEvent.getTitle().toString();
                    if (eventTitle.length() > 8) {
                        eventTitle = eventTitle.substring(0, 8) + "...";
                    }
                    allEventTitle = allEventTitle + eventTitle + "\n";
                    numEvents++;
                }
                Text eventText = new Text(allEventTitle);
                addEventName(ap, eventText);

            }
            calendarDate = calendarDate.plusDays(1);

        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Create a HashMap of LocalDate and Arraylist of ReadOnlyEvent to use for populating events on calendar
     * @param eventList
     * @return HashMap of LocalDate and Arraylist of ReadOnlyEvent
     */
    public HashMap<LocalDate, ArrayList<ReadOnlyEvent>> eventsHashMap(ObservableList<ReadOnlyEvent> eventList) {
        HashMap<LocalDate, ArrayList<ReadOnlyEvent>> hashEvents = new HashMap<LocalDate, ArrayList<ReadOnlyEvent>>();
        for (ReadOnlyEvent event: eventList) {
            if (hashEvents.containsKey(event.getDatetime().getLocalDateTime().toLocalDate())) {
                hashEvents.get(event.getDatetime().getLocalDateTime().toLocalDate()).add(event);
            } else {
                ArrayList<ReadOnlyEvent> newEventList = new ArrayList<ReadOnlyEvent>();
                newEventList.add(event);
                hashEvents.put(event.getDatetime().getLocalDateTime().toLocalDate(), newEventList);
            }
        }

        return hashEvents;
    }

    public void makeCalenderTitle() {
        this.calendarTitle = new Text();
        calendarTitle.setFill(yellow);
        calendarTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
    }

    public void makeButtons(Button previousMonth, Button nextMonth) {
        previousMonth.setOnAction(e -> previousMonth());
        nextMonth.setOnAction(e -> nextMonth());

    }

    public void makeCalendarTitleBar(HBox titleBar, Button previousMonth, Button nextMonth) {
        HBox.setMargin(previousMonth, new Insets(0, 13, 0, 13));
        HBox.setMargin(nextMonth, new Insets(0, 13, 0, 13));
        titleBar.setAlignment(Pos.BASELINE_CENTER);
    }

    public void makeCalendarNavigationTool() {
        makeCalenderTitle();

        Button previousMonth = new Button(" PREV ");
        Button nextMonth = new Button(" NEXT ");

        makeButtons(previousMonth, nextMonth);

        this.titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        makeCalendarTitleBar(titleBar, previousMonth, nextMonth);
    }

    public void makeCalendarSkeleton() {
        // Create the calendar grid pane
        this.calendar = new GridPane();
        createGrid(calendar);

        // Create the days of the weeks from Sunday to Saturday
        this.dayLabels = new GridPane();
        makeDays(dayNames, dayLabels);
    }

    public void makeDays(Text[] dayNames, GridPane gridPane) {
        gridPane.setPrefWidth(600);
        int col = 0;
        for (Text txt : dayNames) {
            txt.setFill(Color.WHITE);
            AnchorPane ap = new AnchorPane();
            ap.setId("calendarDaysPane");
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            txt.setTextAlignment(TextAlignment.CENTER);
            ap.setStyle("-fx-text-align: center;");
            gridPane.add(ap, col++, 0);
        }
    }

    /**
     * Create 5 by 7 grids for calendar
     * @param calendar
     */
    public void createGrid(GridPane calendar) {

        calendar.setPrefSize(500, 450);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 90);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }
    }

    /**
     * Add the event's name on the calendar grid
     * @param ap AnchorPaneNode that we are adding the event to
     * @param eventText Text for the event(s)
     */
    public void addEventName(AnchorPaneNode ap, Text eventText) {
        ap.setBottomAnchor(eventText, 5.0);
        ap.setLeftAnchor(eventText, 5.0);
        ap.getChildren().add(eventText);
    }

    /**
     * add the date number to the grids
     */
    public void addDates(LocalDate calendarDate, AnchorPaneNode ap) {
        Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
        ap.setDate(calendarDate);
        ap.setTopAnchor(txt, 10.0);
        ap.setLeftAnchor(txt, 5.0);
        ap.getChildren().add(txt);
    }


    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth, logic.getFilteredEventList());
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth, logic.getFilteredEventList());
    }



    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
}

