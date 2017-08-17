package element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LabelElement extends BaseElement {

    public LabelElement(By locator) {
        super(locator);
    }

    public LabelElement(WebElement element) {
        super(element);
    }

    public void showElement(){
        init();
    }


}
