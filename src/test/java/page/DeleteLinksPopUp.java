package page;

import element.ClickableElement;
import org.openqa.selenium.By;

public class DeleteLinksPopUp extends BasePage {
    private final static By DELETE_BUTTON = By.id("delete");

    public DeleteLinksPopUp(){
        super();
    }

    public EpamsMainPage delete(){
        new ClickableElement(DELETE_BUTTON).click();
        EpamsMainPage mainPage = new EpamsMainPage();
        refresh();
        return mainPage;
    }
}
