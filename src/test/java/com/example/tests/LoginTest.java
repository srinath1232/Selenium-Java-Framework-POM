package com.example.tests;

import com.example.pages.LoginPage;
import com.example.listeners.ExtentReportListener;
import com.example.utils.ConfigReader;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Listeners(ExtentReportListener.class)
public class LoginTest extends BaseTest {
    @Test
    public void loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(ConfigReader.getProperty("baseUrl"));
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password"));

        assertTrue(
                loginPage.getFlashMessage().contains("You logged into a secure area!"),
                "The success message was not displayed.");
    }
}
