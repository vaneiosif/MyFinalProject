import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoCheckoutTest {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        // Configure Chrome options if needed (e.g., headless mode)
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Uncomment this line for headless mode

        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver(options);

        // Maximize the browser window (optional)
        driver.manage().window().maximize();

        try {
            // Navigate to the website
            driver.get("https://www.saucedemo.com/");

            // Login to the website
            login(driver, "standard_user", "secret_sauce");

            // Add an item to the cart
            addToCart(driver, "Sauce Labs Backpack");

            // Go to the cart
            goToCart(driver);

            // Checkout
            checkout(driver, "John", "Doe", "12345");

            // Go back to the home page
            backToHomePage(driver);

            // Logout
            logout(driver);
        } finally {
            // Close the browser and quit WebDriver after all tests are done
            driver.quit();
        }
    }

    public static void login(WebDriver driver, String username, String password) {
        // Find and populate the username and password fields
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);

        // Click on the login button
        driver.findElement(By.id("login-button")).click();

        // Wait until the inventory page is loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    public static void addToCart(WebDriver driver, String itemName) {
        // Find the item and click on the Add to Cart button
        driver.findElement(By.xpath("//div[text()='" + itemName + "']/ancestor::div[@class='inventory_item']//button"))
                .click();

        // Wait until the item is added to the cart
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("shopping_cart_badge"), "1"));
    }

    public static void goToCart(WebDriver driver) {
        // Click on the shopping cart icon
        driver.findElement(By.className("shopping_cart_link")).click();

        // Wait until the cart page is loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    public static void checkout(WebDriver driver, String firstName, String lastName, String zipCode) {
        // Click on the checkout button
        driver.findElement(By.id("checkout")).click();

        // Populate the checkout information
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(zipCode);

        // Click on the continue button
        driver.findElement(By.id("continue")).click();

        // Wait until the overview page is loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));

        // Click on the finish button
        driver.findElement(By.id("finish")).click();

        // Wait until the thank you page is loaded
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
    }

    public static void backToHomePage(WebDriver driver) {
        // Click on the website logo to go back to the home page
        driver.findElement(By.className("app_logo")).click();
    }

    public static void logout(WebDriver driver) {
        // Click on the menu button to open the menu
        driver.findElement(By.className("bm-burger-button")).click();

        // Click on the logout link in the menu
        driver.findElement(By.id("logout_sidebar_link")).click();

        // Wait for the login page to load after logout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("saucedemo.com"));
    }
}
