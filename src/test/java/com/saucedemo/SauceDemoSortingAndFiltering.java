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

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SauceDemoSortingAndFiltering {

    private WebDriver driver;

    // @BeforeTest: Metoda setup() care va fi executată înainte de test
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

        // Set an implicit wait for the driver to wait for elements to be available
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // @Test: Metoda care reprezintă testul propriu-zis
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

        // Reinitialize the sort dropdown element after page reload
        sortDropdown = driver.findElement(By.cssSelector(".product_sort_container"));
        sortSelect = new Select(sortDropdown);

        // Sort by Name (Z to A)
        sortSelect.selectByValue("za");
        waitForSortingChanges(3000); // Wait for 3 seconds

        // Reinitialize the sort dropdown element after page reload
        sortDropdown = driver.findElement(By.cssSelector(".product_sort_container"));
        sortSelect = new Select(sortDropdown);

        // Sort by Price (low to high)
        sortSelect.selectByValue("lohi");
        waitForSortingChanges(3000); // Wait for 3 seconds

        // Reinitialize the sort dropdown element after page reload
        sortDropdown = driver.findElement(By.cssSelector(".product_sort_container"));
        sortSelect = new Select(sortDropdown);

        // Sort by Price (high to low)
        sortSelect.selectByValue("hilo");
        waitForSortingChanges(3000); // Wait for 3 seconds
    }

    // @AfterTest: Metoda teardown() care va fi executată după finalizarea testului
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
