package com.example.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.example.utils.ExtentReportManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        createTestIfNeeded(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        getOrCreateTest(result).pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable failure = result.getThrowable();
        if (failure == null) {
            getOrCreateTest(result).fail("Test failed.");
        } else {
            getOrCreateTest(result).fail(failure);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Throwable skipReason = result.getThrowable();
        if (skipReason == null) {
            getOrCreateTest(result).skip("Test skipped");
        } else {
            getOrCreateTest(result).skip(skipReason);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.getReport().flush();
    }

    private void createTestIfNeeded(ITestResult result) {
        ExtentTest test = ExtentReportManager.getReport()
                .createTest(result.getMethod().getMethodName());
        result.setAttribute(ExtentReportManager.EXTENT_TEST_ATTRIBUTE, test);
    }

    private ExtentTest getOrCreateTest(ITestResult result) {
        Object test = result.getAttribute(ExtentReportManager.EXTENT_TEST_ATTRIBUTE);
        if (test instanceof ExtentTest extentTest) {
            return extentTest;
        }

        ExtentTest newTest = ExtentReportManager.getReport()
                .createTest(result.getMethod().getMethodName());
        result.setAttribute(ExtentReportManager.EXTENT_TEST_ATTRIBUTE, newTest);
        return newTest;
    }
}
