package com.quiksilver.util;


import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.sql.Timestamp;
import java.util.Date;


import java.text.SimpleDateFormat;

public class TimeUtil 
{
	public String testStartTime, finalTime;
	
	ReadingProperties rp = new ReadingProperties();
	
	public String CurrentDate() {
		  Calendar currentDate = Calendar.getInstance();
		  SimpleDateFormat formatter=new SimpleDateFormat("yyyy_MMM_dd");
		  String dateNow = formatter.format(currentDate.getTime());
		  return dateNow;
	}
	
	public String CurrentTime() {
		Calendar currentTime = Calendar.getInstance();
		String am_pm;
		  int hour = currentTime.get(Calendar.HOUR);
		  int minute = currentTime.get(Calendar.MINUTE);
		  int second = currentTime.get(Calendar.SECOND);
		  if(currentTime.get(Calendar.AM_PM) == 0)
		  am_pm = "AM";
		  else
		  am_pm = "PM";
		  String timeNow = am_pm+"_"+hour+"_"+minute+"_"+second;
		  return timeNow;
	}
	public static String  getTimeStamp( )
    {
	 Date date= new Date();
	 Timestamp timestamp= new Timestamp(date.getTime());
	 
	 System.out.println("Getting timestamp @" +timestamp);
	return timestamp.toString();
    }
}
