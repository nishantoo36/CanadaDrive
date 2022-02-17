package PageObject;

import Utilities.CommonUtility;
import Utilities.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import java.util.List;

public class CarsListPage {
    private final WebDriver webDriver;

    public CarsListPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(className = "province-dropdown")
    private WebElement provinceDropDownEle;

    @FindBy(css = ".province-dropdown__list:nth-child(2)")
    private WebElement provinceDropDownListEle;

    @FindBy(xpath = "//div[contains(text(),'Search Results')]//child::div[@class='v-select__selection v-select__selection--comma']")
    private WebElement selectedProvinceEle;

    @FindBy(css = ".vehicle-filter-box")
    private WebElement filerEle;

    @FindBy(css = ".v-card__text .vehicle-card__title")
    private List<WebElement> carsListTitles;

    @FindBy(css = ".v-chip__content")
    private List<WebElement> selectedFilterListEle;

    @FindBy(xpath = "//span[text()='SORT BY']//following::div[1]")
    private WebElement sortByEle;

    @FindBy(css = ".menuable__content__active")
    private WebElement sortByListEle;

    @FindBy(css = ".primary--text.text-body-1.text-h6-card")
    private List<WebElement> carPriceEle;

    @FindBy(css = "main .vehicle-card")
    private List<WebElement> vehicleCardEle;

    @FindBy(css = ".v-main__wrap .vehicle-card .fav-icon__heart")
    private List<WebElement> favIconEle;

    @FindBy(xpath = "//main//child::span[contains(@class,'fav-icon__heart--color')]//ancestor::div[@class='vehicle-card']")
    private List<WebElement> selectedFavIconCardEle;

    @FindBy(xpath = "//aside//child::span[contains(@class,'fav-icon__heart--color')]//ancestor::div[@class='vehicle-card']")
    private List<WebElement> selectedFavIconSideCardEle;

    @FindBy(css = ".v-badge__badge")
    private WebElement badgeCountEle;


    public String filterXpath = "//span[text()='%s']";
    public String provinceXpath = "//li[contains(text(),'%s')]";
    public String carModalXpath = "//span[text()='%s']";
    public String sortByXpath = "//div[text()='%s']";

    public void selectProvince(String provinceName) {
        provinceDropDownEle.click();
        Wait.untilElementIsVisible(webDriver, provinceDropDownListEle, 4L);
        provinceDropDownListEle.findElement(By.xpath(provinceXpath.replace("%s", provinceName))).click();
    }

    public boolean isProvinceSelected(String provinceName) {
        return Wait.untilElementIsVisible(webDriver, selectedProvinceEle, 10L).getText().equalsIgnoreCase(provinceName);
    }

    public void selectFilter(String filterName) {
        filerEle.findElement(By.xpath(filterXpath.replace("%s", filterName))).click();
    }

    public void selectCarModal(String carBrand, String carModal) {
        selectFilter(carBrand);
        filerEle.findElement(By.xpath(carModalXpath.replace("%s", carModal))).click();
    }

    public void selectSortBy(String sortByType) {
        sortByEle.click();
        Wait.untilElementIsVisible(webDriver, sortByListEle, 4L);
        sortByListEle.findElement(By.xpath(sortByXpath.replace("%s", sortByType))).click();
    }

    public boolean isFilterSelected(String filterName) {
        return Wait.untilAllElementIsVisible(webDriver, selectedFilterListEle, 4L)
                .stream()
                .anyMatch(product -> product.getText().contains(filterName));
    }

    public boolean isCarListTitleContainsBrandAndModal(String brandName, String modalName) {
        return Wait.untilAllElementIsVisible(webDriver, selectedFilterListEle, 4L)
                .stream()
                .allMatch(product -> product.getText().contains(brandName) || product.getText().contains(modalName));
    }

    public boolean isCarListSortedByPrice(String orderBy) {
        List<Integer> carPrices = new ArrayList<>();
//        Wait.untilListElementIsVisible(webDriver, carPriceEle, 4L)
//                .stream()
//                .forEach(product -> carPrices.add(Integer.valueOf(product.getText().replaceAll("\\D+", ""))));
        if(orderBy.equalsIgnoreCase("asc")){
            Wait.untilUrlContains(webDriver,"product_price_asc",4L,3000L);
        }else {
            Wait.untilUrlContains(webDriver,"product_price_desc",4L,1000L);
        }
        for(int i=0;i<vehicleCardEle.size();i++){
            if((!vehicleCardEle.get(i).getText().contains("Coming Soon"))&&(!vehicleCardEle.get(i).getText().contains("Sale Pending"))){
                carPrices.add(Integer.valueOf(carPriceEle.get(i).getText().replaceAll("\\D+", "")));
            }
        }
        if(carPrices.isEmpty()){
            return false;
        }else {
            return CommonUtility.isListSorted(orderBy, carPrices);
        }
    }

    public void addFavItemRandomly(int favItemCount){
     //   int randomNum = CommonUtility.getRandom(0,favIconEle.size()-3);
        int randomNum = 1;
        for (int i=0;i<favItemCount;i++){
            favIconEle.get(randomNum).click();
            randomNum=randomNum+1;
        }
    }

    public boolean isFavItemAvailableInFavList(int favItemCount) throws InterruptedException {
        Wait.untilTextToBe(webDriver,badgeCountEle,String.valueOf(favItemCount),10L);
        if(favItemCount==Integer.parseInt(badgeCountEle.getText())){
            List<String> mainFavItemCardTexts= new ArrayList<>();
            List<String> sideFavItemCardTexts= new ArrayList<>();
            Wait.untilAllElementIsVisible(webDriver, selectedFavIconCardEle, 4L)
                    .stream()
                    .forEach(product -> mainFavItemCardTexts.add(product.getText().replaceAll("[^a-zA-Z0-9]", "").replace("ComingSoonGetNotified","")));
            badgeCountEle.click();
            Thread.sleep(2000);
            Wait.untilAjaxCallIsDone(webDriver,10L);
            Wait.untilAllElementIsVisible(webDriver, selectedFavIconSideCardEle, 20L)
                    .stream()
                    .forEach(product -> sideFavItemCardTexts.add(product.getText().replaceAll("[^a-zA-Z0-9]", "")));
            badgeCountEle.click();
            System.out.println(mainFavItemCardTexts);
            System.out.println(sideFavItemCardTexts);
            return mainFavItemCardTexts.size()==favItemCount && sideFavItemCardTexts.size()==favItemCount  && mainFavItemCardTexts.containsAll(sideFavItemCardTexts);
        }
        return false;
    }

    public String selectAnyAvailableProductFromList() {
        int num = 0;
        boolean temp = false;
        String cardData = "";
        while (!temp && num < vehicleCardEle.size()){
            if(!vehicleCardEle.get(num).getText().contains("Coming Soon")){
                cardData=vehicleCardEle.get(num).getText();
                Wait.untilElementIsClickable(webDriver,vehicleCardEle.get(num),20L);
                vehicleCardEle.get(num).click();
                num++;
                temp=true;
                return cardData;
            }
        }
        throw new NotFoundException("There not car available at this point");

    }

}