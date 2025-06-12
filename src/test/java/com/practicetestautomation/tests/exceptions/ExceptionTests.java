package com.practicetestautomation.tests.exceptions;

import com.practicetestautomation.pageobjects.ExceptionsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionTests {
    private WebDriver driver;
    private Logger logger;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setupBrowser(@Optional("firefox") String browser){
        logger = Logger.getLogger(ExceptionTests.class.getName()); // initialized in setup method so that we can use the same logger instance in all test methods and teardown methods.
        logger.setLevel(Level.INFO); //provides good balance of info without being too verbose
        logger.info("Running test in " + browser);
        switch (browser.toLowerCase()){
            case "safari":
                driver = new SafariDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                logger.warning("Configuration for " + browser + " is missing, so running tests in Chrome");
                driver = new ChromeDriver();
                break;
        }
        // Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        logger.info("Browser is closed");
        driver.quit();
    }
    @Test(groups = {"exception"})
    //NoSuchElementException
    public void testCaseOne(){
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.pushAddButton();
        Assert.assertTrue(exceptionsPage.isRowTwoDisplayedAfterWait(), "Row 2 input field is not displayed");
    }

    @Test(groups = {"exception"})
    // ElementNotInteractableException
    public void testCaseTwo(){
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.pushAddButton();
        exceptionsPage.isRowTwoDisplayedAfterWait();
        exceptionsPage.enterFoodInRowTwo("Nasi Lemak");
        exceptionsPage.saveRowTwo();
        Assert.assertEquals(exceptionsPage.getSavedMessage(),"Row 2 was saved", "Message is not as expected");
    }

    @Test(groups = {"exception"})
    //InvalidElementStateException
    public void testCaseThree(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.pushEditButton();
        exceptionsPage.enterFoodInRowOne("Sushi");
        exceptionsPage.saveRowOne();
        Assert.assertEquals(exceptionsPage.getSavedMessage(),"Row 1 was saved", "Message is not as expected");
    }
    @Test
    //StaleElementReferenceException
    public void testCaseFour(){
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.pushAddButton();

        //Verify instruction text element is no longer displayed
        Assert.assertTrue(exceptionsPage.isInstructionsElementHiddenAfterWait(),"Instructions is still displayed");
    }
    @Test
    //TimeoutException
    public void testCaseFive(){
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.pushAddButton();
        Assert.assertTrue(exceptionsPage.isRowTwoDisplayedAfterWait(),"Row 2 input field is not displayed");
    }
}
