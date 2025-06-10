package com.practicetestautomation.tests.exceptions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
    public void setupBrowser(@Optional("chrome") String browser){
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
    public void testCaseOne(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        logger.info("Click Add button");
        addButton.click();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement rowTwo =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        // Verify Row 2 input field is displayed
        Assert.assertTrue(rowTwo.isDisplayed());
    }

    @Test(groups = {"exception"})
    public void testCaseTwo(){
        // Open page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        logger.info("Click Add button");
        addButton.click();

        // Wait for the second row to load
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement secondInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id = 'row2']/input")));
        // Type text into the second input field
        logger.info("Type in row2");
        secondInput.sendKeys("Pasta");

        // Push Save button using locator By.name(“Save”)
        WebElement saveButton = driver.findElement(By.name("Save"));
        logger.info("Click save button");
        saveButton.click();

        //Verify text saved
        WebElement savedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String actualMessage = savedMessage.getText();
        String expectedMessage = "Row 2 was saved";
        Assert.assertEquals(actualMessage,expectedMessage, "message is different");

        //This page contains two elements with attribute name=”Save”.
        //The first one is invisible. So when we are trying to click on the invisible element, we get ElementNotInteractableException.
        saveButton.click();
        //The same action used to throw ElementNotVisibleException, but now it throws a different exception (not sure if it’s a bug in Selenium or a feature)
    }

    @Test(groups = {"exception"})
    public void testCaseThree(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Clear input field
        WebElement firstInput = driver.findElement(By.xpath("//div[@id='row1']/input"));
        WebElement editButtton = driver.findElement(By.id("edit_btn"));
        editButtton.click();
        logger.info("clearing row1");
        firstInput.clear();

        //Type text into the input field
        String input = "testetstets";
        firstInput.sendKeys(input);

        //Verify text changed
        WebElement saveButton = driver.findElement(By.id("save_btn"));
        logger.info("Click save button");
        saveButton.click();

        //Verify text saved
        WebElement savedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String actualMessage = savedMessage.getText();
        String expectedMessage = "Row 1 was saved";
        Assert.assertEquals(actualMessage,expectedMessage, "message is different");

        //The input field is disabled. Trying to clear the disabled field will throw InvalidElementStateException. We need to enable editing of the input field first by clicking the Edit button.

        //If we try to type text into the disabled input field, we will get ElementNotInteractableException, as in Test case 2.
    }
    @Test
    public void testCaseFour(){

        //Find the instructions text element
        //Push add button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addButton = driver.findElement(By.id("add_btn"));
        logger.info("Click Add button");
        addButton.click();
        //Verify instruction text element is no longer displayed
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))));
    }
    @Test
    public void testCaseFive(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        //Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        logger.info("Click Add button");
        addButton.click();
        //Wait for 3 seconds for the second input field to be displayed
        WebElement secondInput =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        // Verify Row 2 input field is displayed
        Assert.assertTrue(secondInput.isDisplayed());
    }
}
