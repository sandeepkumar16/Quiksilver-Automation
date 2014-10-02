package com.quiksilver.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;

import org.apache.log4j.*;


/*
 * It's very easy to generate your own reports with TestNG with Listeners and Reporters:
Listeners implement the interface org.testng.ITestListener and are notified in real time 
of when a test starts, passes, fails, etc...
Reporters implement the interface org.testng.IReporter and are notified when all the suites 
have been run by TestNG. The IReporter instance receives a list of objects
 that describe the entire test run.
 */

/*
 * I chose to extend TestListenerAdapter, which implements ITestListener with empty methods, 
 * so I don't have to override other methods from the interface that I have no interest in. 
 * If you try public class 'TestScreenshotOnFailure implements ITestResult' - implementing an interface  instead of 
 * extending your class with a TestNG class that implements the interface you want (ITestResult) then
 * you will have to override ALL (15-18) methods declared in ITestResult interface which is not productive
 */

/*THIS CLASS DEFINES RULES for DEFAULT BEHAVIOUR of  org.testng.ITestResult.Failure or Success
 * http://testng.org/javadocs/constant-values.html#org.testng.ITestResult.FAILURE

 * TO USE THIS CLASS:
 * 
 *put this annotation before you class where you define your test methods
 * @Listeners({ com.quicksilver.util.TestListenerFailPass.class })

 */
public class TestListenerFailPass extends TestListenerAdapter {

	
	WebDriver driver;
	private int m_count = 0;
	Logger log=WebDriverManager.LoggerGetInstance();
	

	ReadingProperties rp = new ReadingProperties();
	String failPath=rp.readConfigProperties("fail.screenshot.path");
	String passPath=rp.readConfigProperties("pass.screenshot.path");
	String usergenPath =rp.readConfigProperties("usergen.screenshot.path");
	

	@Override
	public void onTestFailure(ITestResult tr) {
		//log("Failed");//testng logger
		driver   = WebDriverManager.getDriverInstance();
		WebDriverManager.getBrowser(driver);

		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
		String destDir = System.getProperty("user.dir")+failPath;
		new File(destDir).mkdirs();
		String destFile = dateFormat.format(new Date()) + ".png";

		try {
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not take screenshot on failure"+ tr.getInstanceName());//getInstanceName =package+className
			log.debug("Could not take screenshot on failure"+ tr.getInstanceName());//getInstanceName =package+className
		}
		Reporter.setEscapeHtml(false);
		Reporter.log("Saved <a href=../screenshot/FAIL/" + destFile + ">Screenshot</a>");
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		log("Skipped test");
		Reporter.log("Skipped test to avoid test failure due to dependency");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		//log("Pass");
	driver = WebDriverManager.getDriverInstance();

		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
		String destDir = System.getProperty("user.dir")+passPath;
		new File(destDir).mkdirs();
		String destFile = dateFormat.format(new Date()) + ".png";

		try {
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not take screenshot on success"+ tr.getInstanceName());//getInstanceName =package+className

			log.debug("Could not take screenshot on success"+ tr.getInstanceName());//getInstanceName =package+className

		}
		Reporter.setEscapeHtml(false);
		Reporter.log("Saved <a href=../screenshot/PASS/" + destFile + ">Screenshot</a>");
	}

	private void log(String string) {
		System.out.print(string);
		if (++m_count % 40 == 0) {
			System.out.println("");
		}
	}
	
	
	public void onFinish(ISuite suite)
	/*every time testng finished running a testsuite it should create a folder  - label it with suite name and date of run
	 * 
	 */
	{
		
	}
}


