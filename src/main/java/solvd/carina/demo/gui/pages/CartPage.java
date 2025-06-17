package solvd.carina.demo.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;


import java.time.Duration;

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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='checkout']")));

        checkoutButton.scrollTo();
        checkoutButton.click();
        return new CheckoutPage(getDriver());
    }

}
