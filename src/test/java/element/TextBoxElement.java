package element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TextBoxElement extends BaseElement {
    public TextBoxElement(By locator) {
        super(locator);
    }

    public TextBoxElement(WebElement element) {
        super(element);
    }

    public void type(String text){
        init();
        element.clear();
        element.sendKeys(text);
    }
}
