package com.quiksilver.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;
import com.quiksilver.util.CommonMethods;
import com.quiksilver.util.ObjectMap;
import com.quiksilver.util.WebDriverManager;


//TC #2 Create Account 
public class AccountRegistrationTest extends BaseSuite{
private WebDriverManager wm=new WebDriverManager();
	@Test 
	public void registrationForm() throws Exception
	{
		cm.openHomePage(driver);

		Boolean isLoggedIn= cm.isLoggedIn(driver);

		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}

		cm.createTestAccount(driver);		
		//or second option 
		//cm.createTestAccount(driver, "your email", "password");

		isLoggedIn= cm.isLoggedIn(driver);
		if (isLoggedIn==true)
		{
			//clean up logout
			cm.logout(driver);
		}
		String m=	wm.getSessionId();
		Reporter.log(m);
	}
	

	

}