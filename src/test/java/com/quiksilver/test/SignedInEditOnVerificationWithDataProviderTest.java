package com.quiksilver.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;

public class SignedInEditOnVerificationWithDataProviderTest extends BaseSuite {
	
	@DataProvider(name = "changeOnVerification")
	public Object[][] createData1() {
		return new Object[][] {

				//String requestedChange, String expected?
				{ "CHANGE SHIPPING"},
				{ "CHANGE PAYMENT"},
				
		};
	}
	@BeforeMethod
	public void getToEditShippingOnVerification() throws Exception
	{
		cm.openHomePage(driver);
		Reporter.log("Regression: TC#16,17: Edit Shipping/Payment on Verification page.");
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
	
	@Test(dataProvider="changeOnVerification")
	public void testEditOnVerification(String requestedChange) throws Exception
	{
		//because signed in Inscription page is skipped

        //on Step2 billing select master card
        cm.selectPaymentOnStep2(driver, "master");
        
        //on Verification  page
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(map.getLocator("verification_termsconditionsid")));
		
		//before change
		//ts.takeScreenshot(driver);
		
		if (requestedChange.equalsIgnoreCase("change shipping"))
		{
			//Shipping method>>click on Modify btn>>on Inscription change billing address>>click on Continue			
			//driver.findElement(By.xpath("(//a[contains(.,'Modify / Add')])[2]")).click();
			
			driver.findElement(map.getLocator("verification_modifyshipping")).click();
			cm.step1Fields(driver);			
			//click on Continue>>redirect to Verification 
			cm.fromInscriptionToStep2Payment(driver);
			
		}
		
		else if (requestedChange.equalsIgnoreCase("change payment"))
		{
			//Payment method>>click on Modify btn>> on Step2 Payment change payment type>>click on Continue
			//driver.findElement(By.xpath("(//a[contains(.,'Modify / Add')])[3]")).click();
			
			driver.findElement(map.getLocator("verificaton_modifypayment")).click();            
			cm.selectPaymentOnStep2(driver, "visa");
			 
		}
		
		else 
		{
			//can provide default behavior
		}
		
		WebElement verification=driver.findElement(map.getLocator("verification_termsconditionsid"));
		verification.click();
		System.out.println("terms and conditions on Verification page checked "+verification.isSelected());
		
		//after requested change
		//ts.takeScreenshot(driver);
		Assert.assertEquals((verification.isSelected()), true);

		//click on Place Order btn
		cm.fromStep3VerificationToConfirmation(driver);
		
		//verfiy Confirmation page
		cm.submitConfirmation(driver);
	
	}

}
