package element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectElement extends BaseElement {
    public SelectElement(By locator) {
        super(locator);
    }

    public SelectElement(WebElement element) {
        super(element);
    }

    public void select(String option){
        init();
        Select selectElement = new Select(element);
        selectElement.selectByVisibleText(option);
    }
}
