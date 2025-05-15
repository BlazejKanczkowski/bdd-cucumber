package solvd.carina.demo.gui.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.math.BigDecimal;

public class ProductListItemComponent extends AbstractUIObject {

    @FindBy(css = "[data-test='inventory-item-name']")
    private ExtendedWebElement itemName;

    @FindBy(css = "[data-test='inventory-item-price']")
    private ExtendedWebElement itemPrice;

    public ProductListItemComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getItemName() {
        return itemName.getText();
    }

    public BigDecimal getItemPrice() {
        try {
            String rawText = itemPrice.getText().replace("$", "").trim();
            return new BigDecimal(rawText);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to parse price: " + itemPrice.getText(), e);
        }
    }
}
