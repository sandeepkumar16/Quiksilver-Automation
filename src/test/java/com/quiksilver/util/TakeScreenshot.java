/*package com.quiksilver.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;


public class TakeScreenshot {
		
	ReadingProperties rp = new ReadingProperties();
	String failPath=rp.readConfigProperties("fail.screenshot.path");
	String passPath=rp.readConfigProperties("pass.screenshot.path");
	String usergenPath =rp.readConfigProperties("usergen.screenshot.path");	
	
		public  void takeScreenshot(WebDriver driver)
	    
	    {
			
	   	 WebDriverManager.getBrowser(driver);	        
	   	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
		String destDir = System.getProperty("user.dir")+usergenPath;
		new File(destDir).mkdirs();
		String destFile = dateFormat.format(new Date()) + ".png";

		try {
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not take screenshot");//getInstanceName =package+className
		}
	                
	         //Reporter.log("<img src=\"file:///" + str +"/target/screenshot/"+ screenshotName + "\" alt=\"\"/><br/>");
	 		//Reporter.setEscapeHtml(false);
	 		Reporter.log("Saved <a href=../screenshot/UserGen/" + destFile + ">Screenshot</a>");
	    }

		
	}


*/