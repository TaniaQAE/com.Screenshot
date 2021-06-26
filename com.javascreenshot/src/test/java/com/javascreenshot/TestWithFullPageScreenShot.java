package com.javascreenshot;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class TestWithFullPageScreenShot {
	ExtentReports extent;
	ExtentTest logger;
	WebDriver driver;
	ExtentHtmlReporter reporter;

	@BeforeMethod
	public void openApplication() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.nobullproject.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(priority = 1)
	public void urlTest() {
		logger = extent.createTest("urlTest");
		System.out.println(driver.getCurrentUrl().contains("salesforce"));

		logger.createNode("Testing url nagative test");
		Assert.assertNotEquals(driver.getCurrentUrl(), "NOBUL");
	}

	@Test(priority = 2)
	public void titleTest() {
		logger = extent.createTest("titleTest");
		System.out.println(driver.getTitle().contains("salesforce"));
	}

	@Test(priority = 3, enabled = true)
	public void linkTest() {
		logger = extent.createTest("linkTest");
		Assert.assertTrue(driver.getTitle().contains("Workout"));
	}

	@Test(priority = 4, enabled = true)
	public void skipTest() {
		logger = extent.createTest("skipTest");
		throw new SkipException(" We will build it latter. ");
	}

	@AfterMethod
	public void closeApplication(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {

			logger.log(Status.FAIL, result.getName() + " TEST CASE ERROR IS " + result.getThrowable().getMessage());
			String screenShotPath = TestWithFullPageScreenShot.captureFullPge(driver, result.getName());
			logger.addScreenCaptureFromPath(screenShotPath);

		} else if (result.getStatus() == ITestResult.SKIP) {

			logger.log(Status.SKIP, result.getName() + " TEST CASE ERROR IS " + result.getThrowable().getMessage());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.getStatus();
			logger.log(Status.PASS, " TEST CASE PASS IS " + result.getName());
		}
		driver.quit();
	}

	@AfterTest
	public void tearDown() {
		extent.flush();

	}

	public static String captureFullPge(WebDriver driver, String fullPage) throws IOException {

		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver);
		String dest = System.getProperty("user.dir") + "/Screenshot/" + fullPage + System.currentTimeMillis() + ".png";
		ImageIO.write(screenshot.getImage(), "PNG", new File(dest));

		return dest;

	}

}