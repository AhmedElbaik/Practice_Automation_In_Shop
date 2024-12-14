package com.practice.automationtesting.pages;

import com.practice.automationtesting.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class CheckoutPage extends BasePage {
    // Locators
    private final By BILLING_DETAILS_FORM = By.cssSelector(".woocommerce-billing-fields");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify Billing Details Form is Displayed")
    public void verifyBillingDetailsFormDisplayed() {
        WebElement billingForm = WaitUtils.waitForElementVisible(driver, BILLING_DETAILS_FORM);
        Assert.assertTrue(billingForm.isDisplayed(), "Billing details form is not displayed");
    }
}