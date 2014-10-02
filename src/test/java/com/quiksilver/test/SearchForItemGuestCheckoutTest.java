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

//TC#16 Search for item Guest Checkout
public class SearchForItemGuestCheckoutTest extends BaseSuite {
	private WebDriverManager wm=new WebDriverManager();
	@BeforeMethod
	public void searchForItemGetToCheckout() throws Exception
	{
		cm.openHomePage(driver);
		Boolean isLoggedIn= cm.isLoggedIn(driver);

		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}

		cm.searchByItemName(driver);

		//By locator_searchboardshorts=map.getLocator("subcat_searchitem");
		//cm.subcatPageClickProduct(driver, locator_searchboardshorts);

		//3/17 click on 4th item on subcat page
		cm.subcatPageClickProduct(driver, 9);

		//on PDP add to cart
		Reporter.log("On PDP page title is "+ driver.getTitle());
		//ts.takeScreenshot(driver);
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
		cm.pdpPageSelectAddToCartNumSizes(driver);
		cm.fromMiniCartToCart(driver);

		//on Cart page click on Secure checkout
		//ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);

		//click on unregistered checkout btn
		By locator_unregisteredcheckoutbtn=map.getLocator("interstitial_unregisteredcheckoutbtn");
		cm.checkoutSignInClickElement(driver, locator_unregisteredcheckoutbtn);
		return;
		}
		//US site functionality
		cm.pdpPageSelectAddToCart(driver,"S");
		cm.fromMiniCartToCart(driver);

		//on Cart page click on Secure checkout
		//ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);

		//click on unregistered checkout btn
		By locator_unregisteredcheckoutbtn=map.getLocator("interstitial_unregisteredcheckoutbtn");
		cm.checkoutSignInClickElement(driver, locator_unregisteredcheckoutbtn);

	}

	@Test
	public void searchForItemGuestCheckout() throws Exception
	{
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
		   cm.step1Fields(driver);	                	      

	        cm.fromInscriptionToStep2Payment(driver);
	        Reporter.log("After click on 'continue' button on Inscription page got to "+ driver.getTitle());
	        //might use to getText() on 

	        //on Step2 billing select visa
	        cm.selectPaymentOnStep2(driver,  "visa");

	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	        cm.verificationClickPlaceOrder(driver);	        

	        //on Confirmation page 
	        cm.submitConfirmation(driver);	
	        return;
		}

		//US site functionality
		
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
