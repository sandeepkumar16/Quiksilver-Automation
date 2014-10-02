package com.quiksilver.test;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;
import com.quiksilver.util.CommonMethods;
import com.quiksilver.util.ObjectMap;
import com.quiksilver.util.ReadingProperties;
import com.quiksilver.util.WebDriverManager;

//this class can be used for possible future testing of Home page
//@Listeners({ com.quicksilver.util.TestListenerFailPass.class })
public class HomePageTest extends BaseSuite {	
     
	@Test
     public void testHome () throws Exception
     {
    	 cm.openHomePage(driver);
    	 Boolean isLoggedIn=cm.isLoggedIn(driver);   	     		
    	 
     }    
  
}
