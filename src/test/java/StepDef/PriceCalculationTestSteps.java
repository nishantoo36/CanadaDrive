package StepDef;

import PageObject.PriceCalculationPage;
import Utilities.CsvUtility;
import Utilities.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class PriceCalculationTestSteps {
    TestContext testContext;
    PriceCalculationPage priceCalculationPage;
    String address;
    String warranty;
    double warrantyPrice;

    public PriceCalculationTestSteps(TestContext context) {
        testContext = context;
        priceCalculationPage = testContext.getPageObjectManager().getPriceCalculationPage();
    }

    @Then("user should navigate to price calculation page")
    public void userShouldNavigateToPriceCalculationPage() {
        Assert.assertTrue(priceCalculationPage.isUserOnPaymentCalculationPage());
    }

    @When("user set the address for delivery from {string} header and row {int}")
    public void userSetTheAddressForDeliveryFromHeaderAndRowRow(String addressHeader,Integer row) {
        address = CsvUtility.getData(addressHeader,row,PreconditionSteps.csvData);
        priceCalculationPage.addDeliveryAddress(address);
    }

    @Then("user should able to see selected address")
    public void userShouldAbleToSeeSelectedAddress() {
        String actual = priceCalculationPage.getSelectedAddress();
        Assert.assertTrue(actual.contains(address), "Selected address is " + actual);
        priceCalculationPage.saveAndConfirmAddress();
    }

    @When("user select warranty from {string} header and row {int}")
    public void userSelectWarrantyFromHeaderAndRowRow(String warrantyHeader,Integer row) {
        warranty = CsvUtility.getData(warrantyHeader,row,PreconditionSteps.csvData);
        priceCalculationPage.selectWarranty(warranty);
        warrantyPrice = priceCalculationPage.getSelectedWarrantyWholePriceFromWarrantyDialog();
        priceCalculationPage.saveAndConfirmWarranty();
    }


    @Then("user should able to see price for selected warranty")
    public void userShouldAbleToSeePriceForSelectedWarranty() {
        Assert.assertEquals(priceCalculationPage.getSelectedWarrantyPrice(),warrantyPrice);
    }
}
