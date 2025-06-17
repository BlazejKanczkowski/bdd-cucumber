package solvd.carina.demo.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import solvd.carina.demo.gui.components.ProductListItemComponent;

import java.time.Duration;
import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(css = "[data-test='shopping-cart-link']")
    private ExtendedWebElement cartBadge;


    @FindBy(css = "[data-test='inventory-item']")
    private List<ProductListItemComponent> productItems;

    public InventoryPage(WebDriver driver){
        super(driver);
    }

    public CartPage clickCartIcon() {
        cartBadge.scrollTo();
        cartBadge.click();
        return new CartPage(getDriver());
    }

    public ProductListItemComponent getProductByName(String name) {
        return productItems.stream()
                .filter(item -> item.getItemName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found: " + name));
    }

    public void addToCart(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is null or empty");
        }

        String normalizedName = productName.toLowerCase().replace(" ", "-").replaceAll("[().]", "");
        String selector = String.format("[data-test='add-to-cart-%s']", normalizedName);

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                System.out.println("Attempt " + attempt + ": trying selector = " + selector);

                WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
                WebElement buttonElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));

                ExtendedWebElement addToCartButton = new ExtendedWebElement(buttonElement, selector);
                addToCartButton.click();
                System.out.println("Successfully clicked Add to cart for: " + productName);
                return;

            } catch (Exception e) {
                System.out.println("Attempt " + attempt + " failed: " + e.getMessage());
                if (attempt < maxAttempts) {
                    System.out.println("Refreshing page and retrying...");
                    getDriver().navigate().refresh();
                } else {
                    throw new RuntimeException("Add to cart button not found after retries for: " + productName, e);
                }
            }
        }
    }
}
