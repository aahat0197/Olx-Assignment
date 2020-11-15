package com.imdb.ui.pageObjects;

import com.imdb.ui.common.Driver;
import com.imdb.ui.common.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultsPage {

    Utilities utilities = new Utilities();

    private WebDriver webDriver;

    public ResultsPage(WebDriver webDriver){
        this.webDriver= webDriver;
        PageFactory.initElements(webDriver,this);

    }


    @FindBy(xpath = "//a[text()='Movie']")
    private WebElement movieLink;

    public void waitForMovieLinkAndClick(int time)
    {
        WebDriverWait wait = new WebDriverWait(webDriver,time);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Movie']")));
        movieLink.click();
    }
//
//    public void checkTitleIsDisplayedOrNot()
//    {
//
//    }
}
