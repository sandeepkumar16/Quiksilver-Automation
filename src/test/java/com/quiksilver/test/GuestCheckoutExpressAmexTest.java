package com.quiksilver.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;
import com.quiksilver.util.WebDriverManager;

//TC#15 Guest Express Checkout in Smoke Test spreadsheet- test card not specified in TC should test Amex or Master

public class GuestCheckoutExpressAmexTest extends BaseSuite {
	private WebDriverManager wm=new WebDriverManager();	
	@BeforeMethod
	public void getToStep1GuestCheckout() throws Exception
	{
		cm.openHomePage(driver);
		Boolean isLoggedIn= cm.isLoggedIn(driver);

		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}
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
		
		//click on unregistered checkout btn
		By locator_unregisteredcheckoutbtn=map.getLocator("interstitial_unregisteredcheckoutbtn");
		cm.checkoutSignInClickElement(driver, locator_unregisteredcheckoutbtn);
		return;
		}
		
		//US Site functionality
		By locator_tshirtLink=map.getLocator("mens_Tshirt_xpath_US");
		cm.homePageMainNavMen(driver, locator_tshirtLink);
		
	
		By locator_subcatProduct = map.getLocator("subcatproductUS");
		cm.subcatPageHoverOnProductClickExpressLink(driver,locator_subcatProduct);
		
		cm.subcatQuickviewAddtoCart(driver,"S");
		//cm.pdpPageSelectAddToCart(driver,"S");
		cm.fromMiniCartToCart(driver);

		//on Cart page click on Secure checkout
		////ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);
	}
	
	@Test
	public void testGuestCheckoutAmex() throws Exception
	{
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
	        cm.step1Fields(driver);
			//this is optional if the num of test is large just remove taking screenshot below
	        //ts.takeScreenshot(driver);

	        cm.fromInscriptionToStep2Payment(driver);
	        Reporter.log("After click on 'continue' button on Inscription page got to "+ driver.getTitle());
	        
	        //2/10 TEMP SOLUTION TESTING VISA WAITIN FOR AMEX CARD number 	       
	        cm.selectPaymentOnStep2(driver,  "visa");
	        
	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	        cm.verificationClickPlaceOrder(driver);
	        
	        //on Confirmation page 
	        cm.submitConfirmation(driver);	 
	        return;
		}
		
		//US site functionality
		cm.fromCartToGuestCheckout(driver);
		 cm.step1FieldsUS(driver);
		 cm.fromInscriptionToStep2Payment(driver);
	        Reporter.log("After click on 'continue' button on Inscription page got to "+ driver.getTitle());

	        cm.selectPaymentOnStep2US(driver,"visa");

	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	       cm.verificationClickPlaceOrder(driver);

	        //on Confirmation page 
	        cm.submitConfirmation(driver);
	        
	        String m=	wm.getSessionId();
			Reporter.log(m);

	        
	}

}
