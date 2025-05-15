package solvd.carina.demo;

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


public class SauceOrderSteps extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(SauceOrderSteps.class);

    private User user;
    private List<Order> orders;

    @Given("user logs in with username {string}")
    public void user_logs_in_with_username(String username) {
        try (SqlSession session = MyBaitsUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            user = userMapper.getUserByUsername(username);
        }

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        loginPage.login(user.getUsername(), user.getPassword());
        logger.info("User {} logged in successfully", user.getUsername());
    }

    @And("orders his products")
    public void orders_his_products() {
        try (SqlSession session = MyBaitsUtil.getSession()) {
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            orders = orderMapper.getOrdersByUserId(user.getId());
        }

        InventoryPage inventoryPage = new InventoryPage(getDriver());

        for (Order order : orders) {
            if (order.getProductName() != null && !order.getProductName().isEmpty()) {
                inventoryPage.getProductByName(order.getProductName()).addToCart(order.getProductName());
                logger.info("Added product {} to cart", order.getProductName());
            } else {
                logger.warn("Product name is null or empty for order id: {}", order.getId());
            }
        }

        inventoryPage.clickCartIcon();
        logger.info("Navigated to the cart.");
    }

    @And("completes the checkout")
    public void completes_the_checkout() {
        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckout();
        logger.info("Checkout process started.");

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.fillForm("John", "Doe", "12345");
        checkoutPage.finishOrder();
        logger.info("Order completed successfully.");
    }

    @Then("the order should be completed successfully")
    public void the_order_should_be_completed_successfully() {
        CheckoutCompletePage completePage = new CheckoutCompletePage(getDriver());
        Assert.assertTrue(completePage.isThankYouMessageVisible(), "Order confirmation is not visible!");
        logger.info("Order confirmation is visible, order completed successfully.");
    }
}
