package com.saucedemo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert; // Import pentru clasa Assert

public class SauceDemoAddToCartTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        // Set path to ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        // Create a new ChromeDriver instance
        driver = new ChromeDriver();
        // Set an implicit wait for the driver to wait for elements to be available
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void testAddToCart() {
        // Navigate to the Saucedemo website
        driver.get("https://www.saucedemo.com/");

        // Log in to the website (replace with username and password)
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Wait for the products page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        // Add the first two products to the cart
        String addToCartButtonSelector = ".btn_primary";
        driver.findElements(By.cssSelector(addToCartButtonSelector)).get(0).click();
        driver.findElements(By.cssSelector(addToCartButtonSelector)).get(1).click();

        // Go to the cart page
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();

        // Verify the number of products in the cart
        int expectedNumberOfProducts = 2;
        int actualNumberOfProducts = driver.findElements(By.cssSelector(".cart_item")).size();

        // Assert the result
        Assert.assertEquals(actualNumberOfProducts, expectedNumberOfProducts,
                "Number of products in the cart is not as expected.");

        // If the assertion passes, the following message will be printed
        System.out.println("Test passed! 2 products were successfully added to the cart.");
    }

    @AfterTest
    public void teardown() {
        // Close the browser
        driver.quit();
    }
}
