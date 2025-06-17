package solvd.carina.demo.steps;

import com.zebrunner.carina.core.IAbstractTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Hooks implements IAbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setup(Scenario scenario) {
        String threadName = "Scenario-" + Thread.currentThread().getId();
        Thread.currentThread().setName(threadName);
        logger.info("Starting scenario: {}", scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = getDriver();
        if (scenario.isFailed() && driver instanceof TakesScreenshot) {
            try {
                String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
                Path screenshotPath = Paths.get("target", "screenshots", "screenshot-of-failed-" + scenarioName + ".png");
                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.createDirectories(screenshotPath.getParent());
                Files.copy(screenshotFile.toPath(), screenshotPath);
                logger.info("Screenshot saved at: {}", screenshotPath.toAbsolutePath());
            } catch (IOException e) {
                logger.error("Failed to save screenshot: {}", e.getMessage());
            }
        }
        logger.info("Finished scenario: {}", scenario.getName());
    }
}
