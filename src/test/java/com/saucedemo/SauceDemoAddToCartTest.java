package com.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SauceDemoAddToCartTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testAddToCart() {
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

        // Adăugați orice produs în coșul de cumpărături
        // For simplicity, let's add the first product to the cart
        WebElement firstProductAddToCartBtn = driver.findElement(By.cssSelector(".inventory_item:nth-child(1) button.btn_inventory"));
        firstProductAddToCartBtn.click();

        // Verificați că produsul a fost adăugat în coș
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String cartItemsCount = cartBadge.getText();
        Assert.assertEquals(cartItemsCount, "1");

        // Proceed with the checkout or further verifications as needed

        // Apăsați butonul de logout (optional)
        WebElement menuButton = driver.findElement(By.className("bm-burger-button"));
        menuButton.click();

        WebElement logoutButton = driver.findElement(By.id("logout_sidebar_link"));
        logoutButton.click();
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }
}
