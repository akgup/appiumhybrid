package com.appium.parallel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import junit.framework.Assert;

public class ParallelExecution {

	AndroidDriver<MobileElement> driver;

	@BeforeClass
	@Parameters({ "port", "device", "version", "platform" })
	public void setUp(String port, String device, String version, String platform) throws MalformedURLException {

		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("automationName", "Appium");
		capabilities.setCapability("platformName", platform);
		capabilities.setCapability("platformVersion", version);
		capabilities.setCapability("deviceName", device);
		capabilities.setCapability("udid", device);
		capabilities.setCapability("appPackage", "com.makemytrip");
		capabilities.setCapability("appActivity", "com.mmt.travel.app.home.ui.HomeActivity");
		try {

			URL url = new URL("http://127.0.0.1:" + port + "/wd/hub");
			driver = new AndroidDriver<MobileElement>(url, capabilities);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			 Thread.sleep(2000);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void verifyMandatoryLoginFields() throws InterruptedException {

		// click on menu
		driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigation Drawer Open']")).click();
		// click on login menu
		driver.findElement(By.id("com.makemytrip:id/email")).click();
		// click on login button
		driver.findElement(By.id("com.makemytrip:id/btn_login_mmt_account")).click();
		// click on signin
		driver.findElement(By.id("com.makemytrip:id/btn_signin")).click();

		//Waiting for all the elements to load
		Thread.sleep(3000);

		// get the list of the web element
		List<MobileElement> allError = driver.findElements(By.xpath("//android.widget.TextView[@index='1']"));

		Assert.assertEquals("Please enter Email Id", allError.get(1).getText());
		Assert.assertEquals("Please enter Password", allError.get(2).getText());

		  driver.pressKeyCode(AndroidKeyCode.BACK);
		  driver.pressKeyCode(AndroidKeyCode.BACK);
	}

	@Test
	public void verifyMandatorySignUpPageFields() throws InterruptedException {

		// click on menu
		driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigation Drawer Open']")).click();
		// click on login menu
		driver.findElement(By.id("com.makemytrip:id/email")).click();
		// click on signup button
		driver.findElement(By.id("com.makemytrip:id/btn_signup_mmt_account")).click();
		// click on signup button wihtout fill details
		driver.findElement(By.id("com.makemytrip:id/btn_signup")).click();

		// hide keyboard to locate all the errors
		driver.hideKeyboard();
		
		//Waiting for all the elements to load
		Thread.sleep(3000);

		// get the list of the web element
		List<MobileElement> allError = driver.findElements(By.className("android.widget.TextView"));

		Assert.assertEquals("Please enter First name", allError.get(2).getText());
		Assert.assertEquals("Please enter Last name", allError.get(3).getText());
		Assert.assertEquals("Please enter mobile number", allError.get(4).getText());
		Assert.assertEquals("Please enter Email Id", allError.get(5).getText());
		Assert.assertEquals("Please enter Password", allError.get(6).getText());
		
		  driver.pressKeyCode(AndroidKeyCode.BACK);
		  driver.pressKeyCode(AndroidKeyCode.BACK);		 

	}

	@AfterClass
	public void tearDown() throws InterruptedException {

		driver.quit();
	}

}
