package com.javascreenshot;

import java.io.File;
import java.io.IOException;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;

public class TakesScreenShot {


	public static String getScreenshot(WebDriver driver, String screenShotName) {
		TakesScreenShot ts = (TakesScreenShot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/Screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
		}
		return path;

	}



	private File getScreenshotAs(OutputType<File> file) {
		
		return null;
	}
}








