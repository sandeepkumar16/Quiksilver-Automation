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
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;

//TC # 9 Browse from top level-subcategory-category navigation-express shop add to cart-Continue Shoping 
//Add other item to cart-View cart and checkout

/**
 * @author igonzalez
 *Smoke tests group
 */
@Listeners({ com.quiksilver.util.TestListenerFailPass.class })

public class SignedInAddToCartContinueShoppingCheckoutTest extends BaseSuite {
	public  String testEmail = rp.readConfigProperties("yahoo");
	  public String testPassword = rp.readConfigProperties("password_yahoo");  
	  public String master=rp.readConfigProperties("master_nosecurecode");
	  public String master_pass=rp.readConfigProperties("master_pass");
	  

	@BeforeMethod
	public void addToCartContinueShoppingPDP() throws Exception
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
		ts.takeScreenshot(driver);
		
		By locator_subcatProduct = map.getLocator("subcat_product");
		cm.subcatPageHoverOnProductClickExpressLink(driver,locator_subcatProduct);

		cm.fromMiniCartToCart(driver);
		
		//click on continue shopping
		cm.fromCartToContinueShopping(driver);
		
		//main nav jeans
		//driver.findElement(By.cssSelector("a.omni_header_flyout_link.men_clothing_jeans-denim")).click();
		By locator_jeansLink=map.getLocator("men_jeanscss");
		cm.homePageMainNavMen(driver, locator_jeansLink);
		
        //click on jeans product; locator is the same for a diff subcat 
		//cm.subcatPageClickProduct(driver, locator_subcatProduct);
		
		//2/21 click on 8th thumbnail 
		cm.subcatPageClickProduct(driver,7);
		
		//on PDP select Size
		
		//on PDP select Length
		
		//on PDP click on ADD TO CART btn
		cm.pdpPageSelectAddToCart(driver);
		//click on Checkout btn in Mini-cart
		cm.fromMiniCartToCart(driver);
		
		//on Cart page click on Secure checkout
		ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);
		return;
		}
		
		//US Site functionality
		By locator_tshirtLink=map.getLocator("mens_Tshirt_xpath_US");
		cm.homePageMainNavMen(driver, locator_tshirtLink);
		
cm.subcatPageClickProduct(driver, 4);	
		
		//on PDP click on save for later and assert 'saved' msg displayed on the screen
		Reporter.log("On PDP page title is "+ driver.getTitle());
		cm.pdpPageSelectAddToCart(driver);
		
		cm.fromMiniCartToCart(driver);

		//on Cart page click on Secure checkout
		//ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);
	}
	
	@Test
	public void testSignedInAddToCartContinueShopping() throws Exception
	{
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
	        cm.selectPaymentOnStep2(driver, "master");
	        
	      
	        cm.verificationClickPlaceOrder(driver);
	        
	      //on Confirmation page 
	        cm.submitConfirmation(driver);	
	        
	        //recommended products
	        return;
		}
		
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
