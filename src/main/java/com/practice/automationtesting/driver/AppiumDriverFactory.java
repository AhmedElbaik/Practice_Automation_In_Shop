package com.practice.automationtesting.driver;

import com.practice.automationtesting.config.TestSettings;
import com.practice.automationtesting.utils.PathUtils;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class AppiumDriverFactory {
    public static WebDriver initializeAppiumDriver(TestSettings testSettings) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("browserName", "Chrome"); // For browser testing
            caps.setCapability("appium:deviceName", "Pixel6ProAPI31");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:chromedriverExecutable", PathUtils.getRelativeFilePath("chromedriver.exe"));

            // Create the AndroidDriver
            URL appiumServerUrl = new URI(testSettings.getAppiumUri()).toURL();
            AndroidDriver driver = new AndroidDriver(appiumServerUrl, caps);

            // Navigate to the application URL
            driver.get(testSettings.getApplicationUrl());

            return driver;
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("Error initializing Appium driver", e);
        }
    }
}