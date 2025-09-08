package solvd.carina.demo.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {

    @FindBy(css = "[data-test = 'complete-text']")
    private ExtendedWebElement thankYouMessage;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public boolean isThankYouMessageVisible() {
        return thankYouMessage.isElementPresent();
    }
}
