package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private By usernameField = By.id("username"); // Sửa lại nếu id khác
    private By passwordField = By.id("password"); // Sửa lại nếu id khác
    private By loginButton = By.cssSelector("button[type='submit']");
    private By successMsg = By.cssSelector(".flash.success, .alert-success");
    private By errorMsg = By.cssSelector(".flash.error, .alert-danger");

    public void navigate() {
        navigateTo("https://the-internet.herokuapp.com/login"); // Đổi thành URL login của nhóm bạn
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public By getSuccessLocator() {
        return successMsg;
    }

    public By getErrorLocator() {
        return errorMsg;
    }
}