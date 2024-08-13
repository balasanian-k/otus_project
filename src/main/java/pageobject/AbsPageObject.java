package pageobject;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import tools.WaitTools;


public abstract class AbsPageObject {

    protected WebDriver driver;
    protected Actions actions;
    protected WaitTools waitTools;
//    protected Faker faker;


    public AbsPageObject(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.waitTools = new WaitTools(driver);
//        this.faker = new Faker();

        PageFactory.initElements(driver, this);
    }
}
