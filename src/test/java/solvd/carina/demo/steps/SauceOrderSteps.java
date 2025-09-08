package solvd.carina.demo.steps;

import com.zebrunner.carina.core.AbstractTest;
import org.apache.ibatis.session.SqlSession;
import io.cucumber.java.en.*;
import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solvd.carina.demo.db.MyBaitsUtil;
import solvd.carina.demo.db.mappers.OrderMapper;
import solvd.carina.demo.db.mappers.UserMapper;
import solvd.carina.demo.db.models.Order;
import solvd.carina.demo.db.models.User;
import solvd.carina.demo.gui.pages.*;

import java.util.List;

import static solvd.carina.demo.constants.CheckoutConstants.*;

public class SauceOrderSteps extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(SauceOrderSteps.class);

    private User user;
    private List<Order> orders;

    @Given("user {string} logs in")
    public void userLogsIn(String username) {
        try (SqlSession session = MyBaitsUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            user = userMapper.getUserByUsername(username);
        }

        Assert.assertNotNull(user, "User not found in DB: " + username);

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        loginPage.login(user.getUsername(), "secret_sauce"); // zakładamy wspólne hasło
        logger.info("User {} logged in successfully", user.getUsername());
    }

    @And("orders all his products from the database")
    public void ordersAllProductsFromDatabase() {
        try (SqlSession session = MyBaitsUtil.getSession()) {
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            orders = orderMapper.getOrdersByUserId(user.getId());
        }

        InventoryPage inventoryPage = new InventoryPage(getDriver());

        List<Order> validOrders = orders.stream()
                .filter(o -> o.getProductName() != null && !o.getProductName().trim().isEmpty())
                .toList();

        Assert.assertFalse(validOrders.isEmpty(), "No valid product names found for user: " + user.getUsername());

        for (Order order : validOrders) {
            inventoryPage.addToCart(order.getProductName());
            logger.info("Added product {} to cart", order.getProductName());
        }

        inventoryPage.clickCartIcon();
        logger.info("Navigated to the cart.");
    }

    @And("completes the checkout")
    public void completesTheCheckout() {
        logger.info("Starting checkout process...");

        InventoryPage inventoryPage = new InventoryPage(getDriver());
        CartPage cartPage = inventoryPage.clickCartIcon();
        logger.info("Clicked on cart icon.");

        cartPage.clickCheckout();
        logger.info("Clicked on Checkout button.");

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.fillForm(FIRST_NAME, LAST_NAME, ZIP_CODE);
        logger.info("Filled out checkout form.");

        checkoutPage.finishOrder();
        logger.info("Order completed successfully.");
    }

    @Then("the order should be completed successfully")
    public void theOrderShouldBeCompletedSuccessfully() {
        CheckoutCompletePage completePage = new CheckoutCompletePage(getDriver());
        Assert.assertTrue(completePage.isThankYouMessageVisible(), "Order confirmation is not visible!");
        logger.info("Order confirmation is visible, order completed successfully.");
    }
}
