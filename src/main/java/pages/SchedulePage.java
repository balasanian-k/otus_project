package pages;

import data.sorting.EventTypeData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchedulePage extends AbsBasePage {
    public SchedulePage(WebDriver driver) {
        super(driver, "/events/near/");
    }

    @FindBy(css = ".dod_new-event-content")
    private List<WebElement> eventTiles;

    @FindBy(css = ".dod_new-event__calendar-icon ~ .dod_new-event__date-text")
    private List<WebElement> dateEvents;

    @FindBy(css = ".dod_new-event .dod_new-type__text")
    private List<WebElement> eventsTypes;


    private String dropdownSortingEventsListSelector = ".dod_new-events-dropdown";
    private String dropdownEventsListSelector = dropdownSortingEventsListSelector + ".dod_new-events-dropdown_opened";
    private String dropdownSortingEventsItemTemplate = dropdownEventsListSelector + " [title='%s']";



    public SchedulePage checkEventTilesShouldBeVisible() {
        Assertions.assertTrue(waitTools.waitForCondition(ExpectedConditions.visibilityOfAllElements(eventTiles)));
        return this;
    }

    public SchedulePage checkStartEventsDate() {
        for(WebElement dateEvent: dateEvents) {
            LocalDate currentDate = LocalDate.now();
//            List<String> dateFormats = Arrays.asList("dd MMMM yyyy", "d MMMM yyyy");

            Pattern pattern = Pattern.compile("\\d+\\s+[а-яА-Я]+\\s+\\d{4}");
            String dateEventStr = dateEvent.getText();
            Matcher matcher = pattern.matcher(dateEventStr);
            if(!matcher.find()) {
                dateEventStr += String.format(" %d", currentDate.getYear());
            }
            LocalDate eventDate = LocalDate.parse(dateEventStr, DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru")));

            Assertions.assertTrue(eventDate.isAfter(currentDate) || eventDate.isEqual(currentDate), "Event date should be %s");
        }
        return this;
    }

    private SchedulePage dropdownSortingEventsShouldNotBeOpened() {
        Assertions.assertTrue(
            waitTools.waitForCondition(
                  ExpectedConditions.not(
                        ExpectedConditions.attributeContains(
                                $(dropdownSortingEventsListSelector), "class", "dod_new-events-dropdown_opened")
            )
         )
      );
        return this;
    }

    private SchedulePage dropdownSortingEventsShouldBeOpened () {
        Assertions.assertTrue(
                waitTools.waitForCondition(
                        ExpectedConditions.attributeContains(
                                $(dropdownSortingEventsListSelector), "class", "dod_new-events-dropdown_opened")
            )
        );
        return this;
    }

    private SchedulePage openSortingEventsDropdown() {
        $(dropdownSortingEventsListSelector).click();

        return this;
    }

    private SchedulePage sortingItemsShouldBeVisible() {
        Assertions.assertTrue(waitTools.waitElementVisible($(dropdownEventsListSelector)));

        return this;
    }

    private SchedulePage clickSortingItem(EventTypeData eventSortingData) {
        $(String.format(dropdownSortingEventsItemTemplate, eventSortingData.getName())).click();

        return this;
    }

    public SchedulePage selectSortedEventsType(EventTypeData eventSortingData) {
        this.dropdownSortingEventsShouldNotBeOpened()
                .openSortingEventsDropdown()
                .dropdownSortingEventsShouldBeOpened()
                .sortingItemsShouldBeVisible()
                .clickSortingItem(eventSortingData);

        return this;
    }

    public SchedulePage checkEventsType(EventTypeData eventTypeData) {
        for(WebElement element: eventsTypes) {
            Assertions.assertEquals(eventTypeData.getName(), element.getText(), "Event type should be %s");
        }
        return this;
    }
}
