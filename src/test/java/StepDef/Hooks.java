package StepDef;

import Manager.FileReaderManager;
import Utilities.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class Hooks {

    TestContext testContext;
    WebDriver webDriver;
    public static Scenario scenario;

    public Hooks(TestContext context) {
        testContext = context;
    }

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        webDriver = testContext.getDriverManager().getDriver();
        webDriver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        webDriver.get(FileReaderManager.getInstance().getConfigFileReader().getUrl());
        webDriver.manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario) {String path = null;
        String ImageFileName = scenario.getName().toLowerCase()
                + new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime());

        if(scenario.isFailed()) {
            try {
                path = System.getProperty("user.dir") + "//screenshots//Fail-Skip//";
            } catch (WebDriverException noSupportScreenshot) {
                System.err.println(noSupportScreenshot.getMessage());
            }
        }else {
            try {
                path = System.getProperty("user.dir") + "//screenshots//Pass//";
            } catch (WebDriverException noSupportScreenshot) {
                System.err.println(noSupportScreenshot.getMessage());
            }
        }
        takeScreenShot(ImageFileName, path, webDriver);
        testContext.getDriverManager().closeDriver();
    }

    private void takeScreenShot(String screenshotName, String path, Object driver) {
        try {
            File sourcePath = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            File destinationPath = new File(path + screenshotName+".png");
            FileUtils.copyFile(sourcePath, destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
