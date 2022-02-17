package StepDef;

import Utilities.CsvUtility;
import Utilities.TestContext;
import io.cucumber.java.en.Given;
import org.testng.Assert;

import java.util.List;

public class PreconditionSteps {
    TestContext testContext;
    public static List<String[]> csvData;

    public PreconditionSteps(TestContext context) {
        testContext = context;
    }


    @Given("Load test Data from {string} file with reset is {string}")
    public void loadTestDataFromFileWithResetIs(String filePath, String reset) {
        if (Boolean.getBoolean(reset) || csvData == null || csvData.isEmpty()) {
            csvData = CsvUtility.loadData(filePath);
        }
        Assert.assertTrue(csvData.size() > 1, "Unable to load csv data");
    }

}
