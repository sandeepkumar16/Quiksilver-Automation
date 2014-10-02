package com.quiksilver.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.ErrorHandler.UnknownServerException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.Reporter;




public class CommonMethods {
	private ObjectMap map= new ObjectMap();
	ReadingProperties rp = new ReadingProperties();
	Logger log = WebDriverManager.LoggerGetInstance();
	
    String submittedOrdersPath=rp.readConfigProperties("submittedorders.path");
    String createdAccountsPath = rp.readConfigProperties("createdaccounts.path");
    
    public String stagingQuik = rp.readConfigProperties("staging");
    public String productionQuik = rp.readConfigProperties("production");
    public  String stagingDC = rp.readConfigProperties("staging_dcshoes");
    public String productionDC = rp.readConfigProperties("production_dcshoes");  



	//ATT: this waits need to be instantiated in the methods below 
	WebDriverWait shortWait;
	WebDriverWait wait;
	//-----------------------

	Actions action;
	TakeScreenshot ts=new TakeScreenshot();
	Workbook workbook;

	FileWriter fw;
	BufferedWriter bw;
	PrintWriter pw ;


	/*****************************************************START OF ALL COMMON METHODS *****************************/



	//example: type(driver, By.name("txtUserName"), username);

	public static void type (WebDriver driver, By locator, String text) {


		WebElement element = driver.findElement(locator);
		element.clear();
		element.sendKeys(text);
	}

