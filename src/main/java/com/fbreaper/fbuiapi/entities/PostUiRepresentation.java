package com.fbreaper.fbuiapi.entities;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.fbreaper.domain.PostImage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.codeborne.selenide.Selenide.$x;
import static com.fbreaper.utils.configs.NoBeanConfigsLoader.load;

public class PostUiRepresentation extends AbstractUiEntity {

    private Logger log = LoggerFactory.getLogger(PostUiRepresentation.class);

    private SelenideElement rawPost;
    public static final String POST = "//div[@role='article' and contains(@id,'post') and not(contains(@id,','))]";
    private static final String POST_AUTHOR_LINK = ".//h5//a";
    private static final String POST_TIMESTAMP = ".//abbr";
    private static final String POST_MESSAGE = ".//*[contains(@class,'userContent ')]";
    private static final String POST_VIEW_MORE_MESSAGE_LINK = ".//*[contains(text(),'more comments')]/ancestor::div[@class='clearfix'][1]";
    private static final String POST_COMMENTS = ".//*[@class='UFICommentActorAndBodySpacing']";
    private static final String POST_COMMENT_LOADER = ".//*[@aria-valuetext='Loading...']";
    private static final String POST_AUTHOR_IMG = ".//*[contains(@class,'clearfix ')]//img";
    private static final String POST_MAIN_IMAGE = ".//div[@class='mtm']//a[@rel='theater'][1]";
    private static final SelenideElement POST_IMAGE_VIEWER = $x("//*[@id='photos_snowlift']");
    private static final String IMAGE_FULL_SIZE_IN_VIEWER = ".//img[@class='spotlight']";
    private static final String BUTTON_POST_IMAGES_VIEWER_CLOSE = "//*[@id='photos_snowlift']//i[1]";
    private static final String BUTTON_POST_IMAGES_VIEWER_NEXT = ".//a[@title='Next']";
    private static final SelenideElement POST_PHOTO_THEATER_MAIN_DIALOG_LOADED = $x("//*[@id='photos_snowlift' and contains(@class,'Available')]");
    private static final SelenideElement POST_PHOTO_THEATER_MAIN_DIALOG_NAVIGATABEL = $x("//*[@id='photos_snowlift' and contains(@class,'Activated')]");
    private static final String POST_LINK = ".//div[contains(@id,'feed_subtitle_')]//a[@target]";
    private static int IMAGES_LIMIT = load().fbBigImagesLimit();
    private static Long IMAGES_LOAD_TIMEOUT = load().fbBigImagesLoadTimeout();

    public PostUiRepresentation(SelenideElement rawPost){
        this.rawPost = rawPost;
    }

    public List<PostImage> fetchPostFullSizeImages() {
        String imgUrl;
        int firstImgSize = 0;
        List<PostImage> images = new ArrayList<>();
        if (isPostHasFullSizeImages()) {
            rawPost.$x(POST_MAIN_IMAGE).click();
            try {
                POST_PHOTO_THEATER_MAIN_DIALOG_LOADED.should(Condition.visible);
            } catch (Throwable t){
                log.info("IGNORE ERROR [" + t + "] during waiting for image theater will be open. Continue working...");
                Selenide.actions().sendKeys(Keys.ESCAPE).perform();
                return images;
            }
                for (int i = 0; i < IMAGES_LIMIT; i++) {
                    try {
                        imgUrl = fetchAttributeData(POST_IMAGE_VIEWER.$x(IMAGE_FULL_SIZE_IN_VIEWER), "src");
                        byte[] image = FileUtils.readFileToByteArray(Selenide.download(imgUrl));
                        if (firstImgSize == 0) firstImgSize = image.length;
                        if (i != 0 && firstImgSize == image.length) {
                            closePostFullSizeImagesViewer();
                            return images;
                        }
                        images.add(new PostImage(image));
                        activateNavigationBarInPhotoTheater();
                        clickNextImageInPostImageViewer();
                        POST_IMAGE_VIEWER.$x(IMAGE_FULL_SIZE_IN_VIEWER).waitWhile(Condition.attribute("src", imgUrl), IMAGES_LOAD_TIMEOUT);
                    } catch (Throwable t) {
                        log.info("IGNORE ERROR [" + t + "] during imageBytes downloading. Continue working...");
                        if (t.getMessage().contains("Element should not be attribute src")){
                            closePostFullSizeImagesViewer();
                            return images;
                        }
                    }
                }
            closePostFullSizeImagesViewer();
        }
        return images;
    }

