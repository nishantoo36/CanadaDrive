package StepDef;

import PageObject.CarDetailPage;
import PageObject.CarsListPage;
import PageObject.CommonPageMethods;
import Utilities.CsvUtility;
import Utilities.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;


public class CarsListTestSteps {

    TestContext testContext;
    CarsListPage carsListPage;
    CommonPageMethods commonPageMethods;
    CarDetailPage carDetailPage;

    String province;
    String carBrand;
    String carModal;
    String selectedCarTitle;

    public CarsListTestSteps(TestContext context) {
        testContext = context;
        carsListPage = testContext.getPageObjectManager().getCarsPage();
        commonPageMethods = testContext.getPageObjectManager().getCommonMethods();
        carDetailPage = testContext.getPageObjectManager().getCarDetailPage();
    }

    @Given("user should be on car page")
    public void userShouldBeOnCarPage() {
        Assert.assertTrue(commonPageMethods.isPageTitle("Used Cars for Sale in BC | Canada Drives"), "User is not on car page");
    }

    @When("user select province from {string} header and row {int}")
    public void userSelectFromRow(String provinceHeader,Integer row) {
        province = CsvUtility.getData(provinceHeader,row,PreconditionSteps.csvData);
        carsListPage.selectProvince(province);
    }

    @Then("user should see selected province")
    public void userShouldSeeSelectedProvinceAs() {
        Assert.assertTrue(carsListPage.isProvinceSelected(province), "Verification failed, " + province + " is not selected");
    }

    @When("user filer car list with {string} {string} and {string} from row {int}")
    public void userFilerCarListWithAndFromRow(String filterHeader, String brandHeader, String modalHeader,Integer row) {
       String  filter = CsvUtility.getData(filterHeader,row,PreconditionSteps.csvData);
       carBrand = CsvUtility.getData(brandHeader,row,PreconditionSteps.csvData);
       carModal = CsvUtility.getData(modalHeader,row,PreconditionSteps.csvData);
       carsListPage.selectFilter(filter);
       carsListPage.selectCarModal(carBrand, carModal);
    }

    @Then("user should see selected filter with car and modal name")
    public void userShouldSelectedFilter() {
        Assert.assertTrue(carsListPage.isFilterSelected(carBrand+": "+carModal));
    }

    @Then("user should able to see the car list with selected brand and modal")
    public void userShouldAbleToSeeTheCarListWithSelectedBrandAndModal() {
        Assert.assertTrue(carsListPage.isCarListTitleContainsBrandAndModal(carBrand, carModal));
    }

    @When("user do sorting by {string}")
    public void userDoSortingBy(String sortByType) {
        carsListPage.selectSortBy(sortByType);
    }

    @Then("Verify that car list sorted by {string} order by price")
    public void verifyThatCarListSortedByOrder(String orderBy) {
        Assert.assertTrue(carsListPage.isCarListSortedByPrice(orderBy));
    }

    @When("user add {int} item as favorite randomly")
    public void userAddItemAsFavoriteRandomly(int countOfFav) {
        carsListPage.addFavItemRandomly(countOfFav);
    }

    @Then("user should able to see {int} favorite item in favorite list")
    public void userShouldAbleToSeeFavoriteItemInFavoriteList(int countOfFav) throws InterruptedException {
        Assert.assertTrue(carsListPage.isFavItemAvailableInFavList(countOfFav));
    }

    @When("user select any available car from list")
    public void userSelectAnyAvailableCarFromList() {
        selectedCarTitle = carsListPage.selectAnyAvailableProductFromList();
    }

    @Then("user should navigate to the selected car details page")
    public void userShouldNavigateToTheSelectedCarDetailsPage() {
        boolean res = selectedCarTitle.contains(carDetailPage.getCarBrandName()) &&
                selectedCarTitle.contains(carDetailPage.getCarModalName())&&
                selectedCarTitle.contains(carDetailPage.getCarSubModalName())&&
                selectedCarTitle.contains(carDetailPage.getCarPriceEle());
        Assert.assertTrue(res,"User is not on the selected page");
    }

}