	public  boolean isElementPresent (WebDriver d, WebElement el)
	{
		//final int MAXIMUM_WAIT_TIME = 15;
		final int MAX_ELEMENT_RETRIES = 5;
		
		//WebDriverWait wait = new WebDriverWait(d, MAXIMUM_WAIT_TIME);
		int retries = 0;
		while (true)
		{
			try
			{
				return el.isDisplayed();
			}
			catch (NoSuchElementException e)
			{
				if (retries < MAX_ELEMENT_RETRIES)
				{
					retries++;
					continue;
				}
				else
				{
					throw e;
				}
			}
		}
	}
	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			WebElement element = driver.findElement(by);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	
	public static void pause(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void findFrameIframe(WebDriver driver)
	{
		List<WebElement> ele = driver.findElements(By.tagName("iframe"));
	    System.out.println("Number of iframes in a page :" + ele.size());
	    
	    List<WebElement> elem = driver.findElements(By.tagName("frame"));
	    System.out.println("Number of frames in a page :" + elem.size());
	    
	    //IFRAMES
	    for(WebElement el : ele){
	      //Returns the Id of a frame.
	        System.out.println("iFrame Id :" + el.getAttribute("id"));
	      //Returns the Name of a frame.
	        System.out.println("iFrame name :" + el.getAttribute("name"));
	    }

	    //FRAMES
	    for(WebElement el : elem){
		      //Returns the Id of a frame.
		        System.out.println("Frame Id :" + el.getAttribute("id"));
		      //Returns the Name of a frame.
		        System.out.println("Frame name :" + el.getAttribute("name"));
		    }
	    
	    //WINDOW HANDLES
	    Set<String> windows = driver.getWindowHandles();
	    System.out.println("Number of windows in a page :" + windows.size());
	    for(String el : windows){
	        System.out.println("window  handles :" + el);
	    }
	  
	}
	public boolean waitToLoadElement(WebDriver driver, By by, int seconds) {
		boolean found = true;

		long bailOutPeriod = 1000 * seconds;
		long lStartTime = new Date().getTime();

		while (!isElementPresent(driver, by)) {
			long lEndTime = new Date().getTime();
			long difference = lEndTime - lStartTime;

			if (difference < bailOutPeriod) {
				pause(1);
			}
			else {
				found = false;
				break;
			}
		}
		return found;
	}

	public  void dependableClick(WebDriver d, By by)
	{
		final int MAXIMUM_WAIT_TIME = 15;
		final int MAX_STALE_ELEMENT_RETRIES = 5;

		WebDriverWait wait = new WebDriverWait(d, MAXIMUM_WAIT_TIME);
		int retries = 0;
		while (true)
		{
			try
			{
				wait.until(ExpectedConditions.elementToBeClickable(by)).click();

				return;
			}
			catch (StaleElementReferenceException e)
			{
				if (retries < MAX_STALE_ELEMENT_RETRIES)
				{
					retries++;
					continue;
				}
				else
				{
					throw e;
				}
			}
		}
	}
	
	public  void dependableClick(WebDriver d, WebElement el)
	{
		final int MAXIMUM_WAIT_TIME = 15;
		final int MAX_STALE_ELEMENT_RETRIES = 5;

		WebDriverWait wait = new WebDriverWait(d, MAXIMUM_WAIT_TIME);
		int retries = 0;
		while (true)
		{
			try
			{
				
				//wait.until(ExpectedConditions.elementToBeClickable(el)).click();
				return;
			}
			catch (StaleElementReferenceException e)
			{
				if (retries < MAX_STALE_ELEMENT_RETRIES)
				{
					retries++;
					continue;
				}
				else
				{
					throw e;
				}
			}
		}
	}
	public boolean isAlertPresent(WebDriver driver)  
	{ String alertMsg=null;

	try 
	{ 
		Alert alert = driver.switchTo().alert();
		alertMsg =alert.getText(); 

		alert.accept();
		driver.switchTo().defaultContent();

		//  alert.dismiss();
		//((JavascriptExecutor)driver).executeScript("window.confirm = function(msg){return true;};");
		System.out.println("Alert present: alert message "+ alertMsg);
		Reporter.log("Aler is present");
		log.info("Alert present" +alertMsg);

		return true; 
	}   
	catch (NoAlertPresentException Ex) 
	{ 

		System.out.println("No alert!");
		return false;
	}

	catch (UnhandledAlertException Ex) 
	{ 
		System.out.println("Modal dialog present: "+ alertMsg);
		return true; 
	}   
	catch (Exception Ex) 
	{ 
		System.out.println(Ex);
		return true; 
	}   
	}

	//==================generate random string=============================
	public String generateEmail(int len)
	{
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();

		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		sb.append("@yahoo.com");
		return sb.toString();
	}
	/*
	//THIS IS A WORKAROUND JavaScript for Safari issues with Actions class
	public boolean onMouseOver(WebDriver driver, WebElement element)
	{
		boolean result = false;
		try
		{
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(mouseOverScript, element);
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean onMouseOver(WebDriver driver, By locator)
	{
		boolean result = false;
		try
		{
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript(mouseOverScript, driver.findElement(locator));
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		return result;
	}
*/
	//THIS IS A WORKAROUND JavaScript for Safari issues with Actions class
		public boolean onMouseOver(WebDriver driver, WebElement element)
		{
			boolean result = false;
			try
			{
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript(mouseOverScript, element);
				
				result = true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				result = false;
			}
			return result;
		}

		public boolean onMouseOver(WebDriver driver, By locator)
		{
			boolean result = false;
			try
			{
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				JavascriptExecutor js = (JavascriptExecutor) driver;

				js.executeScript(mouseOverScript, driver.findElement(locator));
				result = true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				result = false;
			}
			return result;
		}
	
	//2/25
	public void slowType(WebDriver driver,By locator, String str)
	{
		WebElement el;
		char chars[]=str.toCharArray();
		for (char s: chars)
		{
			el=driver.findElement(locator);
			String letter = s + "";
			el.sendKeys(letter);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void slowType(WebDriver driver, WebElement el, String str)
	{		
		char chars[]=str.toCharArray();
		for (char s: chars)
		{
			
			String letter = s + "";
			el.sendKeys(letter);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	//=========================================AJAX===============================
	public void waitForAjax (WebDriver driver,int timeoutInSeconds)

	{
		System.out.println("Checking active ajax calls by calling jquery.active");

		try{
			JavascriptExecutor jsDriver=(JavascriptExecutor)driver; 
			if (driver instanceof JavascriptExecutor)
			{
				for (int i=0;i<timeoutInSeconds;i++)
				{
					Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
					//return should be a number
					if (numberOfAjaxConnections instanceof Long)
					{
						Long n=(Long)numberOfAjaxConnections;
						System.out.println("Number of active jquery ajax calls: "+n);
						if (n.longValue()==0L)
							break;

					}
					Thread.sleep(1000);
				}

			}
			else
			{
				System.out.println("Web driver: "+driver +" cannot execute javascript");
			}
		}
		catch(InterruptedException e)
		{
			System.out.println(e);
		}
	}

	/*=================METHOD for APPENDING TO TXT======note not useful with loops because each loop iteration
	 * will open and close stream which is not efficient=============================================
	 */
	public void appendToTxt(String filepath, String data)
	{ 
		try 
		{

			File file = new File(filepath);
			// if file doesnt exists, then create it
			if (!file.exists()) 
			{
				file.createNewFile();
			}
			//pw= new PrintWriter(file.getAbsoluteFile());

			fw=	new FileWriter(filepath, true);
			bw=	new BufferedWriter(fw);
			pw = new PrintWriter(bw);

			pw.print(data+"\t\t");
			String timestamp = TimeUtil.getTimeStamp();
			pw.println();
			pw.println("-----------------------"+timestamp+"-----------------------------");


			bw.close();
			fw.close();

		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		finally
		{
			System.out.println("Done appending to 'txt' file");

			pw.close();

		}		

	}
	public void appendToTxt(String filepath, StringBuilder data)
	{ 
		try 
		{
			String [] lines=data.toString().split(" ");

			File file = new File(filepath);
			// if file doesnt exists, then create it
			if (!file.exists()) 
			{
				file.createNewFile();
			}

			fw=	new FileWriter(filepath, true);
			bw=	new BufferedWriter(fw);
			pw = new PrintWriter(bw);

			for(int i=0; i<lines.length;i++)
			{
				pw.print("--------------------------------------------------------------\n");
				pw.print(lines[i]);
				bw.newLine();							
			}

			String timestamp = TimeUtil.getTimeStamp();
			bw.write("----------------------------"+timestamp+"------------------------------");
			System.out.println("Done writing using FileWriter");


			bw.close();
			fw.close();
			pw.close();

		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		finally
		{
			System.out.println("Done appending to 'txt' file");

			pw.close();

		}		

	}
	public void writeToTxt(String filepath, StringBuilder data)
	{
		try
		{
			String [] lines=data.toString().split(" ");

			File file = new File (filepath);
			if( file.exists()!=true)
			{
				file.createNewFile();
			}

			FileWriter fw = new FileWriter (file);
			BufferedWriter bw = new BufferedWriter (fw);

			for(int i=0; i<lines.length;i++)
			{
				bw.write(lines[i]);
				bw.newLine();

			}

			String timestamp = TimeUtil.getTimeStamp();
			bw.write("----------------------------"+timestamp+"------------------------------");
			System.out.println("Done writing using FileWriter");

			bw.close();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Done writing to 'txt' file");
		}

	}

	public void writeToTxt(String filepath, StringBuilder data, String delimiter)
	{
		try
		{
			String [] lines = null;

			if (delimiter.equalsIgnoreCase(" "))
			{
				lines=data.toString().split(" ");
			}

			else if (delimiter.equalsIgnoreCase(","))
			{
				lines=data.toString().split(",");
			}
			File file = new File (filepath);
			if( file.exists()!=true)
			{
				file.createNewFile();
			}

			FileWriter fw = new FileWriter (file);
			BufferedWriter bw = new BufferedWriter (fw);

			for(int i=0; i<lines.length;i++)
			{
				bw.write(lines[i]);
				bw.newLine();

			}

			String timestamp = TimeUtil.getTimeStamp();
			bw.write("----------------------------"+timestamp+"------------------------------");

			bw.close();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Done writing to 'txt' file using FileWriter");
		}

	}
	public void readTxtFile(String filepath)
	{
		ArrayList<String>content = new ArrayList<String>();
		try{
			//FileInputStream fis = new FileInputStream(rp.readConfigProperties("txt.countries"));
			FileInputStream fis = new FileInputStream(filepath);
			InputStreamReader ir = new InputStreamReader(fis);
			BufferedReader br=new BufferedReader(ir);

			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console


				System.out.println (strLine);
				content.add(strLine);
			}
			fis.close();
			br.close();
			ir.close();
			System.out.println ("Successfully executed readTxtFile method");


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public ArrayList<String> readTxtFileReturn(String filepath)
	{
		ArrayList<String>content = new ArrayList<String>();
		try{
			//FileInputStream fis = new FileInputStream(rp.readConfigProperties("txt.countries"));
			FileInputStream fis = new FileInputStream(filepath);
			InputStreamReader ir = new InputStreamReader(fis);
			BufferedReader br=new BufferedReader(ir);

			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console		
				System.out.println (strLine);				
				content.add(strLine);
			}
			fis.close();
			br.close();
			ir.close();

			//remove the first 2 lines and last line for array

			content.remove(0);
			content.remove(1);
			content.remove((content.size()-1));
			System.out.println ("Successfully executed readTxtFile method; removed first 2 lines and last line.");


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return content;
	}
	
	public boolean selectLastOptionFromSelect(WebDriver driver, By locator_selectBox) throws Exception
	{
		
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator_selectBox));
		
		 Select selectBox = new Select(driver.findElement(locator_selectBox));

		 List<WebElement> selectOptions = selectBox.getOptions(); 
		 
		 if(selectOptions.size()>1) 
			{
		 for (WebElement temp : selectOptions)
		 
		  { 
		     System.out.println("selectOption.getText()= " + temp.getText());
		  
		   }		   
		    //select the last option: note selectOptions.get(0)= contains 'please select' so need get(1)
		   dependableClick(driver,selectOptions.get((selectOptions.size()-1)));
		   return true;		   
		}

		else 
		return false;		
	}
	
	public boolean selectOptionFromSelect(WebDriver driver, By locator_selectBox, int option_Number) throws Exception
	{
		
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator_selectBox));
		
		 Select selectBox = new Select(driver.findElement(locator_selectBox));

		 List<WebElement> selectOptions = selectBox.getOptions(); 
		 
		 if(selectOptions.size()>1) 
			{
		 for (WebElement temp : selectOptions)
		 
		  { 
		     System.out.println("selectOption.getText()= " + temp.getText());
		  
		   }		   
		    //select specific option: note selectOptions.get(0)= contains 'please select' so need get(1)
		 //check if int number is NOT > than number of available options
		 
		 if (option_Number>selectOptions.size()-1)
		 {
			 System.out.println("Specified option number is greater than number of avaialble options");
		 }
		 
		 else
		 {
			 dependableClick(driver,selectOptions.get((option_Number)));
		 }
		   return true;		
		}

		else 
		return false;
	}
	/***************************************************END OF ALL COMMON METHODS ************************************/
	/*****************************************************START OF Project Specific COMMON METHODS *****************************/
	/*
	public void verifySearchBreadcrumbs(WebDriver driver, String item) throws Exception

	{
		//\"pants\"
		String searchItem="\""+ item+"\"";
		String text="Home Your search Results for ";
		String s = 	driver.findElement(map.getLocator("search_breadcrumbs")).getText();

		boolean search_breadcrumbs=driver.findElement(map.getLocator("search_breadcrumbs")).isDisplayed();

		Reporter.log("Breadcrumbs for search page are on the page: "+ search_breadcrumbs+" 'value' is +" +s);
		if ((text+searchItem).equals(driver.findElement(By.cssSelector("ol.breadcrumb")).getText())) 
		{
			Reporter.log("Breadcrumbs match entred 'search value'");
		}

	}
	
	*/
	public void openHomePage(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, 15);
		try 
		{
			//wait until Search field is present to make sure that on Home page
			wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("searchField")));
			driver.findElement(map.getLocator("geopopup_close")).click();

		}
		catch (Exception e)
		{
			System.out.println("Geo popup not triggered/present");
		}
		
		finally
		{
			String homePageTitle = driver.getTitle();
			// System.out.println("On home page title is "+ homePageTitle);
			Reporter.log("On Home page title is " + 
					homePageTitle);
			log.info("Page title is " + homePageTitle);
			
		}
		
	}

	public void searchByItemName(WebDriver driver) throws Exception {

		String item = "Boardshorts";
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
		driver.findElement(map.getLocator("searchField")).clear();
		driver.findElement(map.getLocator("searchField")).sendKeys(item);
		driver.findElement(map.getLocator("searchBtn")).click();
		return;
		
		}
		//DC US no search button need to click on Enter key
		driver.findElement(map.getLocator("searchField")).clear();
		driver.findElement(map.getLocator("searchField")).sendKeys(item);
		driver.findElement(map.getLocator("searchField")).sendKeys(Keys.ENTER);
		
		
		CommonMethods.pause(1800);

		/* this can be done later verifying search breadcrumbs
		boolean search_breadcrumbs = driver.findElement(
				map.getLocator("search_breadcrumbs")).isDisplayed();
		log.info("Breadcrumbs for search page are on the page: "
				+ search_breadcrumbs);
		verifySearchBreadcrumbs(driver,item);
		 */
	}
	/***************************************************HOME PAGE MAIN NAV ************************************/
	/*****************************************************HOME PAGE MAIN NAV *****************************/

	public void homePageMainNavMen(WebDriver driver, By locator) throws Exception
	{
		WebElement mensCat=driver.findElement(map.getLocator("nav_men"));
		//this is a workaround JS method to avoid test failure in Safari
		onMouseOver( driver, mensCat);
		Thread.sleep(2000);

		//WebElement mensSubcat= driver.findElement(map.getLocator("men_jeans"));

        //find desired subcat using passed locator 
		WebElement menSubcat=driver.findElement(locator);	    

		//hover over t- shirts
		onMouseOver(driver, menSubcat);
		menSubcat.click();
		Thread.sleep(5000L);

	}

