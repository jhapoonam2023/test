package web_automation.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.*;
//import utils.ConfigurationManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import web_automation.ui.PageFactory.CalculateRetirementSaving;

public class BaseTest {
	public static WebDriver driver;
	public static CalculateRetirementSaving objCalculate;

	// @BeforeSuite(alwaysRun=true)
	public WebDriver InitializeDriver() throws InterruptedException, FileNotFoundException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/testdata/GlobalData.properties");
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			// Invoke Firefox browser
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();

		return driver;
	}

	@BeforeMethod
	public CalculateRetirementSaving LaunchApplication() throws InterruptedException {
		try {
			driver = InitializeDriver();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		objCalculate = new CalculateRetirementSaving(driver);
		objCalculate.GoTo();
		return objCalculate;
	}

	@AfterMethod(alwaysRun = true)
	public void teardownManagers() {
		driver.quit();
	}

}
