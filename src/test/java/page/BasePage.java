package page;

import driver.Driver;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class BasePage {
    private final RemoteWebDriver driver;

    public BasePage(){
        driver = Driver.getInstance().getDriver();
    }

    public void refresh(){
        driver.navigate().refresh();
    }

    public RemoteWebDriver getDriver() {
        return driver;
    }
}