	/************************************************************SUBCAT PAGE*****************************/
	/************************************************************SUBCAT PAGE*****************************/
	public void subcatPageClickProduct(WebDriver driver, By locator) throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver,20);				
	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));	  
	    
		Reporter.log("On subcategory page title is '"+ driver.getTitle()+"'");
		System.out.println("On subcategory page title is '"+ driver.getTitle()+"'");
		
		WebElement product = driver.findElement( locator);
		product.click();

		//driver.findElement(By.xpath("//a/img")).click();
		//	driver.findElement(map.getLocator("subcat_product")).click();
		//wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_zoominbtn")));
	}	

	/*
	public WebElement subcatPageFindProduct(WebDriver driver, int productNumber)
	{
		
		Reporter.log("On subcategory page title is '"+ driver.getTitle()+"'");
		System.out.println("On subcategory page title is '"+ driver.getTitle()+"'");
		
		WebDriverWait wait = new WebDriverWait(driver,15);				
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("name")));	  
	    
	    List<WebElement> linkOmni= driver.findElements(By.className("omni_search_link"));
		System.out.println("linkOmni.size()="+linkOmni.size());		
	 
	 //CHECK IF PASSED NUMBER IS VALID
	 if (productNumber>linkOmni.size())
	 {
		 Reporter.log("FAILURE REASON: Specified product number is too high. There are "+linkOmni.size()+" products in subcat page");
		 System.out.println("FAILURE REASON: Specified product number is too high. There are "+linkOmni.size()+" products in subcat page");

	 }
	 //get product info
	 String productInfo =linkOmni.get(productNumber).getText();
	 System.out.println("On subcat selected product is "+ productInfo);
	 Reporter.log("On subcat selected product is "+ productInfo);


	 // click on  thumbnail based on number passed e.g pass 2 will click on 3d thumbnail -starts counting from 0
	 WebElement product =linkOmni.get(productNumber);
	 return product;
	}
	
	*/
	
	public WebElement subcatPageFindProduct(WebDriver driver, int productNumber)
	{
		
		Reporter.log("On subcategory page title is '"+ driver.getTitle()+"'");
		System.out.println("On subcategory page title is '"+ driver.getTitle()+"'");
		
		WebDriverWait wait = new WebDriverWait(driver,20);				
	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("name")));	  
	    
	   // wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div/a/img")));	  


	    List<WebElement> linkOmni= driver.findElements(By.xpath("//div/a/img"));
		System.out.println("linkOmni.size()="+linkOmni.size());		
	 
	 //CHECK IF PASSED NUMBER IS VALID
	 if (productNumber>linkOmni.size())
	 {
		 Reporter.log("FAILURE REASON: Specified product number is too high. There are "+linkOmni.size()+" products in subcat page");
		 System.out.println("FAILURE REASON: Specified product number is too high. There are "+linkOmni.size()+" products in subcat page");

	 }

	 // click on  thumbnail based on number passed e.g pass 2 will click on 3d thumbnail -starts counting from 0
	 WebElement product =linkOmni.get(productNumber);
	 return product;
	}
	
	
	public void subcatPageClickProduct(WebDriver driver, int productNumber) throws Exception
	{
		WebElement product=subcatPageFindProduct(driver,productNumber);
		product.click();	 
	}	
	
	public void subcatPageHoverOnProductClickExpressLink(WebDriver driver, int productNumber) throws Exception
	{
		WebElement product=subcatPageFindProduct(driver,productNumber);
		WebDriverWait wait=new WebDriverWait(driver, 20);

		//hover over product
		onMouseOver(driver, product);

		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("subcat_productxpressshop")));

		//click on express checkout link
		driver.findElement(map.getLocator("subcat_productxpressshop")).click();

		//wait until add to cart in Express View is visible
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("subcat_addtocartexpress")));

		driver.findElement(map.getLocator("subcat_addtocartexpress")).click();
		
		}
	

	
	public void QuickViewAddToCart(WebDriver driver, String size) throws Exception
	{
		WebDriverWait wait= new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_addtocart")));
		driver.findElement(map.getLocator("pdp_sizeselector")).click();
		Thread.sleep(5000L);
		//US Site// Select size dropdown and size swatch
		if (size.equals("S"))
		{
			wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorSswatch")));
			driver.findElement(map.getLocator("pdp_sizeselectorSswatch")).click();
			
			Thread.sleep(5000L);
			if (driver.findElement(map.getLocator("pdp_sizeunavailMsg")).isDisplayed())
			{
				wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorWithSize")));
				driver.findElement(map.getLocator("pdp_sizeselectorWithSize")).click();
				Thread.sleep(5000L);
				wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorLswatch")));
				driver.findElement(map.getLocator("pdp_sizeselectorLswatch")).click();
				
				
			}
		}
		else
		{
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorXLswatch")));
		driver.findElement(map.getLocator("pdp_sizeselectorXLswatch")).click();
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_addtocart_aftersize")));
		
		Thread.sleep(5000L);
		driver.findElement(map.getLocator("pdp_addtocart_aftersize")).click();
		
		
	}
	
	
	
	public void subcatPageHoverOnProductClickExpressLink(WebDriver driver, By locator) throws Exception
	{

		WebElement product = driver.findElement(locator);
		WebDriverWait wait=new WebDriverWait(driver, 20);

		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{	
			try{
				WebElement element = driver.findElement(map.getLocator("subcat_product"));
				Actions actionsProvider = new Actions(driver);
				actionsProvider.moveToElement(element).perform();
				wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("subcat_productxpressshop")));
				driver.findElement(map.getLocator("subcat_productxpressshop")).click();
				Thread.sleep(5000L);
				wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("subcat_addtocartexpress")));
				driver.findElement(map.getLocator("subcat_addtocartexpress")).click();
		
			}
			catch(Exception e)
			{
				//hover over product
				onMouseOver(driver, locator);
				driver.findElement(map.getLocator("subcat_productxpressshop")).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("subcat_addtocartexpress")));
				driver.findElement(map.getLocator("subcat_addtocartexpress")).click();
				
				
			}
			
			return;
		}
		
		
		
		try{
		//Finding the WebElement
		WebElement element = driver.findElement(map.getLocator("subcatproductUS"));
		Actions actionsProvider = new Actions(driver);
		actionsProvider.moveToElement(element).perform();
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("subcat_productQuickview")));
		driver.findElement(map.getLocator("subcat_productQuickview")).click();
		Thread.sleep(5000L);
		}
		catch(Exception e)
		{
			
			onMouseOver(driver, locator);
			Thread.sleep(5000L);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("subcat_productQuickview")));
		driver.findElement(map.getLocator("subcat_productQuickview")).click();
		Thread.sleep(5000L);
		}
			//click on Checkout btn in Mini Cart  in a diff  method
		

	}	
	
	public void subcatQuickviewAddtoCart(WebDriver driver,String size) throws Exception
	{
		
			WebDriverWait wait= new WebDriverWait(driver,15);
			wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("quickview_AddtoCart")));
			driver.findElement(map.getLocator("quickview_sizeselector")).click();
			Thread.sleep(5000L);
			//US Site// Select size dropdown and size swatch
			if (size.equals("S"))
			{
				wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("quickview_Ssize")));
				driver.findElement(map.getLocator("quickview_Ssize")).click();
				
				Thread.sleep(5000L);
			}
			driver.findElement(map.getLocator("quickview_AddtoCart")).click();
			
			
			Thread.sleep(15000L);
		
	}

	/************************************************************PDP PAGE*****************************/
	/************************************************************PDP PAGE*****************************/

	public void pdpPageZoomIn(WebDriver driver) throws Exception
	{ 

		//wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_zoominbtn")));

		driver.findElement(map.getLocator("pdp_zoominbtn")).click();

		log.info("UserGen screenshot taken");
		CommonMethods.pause(2000);
		ts.takeScreenshot(driver);

		//select color
		//select size
		//click on the 'add to cart ' btn
		//click on 'mini cart' link

	}

	public void pdpPageSelectColor(WebDriver driver) throws Exception
	{ //select color

		driver.findElement(map.getLocator("pdp_firstswatch")).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("pdp_colorlabel")));
		CommonMethods.pause(2000);

		// String selectedColor= driver.findElement(map.getLocator("pdp_selectedswatch")).getAttribute("innerHTML").trim();
		//String selectedColor= driver.findElement(map.getLocator("pdp_selectedswatch")).getText();

		//Reporter.log("Selected color on PDP is: '"+ selectedColor+"'");
		driver.findElement(map.getLocator("pdp_secondswatch")).click();
		CommonMethods.pause(2000);
		//String selectedColor2= driver.findElement(map.getLocator("pdp_selectedswatch")).getText();
		//Reporter.log("After click on 2nd color swatch label changed to '"+selectedColor2+"'");

		//System.out.println("Selected color on PDP is: '"+ selectedColor+"'");
		//log.info("PDP: after click on 2nd color swatch label changed to '"+selectedColor2+"'");


		//click on the 'add to cart ' btn
		//click on 'mini cart' link

	}

	public void pdpPageSelectSize(WebDriver driver) throws Exception
	{      
		driver.findElement(map.getLocator("pdp_firstsize")).click();
		//  driver.findElement(map.getLocator("pdp_secondsize")).click();

		CommonMethods.pause(2000);

		// String selectedColor= driver.findElement(map.getLocator("pdp_selectedswatch")).getAttribute("innerHTML").trim();
		//   String selectedSize= driver.findElement(map.getLocator("pdp_sizelabel")).getText();
		//  Reporter.log("Selected size on PDP is: '"+ selectedSize+"'");

	}
	@SuppressWarnings("deprecation")
	public void pdpPageSaveForLater(WebDriver driver) throws Exception
	{
		WebDriverWait wait=new WebDriverWait (driver,20);
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("pdp_saveforlatercss")));
		
		driver.findElement(map.getLocator("pdp_saveforlatercss")).click();
		
		String expectedText="Saved in your wishlist";	

	//	wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("pdp_savedinwhishlist")));
								
		try {
			String actual=driver.findElement(map.getLocator("pdp_saveforlatercss")).getText();
			System.out.println("pdp_saveforlatercss)).getText()="+ actual);
			//wait until //a[contains(.,'Saved in your wishlist')] appears
			//wait.until(ExpectedConditions.textToBePresentInElementLocated(map.getLocator("pdp_saveforlatercss"), expectedText));
			
			//236,1100
		      
		    } catch (Exception e) {
		    	
		    	log.debug("Exception in pdpSaveForLaber method; "+ e);
		    }
	}


	public void pdpPageSelectAddToCart(WebDriver driver) throws Exception
	{
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
		WebDriverWait wait= new WebDriverWait(driver,25);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_addtocart_UK")));
		driver.findElement(map.getLocator("pdp_addtocart_UK")).click();
		Thread.sleep(5000L);
		return;
		
		//optional logic to ensure mini-cart triggered can go here
		}
		//US Site// Select size dropdown and size swatch
		
		WebDriverWait wait= new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_addtocart")));
		driver.findElement(map.getLocator("pdp_sizeselector")).click();
		Thread.sleep(5000L);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorXLswatch")));
		driver.findElement(map.getLocator("pdp_sizeselectorXLswatch")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_addtocart_aftersize")));
		Thread.sleep(5000L);
		driver.findElement(map.getLocator("pdp_addtocart_aftersize")).click();
		
		/*Thread.sleep(5000L);
		WebElement SizeUnavailStatus=driver.findElement(map.getLocator("pdp_sizeunavailMsg"));
		if (SizeUnavailStatus.isDisplayed())
		{
			wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorWithSize")));
			driver.findElement(map.getLocator("pdp_sizeselectorWithSize")).click();
			Thread.sleep(5000L);
			wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorSswatch")));
			driver.findElement(map.getLocator("pdp_sizeselectorSswatch")).click();
			
		}*/
	}
	
	
	public void pdpPageSelectAddToCart(WebDriver driver, String size) throws Exception
	{
		WebDriverWait wait= new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_addtocart")));
		driver.findElement(map.getLocator("pdp_sizeselector")).click();
		Thread.sleep(5000L);
		//US Site// Select size dropdown and size swatch
		if (size.equals("S"))
		{
			wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorSswatch")));
			driver.findElement(map.getLocator("pdp_sizeselectorSswatch")).click();
			
			Thread.sleep(5000L);
			if (driver.findElement(map.getLocator("pdp_sizeunavailMsg")).isDisplayed())
			{
				wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorWithSize")));
				driver.findElement(map.getLocator("pdp_sizeselectorWithSize")).click();
				Thread.sleep(5000L);
				wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorLswatch")));
				driver.findElement(map.getLocator("pdp_sizeselectorLswatch")).click();
				
				
			}
		}
		else
		{
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorXLswatch")));
		driver.findElement(map.getLocator("pdp_sizeselectorXLswatch")).click();
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_addtocart_aftersize")));
		
		Thread.sleep(5000L);
		driver.findElement(map.getLocator("pdp_addtocart_aftersize")).click();
		
		
	}
	
	
	
	public void pdpPageSelectAddToCartNumSizes(WebDriver driver) throws Exception
	{
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
		WebDriverWait wait= new WebDriverWait(driver,25);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_addtocart_UK")));
		driver.findElement(map.getLocator("pdp_addtocart_UK")).click();
		Thread.sleep(5000L);
		return;
		
		//optional logic to ensure mini-cart triggered can go here
		}
		//US Site// Select size dropdown and size swatch
		
		WebDriverWait wait= new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_addtocart")));
		driver.findElement(map.getLocator("pdp_sizeselector")).click();
		Thread.sleep(5000L);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselector28swatch")));
		driver.findElement(map.getLocator("pdp_sizeselector28swatch")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_addtocart_aftersize")));
		Thread.sleep(5000L);
		driver.findElement(map.getLocator("pdp_addtocart_aftersize")).click();
		
		/*Thread.sleep(5000L);
		WebElement SizeUnavailStatus=driver.findElement(map.getLocator("pdp_sizeunavailMsg"));
		if (SizeUnavailStatus.isDisplayed())
		{
			wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorWithSize")));
			driver.findElement(map.getLocator("pdp_sizeselectorWithSize")).click();
			Thread.sleep(5000L);
			wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_sizeselectorSswatch")));
			driver.findElement(map.getLocator("pdp_sizeselectorSswatch")).click();
			
		}*/
	}
	
	


	/************************************************************mini CART PAGE*****************************/
	/************************************************************CART PAGE*****************************/


	public void fromMiniCartToCart(WebDriver driver) throws Exception
	{
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
		By locator_basket=map.getLocator("minicart_link");
		onMouseOver(driver,locator_basket);	

		//click on Checkout link on mini cart
	dependableClick(driver, map.getLocator("minicart_checkoutbtncss"));
		//driver.findElement(map.getLocator("minicart_link_GBxpath")).click();

		System.out.println("title is "+driver.getTitle());
		Reporter.log("After click on Checkout button on mini-cart title is "+ driver.getTitle());	
		return;
		
		}
		//US site
		WebDriverWait wait= new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("minicart_linkxpath")));
		driver.findElement(map.getLocator("minicart_linkxpath")).click();
