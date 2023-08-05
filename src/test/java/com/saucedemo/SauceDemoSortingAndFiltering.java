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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SauceDemoSortingAndFiltering {

    private WebDriver driver;



    @BeforeTest
    public void setup() {
        // Set path to ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        // Set Chrome options to disable extensions and notifications
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");

        // Create a new ChromeDriver instance with the given options
        driver = new ChromeDriver(options);

        // Maximize the browser window
        driver.manage().window().maximize();

        // Set an implicit wait for the driver to wait for elements to be available
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void testSortingAndFiltering() {
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

        // Sort by Name (A to Z)
        sortSelect.selectByValue("az");
        waitForSortingChanges(3000); // Wait for 3 seconds

        // Get the product names after sorting
        List<WebElement> productElements = driver.findElements(By.cssSelector(".inventory_item_name"));
        List<String> actualProductNames = new ArrayList<>();
        for (WebElement productElement : productElements) {
            actualProductNames.add(productElement.getText());
        }

        // Check if products are sorted in ascending order by name
        List<String> expectedProductNames = new ArrayList<>(actualProductNames);
        Collections.sort(expectedProductNames);
        Assert.assertEquals(actualProductNames, expectedProductNames, "Products are not sorted by name (A to Z).");

        // Reinitialize the sort dropdown element after page reload
        sortDropdown = driver.findElement(By.cssSelector(".product_sort_container"));
        sortSelect = new Select(sortDropdown);

        // Sort by Price (low to high)
        sortSelect.selectByValue("lohi");
        waitForSortingChanges(3000); // Wait for 3 seconds

        // Get the product prices after sorting
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".inventory_item_price"));
        List<Double> actualProductPrices = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            actualProductPrices.add(Double.parseDouble(priceElement.getText().substring(1)));
        }

        // Check if products are sorted in ascending order by price
        List<Double> expectedProductPrices = new ArrayList<>(actualProductPrices);
        Collections.sort(expectedProductPrices);
        Assert.assertEquals(actualProductPrices, expectedProductPrices, "Products are not sorted by price (low to high).");
    }


    @AfterTest
    public void teardown() {
        // Close the browser and quit WebDriver after all tests are done
        driver.quit();
    }

    private static void waitForSortingChanges(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
