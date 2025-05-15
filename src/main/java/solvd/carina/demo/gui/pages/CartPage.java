package solvd.carina.demo.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    @FindBy(css = "[data-test='checkout']")
    private ExtendedWebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCheckoutButtonPresent() {
        return checkoutButton.isElementPresent();
    }

    public CheckoutPage clickCheckout() {
        checkoutButton.click();
        return new CheckoutPage(getDriver());
    }
}
