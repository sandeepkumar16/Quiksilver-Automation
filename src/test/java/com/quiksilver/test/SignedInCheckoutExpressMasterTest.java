package com.quiksilver.test;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
//import org.seleniumhq.jetty7.util.log.Log;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;

//TC#14 in Smoke Test spreadsheet

/**
 * @author igonzalez
 * TC #14 in Smoke Test
 * 2/27 added condition to extend for Regression TC #14
 */
public class SignedInCheckoutExpressMasterTest extends BaseSuite {
	
	public  String testEmail = rp.readConfigProperties("hotmail");
	  public String testPassword = rp.readConfigProperties("password_hotmail");  
	  public String master=rp.readConfigProperties("master_nosecurecode");
	  public String master_pass=rp.readConfigProperties("master_pass");

	@BeforeMethod
	public void getToSubcatExpressCheckout() throws Exception
	{
		cm.openHomePage(driver);
		Boolean isLoggedIn= cm.isLoggedIn(driver);

		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}
		
		
		//login using email and password read from config.properties
		System.out.println("Using email "+testEmail+" password "+testPassword);
		cm.login(driver, testEmail, testPassword);
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{		
		By locator_tshirtLink=map.getLocator("men_tshirtcss");
		
		cm.homePageMainNavMen(driver, locator_tshirtLink);
		
		//on subcat page click on product - pass driver and locator for the product you want to click on
		Reporter.log("On Subcat page title is "+ driver.getTitle());
		//ts.takeScreenshot(driver);
		
		By locator_subcatProduct = map.getLocator("subcat_product");
		cm.subcatPageHoverOnProductClickExpressLink(driver,locator_subcatProduct);

		cm.fromMiniCartToCart(driver);
		
		//on Cart page click on Secure checkout
		//ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);
		return;
		}
		//US Site functionality
		cm.clearCart(driver);
		
				By locator_tshirtLink=map.getLocator("mens_Tshirt_xpath_US");
				cm.homePageMainNavMen(driver, locator_tshirtLink);
				
			
				By locator_subcatProduct = map.getLocator("subcatproductUS");
				cm.subcatPageHoverOnProductClickExpressLink(driver,locator_subcatProduct);
				
				cm.subcatQuickviewAddtoCart(driver,"S");
				cm.fromMiniCartToCart(driver);

				//on Cart page click on Secure checkout
				////ts.takeScreenshot(driver);
				cm.fromCartToSignIn(driver);
				
				
	}
	
	@SuppressWarnings("static-access")
	@Test 
	public void testSignedInCheckoutMaster() throws Exception
	{
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{	
		WebElement masterCardRadio=driver.findElement(map.getLocator("loggedin_master_id"));
		masterCardRadio.click();
		Boolean isCVNFieldAvailable=cm.isElementPresent(driver, (map.getLocator("loggedin_master_pin_xpath")));
	        if (isCVNFieldAvailable==true)
	        {
	        	WebElement CVN=driver.findElement(map.getLocator("loggedin_master_pin_xpath"));
	        	CVN.sendKeys("700");
	        }
	        else
	        {
	        	wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("loggedin_master_pin_xpath")));
	        }
	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	        cm.verificationClickPlaceOrder(driver);
	        
	        //on Confirmation page 
	        cm.submitConfirmation(driver);
	        
	        //click on My Account and verify master card NOT added to the account
	      //click on Continue shopping btn on Cofirmation page
	        cm.fromConfirmationToContinueShopping(driver);
	        
	        //click on My Account==click on 'Welcome msg'
			wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("login_welcome")));
	        driver.findElement(map.getLocator("login_welcome")).click();
	        
	        //click on Payment Settings 
			wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("login_paymentsettings")));
	        driver.findElement(map.getLocator("login_paymentsettings")).click();
	        
	       
	       //ts.takeScreenshot(driver);
	       return;
		}
//US Site
		WebElement masterCardRadio=driver.findElement(map.getLocator("billing_masterid_US"));
		masterCardRadio.click();
		WebElement billing_name=driver.findElement(map.getLocator("billing_nameid"));
		billing_name.sendKeys("veronica peter");

		try
		{   //uncheck save card
			cm.uncheckSaveCard(driver);
		}
					
		catch(Exception e)
		{				
			System.out.println("GUEST CHECKOUT:'Save card' checkbox is not present; ");
		}
		
		WebElement cardNumber=driver.findElement(map.getLocator("billing_ccnumberid"));
		
		
		cardNumber.sendKeys(master);

		WebElement cardCode= driver.findElement(map.getLocator("billing_ccsecuritycodeid"));
		cardCode.sendKeys(master_pass);

		//select month #5
	
		new Select(driver.findElement(map.getLocator("billing_ccmonthid"))).selectByVisibleText("05");
		//change year
		new Select(driver.findElement(map.getLocator("billing_ccyearid"))).selectByVisibleText("2020");
		
		//     WebElement continuebtn=
		driver.findElement(map.getLocator("billing_continuebtn")).click();
		
		
	}

}
