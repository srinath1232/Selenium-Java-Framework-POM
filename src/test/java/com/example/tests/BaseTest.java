package com.example.tests;

import com.example.utils.ExtentReportManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("browser.headless")) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        driver = new ChromeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (driver != null && result.getStatus() == ITestResult.FAILURE) {
                saveFailureScreenshot(result);
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private void saveFailureScreenshot(ITestResult result) {
        String testName = result.getName();
        try {
            Path screenshotDirectory = Path.of("target", "screenshots");
            Files.createDirectories(screenshotDirectory);

            String safeTestName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
            Path screenshotPath = screenshotDirectory.resolve(
                    safeTestName + "-" + System.currentTimeMillis() + ".png"
            );

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), screenshotPath);
            System.out.println("Failure screenshot saved to: " + screenshotPath.toAbsolutePath());
            ExtentReportManager.attachScreenshot(result, screenshotPath);
        } catch (IOException e) {
            System.err.println("Could not save or attach failure screenshot for "
                    + testName + ": " + e.getMessage());
        }
    }
}
