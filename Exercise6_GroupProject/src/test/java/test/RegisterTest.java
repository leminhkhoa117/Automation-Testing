package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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

    @ParameterizedTest
    @Order(1)
    @CsvFileSource(resources = "/register-data.csv", numLinesToSkip = 1)
    void testRegisterFromCSV(String firstName, String lastName, String email, String gender, String mobile, String expected) {
        registerPage.navigate();
        registerPage.enterFirstName(firstName);
        registerPage.enterLastName(lastName);
        registerPage.enterEmail(email);
        registerPage.selectGender(gender);
        registerPage.enterMobile(mobile);
        // Có thể bổ sung các trường khác nếu muốn
        registerPage.submit();

        if (expected.equals("success")) {
            assertTrue(registerPage.isModalDisplayed());
            assertTrue(registerPage.getModalTitle().contains("Thanks for submitting the form"));
        } else {
            assertFalse(registerPage.isModalDisplayed());
        }
    }
}