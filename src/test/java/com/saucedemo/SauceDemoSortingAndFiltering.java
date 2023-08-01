package com.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SauceDemoSortingAndFiltering {

    public static void main(String[] args) {
        // Set path to ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Drivers\\chromedriver.exe");

        // Set Chrome options to disable extensions and notifications
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");

        // Create a new ChromeDriver instance with the given options
        WebDriver driver = new ChromeDriver(options);

        // Set an implicit wait for the driver to wait for elements to be available
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navigate to the Saucedemo website
        driver.get("https://www.saucedemo.com/");

        // Log in to the website
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Wait for the products page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        // Find the sort dropdown element and interact with it using Select class
        WebElement sortDropdown = driver.findElement(By.cssSelector(".product_sort_container"));
        Select sortSelect = new Select(sortDropdown);

        // Select "Price (low to high)" option from the dropdown using selectByValue
        sortSelect.selectByValue("lohi");

        // Wait for the products to be sorted
        wait.until(ExpectedConditions.attributeContains(By.cssSelector(".inventory_item:nth-child(1) .inventory_item_price"), "innerText", "$"));

        // Adding a delay to ensure the page is fully loaded before attempting to find the filtered products
        try {
            Thread.sleep(2000); // 2 seconds delay (optional)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get the names and prices of the sorted products
        List<WebElement> productNames = driver.findElements(By.cssSelector(".inventory_item_name"));
        List<WebElement> productPrices = driver.findElements(By.cssSelector(".inventory_item_price"));

        // Print the names and prices of the sorted products
        for (int i = 0; i < productNames.size(); i++) {
            String name = productNames.get(i).getText();
            String price = productPrices.get(i).getText();
            System.out.println(name + " - " + price);
        }

        // Close the browser
        driver.quit();
    }
}
