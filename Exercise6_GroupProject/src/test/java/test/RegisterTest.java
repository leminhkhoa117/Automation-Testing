package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.RegisterPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterTest extends BaseTest {
    static RegisterPage registerPage;
    static WebDriverWait wait;

    @BeforeAll
    static void initPage() {
        registerPage = new RegisterPage(driver);
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    @ParameterizedTest(name = "Register Test: {0} {1} - {5}")
    @Order(1)
    @CsvFileSource(resources = "/register-data.csv", numLinesToSkip = 1)
    void testRegisterFromCSV(String firstName, String lastName, String email, String gender, String mobile, String expected) {
        System.out.printf("Testing registration: firstName='%s', lastName='%s', email='%s', gender='%s', mobile='%s', expected='%s'%n", 
                         firstName, lastName, email, gender, mobile, expected);
        
        registerPage.navigate();
        
        // Clear any existing data
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        
        // Handle null values and convert to empty string
        firstName = (firstName == null || firstName.equals("null")) ? "" : firstName.trim();
        lastName = (lastName == null || lastName.equals("null")) ? "" : lastName.trim();
        email = (email == null || email.equals("null")) ? "" : email.trim();
        gender = (gender == null || gender.equals("null")) ? "" : gender.trim();
        mobile = (mobile == null || mobile.equals("null")) ? "" : mobile.trim();
        
        // Fill form with safe values
        if (!firstName.isEmpty()) {
            registerPage.enterFirstName(firstName);
        }
        if (!lastName.isEmpty()) {
            registerPage.enterLastName(lastName);
        }
        if (!email.isEmpty()) {
            registerPage.enterEmail(email);
        }
        
        if (!gender.isEmpty()) {
            registerPage.selectGender(gender);
        }
        
        if (!mobile.isEmpty()) {
            registerPage.enterMobile(mobile);
        }
        
        // Submit form
        registerPage.submit();
        
        // Wait for result
        try {
            if (expected.equals("success")) {
                // Wait for success modal
                WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
                assertTrue(modal.isDisplayed(), "Success modal should be displayed");
                assertTrue(registerPage.getModalTitle().contains("Thanks for submitting the form"), 
                          "Modal title should contain success message");
                System.out.println("✅ Registration successful");
            } else {
                // Check for validation errors
                boolean hasValidationError = false;
                
                // Check for common validation error indicators
                try {
                    // Look for validation error messages
                    WebElement errorElement = driver.findElement(By.cssSelector(".was-validated .invalid-feedback, .error-message, [class*='error']"));
                    if (errorElement.isDisplayed()) {
                        hasValidationError = true;
                        System.out.println("Validation error found: " + errorElement.getText());
                    }
                } catch (Exception e) {
                    // No validation error element found
                }
                
                // If no validation error found, check if modal is not displayed
                if (!hasValidationError) {
                    try {
                        WebElement modal = driver.findElement(By.className("modal-content"));
                        if (modal.isDisplayed()) {
                            System.out.println("❌ Modal displayed when it shouldn't be");
                            assertFalse(true, "Modal should not be displayed for invalid data");
                        }
                    } catch (Exception e) {
                        // Modal not found, which is expected for invalid data
                        System.out.println("✅ No modal displayed for invalid data (expected)");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
}