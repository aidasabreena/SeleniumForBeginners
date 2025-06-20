package com.practicetestautomation.tests.login;

import com.practicetestautomation.pageobjects.LoginPage;
import com.practicetestautomation.pageobjects.SuccessfulLoginPage;
import com.practicetestautomation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;


public class LoginTests extends BaseTest {
    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginFunctionality(){
        logger.info("Staring testLoginFunctionality");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        SuccessfulLoginPage successfulLoginPage = loginPage.executeLogin("student", "Password123");
        successfulLoginPage.load();

        logger.info("Verify login functionality");
        Assert.assertEquals(successfulLoginPage.getCurrentUrl(),"https://practicetestautomation.com/logged-in-successfully/");

        logger.info("Verify login message");
        Assert.assertTrue(successfulLoginPage.getPageSource().contains("Congratulations student. You successfully logged in!"));

        Assert.assertTrue(successfulLoginPage.isLogoutButtonDisplayed());
    }
    @Parameters({"username","password","expectedErrorMessage"})
    @Test (groups = {"negative", "regression"})
    public void negativeLoginTest(String username, String password, String expectedErrorMessage) {
        logger.info("Starting negativeLoginTest");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.executeLogin(username, password);
        Assert.assertEquals(loginPage.getErrorMessage(),expectedErrorMessage);

    }
}
