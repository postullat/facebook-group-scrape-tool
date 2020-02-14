package com.fbreaper.fbuiapi.entities;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.fbreaper.fbuiapi.pages.AbstractPage;

import java.util.List;

public abstract class AbstractUiEntity extends AbstractPage {

    protected static final String NO_DATA_FLAG = "NO DATA";
    protected static final String DATA_UTIME_ATTRIBUTE = "data-utime";
    protected static final String HREF_ATTRIBUTE = "href";

    public static String SCROLL_OPTION = "{behavior: \"instant\", block: \"end\", inline: \"end\"}";

    protected String fetchTextData(SelenideElement element){
        try {
            return element.getText();
        } catch (ElementNotFound e){
            return NO_DATA_FLAG;
        }
    }

    protected String fetchAttributeData(SelenideElement element, String attributeName){
        try {
            return element.getAttribute(attributeName);
        } catch (ElementNotFound e){
            return NO_DATA_FLAG;
        }
    }

    protected String fetchInnerTextData(SelenideElement element){
        try {
            return element.innerText();
        } catch (ElementNotFound e){
            return NO_DATA_FLAG;
        }
    }

}
