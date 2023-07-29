package com.saucedemo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class SauceDemoSortingAndFiltering {

    public static void main(String[] args) {
        // Set path to ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Drivers\\chromedriver.exe");

        // Create a new ChromeDriver instance
        WebDriver driver = new ChromeDriver();

        // Set an implicit wait for the driver to wait for elements to be available
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navigate to the Saucedemo website
        driver.get("https://www.saucedemo.com/");

        // Log in to the website (replace with your username and password)
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Wait for the products page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        // Sort products by price (low to high)
        driver.findElement(By.cssSelector("select.product_sort_container")).sendKeys("lohi");

        // Wait for the products to be sorted
        wait.until(ExpectedConditions.attributeContains(By.cssSelector(".inventory_item:nth-child(1) .inventory_item_price"), "innerText", "$"));

        // Adding a delay to ensure the page is fully loaded before attempting to find the filter element
        try {
            Thread.sleep(2000); // 2 seconds delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Filter products by keyword "Sauce Labs Backpack"
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Sauce Labs Backpack");

        try {
            // Wait for the products to be filtered
            wait.until(ExpectedConditions.attributeContains(By.cssSelector(".inventory_item:nth-child(1) .inventory_item_name"), "innerText", "Sauce Labs Backpack"));
        } catch (TimeoutException e) {
            // Handle the exception (element not found)
            System.out.println("The filtered product list is empty or not loaded properly.");
            driver.quit();
            return;
        }

        // Get the names and prices of the filtered products
        List<WebElement> productNames = driver.findElements(By.cssSelector(".inventory_item_name"));
        List<WebElement> productPrices = driver.findElements(By.cssSelector(".inventory_item_price"));

        // Print the names and prices of the filtered products
        for (int i = 0; i < productNames.size(); i++) {
            String name = productNames.get(i).getText();
            String price = productPrices.get(i).getText();
            System.out.println(name + " - " + price);
        }

        // Close the browser
        driver.quit();
    }
}
