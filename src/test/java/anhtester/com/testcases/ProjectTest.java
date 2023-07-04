package anhtester.com.testcases;

import anhtester.com.common.BaseSetup;
import anhtester.com.common.helpers.CaptureHelpers;
import anhtester.com.common.helpers.ValidateUIHelpers;
import anhtester.com.common.ultilities.PropertiesFile;
import anhtester.com.pages.AddProjectPage;
import anhtester.com.pages.DashboardPage;
import anhtester.com.pages.ProjectPage;
import anhtester.com.pages.SignInPage;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class ProjectTest {

    private WebDriver driver;
    private SignInPage signInPage;
    private DashboardPage dashboardPage;
    private ProjectPage projectPage;
    private AddProjectPage addProjectPage;
    private ValidateUIHelpers validateUIHelpers;

    @BeforeClass
    public void setupBrowser() {
        //driver = getDriver();  //extends BaseSetup
        driver = new BaseSetup().setupDriver("chrome");
        validateUIHelpers = new ValidateUIHelpers(driver);
        try {
            CaptureHelpers.startRecord("ProjectTest");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Nó sẽ thực thi sau mỗi lần thực thi testcase (@Test)
    @AfterMethod
    public void takeScreenshot(ITestResult result) {
        validateUIHelpers.waitForPageLoaded(); //Chờ đợi trang load xong mới chụp
        // Khởi tạo đối tượng result thuộc ITestResult để lấy trạng thái và tên của từng Step
        // Ở đây sẽ so sánh điều kiện nếu testcase passed hoặc failed
        // passed = SUCCESS và failed = FAILURE
        if (ITestResult.SUCCESS == result.getStatus()) {
            try {
                // Tạo tham chiếu của TakesScreenshot
                TakesScreenshot ts = (TakesScreenshot) driver;
                // Gọi hàm capture screenshot - getScreenshotAs
                File source = ts.getScreenshotAs(OutputType.FILE);
                //Kiểm tra folder tồn tại. Nêu không thì tạo mới folder
                File theDir = new File("./Screenshots/");
                if (!theDir.exists()) {
                    theDir.mkdirs();
                }
                // result.getName() lấy tên của test case xong gán cho tên File chụp màn hình
                FileHandler.copy(source, new File("./Screenshots/" + result.getName() + ".png"));
                System.out.println("Đã chụp màn hình: " + result.getName());
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        }
    }

    @Test(priority = 1)
    public void signInPage() throws IOException {
        signInPage = new SignInPage(driver);
        driver.get("https://crm.anhtester.com");

        dashboardPage = signInPage.signIn("tld01@mailinator.com", "123456");
        validateUIHelpers.waitForPageLoaded();
    }

    @Test(priority = 2)
    public void openProjectPage() {
        validateUIHelpers.waitForPageLoaded();
        projectPage = dashboardPage.openProjectsPage();
    }

    @Test(priority = 3)
    public void openAddProjectPage() throws InterruptedException {
        validateUIHelpers.waitForPageLoaded();
        addProjectPage = projectPage.addProject();
    }

    @Test(priority = 4)
    public void addProject() throws InterruptedException {
        // Kiểm tra Text tồn tại trên trang
        // Hoặc đã dùng verifyElementText để kiểm tra trong hàm saveProduct
        // validateUIHelpers.verifyPageLoaded("Add project");
        addProjectPage.saveProduct();
    }

    @Test(priority = 5)
    public void searchProject() throws InterruptedException {
        validateUIHelpers.waitForPageLoaded();
        projectPage.enterSearchValue("PD03");
        Thread.sleep(2000);
        projectPage.checkSearchTableByColumn(2, "PD03");
    }

    @AfterClass
    public void closeBrowser() {
        driver.close();
        try {
            CaptureHelpers.stopRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