//driver.get("https://www.stg.dcshoes.com/on/demandware.store/Sites-DC-US-Site/en_US/Cart-Show");
		System.out.println("title is "+driver.getTitle());
		Reporter.log("After click on Checkout button on mini-cart title is "+ driver.getTitle());	
	}


	public void cartDeleteItem(WebDriver driver, By locator) throws Exception
	{
		By locator_deleteButtons=map.getLocator("cart_deleteproductxpath");
		
		//will click on the delete for first item in CART
		driver.findElement(locator_deleteButtons).click();

	}
	
	public void cartDeleteAllItems(WebDriver driver) throws Exception
	{
		//will find multiple buttons for delete if applicable
		By locator_deleteButtons=map.getLocator("cart_deleteproductxpath");
		//store all delete buttons 
		List<WebElement> deleteButtons = driver.findElements(locator_deleteButtons);
		
		for (WebElement el: deleteButtons)
		{
			//click on each delete button; need to check so does not throw StaleElementException then dependableClick()?
			el.click();
		}
		
		
	}

	public void fromCartToSignIn(WebDriver driver) throws Exception
	{
		//in cart click on 'checkout' for redirect to interst sign in page
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("cart_checkoutbtn")));
		driver.findElement(map.getLocator("cart_checkoutbtn")).click();
		Reporter.log("After click on Checkout button on Cart page  title is "+ driver.getTitle());		

	}

	public void fromCartToContinueShopping(WebDriver driver) throws Exception
	{
		 WebDriverWait wait = new WebDriverWait(driver, 20);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("cart_continueshopping")));
		//in cart click on 'checkout' for redirect to interst sign in page

		driver.findElement(map.getLocator("cart_continueshopping")).click();
		Reporter.log("After click on 'Continue Shopping' button on Cart page  title is "+ driver.getTitle());		

	}
	public void fromCartToGuestCheckout(WebDriver driver) throws Exception
	{
		 WebDriverWait wait = new WebDriverWait(driver, 20);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("cart_Guestcheckout_US")));
		//in cart click on 'checkout' for redirect to interst sign in page

	        driver.findElement(map.getLocator("cart_Guestcheckout_US")).click();
		Reporter.log("After click on 'Guest Checkout' button on Cart page  title is "+ driver.getTitle());		

	}
	
	public void fromInscriptionToStep2Payment(WebDriver driver) throws Exception
	{
		 WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("inscription_continuebtnname")));

			ts.takeScreenshot(driver);
	        //click on 'Continue' btn	        
	        driver.findElement(map.getLocator("inscription_continuebtnname")).click();
	        
	        /*
	         * 3/17 Code specific for DC Shoes US check if Address Option radio btns displayed
	         * and select one of the options>>then should auto redirect to Payment page;
	         * 
	         * Need try catch for Address Options because not always displayed
	         * 
	         */
	        Boolean isUSsite=driver.getCurrentUrl().contains("en_US");
			if(isUSsite==true)
			{
				try
				{
					
				wait.until(ExpectedConditions.presenceOfElementLocated(By.className("select-address")));
				    
			WebElement div1=	driver.findElement(By.className("select-address"));
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("address")));

			 //select Address Option 1
			div1.findElement(By.name("address")).click();
			
	        System.out.println("From Inscription redirected to next page");
					  					
				//driver.findElement(By.xpath("//input[@name='address']")).click();;
				//locator for Address Option2    xpath>(//input[@name='address'])[last()]
					
				}
				
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("DC US: Address Options not displayed on Inscription page");
				}				      
			}	       

	}
	
	public void fromStep2PaymentToStep3(WebDriver driver) throws Exception
	{
		 WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("billing_continuebtn")));

	        //click on 'Continue' btn	on Billing Step2 page        
		driver.findElement(map.getLocator("billing_continuebtn")).click();
        System.out.println("From Step 2 Payment  to Step 3 Verification");


	}
	
	public void fromStep3VerificationToConfirmation(WebDriver driver) throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("verification_placeorderbtn")));

		Reporter.log("Current page title: "+driver.getTitle());
		WebElement placeOrder= driver.findElement(map.getLocator("verification_placeorderbtn"));
		placeOrder.click();		
	}
	
	public void fromConfirmationToContinueShopping(WebDriver driver) throws Exception
	{
		 WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("confirmationpage_continueshoppingbtn")));
		Reporter.log("Current page title is "+ driver.getTitle());

	        //click on 'Continue' btn	on Confirmation page        
        driver.findElement(map.getLocator("confirmationpage_continueshoppingbtn")).click();
        System.out.println("From Confirmation page after click on 'Continue Shopping' redirected to "+driver.getTitle());
        Reporter.log("From Confirmation page after click on 'Continue Shopping' redirected to "+driver.getTitle());

	}
	
	
	/************************************************************SIGN IN  PAGE**************************************/
	/************************************************************INTERST SIGN IN PAGE*****************************/

	/**
	   * This method is workaround method needed because of DC Shoes US
	   * @return WebElement.
	   * @exception .
	   * @since Feb 23/2014
	   */
	//this is workaround method needed because of DC Shoes US
	public WebElement returnLoginEmailField(WebDriver driver)
	{
		WebDriverWait wait = new WebDriverWait(driver,15);

		 wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dwfrm_login")));
			//find WebElement form by id
			 WebElement loginForm = driver.findElement(By.id("dwfrm_login"));

			 //inside of form find first div 'formfield username'
			 WebElement divFormFieldUserName= loginForm.findElement(By.tagName("div"));

			 //cannnot be used because compound class names are not supported throws InvalidSelectorException
			// WebElement divFormFieldUserName= loginForm.findElement(By.className("formfield username"));

			 //inside of div formfield username find div class 'value'
			 WebElement divValue =  divFormFieldUserName.findElement(By.className("value"));

			 //inside of div value find input field 
			 WebElement inputFieldEmail = divValue.findElement(By.tagName("input"));
		return inputFieldEmail;
		
	}
	public void login(WebDriver driver, String email, String password) throws Exception
	{ 
		
		/*
		 * 3/17 code specific for DC Shoes US
		 * get url and if contains 'en_US' then type in city
		 * 
		 */		
	Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
			WebDriverWait wait= new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("login_link")));
		
			WebElement loginLink =driver.findElement(map.getLocator("login_link"));
			loginLink.click();
			//////////////*************

			  wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("intercept_registeredrememberme")));
			  WebElement rememberMe=driver.findElement(map.getLocator("intercept_registeredrememberme"));

					if(rememberMe.isSelected()==true)
					{
						Reporter.log("On Checkout sign in page 'remember me' is selected on default load.");
						//check if can check and uncheck remember me checkbox
						rememberMe.click();
						AssertJUnit.assertEquals(rememberMe.isSelected(), false);
					}
					
					
			String title = driver.getTitle();
			Reporter.log("After click on 'Login' link in the header page title is "+title);

			
			
			
			//safari cannot find this element need to use workaround
			//WebElement regEmail = driver.findElement(map.getLocator("intercept_registeredemail"));
			
			//2/20 workaround 
			WebElement regEmail = returnLoginEmailField(driver);
	        Reporter.log("Logging in using email:"+ email+" and password: "+password);
	        System.out.println("Logging in using email:"+ email+" and password: "+password);

			regEmail.clear();
			regEmail.sendKeys(email);
			title = driver.getTitle();

			WebElement regPswd = driver.findElement(map.getLocator("intercept_registeredpswd"));
			regPswd.clear();
			regPswd.sendKeys(password);

			//click on submit button
			wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("intercept_registeredbtn")));
			WebElement regLogin =driver.findElement(map.getLocator("intercept_registeredbtn"));
			regLogin.click();
			
			//wait until logout link is present to indicate that login was successful		
			wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("intercept_signoutlink")));
			
			return;
		}
			
		
		
			WebDriverWait wait= new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("login_link_US")));
		
		WebElement loginLink =driver.findElement(map.getLocator("login_link_US"));
		loginLink.click();
		
		//login interception page

		  wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("intercept_registeredrememberme")));
		  WebElement rememberMe=driver.findElement(map.getLocator("intercept_registeredrememberme"));

				if(rememberMe.isSelected()==true)
				{
					Reporter.log("On Checkout sign in page 'remember me' is selected on default load.");
					//check if can check and uncheck remember me checkbox
					rememberMe.click();
					AssertJUnit.assertEquals(rememberMe.isSelected(), false);
				}
				
				
		String title = driver.getTitle();
		Reporter.log("After click on 'Login' link in the header page title is "+title);

		
		
		
		//safari cannot find this element need to use workaround
		//WebElement regEmail = driver.findElement(map.getLocator("intercept_registeredemail"));
		
		//2/20 workaround 
		WebElement regEmail = returnLoginEmailField(driver);
        Reporter.log("Logging in using email:"+ email+" and password: "+password);
        System.out.println("Logging in using email:"+ email+" and password: "+password);

		regEmail.clear();
		regEmail.sendKeys(email);
		title = driver.getTitle();

		WebElement regPswd = driver.findElement(map.getLocator("intercept_registeredpswd"));
		regPswd.clear();
		regPswd.sendKeys(password);

		//click on submit button
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("intercept_registeredbtn")));
		WebElement regLogin =driver.findElement(map.getLocator("intercept_registeredbtn"));
		regLogin.click();
		
		//wait until logout link is present to indicate that login was successful		
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("intercept_signoutlink")));
		
	}
	
	public boolean isLoggedIn (WebDriver driver) throws Exception
	{
		WebDriverWait wait=null;;
			//logout
			wait=new WebDriverWait(driver, 10);	
			By locator_logout=map.getLocator("intercept_signoutlink");
			if (driver.findElements(locator_logout).size() != 0)
			{
				wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("intercept_signoutlink")));
				System.out.println("Logged in. Logout link is  present. ");
				return true;
			}		
			else 
			{
				System.out.println("NOT logged in. Logout link is not present ");
				return false;				
			}		
	}
	
	public void logout(WebDriver driver)
	{
		try
		{
			//logout
			WebDriverWait wait=new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("intercept_signoutlink")));
			driver.findElement(map.getLocator("intercept_signoutlink")).click();
		}
		
		catch(Exception e)
		{
			System.out.println("Logout link is not present. ");
		}
	}

	public void checkoutSignInClickElement(WebDriver driver, By locator)
	{ 
		WebDriverWait wait =new WebDriverWait(driver,20);
		
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		driver.findElement(locator).click();

	}

	/*************************************CHECKOUT STEP1 PAGE**************************************/
	/************************************CHECKOUT STEP1 PAGE  *****************************************/

	public void step1Fields(WebDriver driver) throws Exception
	{
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("inscription_fnameid")));

		//assert title 
		String title=driver.getTitle();
		Reporter.log("On checkout 'Step1' page title is "+ title);

		 //   step1Salutation(driver, "Mrs");   ->> removed from the UK site

			WebElement fname=driver.findElement(map.getLocator("inscription_fnameid"));
	        fname.clear();
	        fname.sendKeys("Veronica");

	        WebElement lname=driver.findElement(map.getLocator("inscription_lnameid"));
	        lname.clear();
	        lname.sendKeys("Peter");

	        WebElement phone =driver.findElement(map.getLocator("inscription_phoneid"));
	        phone.clear();
	        phone.sendKeys("2024458287");
	        //gender added to UK site
	    	new Select(driver.findElement(map.getLocator("inscription_genderid"))).selectByVisibleText("Female");
	    	
	        //email and confirm email are applicable only for Guest Checkout
	        
	        try
	        {
	        	WebElement email = driver.findElement(map.getLocator("inscription_emailid"));
		        email.clear();
		        email.sendKeys("veronica1315@gmail.com");
		        
		        WebElement confirmEmail =driver.findElement(map.getLocator("inscription_confirmemailid"));
		        confirmEmail.clear();
		        confirmEmail.sendKeys("veronica1315@gmail.com");
	        	
	        }
	        
	        catch(Exception e)
	        {
	        	System.out.println("Signed In. Registered checkout: 'Email' and 'Confirm Email' fields are not present");
	        }
	        

	        WebElement address1 =driver.findElement(map.getLocator("inscription_address1id"));
	        address1.clear();
	        address1.sendKeys("123 Richmond Mews");
	       
	        WebElement address2 =driver.findElement(map.getLocator("inscription_address2id"));
	        address2.clear();
	        address2.sendKeys("apt 2C");

	       
	        WebElement addInfo =driver.findElement(map.getLocator("inscription_additionalid"));
	        addInfo.clear();
	        addInfo.sendKeys("additional info goes here");

	        WebElement loyaltyCard =driver.findElement(map.getLocator("inscription_loyaltycardid"));
	        loyaltyCard.clear();
	        loyaltyCard.sendKeys("");

	        WebElement zip =driver.findElement(map.getLocator("inscription_zipid"));
	        zip.clear();
	        
	        //zip.sendKeys(data.get("ZIP"));
	        //slowType needed because of ajax and to trigger autopopulate of city

	        By locator_zip=map.getLocator("inscription_zipid");
	        slowType(driver, locator_zip, "W1D 3DH");        
	        
	      //this is optional if the num of test is large just remove taking screenshot below
	        ts.takeScreenshot(driver);

	        /*
			 * 3/17 code specific for DC Shoes US
			 * get url and if contains 'en_US' then type in valid zip, city and street
			 * 1.There is shipping validation that checks zip, city, street for DC Shoes US
			 * 2. there is radio btns for 'Address Option1' and 'Address Option2'
			 * 
			 */
			
			Boolean isUSsite=driver.getCurrentUrl().contains("en_US");
			if(isUSsite==true)
			{
				String usCity="Dublin";
				
				address1 =driver.findElement(map.getLocator("inscription_address1id"));
		        address1.clear();
		        address1.sendKeys("5010 Haven Pl");
		       
		        address2 =driver.findElement(map.getLocator("inscription_address2id"));
		        address2.clear();
		        address2.sendKeys("apt 303");
		        
		        zip =driver.findElement(map.getLocator("inscription_zipid"));
		        zip.clear();
		        
		        //zip.sendKeys(data.get("ZIP"));
		        //slowType needed because of ajax and to trigger autopopulate of city

		        locator_zip=map.getLocator("inscription_zipid");
		        slowType(driver, locator_zip, "94568");        
		        
		        driver.findElement(map.getLocator("inscription_cityid")).clear(); 
		        driver.findElement(map.getLocator("inscription_cityid")).sendKeys(usCity);
		        
		      //select 5th state from the drop down
				selectOptionFromSelect(driver,map.getLocator("inscription_state"), 6);
				//this is optional if the num of test is large just remove taking screenshot below
		        ts.takeScreenshot(driver);

				
			}
			
			
	}


	public boolean isCityInError(WebDriver driver) throws Exception
	{
				
		//means element exists = error message for city is displayed 
		
		  //then need to click on drop down arrow and select city value from drop down
		  //get all select options
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("inscription_cityid")));
		
		/*
		 * 3/17 code specific for DC Shoes US
		 * get url and if contains 'en_US' then type in city
		 * 
		 */
		
		Boolean isUSsite=driver.getCurrentUrl().contains("en_US");
		String usCity="Hayward";
		if(isUSsite==true)
		{
			driver.findElement(map.getLocator("registration_city")).clear();
			driver.findElement(map.getLocator("registration_city")).sendKeys(usCity);
			
			return false;
		}
		
		else{
			Select selectBox = new Select(driver.findElement(map.getLocator("inscription_cityid")));

			 List<WebElement> selectOptions = selectBox.getOptions(); 
			 
			 if(selectOptions.size()>1) 
				{
			 for (WebElement temp : selectOptions)
			 
			  { 
			     System.out.println("selectOption.getText()= " + temp.getText());
			  
			   }		   
			    //select the first option: note selectOptions.get(0)= contains 'please select' so need get(1)
			   dependableClick(driver,selectOptions.get(1));
		}
		
		 		}
		return false;
			
	}
	/* Removed from the UK site
	 * 
	 public void step1Salutation(WebDriver driver, String salutation) throws Exception
	
	{
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("inscription_titleid")));
		
		//Miss, Mr arg passed to this method should be exact match for value in select case sensitive
		new Select(driver.findElement(map.getLocator("inscription_titleid"))).selectByVisibleText(salutation);
	}
 */
	
	
	
	
	public void step1FieldsUS(WebDriver driver) throws Exception
	{
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("inscription_fnameid")));

		//assert title 
		String title=driver.getTitle();
		Reporter.log("On checkout 'Step1' page title is "+ title);

		 //   step1Salutation(driver, "Mrs");   ->> removed from the UK site

			WebElement fname=driver.findElement(map.getLocator("inscription_fnameid"));
	        fname.clear();
	        fname.sendKeys("Veronica");

	        WebElement lname=driver.findElement(map.getLocator("inscription_lnameid"));
	        lname.clear();
	        lname.sendKeys("Peter");

	        WebElement phone =driver.findElement(map.getLocator("inscription_phoneid"));
	        phone.clear();
	        phone.sendKeys("2024458287");
	       
	        //email and confirm email are applicable only for Guest Checkout
	        
	        try
	        {
	        	WebElement email = driver.findElement(map.getLocator("inscription_emailid"));
		        email.clear();
		        email.sendKeys("veronica1315@gmail.com");
		        
		        WebElement confirmEmail =driver.findElement(map.getLocator("inscription_confirmemailid"));
		        confirmEmail.clear();
		        confirmEmail.sendKeys("veronica1315@gmail.com");
	        	
	        }
	        
	        catch(Exception e)
	        {
	        	System.out.println("Signed In. Registered checkout: 'Email' and 'Confirm Email' fields are not present");
	        }
	        

	        WebElement address1 =driver.findElement(map.getLocator("inscription_address1id"));
	        address1.clear();
	        address1.sendKeys("123 Richmond Mews");
	       
	        WebElement address2 =driver.findElement(map.getLocator("inscription_address2id"));
	        address2.clear();
	        address2.sendKeys("apt 2C");

	       
	        

	        WebElement zip =driver.findElement(map.getLocator("inscription_zipid"));
	        zip.clear();
	         By locator_zip=map.getLocator("inscription_zipid");
	        slowType(driver, locator_zip, "94132");      
	        
	        String usCity="Dublin";
			
			
	        driver.findElement(map.getLocator("inscription_cityid")).clear(); 
	        driver.findElement(map.getLocator("inscription_cityid")).sendKeys(usCity);
	      //select CA state from the drop down
			new Select(driver.findElement(map.getLocator("shipping_state_US_id"))).selectByVisibleText("California");
	        
	      
			
			
	}

	
	public void step1DifferentShipping(WebDriver driver, String street) throws Exception
	{
		//because signed in Inscription page is skipped

				//click on Inscription label to get to Inscription page
				WebDriverWait wait = new WebDriverWait(driver,20);
				wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("inscription_label")));
				
				driver.findElement(map.getLocator("inscription_label")).click();
				
				//click on radio btn for diff shipping address
				driver.findElement(map.getLocator("inscription_differentshippingid")).click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("inscription_shippingtitleid")));

				//fill out mandatory fields including Salutation drop down		
				new Select(driver.findElement(map.getLocator("inscription_shippingtitleid"))).selectByVisibleText("Mr");
				
				    WebElement fname=driver.findElement(map.getLocator("inscription_shippingfnameid"));
			        fname.clear();
			        fname.sendKeys("FNAME");

			        WebElement lname=driver.findElement(map.getLocator("inscription_shippinglnameid"));
			        lname.clear();
			        lname.sendKeys("LNAME");

			        WebElement phone =driver.findElement(map.getLocator("inscription_shippingphoneid"));
			        phone.clear();
			        phone.sendKeys("1234567891");

			        WebElement address1 =driver.findElement(map.getLocator("inscription_shippingaddress1id"));
			        address1.clear();
			        //type in whatever String was passed to the method
			        address1.sendKeys(street);

			        WebElement zip =driver.findElement(map.getLocator("inscription_shippingzipid"));
			        zip.clear();
			        
			        //slowType needed because of ajax and to trigger autopopulate of city
			        By locator_zip=map.getLocator("inscription_shippingzipid");
			        slowType(driver, locator_zip, "W1D 3DH");      
			        
	}
	
	public void uncheckSaveCard(WebDriver driver) throws Exception
	{
		WebDriverWait wait =new WebDriverWait(driver,8);
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("billing_ccsavecardid")));
		
		Boolean isSaveCardChecked=driver.findElement(map.getLocator("billing_ccsavecardid")).isSelected();
		
		if (isSaveCardChecked==true)
		{
			//click on it to deselect
			driver.findElement(map.getLocator("billing_ccsavecardid")).click();
		}
	}
	
	
	public void selectPaymentOnStep2(WebDriver driver, String paymentType) throws Exception
	{
		String visa=rp.readConfigProperties("verified_visa");
		String cardSecurity=rp.readConfigProperties("cardPin");
		String master= rp.readConfigProperties("master_nosecurecode");
		
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("billing_allcards")));
		Reporter.log("Current page title is "+ driver.getTitle());


		if (paymentType.equalsIgnoreCase("visa"))
		{	
			Boolean isVisaSelected=driver.findElement(map.getLocator("billing_visaid")).isSelected();
			
			if(isVisaSelected==false)
			{
				driver.findElement(map.getLocator("billing_visaid")).click();
			}

			else
			{
				System.out.println("Currently selected payment type Visa");
			}
			try
			{   //uncheck save card
				uncheckSaveCard(driver);
			}
						
			catch(Exception e)
			{				
				System.out.println("GUEST CHECKOUT:'Save card' checkbox is not present; ");
			}
			
			WebElement billing_name=driver.findElement(map.getLocator("billing_nameid"));
			billing_name.sendKeys("fluid");

			WebElement cardNumber=driver.findElement(map.getLocator("billing_ccnumberid"));
			cardNumber.sendKeys(visa);

			WebElement cardCode=driver.findElement(map.getLocator("billing_ccsecuritycodeid"));
			cardCode.sendKeys(cardSecurity);

			//select month #4
			new Select(driver.findElement(map.getLocator("billing_ccmonthid"))).selectByVisibleText("04");
			//change year
			new Select(driver.findElement(map.getLocator("billing_ccyearid"))).selectByVisibleText("2020");
			
			
			//     WebElement continuebtn=
			driver.findElement(map.getLocator("billing_continuebtn")).click();

		}

		else if (paymentType.equalsIgnoreCase("master")||
				paymentType.equalsIgnoreCase("mastercard")||
				paymentType.equalsIgnoreCase("master card")||
				paymentType.equalsIgnoreCase("master_card"))
		{
			driver.findElement(map.getLocator("billing_masterid")).click();
			WebElement billing_name=driver.findElement(map.getLocator("billing_nameid"));
			billing_name.sendKeys("fluid");

			try
			{   //uncheck save card
				uncheckSaveCard(driver);
			}
						
			catch(Exception e)
			{				
				System.out.println("GUEST CHECKOUT:'Save card' checkbox is not present; ");
			}
			
			WebElement cardNumber=driver.findElement(map.getLocator("billing_ccnumberid"));
			cardNumber.sendKeys(master);

			WebElement cardCode= driver.findElement(map.getLocator("billing_ccsecuritycodeid"));
			cardCode.sendKeys(cardSecurity);

			//select month #5
		
			new Select(driver.findElement(map.getLocator("billing_ccmonthid"))).selectByVisibleText("05");
			//change year
			new Select(driver.findElement(map.getLocator("billing_ccyearid"))).selectByVisibleText("2020");
			
			//     WebElement continuebtn=
			driver.findElement(map.getLocator("billing_continuebtn")).click();
		}

		//NEED AMEX CARD NUMBER FROM QUICKSILVER
		else if (paymentType.equalsIgnoreCase("amex")||
				paymentType.equalsIgnoreCase("american express")||
				paymentType.equalsIgnoreCase("amex card")||
				paymentType.equalsIgnoreCase("american_express"))
		{
			driver.findElement(map.getLocator("billing_amexid")).click();

			WebElement billing_name=driver.findElement(map.getLocator("billing_nameid"));
			billing_name.sendKeys("fluid");

			try
			{   //uncheck save card
				uncheckSaveCard(driver);
			}						
			catch(Exception e)
			{				
				System.out.println("GUEST CHECKOUT:'Save card' checkbox is not present; ");
			}
			
			WebElement cardNumber=driver.findElement(map.getLocator("billing_ccnumberid"));
			cardNumber.sendKeys("REPLACE THIS WITH AMEX CARD");

			WebElement cardCode= driver.findElement(map.getLocator("billing_ccsecuritycodeid"));
			cardCode.sendKeys(cardSecurity);

			//select month #5
			new Select(driver.findElement(map.getLocator("billing_ccmonthid"))).selectByVisibleText("05");
			//year
			new Select(driver.findElement(map.getLocator("billing_ccyearid"))).selectByVisibleText("2020");
			
			//     WebElement continuebtn=
			driver.findElement(map.getLocator("billing_continuebtn")).click();

		}
		else if (paymentType.equalsIgnoreCase("paypal")||
				(paymentType.equalsIgnoreCase("PayPal")))
		{
			driver.findElement(map.getLocator("billing_paypalid")).click();
			//do something need paypal info from quicksilver
			//for QS just click on Continue btn 
			
               //WebElement continuebtn=
					driver.findElement(map.getLocator("billing_continuebtn")).click();

		}

	}
	
	
	//**************************************************************************************
	
	
	public void selectPaymentOnStep2US(WebDriver driver, String paymentType) throws Exception
	{
		String visa=rp.readConfigProperties("verified_visa");
		String cardSecurity=rp.readConfigProperties("cardPin");
		String master= rp.readConfigProperties("master_nosecurecode");
		
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("billing_allcards")));
		Reporter.log("Current page title is "+ driver.getTitle());


		if (paymentType.equalsIgnoreCase("visa"))
		{	
			//driver.findElement(map.getLocator("billing_visaid_US")).click();
			
			WebElement billing_name=driver.findElement(map.getLocator("billing_nameid"));
			billing_name.sendKeys("fluid");

			WebElement cardNumber=driver.findElement(map.getLocator("billing_ccnumberid"));
			cardNumber.sendKeys(visa);

			WebElement cardCode=driver.findElement(map.getLocator("billing_ccsecuritycodeid"));
			cardCode.sendKeys(cardSecurity);

			//select month #4
			new Select(driver.findElement(map.getLocator("billing_ccmonthid"))).selectByVisibleText("04");
			//change year
			new Select(driver.findElement(map.getLocator("billing_ccyearid"))).selectByVisibleText("2020");
			
			
			//     WebElement continuebtn=
			driver.findElement(map.getLocator("billing_continuebtn")).click();

		}

		else if (paymentType.equalsIgnoreCase("master")||
				paymentType.equalsIgnoreCase("mastercard")||
				paymentType.equalsIgnoreCase("master card")||
				paymentType.equalsIgnoreCase("master_card"))
		{
			driver.findElement(map.getLocator("billing_masterid")).click();
			WebElement billing_name=driver.findElement(map.getLocator("billing_nameid"));
			billing_name.sendKeys("fluid");

			try
			{   //uncheck save card
				uncheckSaveCard(driver);
			}
						
			catch(Exception e)
			{				
				System.out.println("GUEST CHECKOUT:'Save card' checkbox is not present; ");
			}
			
			WebElement cardNumber=driver.findElement(map.getLocator("billing_ccnumberid"));
			cardNumber.sendKeys(master);

			WebElement cardCode= driver.findElement(map.getLocator("billing_ccsecuritycodeid"));
			cardCode.sendKeys(cardSecurity);

			//select month #5
		
			new Select(driver.findElement(map.getLocator("billing_ccmonthid"))).selectByVisibleText("05");
			//change year
			new Select(driver.findElement(map.getLocator("billing_ccyearid"))).selectByVisibleText("2020");
			
			//     WebElement continuebtn=
			driver.findElement(map.getLocator("billing_continuebtn")).click();
		}

		//NEED AMEX CARD NUMBER FROM QUICKSILVER
		else if (paymentType.equalsIgnoreCase("amex")||
				paymentType.equalsIgnoreCase("american express")||
				paymentType.equalsIgnoreCase("amex card")||
				paymentType.equalsIgnoreCase("american_express"))
		{
			driver.findElement(map.getLocator("billing_amexid")).click();

			WebElement billing_name=driver.findElement(map.getLocator("billing_nameid"));
			billing_name.sendKeys("fluid");

			try
			{   //uncheck save card
				uncheckSaveCard(driver);
			}						
			catch(Exception e)
			{				
				System.out.println("GUEST CHECKOUT:'Save card' checkbox is not present; ");
			}
			
			WebElement cardNumber=driver.findElement(map.getLocator("billing_ccnumberid"));
			cardNumber.sendKeys("REPLACE THIS WITH AMEX CARD");

			WebElement cardCode= driver.findElement(map.getLocator("billing_ccsecuritycodeid"));
			cardCode.sendKeys(cardSecurity);

			//select month #5
			new Select(driver.findElement(map.getLocator("billing_ccmonthid"))).selectByVisibleText("05");
			//year
			new Select(driver.findElement(map.getLocator("billing_ccyearid"))).selectByVisibleText("2020");
			
			//     WebElement continuebtn=
			driver.findElement(map.getLocator("billing_continuebtn")).click();

		}
		else if (paymentType.equalsIgnoreCase("paypal")||
				(paymentType.equalsIgnoreCase("PayPal")))
		{
			driver.findElement(map.getLocator("billing_paypalid")).click();
			//do something need paypal info from quicksilver
			//for QS just click on Continue btn 
			
               //WebElement continuebtn=
					driver.findElement(map.getLocator("billing_continuebtn")).click();

		}

	}
	
	
	
	
