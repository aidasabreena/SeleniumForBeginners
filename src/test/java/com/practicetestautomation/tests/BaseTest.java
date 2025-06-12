package com.practicetestautomation.tests;

import com.practicetestautomation.tests.exceptions.ExceptionTests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest {
    protected WebDriver driver;
    protected Logger logger;

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
                logger.warning("Configuration for " + browser + " is missing, so running tests in Firefox");
                driver = new FirefoxDriver();
                break;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
        logger.info("Browser is closed");
    }
}
