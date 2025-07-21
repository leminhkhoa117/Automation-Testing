package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage extends BasePage {
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Locators cho demoqa.com/automation-practice-form
    private By firstName = By.id("firstName");
    private By lastName = By.id("lastName");
    private By email = By.id("userEmail");
    private By genderMale = By.xpath("//label[text()='Male']");
    private By genderFemale = By.xpath("//label[text()='Female']");
    private By genderOther = By.xpath("//label[text()='Other']");
    private By mobile = By.id("userNumber");
    private By dobInput = By.id("dateOfBirthInput");
    private By subjects = By.id("subjectsInput");
    private By hobbiesSports = By.xpath("//label[text()='Sports']");
    private By hobbiesReading = By.xpath("//label[text()='Reading']");
    private By hobbiesMusic = By.xpath("//label[text()='Music']");
    private By uploadPicture = By.id("uploadPicture");
    private By address = By.id("currentAddress");
    private By state = By.id("react-select-3-input");
    private By city = By.id("react-select-4-input");
    private By submitBtn = By.id("submit");
    private By modalTitle = By.id("example-modal-sizes-title-lg");
    private By modal = By.className("modal-content");

    public void navigate() {
        navigateTo("https://demoqa.com/automation-practice-form");
    }

    public void enterFirstName(String value) { type(firstName, value); }
    public void enterLastName(String value) { type(lastName, value); }
    public void enterEmail(String value) { type(email, value); }
    public void selectGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            return; // Don't select any gender if empty
        }
        
        switch (gender.toLowerCase().trim()) {
            case "male": 
                click(genderMale); 
                break;
            case "female": 
                click(genderFemale); 
                break;
            case "other": 
                click(genderOther); 
                break;
            default:
                System.out.println("Warning: Unknown gender '" + gender + "', skipping gender selection");
                break;
        }
    }
    public void enterMobile(String value) { type(mobile, value); }
    public void enterDob(String value) {
        click(dobInput);
        WebElement dob = driver.findElement(dobInput);
        dob.sendKeys(Keys.CONTROL + "a");
        dob.sendKeys(value);
        dob.sendKeys(Keys.ENTER);
    }
    public void enterSubjects(String value) {
        type(subjects, value);
        driver.findElement(subjects).sendKeys(Keys.ENTER);
    }
    public void selectHobby(String hobby) {
        switch (hobby.toLowerCase()) {
            case "sports": click(hobbiesSports); break;
            case "reading": click(hobbiesReading); break;
            case "music": click(hobbiesMusic); break;
        }
    }
    public void uploadPicture(String filePath) {
        driver.findElement(uploadPicture).sendKeys(filePath);
    }
    public void enterAddress(String value) { type(address, value); }
    public void selectState(String value) {
        type(state, value);
        driver.findElement(state).sendKeys(Keys.ENTER);
    }
    public void selectCity(String value) {
        type(city, value);
        driver.findElement(city).sendKeys(Keys.ENTER);
    }
    public void submit() {
        WebElement submitButton = driver.findElement(submitBtn);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
    }
    public String getModalTitle() { return getText(modalTitle); }
    public boolean isModalDisplayed() { return isElementVisible(modal); }
}