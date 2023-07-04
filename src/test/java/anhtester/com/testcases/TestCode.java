package anhtester.com.testcases;

import anhtester.com.common.BaseSetup;
import anhtester.com.common.helpers.ValidateUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestCode {

    private WebDriver driver;
    private ValidateUIHelpers validateUIHelpers;

    @BeforeClass
    public void setupDriver() {
        driver = new BaseSetup().setupDriver("opera");
        validateUIHelpers = new ValidateUIHelpers(driver);
    }

    @Test
    public void TestRun() throws InterruptedException {

        // driver.get("https://crm.anhtester.com/signin");

        //Khởi tạo biến lưu ngày giờ hiện hành
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        //In giờ trước khi chạy
        System.out.println(dt.format(LocalDateTime.now()));

        driver.navigate().to("https://hrm.anhtester.com/");
        validateUIHelpers.waitForPageLoaded();

        //In ra giờ sau khi chạy để biết thời gian trang loaded
        System.out.println(dt.format(LocalDateTime.now()));

        driver.findElement(By.xpath("//input[@id='iusername']")).sendKeys("employee01");
        driver.findElement(By.xpath("//input[@id='ipassword']")).sendKeys("123456");
        driver.findElement(By.xpath("//span[@class='ladda-label']")).click();

        validateUIHelpers.waitForPageLoaded();

        System.out.println(dt.format(LocalDateTime.now()));

        By element = By.xpath("//h5[contains(text(),'Thanh toán hóa đơn')]");

        validateUIHelpers.verifyElementExist(element);

    }

    @AfterClass
    public void closeDriver() {
        driver.close();
        driver.quit();
    }

}
