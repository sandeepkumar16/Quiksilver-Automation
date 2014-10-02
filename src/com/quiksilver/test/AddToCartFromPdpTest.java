package com.quiksilver.test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;


//TC # 7 Add to Cart from PDP
public class AddToCartFromPdpTest extends BaseSuite {

	@Test
	public void fromHomeToPDP() throws Exception
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
			cm.login(driver, "software_test22@hotmail.com", "fluid");
	
			//pass locator for subcat you want to the method (here it is t-shirts)
			By locator_tshirtLink=map.getLocator("men_tshirtcss");
			cm.homePageMainNavMen(driver, locator_tshirtLink);
			 
		    
			//on subcat page click on product - pass driver and locator for the product you want to click on		
			Reporter.log("On Subcat page title is "+ driver.getTitle());
	
			//2/24 using new CommonMethod, 1 will click on 2nd product on subcat page
			cm.subcatPageClickProduct(driver, 2);		
	
			//on PDP add to cart
			Reporter.log("On PDP page title is "+ driver.getTitle());
			cm.pdpPageSelectAddToCart(driver);
	
			ts.takeScreenshot(driver);
			return;
		}
		
		By locator_tshirtLink=map.getLocator("mens_Tshirt_xpath_US");
		cm.homePageMainNavMen(driver, locator_tshirtLink);
		
		Reporter.log("On Subcat page title is "+ driver.getTitle());
		
		//2/24 using new CommonMethod, '1' means it will click on 2nd product on subcat page
		cm.subcatPageClickProduct(driver,3);	
		
		//on PDP click on save for later and assert 'saved' msg displayed on the screen
		Reporter.log("On PDP page title is "+ driver.getTitle());
		cm.pdpPageSelectAddToCart(driver);
		
		ts.takeScreenshot(driver);
		

	}

}