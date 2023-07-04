package anhtester.com.pages;

import anhtester.com.common.helpers.ValidateUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class SignInPage {

    WebDriver driver;
    private ValidateUIHelpers validateUIHelpers;

    public SignInPage(WebDriver driver) {
        this.driver = driver;
        validateUIHelpers = new ValidateUIHelpers(this.driver);
    }

    //  Element at Sign In page
    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");
    private By signinBtn = By.xpath("//button[@type='submit']");

    public DashboardPage signIn(String emailValue, String passwordValue) {
        validateUIHelpers.waitForPageLoaded();
        Assert.assertTrue(validateUIHelpers.verifyElementText(signinBtn, "Sign in"), "Không phải trang Sign In");
        validateUIHelpers.clearText(emailInput);
        validateUIHelpers.clearText(passwordInput);

        validateUIHelpers.setText(emailInput, emailValue);
        validateUIHelpers.setText(passwordInput, passwordValue);
        validateUIHelpers.clickElement(signinBtn);

        return new DashboardPage(this.driver);
    }
}
