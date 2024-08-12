//package components.modalWindows;
//
//import common.AbsCommon;
//import org.junit.jupiter.api.Assertions;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//
//public class SearchModalW extends AbsCommon implements IModalW {
//
//    public SearchModalW(WebDriver driver) {
//        super(driver);
//    }
//
//    private String searchInputModalWLocator = "";
//
//    @Override
//    public void modalWindShouldNotBeVisible() {
//        Assertions.assertTrue(waitTools.waitNotElementPresent(By.xpath(searchInputModalWLocator), ""));
//
//    }
//
//    @Override
//    public void modalWindShouldBeVisible() {
//        Assertions.assertTrue(waitTools.waitElementPresent(By.xpath(searchInputModalWLocator), ""));
//
//    }
//
//    public void enterSearchRequest(String search) {
//        driver.findElement(By.xpath(searchInputModalWLocator)).sendKeys(search);
//        actions.sendKeys(Keys.ENTER).build().perform();
//    }
//
//}
