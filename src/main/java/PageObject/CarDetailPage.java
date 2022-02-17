package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CarDetailPage {
    private final WebDriver webDriver;

    public CarDetailPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(css = ".vehicle-title-font")
    private List<WebElement> carTitleEle;

    @FindBy(css = ".vehicle-thumbnail .vehicle-price span")
    private WebElement carPriceEle;

    @FindBy(css = ".main-content .start-purchase-button ")
    private WebElement  startPurchaseBtnEle;

    public String getCarModalName(){
       return carTitleEle.get(0).getText().split(" ")[2];
    }

    public String getCarBrandName(){
        return carTitleEle.get(0).getText().split(" ")[1];
    }

    public String getCarSubModalName(){
        return carTitleEle.get(1).getText();
    }

    public String getCarPriceEle(){
        return carPriceEle.getText();
    }

    public void clickOnStartPurchase(){
        startPurchaseBtnEle.click();
    }
}