    public PostImage fetchPostAuthorImage(){
        PostImage image = new PostImage();
        try {
            File img = Selenide.download(rawPost.$x(POST_AUTHOR_IMG).getAttribute("src"));
            image.setImageBytes(FileUtils.readFileToByteArray(img));
            image.setFileName("authorPhoto");
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return image;
    }

    public String fetchPostAuthor(){
        return fetchTextData(rawPost.$x(POST_AUTHOR_LINK));
    }

    public String fetchPostTimestamp(){
        return fetchAttributeData(rawPost.$x(POST_TIMESTAMP), DATA_UTIME_ATTRIBUTE);
    }

    public String fetchPostAuthorProfileLink(){
        return fetchAttributeData(rawPost.$x(POST_AUTHOR_LINK), HREF_ATTRIBUTE);
    }

    public String fetchPostMessage(){
        return fetchInnerTextData(rawPost.$x(POST_MESSAGE));
    }

    public String fetchPostMessageWithNoDuplicationAndFilter(){
        String rawTexts = fetchInnerTextData(rawPost.$x(POST_MESSAGE));
        rawTexts = rawTexts.replace("See Translation", "");
        rawTexts = rawTexts.replace("See More", "");
        rawTexts = rawTexts.replace("\n", "");
        return rawTexts;
    }

    public String fetchPostLink() {
        return fetchAttributeData(rawPost.$x(POST_LINK), "href");
    }

    public List<PostCommentUiRepresentation> fetchAllComments(){
        if(isPostHasMoreComments()){
            openAllComments();
        }
        return new PostCommentUiRepresentation.Builder(rawPost.$$x(POST_COMMENTS)).build() ;
    }

    private Boolean isPostHasMoreComments(){
        return rawPost.$x(POST_VIEW_MORE_MESSAGE_LINK).is(Condition.visible);
    }

    private void openAllComments(){
        rawPost.$x(POST_VIEW_MORE_MESSAGE_LINK).scrollIntoView(SCROLL_OPTION).click();
        rawPost.$x(POST_VIEW_MORE_MESSAGE_LINK).$x(POST_COMMENT_LOADER).waitWhile(Condition.visible, 10_000);
    }

    private Boolean isPostHasFullSizeImages(){
        return rawPost.$x(POST_MAIN_IMAGE).is(Condition.exist) ? Boolean.TRUE : Boolean.FALSE;
    }

    private void closePostFullSizeImagesViewer(){
        if(POST_IMAGE_VIEWER.$x(BUTTON_POST_IMAGES_VIEWER_CLOSE).is(Condition.visible)) {
            POST_IMAGE_VIEWER.$x(BUTTON_POST_IMAGES_VIEWER_CLOSE).click();
        }
    }

    private void clickNextImageInPostImageViewer(){
        if (POST_IMAGE_VIEWER.$x(BUTTON_POST_IMAGES_VIEWER_NEXT).is(Condition.visible)){
            Selenide.actions().moveToElement(POST_IMAGE_VIEWER.$x(BUTTON_POST_IMAGES_VIEWER_NEXT), 2, 2).click().perform();
        }
    }

    public void scrollIntoView(){
        rawPost.scrollIntoView(SCROLL_OPTION);
    }

    private void activateNavigationBarInPhotoTheater(){
        if (!POST_PHOTO_THEATER_MAIN_DIALOG_NAVIGATABEL.is(Condition.exist)) {
            Selenide.actions()
                    .moveToElement(POST_IMAGE_VIEWER.$x(IMAGE_FULL_SIZE_IN_VIEWER), 5, 5)
                    .moveToElement(POST_IMAGE_VIEWER.$x(IMAGE_FULL_SIZE_IN_VIEWER), 10, 10)
                    .moveToElement(POST_IMAGE_VIEWER.$x(IMAGE_FULL_SIZE_IN_VIEWER), 15, 15)
                    .perform();
            POST_PHOTO_THEATER_MAIN_DIALOG_NAVIGATABEL.should(Condition.exist);
        }
    }

    @Override
    public String toString() {
        return "PostUiRepresentation{" +
                "rawPost=" + rawPost +
                '}';
    }
}