//*********************************************************	
	
	
	
	
	
	//using email and password supplied as parameters to the method
	public ArrayList<String> createTestAccount(WebDriver driver, String email, String password) throws Exception
	{
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("register_link")));
		
		WebElement registerLink =driver.findElement(map.getLocator("register_link"));
		registerLink.click();
		
		String title = driver.getTitle();
		Reporter.log("After click on 'Register' link in the header page title is "+title);

		wait.until(ExpectedConditions.titleContains("Account"));       
		
//		new Select(driver.findElement(map.getLocator("registration_salutation"))).selectByVisibleText("Mrs");

		driver.findElement(map.getLocator("registratiion_fname")).clear();
		driver.findElement(map.getLocator("registratiion_fname")).sendKeys("Fluid");
		
		driver.findElement(map.getLocator("registration_lname")).clear();
		driver.findElement(map.getLocator("registration_lname")).sendKeys("Fluid");
		//GENDER- n/a for US
        
        
	      
		//	new Select(driver.findElement(map.getLocator("registration_genderxpath"))).selectByVisibleText("Male");
			
		driver.findElement(map.getLocator("registration_email")).clear();		
		driver.findElement(map.getLocator("registration_email")).sendKeys(email);

		driver.findElement(map.getLocator("registration_emailconfirm")).clear();
		driver.findElement(map.getLocator("registration_emailconfirm")).sendKeys(email);

		driver.findElement(map.getLocator("registration_password")).clear();
		driver.findElement(map.getLocator("registration_password")).sendKeys(password);

		driver.findElement(map.getLocator("registration_confirmpassword")).clear();
		driver.findElement(map.getLocator("registration_confirmpassword")).sendKeys(password);

		driver.findElement(map.getLocator("registration_address1")).clear();
		driver.findElement(map.getLocator("registration_address1")).sendKeys("5050 Haven pl");

		driver.findElement(map.getLocator("registration_address2")).clear();
		driver.findElement(map.getLocator("registration_address2")).sendKeys("apt 1");

		driver.findElement(map.getLocator("registration_zip")).clear();
		
		//this might be better to be replaced with slow type() method
		slowType(driver, (map.getLocator("registration_zip")), "94132");
		driver.findElement(map.getLocator("registration_city")).clear();
		driver.findElement(map.getLocator("registration_city")).sendKeys("san francisco");
		
		
		//select 5th state from the drop down
		selectOptionFromSelect(driver,map.getLocator("registration_state"), 6);

		
		

		
		driver.findElement(map.getLocator("registration_phone")).clear();
		driver.findElement(map.getLocator("registration_phone")).sendKeys("1234567");

	
		
		//click on confirm btn
		driver.findElement(map.getLocator("registration_confirmbtn")).click();

		//wait until logout link is present to indicate that acct was successfully created
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("intercept_signoutlink")));

		List<String> account= new ArrayList<String>();
		account.add(email);
		account.add(password);
		String email_password="User selected: "+email+" password: "+password;

		appendToTxt(System.getProperty("user.dir")+createdAccountsPath, email_password);	        
		//should return email and password
		return (ArrayList<String>) account;
		
		
	}
	
	//using randomly generated email+password 12345
	public ArrayList<String> createTestAccount(WebDriver driver) throws Exception
	{
		//need to generate random string to avoid error 'Email taken'
				String email = generateEmail(8);
				String password = "12345";
				
				
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("register_link")));
		
		WebElement registerLink =driver.findElement(map.getLocator("register_link"));
		registerLink.click();
		
		String title = driver.getTitle();
		Reporter.log("After click on 'Register' link in the header page title is "+title);

		wait.until(ExpectedConditions.titleContains("Account"));     

		
		
		driver.findElement(map.getLocator("registratiion_fname")).clear();
		driver.findElement(map.getLocator("registratiion_fname")).sendKeys("Veronica");
		
		driver.findElement(map.getLocator("registration_lname")).clear();
		driver.findElement(map.getLocator("registration_lname")).sendKeys("Peter");
		//GENDER
         
		new Select(driver.findElement(map.getLocator("registration_genderxpath"))).selectByVisibleText("Female");
		

		driver.findElement(map.getLocator("registration_email")).clear();	
		
		driver.findElement(map.getLocator("registration_email")).sendKeys(email);

		driver.findElement(map.getLocator("registration_emailconfirm")).clear();
		driver.findElement(map.getLocator("registration_emailconfirm")).sendKeys(email);

		driver.findElement(map.getLocator("registration_password")).clear();
		driver.findElement(map.getLocator("registration_password")).sendKeys(password);

		driver.findElement(map.getLocator("registration_confirmpassword")).clear();
		driver.findElement(map.getLocator("registration_confirmpassword")).sendKeys(password);

		driver.findElement(map.getLocator("registration_address1")).clear();
		driver.findElement(map.getLocator("registration_address1")).sendKeys("5050 Haven p ;");

		driver.findElement(map.getLocator("registration_address2")).clear();
		driver.findElement(map.getLocator("registration_address2")).sendKeys("apt 1");

		driver.findElement(map.getLocator("registration_zip")).clear();
		
		//replaced with slow type() method
		slowType(driver, (map.getLocator("registration_zip")), "SE3 0RL");
	    
		driver.findElement(map.getLocator("registration_phone")).clear();
		driver.findElement(map.getLocator("registration_phone")).sendKeys("1234567");

		//select and deselect
		driver.findElement(map.getLocator("registration_addtositeemail")).click();
		driver.findElement(map.getLocator("registration_addtositeemail")).click();

		
		//select and deselect
		driver.findElement(map.getLocator("register_addtopartneremail")).click();
		driver.findElement(map.getLocator("register_addtopartneremail")).click();
		
		//click on confirm btn
		driver.findElement(map.getLocator("registration_confirmbtn")).click();
		
		//wait until logout link is present to indicate that acct was successfully created
		wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("intercept_signoutlink")));

		List<String> account= new ArrayList<String>();
		account.add(email);
		account.add(password);
		String email_password="Randomly generated: "+email+" password: "+password;
 
		appendToTxt(System.getProperty("user.dir")+createdAccountsPath, email_password);	        
        System.out.println("Created new account using "+email_password);
		return (ArrayList<String>) account;
		//should return email and password
		}
		
		//US site functionality
		//Clicking login link
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("login_link_US")));
		
		WebElement registerLink =driver.findElement(map.getLocator("login_link_US"));
		registerLink.click();
		String title = driver.getTitle();
		Reporter.log("After click on 'Register' link in the header page title is "+title);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("login_create_acc_US")));

		//Click on New Account button
		driver.findElement(map.getLocator("login_create_acc_US")).click();
		
		
		
		wait.until(ExpectedConditions.titleContains("Account")); 
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("registration_salutation")));
		
		new Select(driver.findElement(map.getLocator("registration_salutation"))).selectByVisibleText("Mrs");
		driver.findElement(map.getLocator("registratiion_fname")).clear();
		driver.findElement(map.getLocator("registratiion_fname")).sendKeys("Fluid");
		
		driver.findElement(map.getLocator("registration_lname")).clear();
		driver.findElement(map.getLocator("registration_lname")).sendKeys("Fluid");

