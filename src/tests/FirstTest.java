package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/u18285002/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
    }

    @After
    public void tearDown (){
        driver.quit();
    }

    @Test
    public void firstTest (){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
    }
    @Test
    public void testCancelSearch(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search finder",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );
        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot article title",
                15
        );

        String article_title = title_element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testAssertElementHasText() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Cannot article title",
                15
        );

    }
    @Test
    public void testCancelSearchEx3() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                5
        );
        boolean articleTrue = waitForListElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']"),
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Cannot find search input",
                10
        );
        Assert.assertTrue(
                "One article found",
                articleTrue
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']"),
                "One article found",
                10
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by, error_message, 5);
    }
    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message,timeoutInSeconds);
        element.click();
        return element;
    }
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message,timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }
    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.clear();
        return element;
    }
    private void assertElementHasText(By by, String text, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        String text_content = element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected title",
                text,
                text_content
        );
    }
    private boolean waitForListElement(By by_parent, By by, String error_message, long timeoutInSeconds){
        return waitForElementPresent(by_parent, error_message, timeoutInSeconds).findElements(by).size() > 1;
    }
}
