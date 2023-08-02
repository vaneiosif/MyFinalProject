package com.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoCheckout {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com/");

        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("performance_glitch_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_link")));

        // Adăugare produs "Sauce Labs Backpack" în coș
        WebElement addToCartBackpackButton = driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item']//button"));
        addToCartBackpackButton.click();

        // Adăugare produs "Sauce Labs Bike Light" în coș
        WebElement addToCartBikeLightButton = driver.findElement(By.xpath("//div[text()='Sauce Labs Bike Light']/ancestor::div[@class='inventory_item']//button"));
        addToCartBikeLightButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link")));
        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("first-name")));
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        WebElement zipCodeField = driver.findElement(By.id("postal-code"));

        firstNameField.sendKeys("Vanessa");
        lastNameField.sendKeys("Iosif");
        zipCodeField.sendKeys("0000");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        WebElement finishButton = driver.findElement(By.id("finish"));
        finishButton.click();


        // Apasă butonul "Back to Home"
        WebElement backToHomeButton = driver.findElement(By.id("back-to-products"));
        backToHomeButton.click();

        // Deschide meniul lateral de navigare
        WebElement menuButton = driver.findElement(By.className("bm-burger-button"));
        menuButton.click();

        // Așteaptă să fie vizibil butonul de logout
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));

        // Apasă butonul "Logout"
        WebElement logoutButton = driver.findElement(By.id("logout_sidebar_link"));
        logoutButton.click();

        // Așteaptă mesajul de confirmare "THANK YOU FOR YOUR ORDER"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='THANK YOU FOR YOUR ORDER']")));

        // Verifică dacă mesajul de confirmare este afișat
        WebElement thankYouMessage = driver.findElement(By.xpath("//h2[text()='THANK YOU FOR YOUR ORDER']"));
        assert thankYouMessage.isDisplayed() : "Checkout failed! Thank you message not displayed.";

        Thread.sleep(2000);
        driver.quit();
    }
}