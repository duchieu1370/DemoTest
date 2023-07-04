package anhtester.com.testcases;

import anhtester.com.common.BaseSetup;
import anhtester.com.common.ultilities.PropertiesFile;
import anhtester.com.pages.SignInPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PropertiesTest {

    private WebDriver driver;
    private SignInPage signInPage;

    @BeforeClass
    public void createDriver() {
        // Gọi hàm để khởi tạo file properties
        PropertiesFile.setPropertiesFile();
        PropertiesFile.setPropValue("browser", "chrome");
        // Đọc data từ file properties với key là "browser"
        driver = new BaseSetup().setupDriver(PropertiesFile.getPropValue("browser"));
    }

    @Test
    public void signinCRM() {
        signInPage = new SignInPage(driver);
        driver.get("https://crm.anhtester.com");

        // Đọc data từ file properties
        signInPage.signIn(PropertiesFile.getPropValue("email"), PropertiesFile.getPropValue("password"));

    }

    @Test
    public void signinCRM_AfterSetValue() {
        signInPage = new SignInPage(driver);
        driver.get("https://crm.anhtester.com");

        //Set giá trị vào file properties
        PropertiesFile.setPropValue("email", "tmb01@mailinator.com");

        // Đọc data từ file properties
        signInPage.signIn(PropertiesFile.getPropValue("email"), PropertiesFile.getPropValue("password"));

        PropertiesFile.setPropValue("pin", "456");
    }

    @AfterClass
    public void closeDriver() {
        driver.quit();
    }
}
