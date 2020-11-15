package com.imdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.api.common.DataProviderFactory;
import com.imdb.api.common.JsonFilePath;
import com.imdb.api.manager.IMDBManager;
import com.imdb.api.model.imdbResponse.IdTitleAPI.TitleResponseDTO;
import com.imdb.api.model.imdbResponse.SearchAPI.SearchDTO;
import com.imdb.api.model.imdbResponse.SearchAPI.SearchResponseDTO;
import com.imdb.ui.common.Driver;
import com.imdb.ui.pageObjects.HomePage;
import com.imdb.ui.pageObjects.ResultsPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class IMDBTest {
    public Properties propertyFile = new Properties();
    private FileInputStream stream;
    static Logger logger= Logger.getLogger(IMDBTest.class);
    public String browser;

    @Parameters({ "environment","browser" })
    @BeforeClass
    public void setEnvironment(@Optional("imdb") String environment, String browser) throws Exception {

        try {
            stream = new FileInputStream(
                    System.getProperty("user.dir") + "/config/config_" + environment + ".properties");
            try {
                propertyFile.load(stream);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                logger.error("File Not Found Exception");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logger.error("File Not Found Exception");
            e.printStackTrace();
        }
        this.browser= browser;

    }


    public WebDriver setup(String browserName) throws Exception{
        WebDriver webDriver;
        if (browserName.equalsIgnoreCase("Firefox")) {
            webDriver = new FirefoxDriver();
        }
        else if (browserName.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver",
                    propertyFile.getProperty("CHROME_DRIVER_PATH"));
            webDriver = new ChromeDriver();
        }
        else {
            throw new Exception("Browser is not correct");
        }
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return webDriver;
    }

    public void closeDriver(WebDriver webDriver)
    {
        System.err.println("After ID" + Thread.currentThread().getId());
            webDriver.close();

    }

    @JsonFilePath(path = "imdb.json")
    @Test(enabled = true, dataProvider = "json", dataProviderClass = DataProviderFactory.class)
    public void searchIMDBMovieWithHightestIMDBRating(HashMap<String, Object> data) throws Exception {

        WebDriver webDriver= setup(browser);
        ObjectMapper objectMapper= new ObjectMapper();
        HashMap<String,Object> searchRequest= objectMapper.convertValue(data.get("searchRequest"),HashMap.class);
        IMDBManager imdbManager= new IMDBManager();
        SearchResponseDTO searchResponseDTO= imdbManager.searchAPI(searchRequest);
        HashMap<String,Object> titleRequest= objectMapper.convertValue(data.get("titleRequest"),HashMap.class);
        for(SearchDTO searchDTO:searchResponseDTO.getSearch())
        {
            HashMap<String,Object> titleRequestQueryParams= objectMapper.convertValue(titleRequest.get("queryParams"),HashMap.class);
            titleRequestQueryParams.put("i",searchDTO.getImdbId());
            titleRequest.put("queryParams",titleRequestQueryParams);

            TitleResponseDTO titleResponseDTO= imdbManager.titleAPI(titleRequest);
            searchDTO.setRating(Float.parseFloat(titleResponseDTO.getImdbRating()));
        }
        searchResponseDTO.getSearch().sort(new Comparator<SearchDTO>() {
            public int compare(SearchDTO o1, SearchDTO o2) {
                if( o1.getRating()<o2.getRating())
                {
                    return 1;
                }
                else if (o1.getRating()>o2.getRating())
                {
                    return -1;
                }
                return 0;

            }
        });
       String imdbWebURL = propertyFile.getProperty("IMDB_WEB_URL");
        webDriver.get(imdbWebURL);
        JSONObject jsonObjectWebSearchData= (JSONObject) data.get("webSearchData");
        HomePage homePage=PageFactory.initElements(webDriver,HomePage.class);
        homePage.setSearchInputBox(jsonObjectWebSearchData.get("searchValue").toString());
        homePage.clickSearchButton();
        ResultsPage resultsPage = PageFactory.initElements(webDriver,ResultsPage.class);
        resultsPage.waitForMovieLinkAndClick(30); System.out.println(objectMapper.writeValueAsString(searchResponseDTO));
        for(int j=0;j<3;j++)
        {
            Assert.assertTrue(webDriver.findElement(By.xpath("//a[text()='"+searchResponseDTO.getSearch().get(j).getTitle()+"']")).isDisplayed());
        }
        closeDriver(webDriver);
    }


    @JsonFilePath(path = "imdb.json")
    @Test(enabled = true, dataProvider = "json", dataProviderClass = DataProviderFactory.class)
    public void searchIMDBMovieWithHightestIMDBRatin(HashMap<String, Object> data) throws Exception {
        WebDriver webDriver= setup(browser);
        ObjectMapper objectMapper= new ObjectMapper();
        HashMap<String,Object> searchRequest= objectMapper.convertValue(data.get("searchRequest"),HashMap.class);
        IMDBManager imdbManager= new IMDBManager();
        SearchResponseDTO searchResponseDTO= imdbManager.searchAPI(searchRequest);
        HashMap<String,Object> titleRequest= objectMapper.convertValue(data.get("titleRequest"),HashMap.class);
        for(SearchDTO searchDTO:searchResponseDTO.getSearch())
        {
            HashMap<String,Object> titleRequestQueryParams= objectMapper.convertValue(titleRequest.get("queryParams"),HashMap.class);
            titleRequestQueryParams.put("i",searchDTO.getImdbId());
            titleRequest.put("queryParams",titleRequestQueryParams);

            TitleResponseDTO titleResponseDTO= imdbManager.titleAPI(titleRequest);
            searchDTO.setRating(Float.parseFloat(titleResponseDTO.getImdbRating()));
        }
        searchResponseDTO.getSearch().sort(new Comparator<SearchDTO>() {
            public int compare(SearchDTO o1, SearchDTO o2) {
                if( o1.getRating()<o2.getRating())
                {
                    return 1;
                }
                else if (o1.getRating()>o2.getRating())
                {
                    return -1;
                }
                return 0;

            }
        });
        String imdbWebURL = propertyFile.getProperty("IMDB_WEB_URL");
        webDriver.get(imdbWebURL);
        JSONObject jsonObjectWebSearchData= (JSONObject) data.get("webSearchData");
        HomePage homePage=PageFactory.initElements(webDriver,HomePage.class);
        homePage.setSearchInputBox(jsonObjectWebSearchData.get("searchValue").toString());
        homePage.clickSearchButton();
        ResultsPage resultsPage = PageFactory.initElements(webDriver,ResultsPage.class);
        resultsPage.waitForMovieLinkAndClick(30);
        System.out.println(objectMapper.writeValueAsString(searchResponseDTO));
        for(int j=0;j<3;j++)
        {
            Assert.assertTrue(webDriver.findElement(By.xpath("//a[text()='"+searchResponseDTO.getSearch().get(j).getTitle()+"']")).isDisplayed());
        }
        closeDriver(webDriver);
    }



}
