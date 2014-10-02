package com.quiksilver.test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;

/**
 * @author igonzalez
 *Regression group: TC #13 in Regression xlsx 
 *Logged in --> Add new credit card to Billing Profile - visa & Save this card on user account 
 *
 *this TC requires:
 *-home page>>create new account>>add item to cart>>checkout>>billing page do NOT use uncheckSaveCard()
 *2/27 - added @DataProvider; once have Amex info>> this class can be used to test saving different payment types
 */


public class SignedInAddCardToProfileWithDataProviderTest extends BaseSuite {
	
	@DataProvider(name = "multicard")
	public Object[][] createData1() {
		return new Object[][] {

				{ "visa", "Visa"},
				/*
			    { "amex", "Amex"},
			    { "master", "Master"}
				*/
		};
	}

	@BeforeMethod
	public void getToVerifySavedCardCheckout() throws Exception
	{
		cm.openHomePage(driver);
		Boolean isLoggedIn= cm.isLoggedIn(driver);

		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}
		
		//create new test account
		cm.createTestAccount(driver);	
				
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
	}
	
	@Test (dataProvider="multicard")
	public void testSignedInAddCardToProfile(String card, String expected) throws Exception
	{
		//because signed in Inscription page is skipped

	        //on Step2 billing select master card
	        cm.selectPaymentOnStep2(driver, card);
	        
	        //go back to Billing page and check 'save this card'
	        WebDriverWait wait = new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("inscription_label")));
			
			//need to click on Billing label 
			driver.findElement(map.getLocator("billing_label")).click();		
			wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("billing_ccsavecardid")));
			Boolean isSaveCardChecked=driver.findElement(map.getLocator("billing_ccsavecardid")).isSelected();

			if (isSaveCardChecked ==false)
			{
				driver.findElement(map.getLocator("billing_ccsavecardid")).click();

			}
			
			//take screenshot on Payment page to capture selected card type
			//ts.takeScreenshot(driver);
			//click on Continue btn on Payment page
			cm.fromStep2PaymentToStep3(driver);
			
	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	        cm.verificationClickPlaceOrder(driver);
	        
	        //on Confirmation page 
	        cm.submitConfirmation(driver);	
	        
	        //click on Continue shopping btn on Cofirmation page
	        cm.fromConfirmationToContinueShopping(driver);
	        
	        //click on My Account==click on 'Welcome msg'
			wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("login_welcome")));
	        driver.findElement(map.getLocator("login_welcome")).click();
	        
	        //click on Payment Settings 
			wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("login_paymentsettings")));
	        driver.findElement(map.getLocator("login_paymentsettings")).click();
	        
	        //verify  card info  is displayed 
	       String cardInfo= driver.findElement(map.getLocator("login_firstsavedcard")).getText();
	       
	      int isSaved= cardInfo.compareToIgnoreCase(expected);
	       //because selected Master card on Payment screen
	       Assert.assertTrue(isSaved==0, "expected isSaved=0 and but was isSaved="+isSaved);
	       
	       //take screenshot on My Account>>Payment Setting page to verify saved card
	       //ts.takeScreenshot(driver);

	}
	

}
