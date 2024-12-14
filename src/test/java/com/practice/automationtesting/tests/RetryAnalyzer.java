package com.practice.automationtesting.tests;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 3; // Maximum number of retries

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            // Check if specific exceptions are present for retry (optional)
            // if(result.getThrowable() instanceof MyCustomException) {
            retryCount++;
            return true;
            // }            return true; // Retry the test
        }
        return false; // Stop retrying
    }
}