package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.LoginPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends BaseTest {
    static LoginPage loginPage;
    static WebDriverWait wait;

    @BeforeAll
    static void initPage() {
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    void testLoginSuccess() {
        loginPage.navigate();
        loginPage.login("tomsmith", "SuperSecretPassword!"); // Đổi thành tài khoản hợp lệ của nhóm bạn
        WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getSuccessLocator()));
        assertTrue(success.getText().toLowerCase().contains("success") || success.getText().toLowerCase().contains("logged"));
    }

    @ParameterizedTest(name = "CSV File: {0} / {1}")
    @Order(4)
    @CsvFileSource(resources = "/login-data.csv", numLinesToSkip = 1)
    void testLoginFromCSV(String username, String password, String expected) {
        System.out.printf("Testing with username='%s', password='%s', expected='%s'%n", username, password, expected);
        // Reset cookies/session
        driver.manage().deleteAllCookies();
        loginPage.navigate();
        username = (username == null) ? "" : username.trim();
        password = (password == null) ? "" : password.trim();

        loginPage.login(username, password);
        
        // Handle Google Password Manager popup if it appears
        try {
            // Wait a bit for popup to appear
            Thread.sleep(1000);
            
            // Try multiple selectors for the popup
            String[] popupSelectors = {
                "button[aria-label='OK']",
                "button:contains('OK')",
                "div[role='dialog'] button",
                "button[data-testid='ok-button']",
                "button:contains('Change your password')"
            };
            
            for (String selector : popupSelectors) {
                try {
                    WebElement popup = driver.findElement(By.cssSelector(selector));
                    if (popup.isDisplayed()) {
                        popup.click();
                        System.out.println("Google Password Manager popup handled with selector: " + selector);
                        Thread.sleep(500); // Wait for popup to close
                        break;
                    }
                } catch (Exception e) {
                    // Continue to next selector
                }
            }
        } catch (Exception e) {
            // Popup didn't appear, continue with test
            System.out.println("No popup detected, continuing with test");
        }
        
        By resultLocator = expected.equals("success") ? loginPage.getSuccessLocator() : loginPage.getErrorLocator();
        WebElement result = (new WebDriverWait(driver, java.time.Duration.ofSeconds(20)))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(resultLocator));

        if (expected.equals("success")) {
            assertTrue(result.getText().contains("You logged into a secure area!"));
        } else {
            assertTrue(result.getText().toLowerCase().contains("invalid"));
        }
    }
}