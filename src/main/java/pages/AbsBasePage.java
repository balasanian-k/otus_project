package pages;

import common.AbsCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public abstract class AbsBasePage extends AbsCommon {

    private String BASE_URL = System.getProperty("base.url");

    private String path = "";

    public AbsBasePage(WebDriver driver, String path) {
        super(driver);
        this.path = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
    }

    public void open() {
        driver.get(BASE_URL + this.path);
    }

    public void open(List<String> queryParams) {
        String url = BASE_URL + this.path + "?" + String.join("&", queryParams);

        driver.get(url);
    }

    public WebElement $(By by) {
        return driver.findElement(by);
    }

    public WebElement $(String locator) {
        By by = locator.startsWith("/") ? By.xpath(locator) : By.cssSelector(locator);

        return this.$(by);
    }
}
