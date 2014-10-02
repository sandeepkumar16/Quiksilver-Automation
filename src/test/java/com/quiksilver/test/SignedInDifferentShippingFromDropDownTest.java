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
 *TC #10 in Regression xlsx
 */
//TC # 10 in Regression xls Logged-in --> selected different shipping address from address book dropdown          
public class SignedInDifferentShippingFromDropDownTest extends BaseSuite {
	
	
	@BeforeMethod
	public void logInGetToInscription() throws Exception
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
		//ts.takeScreenshot(driver);
		
		By locator_subcatProduct = map.getLocator("subcat_product");
		cm.subcatPageHoverOnProductClickExpressLink(driver,locator_subcatProduct);

		cm.fromMiniCartToCart(driver);
		
		//on Cart page click on Secure checkout
		//ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);		
		
	}
	
	@Test()
	public void selectShippingOnInscriptionFromDropDown() throws Exception
	{
		//because signed in Inscription page is skipped
		//click on Inscription label to get to Inscription page +click radio btn for diff shipping+fclick on last option in drop down
		//click on Inscription label to get to Inscription page
		
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("inscription_label")));
		
		driver.findElement(map.getLocator("inscription_label")).click();
		
		//click on radio btn for diff shipping address
		driver.findElement(map.getLocator("inscription_differentshippingid")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("inscription_shippingtitleid")));

		//click on last option from select 
		By locator_shippingSelect=map.getLocator("inscription_selectboxdifferentshipping");		
		cm.selectLastOptionFromSelect(driver, locator_shippingSelect);
		
		
		//wait for field to auto populate+click on continue btn
            cm.fromInscriptionToStep2Payment(driver);

	        //on Step2 billing select master card
	        cm.selectPaymentOnStep2(driver, "master");
	        
	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	        cm.verificationClickPlaceOrder(driver);
	        
	        //on Confirmation page 
	        cm.submitConfirmation(driver);	        
	}


}
