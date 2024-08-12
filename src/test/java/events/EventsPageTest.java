package events;

import data.sorting.EventTypeData;
import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.SchedulePage;


public class EventsPageTest {

    Logger logger = (Logger) LogManager.getLogger(EventsPageTest.class);

    private WebDriver driver;

    private SchedulePage schedulePage;

    @BeforeEach
    public void init() {
        logger.info("Соблюдение предусловий");
        this.driver = new DriverFactory().create();

        this.schedulePage = new SchedulePage(driver);
        schedulePage.open();
    }

    @AfterEach
    public void close() {
        logger.info("Соблюдение постусловий");
        if(this.driver !=null) {
            this.driver.close();
            this.driver.quit();
        }
    }

    @Test
    public void eventsTiles() {
        logger.info("Валидация дат предстоящих мероприятий");
        schedulePage
                .checkEventTilesShouldBeVisible()
                .checkStartEventsDate();
        logger.info("PASSED");
    }

    @Test
    public void selectSortedType() {
        logger.info("Просмотр мероприятий по типу");
        schedulePage
                .selectSortedEventsType(EventTypeData.OPEN)
                .checkEventTilesShouldBeVisible()
                .checkEventsType(EventTypeData.OPEN);
        logger.info("PASSED");
    }
}
