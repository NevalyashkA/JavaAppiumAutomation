package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class ArticlePageObject extends MainPageObject{

    private static final String
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
            OPTIONS_FONT_AND_THEME = "xpath://*[@text='Font and theme']",
            ADD_TO_MY_LIS_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            MY_LIST_INPUT = "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
            LIST_ITEM_TITLE = "id:org.wikipedia:id/item_title",
            LIST_ITEM_TITLE_TPL = "xpath://*[@text='Articles']";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    private static String getListItemTitleElement (String substring){
        return LIST_ITEM_TITLE_TPL.replace("{ITEM}",substring);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title on page!",
                15
        );
    }
    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }
    public void swipeToFooter (){
        this.swipeUpToElement(
                FOOTER_ELEMENT,
                "Cannot find the end of the article",
                20

        );
    }
    public void addArticleToMyList (String name_of_folder){
        waitForTitleElement();
        this.waitForElementPresent(
                CLOSE_ARTICLE_BUTTON,
                "Cannot find btn to option article");

        this.waitForElementAndClick(
               OPTIONS_BUTTON,
                "Cannot find btn to option article",
                10
        );
        this.waitForElementPresent(
                OPTIONS_FONT_AND_THEME,
                "Cannot find options list",
                5
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article 'Add to reading list'",
                5
        );
        this.waitForElementAndClick(
                ADD_TO_MY_LIS_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );
        this.waitForElementAndClear(
                MY_LIST_INPUT,
                "Cannot find input to set name of articles folder",
                5
        );
        this.waitForElementAndSendKeys(
                MY_LIST_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press ok btn",
                5
        );
    }
    public void addNextArticleToMyList (String name_of_folder){
        waitForTitleElement();
        this.waitForElementPresent(
                CLOSE_ARTICLE_BUTTON,
                "Cannot find btn to option article");

        this.waitForElementAndClick(
               OPTIONS_BUTTON,
                "Cannot find btn to option article",
                5
        );
        this.waitForElementPresent(
                OPTIONS_FONT_AND_THEME,
                "Cannot find options list",
                5
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article 'Add to reading list'",
                5
        );
        this.waitForElementAndClick(
                getListItemTitleElement(name_of_folder),
                "Cannot find created list articles",
                15
        );

    }
    public void closeArticle(){
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );
    }
}
