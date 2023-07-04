package anhtester.com.testcases;

import anhtester.com.common.helpers.ExcelHelpers;
import anhtester.com.common.BaseSetup;
import anhtester.com.pages.SignInPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SignInTest {

    private WebDriver driver;
    private SignInPage signInPage;
    private ExcelHelpers excel;

    @BeforeClass
    public void setupBrowser() {
        driver = new BaseSetup().setupDriver("chrome");
        excel = new ExcelHelpers();
    }

    @Test
    public void signInPage() throws Exception {

        // Setup đường dẫn của file excel
        excel.setExcelFile("src/test/resources/Book1.xlsx", "Sheet1");

        signInPage = new SignInPage(driver);
        driver.get("https://crm.anhtester.com");

        // Đọc data từ file excel
        signInPage.signIn(excel.getCellData("username", 1), excel.getCellData("password", 1));

        //Ghi data vào file excel
        //excel.setCellData("anhtester.com", 5, 0);

        // Chú ý: dòng và cột trong code nó hiểu bắt đầu từ 0

        Thread.sleep(2000);
    }

    @Test
    public void signInPageReadExcelDynamic() throws Exception {

        //Setup đường dẫn của file excel
        excel.setExcelFile("src/test/resources/Book1.xlsx", "Sheet1");

        signInPage = new SignInPage(driver);
        driver.get("https://crm.anhtester.com");

        for (int i = 1; i < 3; i++) {
            signInPage.signIn(excel.getCellData("username", i), excel.getCellData("password", i));
            Thread.sleep(1000);
        }

        // Ghi nhiều dòng vào file
//        for (int i = 1; i < 2; i++) {
//            excel.setCellData("AN01", i, 3);
//        }
    }

    @AfterClass
    public void closeBrowser() {
        driver.close();
    }

}
