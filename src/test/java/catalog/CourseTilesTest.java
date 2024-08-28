package catalog;

import data.category.CourseCategoryData;
import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CatalogPage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CourseTilesTest {

    Logger logger = LogManager.getLogger(CourseTilesTest.class);

    private WebDriver driver;
    private CatalogPage catalogPage = null;

    @BeforeEach
    public void init() {
        logger.info("Создание нового экземпляра Webdriver");
        this.driver = new DriverFactory().create();

        logger.info("Инициализация объекта CatalogPage с использованием драйвера");
        this.catalogPage = new CatalogPage(driver);

        List<String> queryParams = catalogPage.getCourseCategoryParams(CourseCategoryData.TESTING);
        catalogPage.open(queryParams);
    }

    @AfterEach
    public void close() {
        if(this.driver !=null) {
            logger.info("Закрываем соединение");
            this.driver.close();
            this.driver.quit();
        }
    }

    @Test
    public void lessonsTileNumbers() {
        logger.info("Проверка количества курсов в разделе Тестирование");
        catalogPage.courseTilesNumberShouldBeSameAs(10);
        logger.info("PASSED");
    }

    @Test
    public void checkDataOnLessonPage() throws IOException {
        int i;
        String expectedHeader = null;
        String expectedCourseDuration = null;
        for (i = 1; i < catalogPage.getTilesNumbers(); i++) {
            expectedHeader = catalogPage.getCourseNameByIndex(i);
            expectedCourseDuration = catalogPage.getCourseDuration(i);

            logger.info("Проверка и просмотр карточки курса, используя JSOUP");

            catalogPage.checkCourseHeaderByIndex(i, expectedHeader);
            catalogPage.checkCourseDescriptionByIndex(i);
            catalogPage.checkCourseDuration(i, expectedCourseDuration);
            catalogPage.checkCourseFormat(i, "Онлайн");

            logger.info("PASSED");
        }
    }
}
