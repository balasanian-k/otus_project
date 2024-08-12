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

    Logger logger = (Logger) LogManager.getLogger(CourseTilesTest.class);

    private WebDriver driver;

    private CatalogPage catalogPage = null;
    private String baseUrl = System.getProperty("base.url");

    @BeforeEach
    public void init() {
        this.driver = new DriverFactory().create();
        driver.get(baseUrl);

        List<String> queryParams = new ArrayList<>();
        queryParams.add(String.format("categories=%s", CourseCategoryData.TESTING.name().toLowerCase(Locale.ROOT)));

        this.catalogPage = new CatalogPage(driver);
        catalogPage.open(queryParams);
    }

    @AfterEach
    public void close() {
        if(this.driver !=null) {
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
        for(int i=1; i < catalogPage.getTilesNumbers(); i++) {
            String expectedHeader = catalogPage.getCourseNameByIndex(i);
            String expectedCourseDuration = catalogPage.getCourseDuration(i);

            logger.info("Проверка и просмотр карточки курса");
            catalogPage.checkCourseHeaderByIndex(i, expectedHeader);
            catalogPage.checkCourseDescriptionByIndex(i);
            catalogPage.checkCourseDuration(i, expectedCourseDuration);
            catalogPage.checkCourseFormat(i, "Онлайн");
            logger.info("PASSED");
        }

//        catalogPage.clickRandomCourseTile();
//        LessonPage lessonPage = new LessonPage(driver, "");
//        lessonPage.


    }
}
