package solvd.carina.demo.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import solvd.carina.demo.gui.components.ProductListItemComponent;

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

        String normalizedName = productName.toLowerCase().replace(" ", "-");
        String selector = String.format("[data-test='add-to-cart-%s']", normalizedName);

        ExtendedWebElement addToCartButton = new ExtendedWebElement(
                getDriver().findElement(By.cssSelector(selector)), selector);

        if (addToCartButton.isElementPresent()) {
            addToCartButton.click();
        } else {
            throw new RuntimeException("Add to cart button not found for product: " + productName);
        }
    }
}
