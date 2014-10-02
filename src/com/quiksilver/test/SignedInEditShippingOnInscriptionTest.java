package com.quiksilver.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;
/**
 * @author igonzalez
 *2/26 Regression group
 *TC #9 in Regression xlsx
 */
//TC # 9 in Regression xls Logged In --> Edit shipping info on Inscription page
public class SignedInEditShippingOnInscriptionTest extends BaseSuite {


	@BeforeMethod
	public void getToEditShippingOnInscription() throws Exception
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
	public void editShippingOnInscription() throws Exception
	{
		String address1 = "101 Fluid Ave";
		String diffAddress = "101 Test Dr";

		//because signed in Inscription page is skipped
		//click on Inscription label to get to Inscription page +click radio btn for diff shipping+fill out form
		cm.step1DifferentShipping(driver, address1 );

		//click on 'Continue' btn	        
		cm.fromInscriptionToStep2Payment(driver);

		//go back to inscription and change shipping address
		cm.step1DifferentShipping(driver, diffAddress );

		//click on 'Continue' btn	        
		cm.fromInscriptionToStep2Payment(driver);
		
		//on Step2 billing select master card
		cm.selectPaymentOnStep2(driver, "master");

		//on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
		cm.verificationClickPlaceOrder(driver);

		//on Confirmation page 
		cm.submitConfirmation(driver);	        
	}


}
