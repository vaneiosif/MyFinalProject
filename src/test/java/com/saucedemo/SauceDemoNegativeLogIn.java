package com.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SauceDemoNegativeLogIn {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        String url = "https://www.saucedemo.com/";
        driver.get(url);

        String username = "standard_user";
        String password = "secret_sauce";

        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        loginButton.click();

        String expectedProductsPageTitle = "Swag Labs";
        WebElement productsTitle = driver.findElement(By.className("title"));
        Assert.assertTrue(productsTitle.isDisplayed(), "Login-ul a eșuat sau nu s-a ajuns pe pagina de produse.");
    }

    @Test(priority = 2)
    public void testFailedLogin() {
        String url = "https://www.saucedemo.com/";
        driver.get(url);

        String username = "locked_out_user";
        String password = "wrong_password";

        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        loginButton.click();

        WebElement errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']"));

        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        String actualErrorMessage = errorMessage.getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message is incorrect.");


    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }
}
