package com.imdb.ui.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver webDriver;

    public HomePage(WebDriver webDriver){
        this.webDriver= webDriver;
        PageFactory.initElements(webDriver,this);

    }

    @FindBy(xpath = "//input[contains(@class,'imdb-header-search__input')]")
    private WebElement searchInputBox;

    @FindBy(id = "suggestion-search-button")
    private WebElement	searchButton;

    public void setSearchInputBox(String searchInputBoxValue)
    {
        searchInputBox.sendKeys(searchInputBoxValue);
    }

    public void clickSearchButton()
    {
        searchButton.click();
    }
}
