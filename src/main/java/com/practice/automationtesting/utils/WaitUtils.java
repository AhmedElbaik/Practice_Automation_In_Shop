package com.practice.automationtesting.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class WaitUtils {
    private static final long TIMEOUT = 10;

    @Step("Wait for Element to be Visible")
    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Wait for Elements to be Visible")
    public static List<WebElement> waitForElementsVisible(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    @Step("Wait for Element to be Clickable")
    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Step("Wait for Element to be Present")
    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            // Use a short wait to check for the element's presence
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Scroll to the element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Wait for a specfic condition for an element to happen")
    public static boolean waitForCondition(WebDriver driver, int timeoutInSeconds, Function<WebDriver, Boolean> condition) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        try {
            return wait.until(condition);
        } catch (TimeoutException e) {
            throw new AssertionError("Condition not met within " + timeoutInSeconds + " seconds", e);
        }
    }
}