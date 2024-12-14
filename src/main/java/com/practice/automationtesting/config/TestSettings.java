package com.practice.automationtesting.config;

import com.practice.automationtesting.enums.TestConstants;

public class TestSettings {
    private TestConstants.BrowserType browserType;
    private String applicationUrl;
    private Float timeoutInterval;
    private Float timeoutPollingInterval;
    private TestConstants.TestRunType testRunType;
    private String gridUri;
    private String appiumUri;
    private static String userName;
    private static String password;
    private String browserMode;

    // Getters and setters for all fields

    public TestConstants.BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(TestConstants.BrowserType browserType) {
        this.browserType = browserType;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public Float getTimeoutInterval() {
        return timeoutInterval;
    }

    public void setTimeoutInterval(Float timeoutInterval) {
        this.timeoutInterval = timeoutInterval;
    }

    public Float getTimeoutPollingInterval() {
        return timeoutPollingInterval;
    }

    public void setTimeoutPollingInterval(Float timeoutPollingInterval) {
        this.timeoutPollingInterval = timeoutPollingInterval;
    }

    public TestConstants.TestRunType getTestRunType() {
        return testRunType;
    }

    public void setTestRunType(TestConstants.TestRunType testRunType) {
        this.testRunType = testRunType;
    }

    public String getGridUri() {
        return gridUri;
    }

    public void setGridUri(String gridUri) {
        this.gridUri = gridUri;
    }

    public static String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        TestSettings.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        TestSettings.password = password;
    }

    public String getBrowserMode() {
        return browserMode;
    }

    public void setBrowserMode(String browserMode) {
        this.browserMode = browserMode;
    }

    public String getAppiumUri() {
        return appiumUri;
    }

    public void setAppiumUri(String appiumUri) {
        this.appiumUri = appiumUri;
    }
}
