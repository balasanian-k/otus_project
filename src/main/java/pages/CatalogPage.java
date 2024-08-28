package pages;

import data.category.CourseCategoryData;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public List<String> getCourseCategoryParams(CourseCategoryData category) {
        List<String> queryParams = new ArrayList<>();
        queryParams.add(String.format("categories=%s", category.name().toLowerCase(Locale.ROOT)));
        return queryParams;
    }

    public void open(List<String> queryParams) {
        String url = "https://otus.ru/catalog/courses";
        if (!queryParams.isEmpty()) {
            url += "?" + String.join("&", queryParams);
        }
        driver.get(url);
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

        Assertions.assertEquals(expectedHeader, headerPageElement.text(), "Course header should be %s");
        headerPageElement.text();
    }

    public void checkCourseDescriptionByIndex(int index) throws IOException {
        Elements elements = getDomePage(index).selectXpath("//h1/following-sibling::div[text()]");

        if(elements.isEmpty()) {
            elements = getDomePage(index).selectXpath("//h1/following-sibling::div/p[text()]");
        }
        Element headerPageElement = elements.get(0);

        Assertions.assertFalse(headerPageElement.text().isEmpty(), "Course description should be %s");
    }

    public void checkCourseDuration(int index, String expectedDuration) throws IOException {
        Element headerPageElement = getDomePage(index).selectXpath("//div/following-sibling::p[contains(text(), 'месяц')]").get(0);

        Assertions.assertEquals(expectedDuration.replaceAll("^.*?·\\s*", ""), headerPageElement.text(), "Course duration should be %s");
    }

    public void checkCourseFormat(int index, String format) throws IOException {
        Element courseFormatElement = getDomePage(index).selectXpath(String.format("//p[contains(text(), '%s')]", format)).get(0);

        Assertions.assertFalse(courseFormatElement.text().isEmpty(), "Course format should be %s");

    }
}
