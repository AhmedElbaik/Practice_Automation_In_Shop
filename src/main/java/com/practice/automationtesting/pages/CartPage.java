package com.practice.automationtesting.pages;

import com.practice.automationtesting.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class CartPage extends BasePage {
    // Locators
    private final By CART_ITEM_NAME = By.cssSelector(".cart_item .product-name");
    private final By PROCEED_TO_CHECKOUT_BUTTON = By.cssSelector(".checkout-button");
    private final By CART_ITEM_PRICE = By.cssSelector(".cart_item .product-price");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify Cart Item Details")
    public void verifyCartItem(String expectedBookName, String expectedPrice) {
        WebElement itemNameElement = WaitUtils.waitForElementVisible(driver, CART_ITEM_NAME);
        WebElement itemPriceElement = WaitUtils.waitForElementVisible(driver, CART_ITEM_PRICE);

        // Add details to Allure report
        io.qameta.allure.Allure.parameter("Expected Book Name", expectedBookName);
        io.qameta.allure.Allure.parameter("Expected Price", expectedPrice);
        io.qameta.allure.Allure.parameter("Actual Book Name", itemNameElement.getText());
        io.qameta.allure.Allure.parameter("Actual Price", itemPriceElement.getText().trim());

        Assert.assertTrue(itemNameElement.getText().contains(expectedBookName),
                "Cart item does not match expected book name");
        Assert.assertEquals(itemPriceElement.getText().trim(), expectedPrice,
                "Cart item price does not match expected price");
    }

    @Step("Proceed to Checkout")
    public CheckoutPage proceedToCheckout() {
        WebElement checkoutButton = WaitUtils.waitForElementClickable(driver, PROCEED_TO_CHECKOUT_BUTTON);
        checkoutButton.click();
        return new CheckoutPage(driver);
    }
}