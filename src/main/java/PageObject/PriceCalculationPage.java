package PageObject;

import Utilities.Wait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PriceCalculationPage {
    private final WebDriver webDriver;

    public PriceCalculationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "//span[text()='Payment Calculation']")
    private WebElement paymentCalculationHeaderEle;

    @FindBy(xpath = "//div[contains(text(),'Calculate Delivery')][1]")
    private WebElement calculateDeliveryEle;

    @FindBy(xpath = "//div[contains(text(),'Select Warranty')][1]")
    private WebElement selectWarrantyEle;

    @FindBy(id = "street_address")
    private WebElement streetAddressEle;

    @FindBy(xpath = "//div[@class='pcaitem'][1]")
    private WebElement firstAddressSuggessionEle;

    @FindBy(css = ".app-cpost-result__address")
    private WebElement selectedAddressEle;

    @FindBy(xpath = "//div[contains(text(),'Protect Your Purchase')]//following::span[contains(text(),'Save and Confirm')]")
    private WebElement saveAndConfirmForWarrantyEle;

    @FindBy(xpath = "//div[contains(text(),'Calculate Delivery Cost')]//following::button[contains(@class,'v-btn--is-elevated')][1]//span")
    private WebElement saveAndConfirmForAddressEle;

    @FindBy(xpath = "//div[contains(text(),'Warranty Plan')]//following::span[text()][1]")
    private WebElement selectedWarrantyEle;

    @FindBy(css = ".warranty-options__item")
    private List<WebElement> warrantyOptionEle;

    @FindBy(xpath = "//div[contains(@class,'warranty-options__item--selected')]/child::div[text()]//child::div[text()][2]")
    private WebElement selectedWarrantyPriceEle;

    public boolean isUserOnPaymentCalculationPage(){
        Wait.untilPageReadyState(webDriver,5L);
        return Wait.isElementPresentAfterWaitApply(webDriver,paymentCalculationHeaderEle,10L);
    }

    public void addDeliveryAddress(String address){
        Wait.untilElementIsVisible(webDriver,calculateDeliveryEle,5L).click();
        Wait.untilElementIsVisible(webDriver,streetAddressEle,5L).sendKeys(address);
        Actions actions =new Actions(webDriver);
        actions.moveToElement(streetAddressEle).click().build().perform();
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actions.sendKeys(Keys.DOWN).build().perform();
        Wait.untilElementIsVisible(webDriver,firstAddressSuggessionEle,10L).click();
      //  actions.moveToElement(firstAddressSuggessionEle).click().build().perform();

    }
    public String getSelectedAddress(){
        Wait.untilElementIsVisible(webDriver,selectedAddressEle,10L);
        return selectedAddressEle.getText();
    }

    public void saveAndConfirmAddress(){
       Wait.untilElementIsVisible(webDriver,selectedAddressEle,20L);
       Wait.untilElementIsClickable(webDriver,saveAndConfirmForAddressEle,20L).click();
    }

    public void saveAndConfirmWarranty(){
        Wait.untilElementIsClickable(webDriver,saveAndConfirmForWarrantyEle,20L).click();
    }

    public void selectWarranty(String warrantyPeriod){
        Wait.untilElementIsVisible(webDriver,selectedWarrantyEle,20L).click();
        for (WebElement ele:warrantyOptionEle){
            if (ele.getText().contains(warrantyPeriod)){
                ele.click();
                break;
            }
        }
    }

    public double getSelectedWarrantyPrice(){
       return Double.parseDouble(selectedWarrantyEle.getText().replaceAll("[^0-9 .]",""));
    }

    public double getSelectedWarrantyWholePriceFromWarrantyDialog(){
       return Double.parseDouble(selectedWarrantyPriceEle.getText().split("\\|")[1].replaceAll("[^0-9 .]",""));
    }

}


