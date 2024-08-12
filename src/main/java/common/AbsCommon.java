package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import tools.WaitTools;

public abstract class AbsCommon {

    protected WebDriver driver;
    protected WaitTools waitTools;
    protected Actions actions;

    public AbsCommon(WebDriver driver) {
        this.driver = driver;
        waitTools = new WaitTools(driver);
        this.actions = new Actions(driver);

        PageFactory.initElements(driver, this);
    }
}