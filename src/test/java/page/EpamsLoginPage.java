package page;

import element.ClickableElement;
import element.LabelElement;
import element.TextBoxElement;
import org.openqa.selenium.By;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class EpamsLoginPage {
    private final static By LOGIN_FIELD_LOCATOR = By.id("userNameInput");
    private final static By PASSWORD_FIELD_LOCATOR = By.id("passwordInput");
    private final static By SUBMIT_BUTTON_LOCATOR = By.id("submitButton");
    private final static By USER_LOCATOR = By.xpath("//*[contains(@class,'settings-user')]");
    private final static By MAIN_SITE_LOGO_LOCATOR = By.xpath("//*[@class = 'site-logo']");
    private final static By LOGIN_PAGE_LOGO_LOCATOR = By.className("logoImage");
    private String userName;
    private String password;

    public EpamsMainPage logIn(String userName, String password ){
        this.userName = userName;
        this.password = password;
        EpamsMainPage epamsMainPage = new EpamsMainPage();
        LabelElement siteLogoElement = new LabelElement(MAIN_SITE_LOGO_LOCATOR);
        LabelElement loginPageLogoElement = new LabelElement(LOGIN_PAGE_LOGO_LOCATOR);
        if (siteLogoElement.waitForIsElementPresent(5)){
            return epamsMainPage;
        }else if (loginPageLogoElement.waitForIsElementPresent(5)){
            new TextBoxElement(LOGIN_FIELD_LOCATOR).type(userName);
            new TextBoxElement(PASSWORD_FIELD_LOCATOR).type(password);
            new ClickableElement(SUBMIT_BUTTON_LOCATOR).click();
        }else {
            (new Thread(new LoginWindow())).start();
        }

        return epamsMainPage;
    }

    //inner class for Login thread
    class LoginWindow implements Runnable {

        @Override
        public void run() {
            try {
                login();
            } catch (Exception ex) {
                System.out.println("Error in Login Thread: " + ex.getMessage());
            }
        }

        void login() throws Exception {

            //wait - increase this wait period if required
            //Thread.sleep(5000);

            //create robot for keyboard operations
            Robot robot = new Robot();

            //Enter user name by ctrl-v
            StringSelection userNameSelection = new StringSelection(userName);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(userNameSelection, null);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            //tab to password entry field
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            //Thread.sleep(2000);

            //Enter password by ctrl-v
            StringSelection pwd = new StringSelection(password);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pwd, null);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            //press enter
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            //wait
            //Thread.sleep(5000);
        }
    }
}
