package com.saucedemo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert; // Import pentru clasa Assert

public class SauceDemoCheckoutTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Uncomment this line for headless mode
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void testCheckout() {
        driver.get("https://www.saucedemo.com/");
        login(driver, "standard_user", "secret_sauce");
        addToCart(driver, "Sauce Labs Backpack");
        goToCart(driver);
        checkout(driver, "John", "Doe", "12345");
        backToHomePage(driver);
        logout(driver);
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }

    public void login(WebDriver driver, String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    public void addToCart(WebDriver driver, String itemName) {
        driver.findElement(By.xpath("//div[text()='" + itemName + "']/ancestor::div[@class='inventory_item']//button"))
                .click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("shopping_cart_badge"), "1"));
    }

    public void goToCart(WebDriver driver) {
        driver.findElement(By.className("shopping_cart_link")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    public void checkout(WebDriver driver, String firstName, String lastName, String zipCode) {
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(zipCode);
        driver.findElement(By.id("continue")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        driver.findElement(By.id("finish")).click();
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
    }

    public void backToHomePage(WebDriver driver) {
        driver.findElement(By.className("app_logo")).click();
    }

    public void logout(WebDriver driver) {
        driver.findElement(By.className("bm-burger-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logoutLink.click();
        wait.until(ExpectedConditions.urlContains("saucedemo.com"));
    }
}
