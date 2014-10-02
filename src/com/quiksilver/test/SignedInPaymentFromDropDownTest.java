package com.quiksilver.test;


import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.seleniumhq.jetty7.util.log.Log;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;

/**
 * @author igonzalez
 *2/26 Regression group
 *TC #12 in Regression xlsx
 *because signed in+have saved cards: Inscription page and Billing page are skipped
	- click on Billing label to get to Billing page 
	-use drop down to select credit card
	- type in security code in the input field (does not get auto populated)
 */
// TC#12 Logged --> Edit payment info from Billing page

public class SignedInPaymentFromDropDownTest extends BaseSuite{
	
	@BeforeMethod
	public void getToPaymentDropDownOnStep2() throws Exception
	{
		cm.openHomePage(driver);
		Boolean isLoggedIn= cm.isLoggedIn(driver);

		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}
		cm.login(driver, testEmailWithSavedCards, pswdForTestEmailWithSavedCards);
		System.out.println("Using email "+testEmailWithSavedCards+" password "+pswdForTestEmailWithSavedCards);

		By locator_tshirtLink=map.getLocator("men_tshirtcss");
		cm.homePageMainNavMen(driver, locator_tshirtLink);
		
		//on subcat page click on product - pass driver and locator for the product you want to click on
		Reporter.log("On Subcat page title is "+ driver.getTitle());
		ts.takeScreenshot(driver);
		
		By locator_subcatProduct = map.getLocator("subcat_product");
		cm.subcatPageHoverOnProductClickExpressLink(driver,locator_subcatProduct);

		cm.fromMiniCartToCart(driver);
		
		//on Cart page click on Secure checkout
		ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);		

	}
	
	@Test()
	public void selectPaymentFromDropDownOnPaymentPage() throws Exception
	{
		String cardSecurity=rp.readConfigProperties("cardPin");

		//because signed in+have saved cards: Inscription page and Billing page are skipped
		//click on Billing label to get to Billing page 
		
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("inscription_label")));
		
		//need to click on Billing label ??
		driver.findElement(map.getLocator("billing_label")).click();		
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("billing_selectcardddropdown")));

		//uncheck save card 
		cm.uncheckSaveCard(driver);
		
		//click on last option from select Credit Card drop down 
		By locator_shippingSelect=map.getLocator("billing_selectcardddropdown");		
		cm.selectLastOptionFromSelect(driver, locator_shippingSelect);		
		
		//type in the Security code for credit card which does not auto populate
		WebElement cardCode=driver.findElement(map.getLocator("billing_ccsecuritycodeid"));
		cardCode.sendKeys(cardSecurity);
		
		//wait for field to auto populate+click on continue btn
            cm.fromStep2PaymentToStep3(driver);

	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	        cm.verificationClickPlaceOrder(driver);
	        
	        //on Confirmation page 
	        cm.submitConfirmation(driver);	        
	}

}
