package driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Driver {
    private final RemoteWebDriver driver;
    private static final String PROPERTIES_FILE = "src/test/resources/driver.properties";
    private static Browser currentBrowser;
    private static Driver instance;
    private static int defaultImplicitWaitTimeOut;
    private static int defaultExplicitWaitTimeout;
    private static String url;

    private Driver(){
        driver = setUpDriver();
    }

    public static synchronized Driver getInstance(){
        if (instance == null){
            instance = new Driver();
        }
        return instance;
    }

    private RemoteWebDriver setUpDriver() {
        RemoteWebDriver driver = null;
        initProperties();

        switch (currentBrowser){
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", "src/test/resources/driver/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(defaultImplicitWaitTimeOut, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }



    public RemoteWebDriver getDriver(){
        return driver;
    }

    public static int getDefaultImplicitWaitTimeOut() {
        return defaultImplicitWaitTimeOut;
    }

    public static int getDefaultExplicitWaitTimeout() {
        return defaultExplicitWaitTimeout;
    }

    public static String getUrl() {
        return url;
    }

    private static void initProperties(){
        Properties properties = null;
        try(FileInputStream fileProperty = new FileInputStream(PROPERTIES_FILE)) {
            properties = new Properties();
            properties.load(fileProperty);
            defaultImplicitWaitTimeOut = Integer.parseInt(properties.getProperty("defaultImplicitWaitTimeout"));
            defaultExplicitWaitTimeout = Integer.parseInt(properties.getProperty("defaultExplicitWaitTimeout"));
            url = properties.getProperty("url");
            try{
                currentBrowser = Browser.valueOf(properties.getProperty("browser").toUpperCase());
            }catch (IllegalArgumentException e){
                currentBrowser = Browser.CHROME;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum Browser{
        CHROME,
        FIREFOX;
    }

}
