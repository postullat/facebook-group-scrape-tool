package com.fbreaper.fbuiapi.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

@Component
public class LoginPage extends AbstractPage {

    private static final String URL = "https://www.facebook.com/";

    private static final SelenideElement LOGIN_INPUT = $(By.xpath("//*[@id='email']"));
    private static final SelenideElement PASSWORD_INPUT = $(By.xpath("//*[@id='pass']"));
    private static final SelenideElement LOGIN_BUTTON = $(By.xpath("//*[@value='Log In']"));
    private static final SelenideElement SWITCH_TO_ENGLISH_URL = $x("//a[@title='English (US)']");

    public void doLogIn(String login, String pass){
        open(URL);
        WebDriverRunner.getWebDriver().manage().window().fullscreen();
        setEnglishLanguage();
        LOGIN_INPUT.setValue(login);
        PASSWORD_INPUT.setValue(pass);
        LOGIN_BUTTON.click();
    }

    private void setEnglishLanguage(){
        if(!LOGIN_BUTTON.is(Condition.exist)){
            SWITCH_TO_ENGLISH_URL.click();
        }
    }

}
