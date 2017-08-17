package page;

import element.ClickableElement;
import element.LabelElement;
import element.SelectElement;
import element.TextBoxElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class EpamsMainPage extends BasePage {

    private final static By MY_LONGLINK_LOCATOR = By.xpath("//tr[@class='ng-scope']/td[3]");
    private final static By MY_SHORTLINK_ABOUT_LONGLINK_LOCATOR = By.xpath("./preceding-sibling::td[1]/a");
    private final static String MY_LONGLINK_LOCATOR_FORMAT = "//*[@id='linklistbody']//td[3 and text()='%s']";
    private final static String MY_SHORTLINK_LOCATOR_FORMAT = "//*[@id='linklistbody']//a[text()='%s']";
    private final static By MY_LONGLINK_ABOUT_SHORTLINK_LOCATOR = By.xpath("./parent::td/following-sibling::td[1]");
    private final static By LONGLINK_FIELD_LOCATOR = By.id("longlink");
    private final static By SUBMIT_BUTTON_LOCATOR = By.id("submitshortlink");
    private final static By SELECT_ALL_LINKS_CHECKBOX_LOCATOR = By.xpath("//*[@id='markallfordeletion']/following-sibling::label");
    private final static By DELETE_SELECTED_LINKS_LOCATOR = By.xpath("//*[@class='pull-right']/button");
    private final static By SITE_LOGO_LOCATOR = By.xpath("//*[@class = 'site-logo']");
    private final static By SHORTLINK_NAME_FIELD_LOCATOR = By.id("shortlink");
    private final static By FILTER_SELECT_LOCATOR = By.xpath("//*[@id='contentBody']//select");
    private final static By LAST_INSERTED_SHORTLINK_LOCATOR = By.xpath(".//*[@id='linklistbody']/tr[1]/td[2]/a");
    private final static By HINT_FOR_SHORTLINK_AND_LONGLINK_LOCATOR = By.xpath("//*[@class = 'tooltip bottom in']");
    private final static By EDIT_LINK_ABOUT_SHORTLINK_LOCATOR = By.xpath("./../..//i[contains(@class, 'glyphicon-pencil')]");
    private final static By SERVER_RETURNED_ERROR_LOCATOR = By.xpath("//*[@class = 'toast-title']");
    private final static By EXPIRATION_DATE_ABOUT_SHORTLINK_LOCATOR = By.xpath("./../..//*[contains(@class, 'glyphicon-time')]");
    private final static By SEARCH_FILD_LOCATOR = By.cssSelector("#keywords");
	private final static By SEARCH_BUTTON_LOCATOR = By.cssSelector("#contentBody > div > div > div.container > div:nth-child(1) > div:nth-child(5) > button");
	private final static By SELECT_LINK_ONE_FOR_DELETE_LOCATOR = By.xpath(".//*[@id='linklistbody']/tr[2]/td[1]/div[1]/label");
	private final static By SELECT_LINK_TWO_FOR_DELETE_LOCATOR = By.xpath(".//*[@id='linklistbody']/tr[1]/td[1]/div[1]/label");
	private final static By SELECT_LINK_THREE_FOR_DELETE_LOCATOR = By.xpath(".//*[@id='linklistbody']/tr[3]/td[1]/div[1]/label");
	private final static By SELECT_AND_COUNT_ALL_LINKS_LOCATOR = By.xpath(".//*[@id='linklistbody']/tr");
	private final static int TIMEOUT_IN_SECONDS = 10;

    EpamsMainPage() {
        super();
        new LabelElement(SITE_LOGO_LOCATOR).waitForIsElementPresent();
    }

    public EpamsMainPage createShortLink(String longlink) {
        return createShortLink("", longlink);
    }

    public EpamsMainPage createShortLink(String shortLinkname, String longlink) {
        new TextBoxElement(LONGLINK_FIELD_LOCATOR).type(longlink);
        new TextBoxElement(SHORTLINK_NAME_FIELD_LOCATOR).type(shortLinkname);
        new ClickableElement(SUBMIT_BUTTON_LOCATOR).click();
        return this;
    }

    public EpamsMainPage selectFilter(String option){
        SelectElement element = new SelectElement(FILTER_SELECT_LOCATOR);
        element.select(option);
        refresh();
        return this;
    }

    public EpamsMainPage editShortLink(String oldShortLink, String newShortLink){
        LabelElement myShortLink = new LabelElement(By.xpath(String.format(MY_SHORTLINK_LOCATOR_FORMAT, oldShortLink)));
        new ClickableElement(myShortLink.getElement().findElement(EDIT_LINK_ABOUT_SHORTLINK_LOCATOR)).click();
        return new EditLinkPopUp().editShortLink(newShortLink);
    }

    public EpamsMainPage editLongLink(String oldLongLink, String newLongLink){
        LabelElement myLongLink = new LabelElement(By.xpath(String.format(MY_LONGLINK_LOCATOR_FORMAT, oldLongLink)));
        LabelElement myShortLink = new LabelElement(myLongLink.getElement().findElement(MY_SHORTLINK_ABOUT_LONGLINK_LOCATOR));
        new ClickableElement(myShortLink.getElement().findElement(EDIT_LINK_ABOUT_SHORTLINK_LOCATOR)).click();
        return new EditLinkPopUp().editLongLink(newLongLink);
    }

    public EpamsMainPage editExpirationDate(String shortLink, String date){
        LabelElement myShortLink = new LabelElement(By.xpath(String.format(MY_SHORTLINK_LOCATOR_FORMAT, shortLink)));
        new ClickableElement(myShortLink.getElement().findElement(EDIT_LINK_ABOUT_SHORTLINK_LOCATOR)).click();
        return new EditLinkPopUp().editExpirationDate(date);
    }

    public EpamsMainPage deleteAllLinks() {
    	refresh();
        new ClickableElement(SELECT_ALL_LINKS_CHECKBOX_LOCATOR).click();
        new ClickableElement(DELETE_SELECTED_LINKS_LOCATOR).click();
        return new DeleteLinksPopUp().delete();
    }

    public boolean isExpirationDateCorrect(String shortLink, String date){
        LabelElement myShortLink = new LabelElement(By.xpath(String.format(MY_SHORTLINK_LOCATOR_FORMAT, shortLink)));
        LabelElement dateElement = new LabelElement(myShortLink.getElement().findElement(EXPIRATION_DATE_ABOUT_SHORTLINK_LOCATOR));
        dateElement.showElement();
        String actualDate = dateElement.getElement().getAttribute("data-original-title");
        refresh();
        return actualDate.startsWith(date);
    }

    public boolean isLongLinkPresent(String link){
        LabelElement longLinkElement = new LabelElement(By.xpath(String.format(MY_LONGLINK_LOCATOR_FORMAT, link)));
        boolean longLinkPresent = false;
        if (longLinkElement.waitForIsElementPresent()){
            longLinkElement.showElement();
            longLinkPresent = true;
        }
        refresh();
        return longLinkPresent;
    }

    public boolean isHintForLinks(String hintText){
        LabelElement myLE = new LabelElement(HINT_FOR_SHORTLINK_AND_LONGLINK_LOCATOR);
        boolean isHintPresent = false;
        if (myLE.waitForIsElementPresent() && hintText.equals(myLE.getText())){
            isHintPresent = true;
        }
        refresh();
        return isHintPresent;
    }

    public boolean isServerReturnedError(){
        LabelElement myLE = new LabelElement(SERVER_RETURNED_ERROR_LOCATOR);
        boolean isErrorMessagePresent = false;
        if (myLE.waitForIsElementPresent() && "SERVER RETURNED ERROR".equals(myLE.getText().toUpperCase())){
            isErrorMessagePresent = true;
        }
        refresh();
        return isErrorMessagePresent;
    }

    public boolean isShortLinkMatchesLongLink(String shortLinkName, String longLink){
        LabelElement myShortLink = new LabelElement(By.xpath(String.format(MY_SHORTLINK_LOCATOR_FORMAT, shortLinkName)));
        LabelElement myLongLink = new LabelElement(myShortLink.getElement().findElement(MY_LONGLINK_ABOUT_SHORTLINK_LOCATOR));
        myLongLink.showElement();
        String text = myLongLink.getText();
        refresh();
        return text.equals(longLink);
    }

    public String getLinkTabURL() {
        ClickableElement shortLinkElement = new ClickableElement(LAST_INSERTED_SHORTLINK_LOCATOR);
        shortLinkElement.click();

        String mainTab = getDriver().getWindowHandle();
        String linkTab = null;
        String actualLink = null;


        Set<String> set = getDriver().getWindowHandles();

        for (String s : set) {
            if (!s.equals(mainTab)) {
                linkTab = s;
            }
        }

        getDriver().switchTo().window(linkTab);
        actualLink = getDriver().getCurrentUrl();
        getDriver().close();
        getDriver().switchTo().window(mainTab);
        refresh();

		return actualLink;
	}

	public String searchByShortLinkName() {

		refresh();
		String newLinkNameForSearch = new LabelElement(LAST_INSERTED_SHORTLINK_LOCATOR).getElement().getText();
		new TextBoxElement(SEARCH_FILD_LOCATOR).type(newLinkNameForSearch);
		new ClickableElement(SEARCH_BUTTON_LOCATOR).click();
		return newLinkNameForSearch;
	}

	public boolean isSameLinkFoundBySearch(String linkNameForSearch) {

		String foundedShortLink = new LabelElement(LAST_INSERTED_SHORTLINK_LOCATOR).getElement().getText();
		return linkNameForSearch.equals(foundedShortLink);
	}

	public EpamsMainPage deleteNotAllLinks() {

		WebDriverWait wait = new WebDriverWait(getDriver(), TIMEOUT_IN_SECONDS);
		wait.until(ExpectedConditions.presenceOfElementLocated(SELECT_LINK_THREE_FOR_DELETE_LOCATOR));

		new ClickableElement(SELECT_LINK_ONE_FOR_DELETE_LOCATOR).click();
		new ClickableElement(SELECT_LINK_TWO_FOR_DELETE_LOCATOR).click();
		new ClickableElement(DELETE_SELECTED_LINKS_LOCATOR).click();
		if (confirmWindowDisappear()) {

			return new EpamsMainPage();
		}
		return new DeleteLinksPopUp().delete();
	}

	public boolean confirmWindowDisappear() {

		return getDriver().findElement(By.id("delete")).isDisplayed();
	}

	public int checkNumberOflinks() {

		List<WebElement> countRemainLinks = new LabelElement(SELECT_AND_COUNT_ALL_LINKS_LOCATOR).getList();
		refresh();
		return countRemainLinks.size();
	}
}
