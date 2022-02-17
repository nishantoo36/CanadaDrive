package StepDef;

import PageObject.CarDetailPage;
import Utilities.TestContext;
import io.cucumber.java.en.When;

public class CarDetailsTestSteps {
    TestContext testContext;
    CarDetailPage carDetailPage;

    public CarDetailsTestSteps(TestContext context) {
        testContext = context;
        carDetailPage = testContext.getPageObjectManager().getCarDetailPage();
    }

    @When("user click on start purchase button")
    public void userClickOnStartPurchaseButton() {
        carDetailPage.clickOnStartPurchase();
    }
}
