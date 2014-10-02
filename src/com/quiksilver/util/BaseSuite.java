package com.quiksilver.util;

import java.awt.Point;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;



public class BaseSuite {
	
    public WebDriver driver;
	public WebDriverWait  wait;

	public WebDriverWait shortWait;
	public Actions action;

   // public Logger log = WebDriverManager.LoggerGetInstance();
    public ReadingProperties rp = new ReadingProperties();
    public  ObjectMap map = new ObjectMap();
    public CommonMethods cm =new CommonMethods();
    public  TakeScreenshot ts= new TakeScreenshot();
    
  public String stagingQuik = rp.readConfigProperties("staging");
  public String productionQuik = rp.readConfigProperties("production");
  public  String stagingDC = rp.readConfigProperties("staging_dcshoes");   //dc shoes uk site in staging
  public String productionDC = rp.readConfigProperties("production_dcshoes");  
  
  public String stagingDC_US=rp.readConfigProperties("staging_dcshoesUS");  //dc shoes us site in staging


  public String testEmail = rp.readConfigProperties("yahoo_random");
  public String testPassword = rp.readConfigProperties("password_random");
  
  public String testEmailWithSavedCards = rp.readConfigProperties("hotmail");
  public String pswdForTestEmailWithSavedCards = rp.readConfigProperties("password_hotmail");

	 @Parameters("browser")
     @BeforeClass	 
     public void beforeTest(@Optional("chrome") String browser){
		 
		 if (browser.equalsIgnoreCase("firefox")) 

			{       
				driver=WebDriverManager.startDriver("firefox");
				wait=new WebDriverWait(driver,20);
				shortWait= new WebDriverWait(driver,6);

			}

		 if (browser.equalsIgnoreCase("chrome")) 
			
			{
				driver=WebDriverManager.startDriver("chrome");
				wait=new WebDriverWait(driver,20);
				shortWait= new WebDriverWait(driver,6);
				

			} 

			 if (browser.equalsIgnoreCase("safari")) 				
			{
				driver=WebDriverManager.startDriver("safari");
			    wait=new WebDriverWait(driver,20);
				shortWait= new WebDriverWait(driver,6);
			} 
	 }
	 
	 @Parameters("url")
	 @BeforeMethod
	 public void getToHome(@Optional("stagingDC")String url)
	 {
		 if (url.equalsIgnoreCase("stagingQuik"))
		 {
			 driver.get(stagingQuik);
		 }
		 else  if (url.equalsIgnoreCase("productionQuik"))
		 {
			 driver.get(productionQuik);
		 }
		 
		 else  if (url.equalsIgnoreCase("stagingDC"))
		 {
			 driver.get(stagingDC);
		 }
		 else  if (url.equalsIgnoreCase("productionDC"))
		 {
			 driver.get(productionDC);
		 }
		 else if (url.equalsIgnoreCase("stagingDC_US"))
		 {
			 driver.get(stagingDC_US);

		 }
	 }
	 @AfterClass(alwaysRun=true)
	 public void tearDown()
	 {
		 driver.quit();
	 }
}
