package ui;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;

public class MyListsPageObject extends MainPageObject{
    private static final String
            FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']",
            ITEM_OVERFLOW_MENU = "id:org.wikipedia:id/item_overflow_menu";

    private static String getFolderXpathByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
    private static String getSavedArticleXpathByTitle(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    public MyListsPageObject(AppiumDriver driver){
        super(driver);
    }
    public void openFolderByName(String name_of_folder){
        this.waitForElementPresent(
                ITEM_OVERFLOW_MENU,
                "Cannot find item overflow menu",
                5
        );
        this.waitForElementAndClick(
                getFolderXpathByName(name_of_folder),
                "Cannot find folder by name " + name_of_folder,
                10
        );
    }
    public void openArticleByName(String name_article){
        this.waitForElementAndClick(
                getFolderXpathByName(name_article),
                "Cannot find atricle by name " + name_article,
                5
        );
    }
    public void waitForArticleToAppearByTitle(String article_title){
        this.waitForElementPresent(
                getSavedArticleXpathByTitle(article_title),
                "Cannot delete saved article",
                15
        );
    }
    public void waitForArticleToDisappearByTitle(String article_title){
        this.waitForElementNotPresent(
                getSavedArticleXpathByTitle(article_title),
                "Cannot delete saved article",
                15
        );
    }
    public void swipeByArticleToDelete(String article_title){
        this.waitForArticleToAppearByTitle(article_title);
        this.swipeElementToLeft(
                getSavedArticleXpathByTitle(article_title),
                "Cannot saved article"
        );
        this.waitForArticleToDisappearByTitle(article_title);
    }
}
