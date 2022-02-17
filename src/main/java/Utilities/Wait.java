package Utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

public class Wait {

    /**
     *
     * @param webDriver object
     * @param timeOutInSeconds time for wait
     * @param waitCondition condition to wait
     */
    private static void until(WebDriver webDriver, Long timeOutInSeconds, Function<WebDriver, Boolean> waitCondition) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, timeOutInSeconds);
        try {
            webDriverWait.until(waitCondition);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void untilAjaxCallIsDone(WebDriver webDriver, Long timeOutInSeconds) {
        until(webDriver, timeOutInSeconds, (function) -> {
            Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) webDriver).executeScript("return jQuery.active==0");
            if (!isJqueryCallDone) System.out.println("jQuery call is in progress");
            return isJqueryCallDone;
        });
    }

    public static void untilPageReadyState(WebDriver webDriver, Long timeOutInSeconds) {
        until(webDriver, timeOutInSeconds, (function) -> {
            String isPageLoaded = String.valueOf(((JavascriptExecutor) webDriver).executeScript("return document.readyState"));
            if (isPageLoaded.equals("complete")) {
                return true;
            } else {
                System.out.println("Document is loading");
                return false;
            }
        });
    }

    /**
     *
     * @param webDriver object value
     * @param webElement element for the visible condition
     * @param timeOutInSeconds wait time
     * @return web element if element visible else throw the ElementNotVisibleException exception
     */
    public static WebElement untilElementIsVisible(WebDriver webDriver, WebElement webElement, Long timeOutInSeconds) {
        try {
            return new WebDriverWait(webDriver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
        } catch (TimeoutException e) {
            throw new ElementNotVisibleException("Unable to find element " + webElement);
        }
    }

    /**
     *
     * @param webDriver object value
     * @param webElement element for the clickable condition
     * @param timeOutInSeconds wait time
     * @return web element if element clickable else throw the ElementClickInterceptedException exception
     */
    public static WebElement untilElementIsClickable(WebDriver webDriver, WebElement webElement, Long timeOutInSeconds) {
        try {
            return new WebDriverWait(webDriver, timeOutInSeconds).until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (TimeoutException e) {
            throw new ElementClickInterceptedException("Unable to find element clickable " + webElement);
        }
    }

    /**
     *
     * @param webDriver object value
     * @param webElements List of all element for the visible condition
     * @param timeOutInSeconds wait time
     * @return List of element if all element visible else throw the ElementNotVisibleException exception
     */
    public static List<WebElement> untilAllElementIsVisible(WebDriver webDriver, List<WebElement> webElements, Long timeOutInSeconds) {
        try {
            return new WebDriverWait(webDriver, timeOutInSeconds).until(ExpectedConditions.visibilityOfAllElements(webElements));
        } catch (TimeoutException e) {
            throw new ElementNotVisibleException("Unable to find element list");
        }
    }

    /**
     *
     * @param webDriver object value
     * @param webElement element for the visible condition
     * @param timeOutInSeconds wait time
     * @return true if element visible else false
     */
    public static boolean isElementPresentAfterWaitApply(WebDriver webDriver, WebElement webElement, Long timeOutInSeconds) {
        try {
            untilElementIsVisible(webDriver,webElement,timeOutInSeconds);
            return true;
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param webDriver object value
     * @param urlContainsText url text which should contains in url
     * @param timeOutInSeconds time out to change the url
     * @param waitAfterURLChange true if url contains the expected text else false
     */
    public static void untilUrlContains(WebDriver webDriver, String urlContainsText, Long timeOutInSeconds,Long waitAfterURLChange) {
        try {
             new WebDriverWait(webDriver, timeOutInSeconds).until(ExpectedConditions.urlContains(urlContainsText));
             Thread.sleep(waitAfterURLChange);
        } catch (TimeoutException e) {
            throw new TimeoutException("Url is not containing"+urlContainsText);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param webDriver object value
     * @param element element in which wait for text change
     * @param text text which is expected to present
     * @param timeOutInSeconds true if text present else false
     */
    public static void untilTextToBe(WebDriver webDriver, WebElement element, String text, Long timeOutInSeconds) {
        try {
            new WebDriverWait(webDriver, timeOutInSeconds).until(ExpectedConditions.textToBePresentInElement(element,text));
        } catch (TimeoutException e) {
            throw new TimeoutException("Element is not containing " + text);
        }
    }


}
