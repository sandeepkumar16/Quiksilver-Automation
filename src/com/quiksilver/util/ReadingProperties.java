package com.quiksilver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;


//this is a class for reading from properties files; 
//it has 2 methods: one for reading from locators.properties and another for environment.properties
//Test class is for unit testing (which was successful);

public class ReadingProperties {

	public   String value;
	public  Logger loger =Logger.getLogger("myLogger");
	public String key;
	Properties property=new Properties();
	

	//**************************************************************
	public  String readORProperties(String key)
	{

		File file = new  File("OR.properties");
		try {
			System.out.println("path is "+file.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//read from the properties file		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(System.getProperty("user.dir")+"/src/com/quiksilver/config/OR.properties");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {

			property.load(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		if (property.getProperty(key)!=null)//check if key exists in config.properties file
{
		System.out.println("Successfully read from OR.properties file <"+ key+">");
		value =	property.getProperty(key);
}

		else 
		{
			loger.debug("Could not read specified key from the OR.properties file <"+key+">");
			
		}
		return value;
	}

	//*****************************************************************************
	
	public  String readConfigProperties(String key)
	{
		File file = new  File("config.properties");
		try {
			System.out.println("path is "+file.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//read from the properties file		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(System.getProperty("user.dir")+"/src/com/quiksilver/config/config.properties");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Make sure path to config.properties is correct.");
		}
		try {

			property.load(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//String val=property.getProperty(key);

		if (property.getProperty(key)!=null)//check if key exists in config.properties file
{
		 System.out.println("Successfully read from config.properties file <"+ key+">");
       //loger.info("Successfully read from config.properties file <"+ key+">");
		value =	property.getProperty(key);
}

else 
{
	loger.debug("Could not read specified key from the config.properties file <"+key+">");
}
		return value;
	}
	
	/*******************************************/
	//*****************************************************************************

}


//THIS IS A small TEST FOR 3 readingProperties methods
class TestReadingProperies
{
	public static void main (String args[])
	{
		Logger log = Logger.getLogger("LOGGER");
		ReadingProperties rp= new ReadingProperties();
		String configVal =rp.readConfigProperties("master_nosecurecode");
		System.out.println("<master_nosecurecode> :" + configVal);
		
		String m = rp.readConfigProperties("verified_visa");
		System.out.println("value for <verified_visa> is "+m);

		log.info("reading report_file_name frop config.properties file value is <"+ configVal+">");
		//log.info(orVal1);
		
	}
}
