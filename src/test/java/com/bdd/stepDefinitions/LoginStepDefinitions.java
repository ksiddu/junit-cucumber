package com.bdd.stepDefinitions;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Assert;

import com.bdd.utils.BrowserFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
//import org.testng.Assert;

public class LoginStepDefinitions {

	WebDriver driver;

	private byte[] screenShotFileName = (byte[]) null;
	private String ErrorMessage;

	@Before
	public void setupBrowser() {
		//driver = BrowserFactory.getDriver("chrome");
		driver = BrowserFactory.getDriver("firefox");
		System.out.println("============================================");
		System.out.println("Opening the Driver");
		System.out.println("============================================");
	}

	@Given("^I go to login page$")
	public void goToUrl() {
		String loginUrl = "http://localhost:4200/login";
		driver.get(loginUrl);
	}

	@When("^I enter valid login details$")
	public void enterLogin() {
		WebElement userName = driver.findElement(By.id("userId"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement button = driver.findElement(By.className("my-btn"));

		userName.sendKeys("admin");
		password.sendKeys("admin");
		button.click();
		System.out.println("Login details entered - loginApplication()");
		System.out.println("============================================");
	}

	@When("^I enter invalid login details$")
	public void enterInvalidLogin() {
		WebElement userName = driver.findElement(By.id("userId"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement button = driver.findElement(By.className("my-btn"));

		userName.sendKeys("admin");
		password.sendKeys("admins");
		button.click();
		System.out.println("Login details entered - loginApplication()");
		System.out.println("============================================");
	}

	@Then("^I verify the dashboard page$")
	public void verifyLogin() {
		String expectedText = "Login is successfull!!";

		WebElement loginText = driver.findElement(By.xpath("//p"));

		// Assert.assertTrue(loginText.getText().contains(expectedText), "Login
		// is failed");
		/*
		if (loginText.getText().contentEquals(expectedText)) {
			System.out.println("Login successful");
			System.out.println("MyApp Page title : " + driver.getTitle());
			System.out.println("============================================");
		} else {
			System.out.println("Login unsuccessful");
			System.out.println("============================================");
		}
		*/
		
		boolean flag = driver.getPageSource().contains(expectedText);
		Assert.assertTrue("Expected Text not present "+expectedText, flag);
	}

	@Then("^I verify the invalid login dashboard page$")
	public void verifyInValidLogin() {
		 String expectedText = "Login is failed!!";
		//String expectedText = "Login is success!";
		WebElement loginText = driver.findElement(By.xpath("//p"));
		
		// Assert.assertTrue(loginText.getText().contains(expectedText), "Login
		// is failed");
		/*
		if (loginText.getText().contentEquals(expectedText)) {
			System.out.println("Login un successful");
			System.out.println("MyApp Page title : " + driver.getTitle());
			System.out.println("============================================");
		} else {
			System.out.println("Login successful");
			System.out.println("============================================");
		}
		*/
		boolean flag = driver.getPageSource().contains(expectedText);
		Assert.assertTrue("Expected Text not present"+expectedText, flag);
	}

	@After
	public void closeBrowser(Scenario sc) {
		if (sc.isFailed()) {
			System.out.println("Taking screenshot & attaching it to the report");
			setScreenShot(sc);

		}
		driver.quit();
		System.out.println("============================================");
		System.out.println("Closing the Driver");
		System.out.println("============================================");
	}

	public byte[] getScreenShot(Scenario sc) {
		screenShotFileName = takeScreenShotAndReturnFileName(sc.getName());

		return screenShotFileName;
	}

	public void setScreenShot(Scenario sc) {
		getScreenShot(sc);
		sc.embed(screenShotFileName, "image/png");
	}

	public String getError() {

		return this.ErrorMessage;

	}

	public void setError(Scenario sc, String ErrorMessage) {

		this.ErrorMessage = ErrorMessage;
		sc.write(ErrorMessage);

	}

	public byte[] takeScreenShotAndReturnFileName(String Screenshotname) {
		String screenShotFileName = null;
		byte[] screenshotBytes = null;
		if (!(this.driver instanceof HtmlUnitDriver)) {
			screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			screenShotFileName = Screenshotname + System.currentTimeMillis() + ".png";
			String screenShotPath = "screenshots" + File.separator + screenShotFileName;
			// String
			// screenShotPath="build"+File.separator+"screenshots"+File.separator+CucumberHooks.getSceanrio().getName().replace("
			// ", "_")+File.separator+File.separator+Screenshotname+".png";
			try {
				FileUtils.writeByteArrayToFile(new File(screenShotPath), screenshotBytes);
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		return screenshotBytes;
	}
}
