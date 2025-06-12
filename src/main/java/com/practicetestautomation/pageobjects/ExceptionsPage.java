package com.practicetestautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExceptionsPage extends BasePage {
    private By addButtonLocator = By.id("add_btn");
    private By rowOneLocator = By.xpath("//div[@id='row1']/input");
    private By rowTwoLocator = By.xpath("//div[@id='row2']/input");
    private By rowOneSaveButtonLocator = By.xpath("//div[@id='row1']/button[@name='Save']");
    private By rowTwoSaveButtonLocator = By.xpath("//div[@id='row2']/button[@name='Save']");
    private By savedMessageLocator = By.id("confirmation");
    private By editButtonLocator = By.id("edit_btn");
    private By instructionsLocator = By.id("instructions");

    public ExceptionsPage(WebDriver driver){
        super(driver);
    }
    public void visit(){
        super.visit("https://practicetestautomation.com/practice-test-exceptions/");
    }
    public void pushAddButton(){
        driver.findElement(addButtonLocator).click();
    }
    public void pushEditButton(){
        driver.findElement(editButtonLocator).click();
    }
    public boolean isRowTwoDisplayedAfterWait(){
        return waitForIsDisplayed(rowTwoLocator);
    }
    public void enterFoodInRowOne(String food){
        WebElement rowOneInput = driver.findElement(rowOneLocator);
        rowOneInput.clear();
        rowOneInput.sendKeys(food);

    }
    public void enterFoodInRowTwo(String food){
        driver.findElement(rowTwoLocator).sendKeys(food);
    }
    public void saveRowOne(){
        driver.findElement(rowOneSaveButtonLocator).click();
    }
    public void saveRowTwo(){
        driver.findElement(rowTwoSaveButtonLocator).click();
    }
    public String getSavedMessage(){
        return waitForElement(savedMessageLocator).getText();
    }
    public boolean isInstructionsElementHiddenAfterWait(){
        return waitForIsHidden(instructionsLocator);
    }
}
