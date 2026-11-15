package com.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Path;

public final class ExtentReportManager {
    public static final String EXTENT_TEST_ATTRIBUTE = "extentTest";

    private static final ExtentReports REPORT = createReport();

    private ExtentReportManager() {
    }

    public static ExtentReports getReport() {
        return REPORT;
    }

    public static void attachScreenshot(ITestResult result, Path screenshotPath) throws IOException {
        Object reportTest = result.getAttribute(EXTENT_TEST_ATTRIBUTE);
        if (reportTest instanceof ExtentTest extentTest) {
            extentTest.addScreenCaptureFromPath(screenshotPath.toAbsolutePath().toString());
        }
    }

    private static ExtentReports createReport() {
        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(Path.of("target", "extent-report.html").toString());
        ExtentReports report = new ExtentReports();
        report.attachReporter(sparkReporter);
        return report;
    }
}
