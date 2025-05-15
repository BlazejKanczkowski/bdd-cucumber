package solvd.carina.demo.gui.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.FindBy;

import java.math.BigDecimal;
import java.util.List;

import static solvd.carina.demo.constants.ProductConstants.*;

public class ProductListItemComponent extends AbstractUIObject {

    @FindBy(css = "[data-test='inventory-item-name']")
    private ExtendedWebElement itemName;

    @FindBy(css = "[data-test='inventory-item-price']")
    private ExtendedWebElement itemPrice;

    @FindBy(css = "[data-test='add-to-cart-sauce-labs-backpack']")
    private ExtendedWebElement addToCartButtonBackpack;

    @FindBy(css = "[data-test='add-to-cart-sauce-labs-bike-light']")
    private ExtendedWebElement addToCartButtonBikeLight;

    @FindBy(css = "[data-test='add-to-cart-sauce-labs-bolt-t-shirt']")
    private ExtendedWebElement addToCartButtonBoltTShirt;

    @FindBy(css = "[data-test='add-to-cart-sauce-labs-onesie']")
    private ExtendedWebElement addToCartButtonOnesie;

    @FindBy(css = "[data-test='add-to-cart-test-all-the-things-t-shirt']")
    private ExtendedWebElement addToCartButtonTestShirt;

    @FindBy(css = "[data-test='add-to-cart-sauce-labs-fleece-jacket']")
    private ExtendedWebElement addToCartButtonFleeceJacket;

    @FindBy(css = "[data-test='inventory-item']")
    private List<ProductListItemComponent> productItems;

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

    public void addToCart(String productName) {
        switch (productName) {
            case SAUCE_LABS_BACKPACK:
                addToCartButtonBackpack.click();
                break;
            case SAUCE_LABS_BIKE_LIGHT:
                addToCartButtonBikeLight.click();
                break;
            case SAUCE_LABS_BOLT_T_SHIRT:
                addToCartButtonBoltTShirt.click();
                break;
            case SAUCE_LABS_ONESIE:
                addToCartButtonOnesie.click();
                break;
            case TEST_ALL_THE_THINGS_T_SHIRT:
                addToCartButtonTestShirt.click();
                break;
            case SAUCE_LABS_FLEECE_JACKET:
                addToCartButtonFleeceJacket.click();
                break;
            default:
                throw new RuntimeException("Product not found: " + productName);
        }
    }
}
