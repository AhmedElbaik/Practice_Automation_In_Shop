package com.practice.automationtesting.pages;

import com.practice.automationtesting.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class ShopPage extends BasePage {
    // Updated locators
    private final By BOOK_BY_NAME(String bookName) {
        return By.xpath("//h3[contains(text(), '" + bookName + "')]");
    }

    private final By BOOK_PRICE_BY_NAME(String bookName) {
        return By.xpath("//h3[contains(text(), '" + bookName + "')]/following::ins//span[@class=\"woocommerce-Price-amount amount\"]");
    }

    private final By BOOK_ADD_TO_BASKET_BY_NAME(String bookName) {
        return By.xpath("//h3[contains(text(), '" + bookName + "')]/following::a[contains(@class, 'add_to_cart_button')]");
    }

    private final By CART_MENU_ITEM = By.cssSelector(".wpmenucart-contents");
    private final By CART_CONTENTS = By.cssSelector(".cartcontents");

    public ShopPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify book is present")
    public void verifyBookExists(String bookName) {
        WebElement bookElement = WaitUtils.waitForElementVisible(driver, BOOK_BY_NAME(bookName));
        Assert.assertTrue(bookElement.isDisplayed(), bookName + " book is not visible");
    }

    @Step("Get book price")
    public String getBookPrice(String bookName) {
        WebElement priceElement = WaitUtils.waitForElementVisible(driver, BOOK_PRICE_BY_NAME(bookName));
        String price = priceElement.getText().trim();
        saveBookPriceToAllureReport(bookName, price);
        return price;
    }

    @Step("Add book to basket")
    public void addBookToBasket(String bookName) {
        // Get the initial number of items in cart
        WebElement initialCartContents = WaitUtils.waitForElementVisible(driver, CART_CONTENTS);
        int initialItemCount = Integer.parseInt(initialCartContents.getText().split(" ")[0]);

        // Add book to basket
        WebElement addToBasketButton = WaitUtils.waitForElementClickable(driver, BOOK_ADD_TO_BASKET_BY_NAME(bookName));
        addToBasketButton.click();

        // Wait for cart to update and verify item count increased
        WaitUtils.waitForCondition(driver, 10, webDriver -> {
            WebElement updatedCartContents = webDriver.findElement(CART_CONTENTS);
            int updatedItemCount = Integer.parseInt(updatedCartContents.getText().split(" ")[0]);
            return updatedItemCount == initialItemCount + 1;
        });

        // Additional explicit assertion
        WebElement updatedCartContents = WaitUtils.waitForElementVisible(driver, CART_CONTENTS);
        int updatedItemCount = Integer.parseInt(updatedCartContents.getText().split(" ")[0]);
        Assert.assertEquals(updatedItemCount, initialItemCount + 1,
                "Cart item count did not increase after adding book");
    }

    @Step("Navigate to Cart")
    public CartPage navigateToCart() {
        WebElement cartMenuItem = WaitUtils.waitForElementClickable(driver, CART_MENU_ITEM);
        cartMenuItem.click();
        return new CartPage(driver);
    }

    // Helper method to save price to Allure report
    private void saveBookPriceToAllureReport(String bookName, String price) {
        io.qameta.allure.Allure.parameter(bookName + " Book Price", price);
    }
}