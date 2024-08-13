package pages;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.util.List;

public class CatalogPage extends AbsBasePage {

    public CatalogPage(WebDriver driver) {
       super(driver, "/catalog/courses");
    }

    @FindBy(xpath = "//section//div[not(@style)]/a[contains(@href, '/lessons/')][.//h6]")
    private List<WebElement> courseTiles;

    @FindBy(xpath = "//section//div[not(@style)]/a[contains(@href, '/lessons/')]/h6/following-sibling::div")
    private List<WebElement> courseDuration;

    public void courseTilesNumberShouldBeSameAs(int number) {
        Assertions.assertEquals(
                number,
                courseTiles.size(),
                String.format("Number of lessons tiles should be %d", number)
        );
    }

    public void clickRandomCourseTile() {
        faker.options().nextElement(courseTiles);
    }

    public String getCourseNameByIndex(int index) {
        return courseTiles.get(--index).findElement(By.xpath(".//h6")).getText();
    }

    public String getCourseDuration(int index) {
       return courseDuration.get(--index).getText();
    }

    public int getTilesNumbers() {
        return courseTiles.size();
    }

    private Document getDomePage(int index) throws IOException {
        String url = courseTiles.get(--index).getAttribute("href");
        return Jsoup.connect(url).get();
    }

    public void checkCourseHeaderByIndex(int index, String expectedHeader) throws IOException {
        Document dom = getDomePage(index);
        Element headerPageElement = dom.selectFirst("h1");

//        дописать ошибку
        Assertions.assertEquals(expectedHeader, headerPageElement.text(), "Course header should be %s");
        headerPageElement.text();
    }

    public void checkCourseDescriptionByIndex(int index) throws IOException {
        Elements elements = getDomePage(index).selectXpath("//h1/following-sibling::div[text()]");

        if(elements.isEmpty()) {
            elements = getDomePage(index).selectXpath("//h1/following-sibling::div/p[text()]");
        }
        Element headerPageElement = elements.get(0);

       //        дописать ошибку
        Assertions.assertFalse(headerPageElement.text().isEmpty(), "Course description should be %s");
    }

    public void checkCourseDuration(int index, String expectedDuration) throws IOException {
        Element headerPageElement = getDomePage(index).selectXpath("//div/following-sibling::p[contains(text(), 'месяц')]").get(0);

        //        дописать ошибку
        Assertions.assertEquals(expectedDuration.replaceAll("^.*?·\\s*", ""), headerPageElement.text(), "Course duration should be %s");
    }

    public void checkCourseFormat(int index, String format) throws IOException {
        Element courseFormatElement = getDomePage(index).selectXpath(String.format("//p[contains(text(), '%s')]", format)).get(0);

        //        дописать ошибку
        Assertions.assertFalse(courseFormatElement.text().isEmpty(), "Course format should be %s");

    }

}