driver.findElement(map.getLocator("registration_email")).clear();	
		
		driver.findElement(map.getLocator("registration_email")).sendKeys(email);

		driver.findElement(map.getLocator("registration_emailconfirm")).clear();
		driver.findElement(map.getLocator("registration_emailconfirm")).sendKeys(email);

		driver.findElement(map.getLocator("registration_password")).clear();
		driver.findElement(map.getLocator("registration_password")).sendKeys(password);

		driver.findElement(map.getLocator("registration_confirmpassword")).clear();
		driver.findElement(map.getLocator("registration_confirmpassword")).sendKeys(password);
		
		driver.findElement(map.getLocator("registration_address1")).clear();
		driver.findElement(map.getLocator("registration_address1")).sendKeys("5050 Haven pl");

		driver.findElement(map.getLocator("registration_address2")).clear();
		driver.findElement(map.getLocator("registration_address2")).sendKeys("apt 1");

		driver.findElement(map.getLocator("registration_zip")).clear();
		
		//this might be better to be replaced with slow type() method
		slowType(driver, (map.getLocator("registration_zip")), "94132");
		driver.findElement(map.getLocator("registration_city")).clear();
		driver.findElement(map.getLocator("registration_city")).sendKeys("san francisco");
		
		
		//select 5th state from the drop down
		new Select(driver.findElement(map.getLocator("registration_state_US"))).selectByVisibleText("California");
