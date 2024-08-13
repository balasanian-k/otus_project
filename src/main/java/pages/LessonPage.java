package pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.util.List;

public class LessonPage extends AbsBasePage {

    public LessonPage(WebDriver driver, String lessonPath) {
        super(driver, String.format("/lessons/%s", lessonPath));
    }

    @FindBy(xpath = "//section//div[not(@style)]/a[contains(@href, '/lessons/')][.//h6]")
    private List<WebElement> randomCourseTile;

    @FindBy(xpath = "//section//div[not(@style)]/a[contains(@href, '/lessons/')]/h6/following-sibling::div")
    private List<WebElement> randomCourseDuration;

    public void randomCourseTileNumberShouldBeSameAs(int number) {
        Assertions.assertEquals(
                number,
                randomCourseTile.size(),
                String.format("Number of lessons tiles should be %d", number)
        );
    }

    public String getRandomCourseNameByIndex(int index) {
        return randomCourseTile.get(--index).findElement(By.xpath(".//h6")).getText();
    }

    public String getRandomCourseDuration(int index) {
        return randomCourseDuration.get(--index).getText();
    }

    public int getRandomTileNumber() {
        return randomCourseTile.size();
    }

    private Document getRandomDomePage(int index) throws IOException {
        String url = randomCourseTile.get(--index).getAttribute("href");
        return Jsoup.connect(url).get();
    }

    public void checkRandomCourseHeaderByIndex(int index, String expectedHeader) throws IOException {
        Document dom = getRandomDomePage(index);
        Element headerPageElement = dom.selectFirst("h1");

//        дописать ошибку
        Assertions.assertEquals(expectedHeader, headerPageElement.text(), "Course header should be %s");
        headerPageElement.text();
    }

    public void checkRandomCourseDescriptionByIndex(int index) throws IOException {
        Elements elements = getRandomDomePage(index).selectXpath("//h1/following-sibling::div[text()]");

        if(elements.isEmpty()) {
            elements = getRandomDomePage(index).selectXpath("//h1/following-sibling::div/p[text()]");
        }
        Element headerPageElement = elements.get(0);

        //        дописать ошибку
        Assertions.assertFalse(headerPageElement.text().isEmpty(), "Course description should be %s");
    }

    public void checkRandomCourseDuration(int index, String expectedDuration) throws IOException {
        Element headerPageElement = getRandomDomePage(index).selectXpath("//div/following-sibling::p[contains(text(), 'месяц')]").get(0);

        //        дописать ошибку
        Assertions.assertEquals(expectedDuration.replaceAll("^.*?·\\s*", ""), headerPageElement.text(), "Course duration should be %s");
    }

    public void checkRandomCourseFormat(int index, String format) throws IOException {
        Element courseFormatElement = getRandomDomePage(index).selectXpath(String.format("//p[contains(text(), '%s')]", format)).get(0);

        //        дописать ошибку
        Assertions.assertFalse(courseFormatElement.text().isEmpty(), "Course format should be %s");

    }



}
