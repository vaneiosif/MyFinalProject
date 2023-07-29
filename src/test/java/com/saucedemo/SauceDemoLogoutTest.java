package com.saucedemo;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SauceDemoLogoutTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginAndLogout() {
        String url = "https://www.saucedemo.com/";
        driver.get(url);

        // Introduceți credențialele de login
        String username = "standard_user";
        String password = "secret_sauce";

        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        // Apăsați butonul de login
        loginButton.click();

        // Verificați dacă sunteți redirecționat către pagina de produse
        String expectedProductsPageTitle = "Swag Labs";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedProductsPageTitle);

        // Apăsați butonul de logout
        WebElement logoutButton = driver.findElement(By.id("react-burger-menu-btn"));
        logoutButton.click();

        // Așteptați ca meniul să fie complet afișat
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));

        // Faceți clic pe butonul de logout
        logoutLink.click();

        // Verificați dacă sunteți redirecționat către pagina de login
        String expectedLoginPageTitle = "Swag Labs";
        String actualLoginPageTitle = driver.getTitle();
        Assert.assertEquals(actualLoginPageTitle, expectedLoginPageTitle);
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }
}
