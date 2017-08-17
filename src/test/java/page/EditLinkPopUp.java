package page;

import element.ClickableElement;
import element.TextBoxElement;
import org.openqa.selenium.By;

public class EditLinkPopUp extends BasePage {
    private final static By SHORT_LINK_FIELD = By.id("editShortlink");
    private final static By LONG_LINK_FIELD = By.id("editFullLink");
    private final static By EXPIRATION_DATE_FIELD = By.id("editExpirationDate");
    private final static By CANCEL_BUTTON = By.xpath("//*[@id='editUrlFrm']//button[text()='Cancel']");
    private final static By SAVE_BUTTON = By.xpath("//*[@id='editUrlFrm']//button[text()='Save changes']");

    public EpamsMainPage editShortLink(String shortLink){
        new TextBoxElement(SHORT_LINK_FIELD).type(shortLink);
        new ClickableElement(SAVE_BUTTON).click();
        EpamsMainPage mainPage = new EpamsMainPage();
        refresh();
        return mainPage;
    }

    public EpamsMainPage editLongLink(String newLongLink){
        new TextBoxElement(LONG_LINK_FIELD).type(newLongLink);
        new ClickableElement(SAVE_BUTTON).click();
        EpamsMainPage mainPage = new EpamsMainPage();
        refresh();
        return mainPage;
    }

    public EpamsMainPage editExpirationDate(String date){
        new TextBoxElement(EXPIRATION_DATE_FIELD).type(date);
        new ClickableElement(SAVE_BUTTON).click();
        EpamsMainPage mainPage = new EpamsMainPage();
        refresh();
        return mainPage;
    }

    public EpamsMainPage cancel(){
        new ClickableElement(CANCEL_BUTTON).click();
        EpamsMainPage mainPage = new EpamsMainPage();
        refresh();
        return mainPage;
    }







}
