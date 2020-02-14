package com.fbreaper.fbuiapi.pages;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@PropertySource(value = "classpath:application.properties")
public abstract class AbstractPage {

    private Logger log = LoggerFactory.getLogger(AbstractPage.class);

    @Value("${selenide.timeout}")
    long timeout;

    @PostConstruct
    private void applySelenideConfig(){
        Configuration.timeout = timeout;
        Configuration.browser = BrowserType.FIREFOX;
        Configuration.savePageSource = false;
        Configuration.screenshots = false;
    }

    public void waitABit(Integer mls){
        try{
            Thread.sleep(mls);
        }catch (Throwable t){
            log.warn("IGNORED Throwable [" + t + "]");
        }
    }


}