//		selectOptionFromSelect(driver,map.getLocator("registration_state_US"), 6);

		driver.findElement(map.getLocator("registration_phone")).clear();
		driver.findElement(map.getLocator("registration_phone")).sendKeys("2024458287");
		//click on confirm btn
				driver.findElement(map.getLocator("registration_confirmbtn")).click();
				
				//wait until logout link is present to indicate that acct was successfully created
				wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("intercept_signoutlink")));

				List<String> account= new ArrayList<String>();
				account.add(email);
				account.add(password);
				String email_password="Randomly generated: "+email+" password: "+password;
		 
				appendToTxt(System.getProperty("user.dir")+createdAccountsPath, email_password);	        
		        System.out.println("Created new account using "+email_password);
				return (ArrayList<String>) account;
				//should return email and password

	}
	
	public void  verificationClickPlaceOrder(WebDriver driver) throws Exception 
	{
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("verification_termsconditionsid")));

		WebElement verification=driver.findElement(map.getLocator("verification_termsconditionsid"));
		verification.click();
		System.out.println("terms and conditions on Verification page checked "+verification.isSelected());
		Assert.assertEquals((verification.isSelected()), true);

		ts.takeScreenshot(driver);
		Reporter.log("Current page title: "+driver.getTitle());
		WebElement placeOrder= driver.findElement(map.getLocator("verification_placeorderbtn"));
		placeOrder.click();	
		  return;
		}
		
		WebElement placeOrder= driver.findElement(map.getLocator("verification_placeorderbtn"));
		placeOrder.click();	
				

	}

	public void submitConfirmation(WebDriver driver) throws Exception
	{
		//on Confirmation page 
		String title= driver.getTitle();
		String expectedTitle="Thank You";

		//Assert.assertTrue(driver.getTitle().contains(expectedTitle), "expected title contains: "+expectedTitle+" but received "+driver.getTitle());
		//get order number

		String orderNum= driver.findElement(map.getLocator("order_numbervalue")).getText();
		Reporter.log("On 'Confirmation' page order number is "+orderNum);
		Reporter.log("This page title is :"+title);
		Reporter.log("Current page title: "+driver.getTitle());
		Reporter.log("On 'Confirmation' page order number is "+orderNum);
		
		appendToTxt(System.getProperty("user.dir")+submittedOrdersPath, orderNum);	        
	}


}