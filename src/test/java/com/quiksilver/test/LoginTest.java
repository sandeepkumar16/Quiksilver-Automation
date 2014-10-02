package com.quiksilver.test;


import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;
import com.quiksilver.util.CommonMethods;
import com.quiksilver.util.ObjectMap;
import com.quiksilver.util.ReadingProperties;
import com.quiksilver.util.WebDriverManager;


//TC#1 Login with DataProvider
public class LoginTest extends BaseSuite {		
	private WebDriverManager wm=new WebDriverManager();
	@DataProvider(name = "registeredCheckout")
	public Object[][] createData1() {
		return new Object[][] {

				//valid email+pswd
				{ "software_test22@hotmail.com", "fluid", "Welcome,Veronica"},
				{ "software_test22@yahoo.com", "fluid2", "Welcome,Veronica"},

		};
	}

	//this is positive testing all the data is valid
	@Test (dataProvider="registeredCheckout")
	public void checkoutSignInRegistered(String email, String password, String expected) throws Exception
	{
		cm.openHomePage(driver);
		Boolean isLoggedIn= cm.isLoggedIn(driver);
		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}

		cm.login(driver, email, password);	

	//check if Welcome msg displayed
	String welcome = driver.findElement(map.getLocator("login_welcome")).getText();
	//Assert.assertTrue(welcome.contains(expected), "expected :" +expected +"but message was "+welcome);
Reporter.log("Welcome message is : "+welcome);
	//logout to clean up
		cm.logout(driver);
		//to make sure that logged out and got back to interst sign in page
		driver.findElement(map.getLocator("create_accountbtn"));
		String m=	wm.getSessionId();
		Reporter.log(m);
	}


}
