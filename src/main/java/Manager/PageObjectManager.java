package Manager;

import PageObject.CarDetailPage;
import PageObject.CarsListPage;
import PageObject.CommonPageMethods;
import PageObject.PriceCalculationPage;
import org.openqa.selenium.WebDriver;

/**
 * This page initialises the object one time then hold it for the whole run which help to reuse the object memory
 */
public class PageObjectManager {

    private final WebDriver webDriver;
    private CarsListPage carsListPage;
    private CommonPageMethods commonPageMethods;
    private CarDetailPage carDetailPage;
    private PriceCalculationPage priceCalculationPage;

    public PageObjectManager(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public CarsListPage getCarsPage() {
        return (carsListPage == null) ? carsListPage = new CarsListPage(webDriver) : carsListPage;
    }

    public CommonPageMethods getCommonMethods() {
        return (commonPageMethods == null) ? commonPageMethods = new CommonPageMethods(webDriver) : commonPageMethods;
    }

    public CarDetailPage getCarDetailPage() {
        return (carDetailPage == null) ? carDetailPage = new CarDetailPage(webDriver) : carDetailPage;
    }

    public PriceCalculationPage getPriceCalculationPage() {
        return (priceCalculationPage == null) ? priceCalculationPage = new PriceCalculationPage(webDriver) : priceCalculationPage;
    }
}

