package com.saucedemo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoAddToCartTest {

    public static void main(String[] args) {
        // Set path to ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Drivers\\chromedriver.exe");

        // Create a new ChromeDriver instance
        WebDriver driver = new ChromeDriver();

        // Set an implicit wait for the driver to wait for elements to be available
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navigate to the Saucedemo website
        driver.get("https://www.saucedemo.com/");

        // Log in to the website (replace with username and password)
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Wait for the products page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        // Add more than 5 products to the cart
        int numberOfProductsToAdd = 6;
        for (int i = 0; i < numberOfProductsToAdd; i++) {
            String addToCartButtonSelector = ".btn_primary";
            // Get all the "Add to Cart" buttons and click the i-th button
            driver.findElements(By.cssSelector(addToCartButtonSelector)).get(i).click();
        }

        // Go to the cart page
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();

        // Verify the number of products in the cart
        int expectedNumberOfProducts = 5;
        int actualNumberOfProducts = driver.findElements(By.cssSelector(".cart_item")).size();

        if (actualNumberOfProducts > expectedNumberOfProducts) {
            System.out.println("Test passed! More than 5 products can be added to the cart.");
        } else {
            System.out.println("Test failed! The number of products in the cart is not more than 5.");
        }

        // Close the browser
        driver.quit();
    }
}
