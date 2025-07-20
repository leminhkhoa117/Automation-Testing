package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import pages.RegistrationPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Registration Tests for demoqa.com")
class RegistrationTest extends BaseTest {
    static RegistrationPage registrationPage;

    @BeforeAll
    static void setupPage() {
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Should register successfully with valid data")
    void testRegisterSuccess() {
        registrationPage.navigate();
        registrationPage.enterFirstName("John");
        registrationPage.enterLastName("Doe");
        registrationPage.enterEmail("john.doe@example.com");
        registrationPage.selectGender("Male");
        registrationPage.enterMobile("0123456789");
        registrationPage.enterDob("10 May 1990");
        registrationPage.enterSubjects("Maths");
        registrationPage.selectHobby("Sports");
        registrationPage.enterAddress("123 Main St");
        registrationPage.selectState("NCR");
        registrationPage.selectCity("Delhi");
        registrationPage.submit();

        assertTrue(registrationPage.isModalDisplayed());
        assertTrue(registrationPage.getModalTitle().contains("Thanks for submitting the form"));
    }

    @ParameterizedTest(name = "Register with: {0}, {1}, {2}, {3}, {4}")
    @Order(2)
    @CsvFileSource(resources = "/registration-data.csv", numLinesToSkip = 1)
    void testRegisterWithCsv(String firstName, String lastName, String email, String gender, String mobile) {
        registrationPage.navigate();
        registrationPage.enterFirstName(firstName);
        registrationPage.enterLastName(lastName);
        registrationPage.enterEmail(email);
        registrationPage.selectGender(gender);
        registrationPage.enterMobile(mobile);
        registrationPage.enterDob("10 May 1990");
        registrationPage.enterSubjects("Maths");
        registrationPage.selectHobby("Sports");
        registrationPage.enterAddress("123 Main St");
        registrationPage.selectState("NCR");
        registrationPage.selectCity("Delhi");
        registrationPage.submit();

        assertTrue(registrationPage.isModalDisplayed());
        assertTrue(registrationPage.getModalTitle().contains("Thanks for submitting the form"));
    }
} 