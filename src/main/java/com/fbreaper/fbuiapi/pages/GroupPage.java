package com.fbreaper.fbuiapi.pages;

import com.codeborne.selenide.*;
import com.fbreaper.fbuiapi.services.FbUiInteractionService;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.fbreaper.fbuiapi.entities.AbstractUiEntity.SCROLL_OPTION;
import static com.fbreaper.fbuiapi.entities.PostUiRepresentation.POST;

@Component
public class GroupPage extends AbstractPage {

    private String url;

    private Logger log = LoggerFactory.getLogger(GroupPage.class);

    public GroupPage(){

    }

    public GroupPage(String url){
        this.url = url;
    }

    public void open(){
        Selenide.open(url);
        $$x(POST).shouldHave(CollectionCondition.sizeGreaterThan(0));
        $x("//body").sendKeys(Keys.ESCAPE);
        $$x(POST).get(0).should(Condition.visible);
    }

    public List<SelenideElement> getAllPosts(){
        return $$x(POST);
    }

    public List<SelenideElement> getNewPostsBatch(Integer timeOut){
        List<SelenideElement> currentPostsBatch = getAllPosts();
        currentPostsBatch.get(currentPostsBatch.size() - 1).scrollIntoView(SCROLL_OPTION);
        return $$x(POST).shouldHave(CollectionCondition.sizeGreaterThan(currentPostsBatch.size()), timeOut);
    }

    public void setUrl(String url){
        this.url = url;
    }

}
