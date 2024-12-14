package com.practice.automationtesting.tests;

import com.practice.automationtesting.config.ConfigReader;
import com.practice.automationtesting.config.TestSettings;
import com.practice.automationtesting.driver.AppiumDriverFactory;
import com.practice.automationtesting.driver.DriverFactory;
import com.practice.automationtesting.enums.TestConstants;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;

public class BaseTest {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected TestSettings testSettings;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String browser) {
        testSettings = ConfigReader.getTestSettings("src/main/resources/appSettings.json");

        // Override browser type from parameter
        TestConstants.BrowserType browserType = switch (browser.toLowerCase()) {
            case "firefox" -> TestConstants.BrowserType.FIREFOX;
            case "edge" -> TestConstants.BrowserType.EDGE;
            default -> TestConstants.BrowserType.CHROME;
        };

        testSettings.setBrowserType(browserType); // Pass the enum value

        WebDriver driver;
        if (testSettings.getTestRunType() == TestConstants.TestRunType.ANDROID) {
            // Use AppiumDriverFactory for remote Appium testing
            driver = AppiumDriverFactory.initializeAppiumDriver(testSettings);
        } else {
            // Fallback to existing DriverFactory for local testing
            driver = DriverFactory.initializeDriver(testSettings);
        }

        setDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                // Add test result status to Allure report
                if (result.getStatus() == ITestResult.FAILURE) {
                    Allure.attachment("Test Failed Screenshot",
                            new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
                }

                String testCaseName = result.getMethod().getMethodName();
                String screenshotPath = "screenshots/" + testCaseName + ".png";
                File destFile = new File(screenshotPath);
                takeScreenshot(destFile);
            } catch (Exception e) {
                System.out.println("Failed to take screenshot: " + e.getMessage());
            } finally {
                driver.quit();
            }
        }
    }

    // Create a separate method to generate Allure report
    @AfterSuite(alwaysRun = true)
    public void generateAllureReport() {
        // Run Allure serve command and terminate after opening in browser
        try {
            String projectDir = getProjectDirectory();
            ProcessBuilder processBuilder = new ProcessBuilder();
            String command = String.format("cd %s && allure serve target/allure-results", projectDir);

            if (isWindows()) {
                processBuilder.command("cmd.exe", "/c", command);
            } else {
                processBuilder.command("bash", "-c", command);
            }

            Process process = processBuilder.start();

            // Terminate the Allure server after a delay (optional)
            Thread.sleep(5000); // Wait 5 seconds (adjust as needed)
            process.destroy(); // Kill the process

            System.out.println("Allure server started and terminated after opening the browser.");
        } catch (Exception e) {
            System.out.println("Error executing Allure serve command: " + e.getMessage());
        }
    }

    /**
     * Helper method to get the project directory.
     */
    private String getProjectDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * Helper method to determine if the OS is Windows.
     */
    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    public WebDriver getDriver() {
        return this.driver.get();
    }

    public void takeScreenshot(File destFile) {
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(file, destFile);
                try (InputStream is = new FileInputStream(destFile)) {
                    Allure.addAttachment("Screenshot", is);
                }
            } catch (IOException | WebDriverException e) {
                System.out.println("Failed to take or save screenshot: " + e.getMessage());
            }
        }
    }
}