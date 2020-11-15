package com.imdb.ui.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Driver {

    private static Driver driver=null;
    private WebDriver webDriver;


    public Driver(String browserName, String path){
        switch (browserName) {
            case "chrome":
                setDriverPath(browserName,path);
                webDriver = new ChromeDriver();
        }

    }

    public synchronized static Driver getInstanceOfDriverClass(String browserName, String path){
        if(driver==null){
            driver = new Driver(browserName,path);
        }
        return driver;
    }

    public synchronized WebDriver getWebDriver()
    {
        return webDriver;
    }

    public void openBrowser(String url)
    {
        webDriver.get(url);
    }

    public void setDriverPath(String browserName, String path)
    {
        switch (browserName){
            case "chrome":
                System.setProperty("webdriver.chrome.driver",path);
    }
    }


}
