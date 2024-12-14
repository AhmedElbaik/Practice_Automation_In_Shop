package com.practice.automationtesting.tests;

import com.practice.automationtesting.pages.CartPage;
import com.practice.automationtesting.pages.CheckoutPage;
import com.practice.automationtesting.pages.ShopPage;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("E-commerce Shop Functionality")
@Feature("Book Purchase Workflow")
public class ShopTest extends BaseTest {
    @Test(description = "Verify Thinking in HTML book purchase flow", retryAnalyzer = RetryAnalyzer.class)
    @Story("User purchases Thinking in HTML book")
    @Description("Workflow to purchase Thinking in HTML book")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Shop Page", type = "mylink", value = "https://practice.automationtesting.in/shop/")
    public void testThinkingInHTMLBookPurchase() {
        // Navigate to Shop Page
        ShopPage shopPage = new ShopPage(getDriver());

        // Book details
        String bookName = "Thinking in HTML";

        // Verify book exists
        shopPage.verifyBookExists(bookName);

        // Get book price
        String bookPrice = shopPage.getBookPrice(bookName);
        Allure.parameter("Book Price", bookPrice);

        // Add book to basket
        shopPage.addBookToBasket(bookName);

        // Navigate to cart
        CartPage cartPage = shopPage.navigateToCart();

        // Verify cart item details
        cartPage.verifyCartItem(bookName, bookPrice);

        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // Verify billing details form
        checkoutPage.verifyBillingDetailsFormDisplayed();
    }
}