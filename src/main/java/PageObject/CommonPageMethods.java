package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CommonPageMethods {

    private final WebDriver webDriver;

    public CommonPageMethods(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public boolean isPageTitle(String title){
        return webDriver.getTitle().equals(title);
    }
}
