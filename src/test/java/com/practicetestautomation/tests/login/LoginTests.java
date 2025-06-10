package com.practicetestautomation.tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginTests {
    private WebDriver driver;
    private Logger logger;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setupBrowser(@Optional("chrome") String browser){
        logger = Logger.getLogger(LoginTests.class.getName()); // initialized in setup method so that we can use the same logger instance in all test methods and teardown methods.
        logger.setLevel(Level.INFO); //provides good balance of info without being too verbose
        logger.info("Running test in " + browser);
        switch (browser.toLowerCase()){
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                logger.warning("Configuration for " + browser + " is missing, so running tests in Chrome");
                driver = new ChromeDriver();
                break;
        }
        driver = new FirefoxDriver();
        // Open page
        driver.get("https://practicetestautomation.com/practice-test-login/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        logger.info("Browser is closed");
        driver.quit();
    }
    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginOne(){
        logger.info("Staring testLoginOne");
        // Type username student into Username field
        WebElement usernameInput = driver.findElement(By.id("username"));
        logger.info("Type username");
        //usernameInput.sendKeys("student");
        usernameInput.sendKeys("student");
        // Type password Password123 into Password field
        WebElement passwordInput = driver.findElement(By.id("password"));
        logger.info("Type password");
        passwordInput.sendKeys("Password123");

        // Push Submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        logger.info("Click submit button");
        submitButton.click();

        try {
            Thread.sleep(2000); // to add a pause
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Verify new page URL contains practicetestautomation.com/logged-in-successfully/
        logger.info("Verify login functionality");
        String expectedPageUrl = "https://practicetestautomation.com/logged-in-successfully/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl,expectedPageUrl);

        // Verify new page contains expected text ('Congratulations' or 'successfully logged in')
        logger.info("Verify login message");
        String expectedMessage = "Congratulations student. You successfully logged in!";
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains(expectedMessage));

        // Verify button Log out is displayed on the new page
        logger.info("Verify logout button visibility");
        WebElement logoutButton = driver.findElement(By.linkText("Log out"));
        Assert.assertTrue(logoutButton.isDisplayed());
    }
    @Parameters({"username","password","expectedErrorMessage"})
    @Test (groups = {"negative", "regression"})
    public void negativeLoginTest(String username, String password, String expectedErrorMessage) {
        logger.info("Starting negativeLoginTest");
        // Type username incorrectUser into Username field
        WebElement usernameInput = driver.findElement(By.id("username"));
        logger.info("Type username" + username);
        usernameInput.sendKeys(username);
        // Type password Password123 into Password field
        WebElement passwordInput = driver.findElement(By.id("password"));
        logger.info("Type password");
        passwordInput.sendKeys(password);

        // Push Submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        logger.info("Click submit button");
        submitButton.click();

        try {
            Thread.sleep(2000); // to add a pause
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Verify error message is displayed
        logger.info("Verify the expected error message" + expectedErrorMessage);
        WebElement errorMessage = driver.findElement(By.id("error"));
        Assert.assertTrue(errorMessage.isDisplayed());
        // Verify error message text is Your username is invalid!
        logger.info("Verify error message");
        String actualErrorMessage = errorMessage.getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
