package com.practicetestautomation.tests.exceptions;

import com.practicetestautomation.pageobjects.ExceptionsPage;
import com.practicetestautomation.tests.BaseTest;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ExceptionTests extends BaseTest {
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
