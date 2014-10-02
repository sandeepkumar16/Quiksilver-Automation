package com.quiksilver.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream; 
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;

public class Return2D {
	int rowIndex = 0, columnIndex = 0;
	Workbook wb;
	Sheet ws;
	Row wr;
	Cell cell;
	String fileName, sheetName;
	
	//One Object is Hashtable ; second Object is array []
	Hashtable <String, String> [] data = null;	
	
	  public boolean isSheetExist(String sheetName){
	        int index = wb.getSheetIndex(sheetName);
	        if(index==-1){
	            index=wb.getSheetIndex(sheetName.toUpperCase());
	                if(index==-1)
	                    return false;
	                else
	                    return true;
	        }
	        else
	            return true;
	    }
	  
	@SuppressWarnings("deprecation")
	public  void open(String fileName, String sheetName) throws IOException  {
		
		this.fileName = fileName;
		this.sheetName = sheetName;
		if (fileName.indexOf("xlsx") < 0) 
			
		{					
			wb = new HSSFWorkbook(new FileInputStream(new File(fileName)));
			if (isSheetExist(sheetName)==false)
			{
				System.out.println("Specified sheet does NOT EXIST. Create sheet to fix NPE exception.");
			}
			else
			ws = wb.getSheet(sheetName);			
		} else {
			wb = new XSSFWorkbook(fileName);
			if (isSheetExist(sheetName)==false)
			{
				System.out.println("Specified sheet does NOT EXIST. Create sheet to fix NPE exception.");
			}
			else
			ws = wb.getSheet(sheetName);	
			ws = (XSSFSheet) wb.getSheet(sheetName);			
		}			
	}
	
	private  static String getCellValue(Cell cell) {
	    if (cell == null) {
	        return "";
	    }
	    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	        return cell.getStringCellValue();
	    } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	        return cell.getNumericCellValue() + "";
	    } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	        return cell.getBooleanCellValue() + "";
	    }else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
	        return cell.getStringCellValue();
	    }else if(cell.getCellType() == Cell.CELL_TYPE_ERROR){
	        return cell.getErrorCellValue() + "";
	    } 
	    else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
			return ""; 
	    }
	    else {
	        return null;
	    }
	}
	
	@SuppressWarnings("unchecked")
	public Object[][] getData() throws IOException  {	
		data = new Hashtable[ws.getPhysicalNumberOfRows()];     
		
		//header
		wr = ws.getRow(0);	
		
		for(rowIndex = 1; rowIndex < ws.getPhysicalNumberOfRows(); rowIndex++) {
		//	System.out.println("Physically def rows "+ ws.getPhysicalNumberOfRows());
			data[rowIndex - 1] = new Hashtable <String, String>();
			
			for (columnIndex = 0; columnIndex < ws.getRow(rowIndex).getPhysicalNumberOfCells(); columnIndex++) {
					
				cell =ws.getRow(rowIndex).getCell(columnIndex) ;
//need to handle empty cells 
				String cellVal = Return2D.getCellValue(cell);
				
				/*to keep track of curr values
				System.out.println("cellVal ="+cellVal);
				System.out.println("column index = "+columnIndex+" rownum=" +ws.getRow(rowIndex).getRowNum());
				System.out.println("wr.getCell(columnIndex).toString()=" +wr.getCell(columnIndex).toString());
		        System.out.println("ws.getRow(rowIndex).getCell(columnIndex).toString() ="+ ws.getRow(rowIndex).getCell(columnIndex).toString());
				putting 2 Strings
				*/
				
				data[rowIndex - 1].put(wr.getCell(columnIndex).toString(), cellVal);
			}		
		}
		
		//[1] - stands for one array to hold 'n' number of Hashtable<String, String>[] arrays; where n=row numbers in xlsx spreedsheet
		Object[][] obj = new Object[data.length - 1][1];		 
		for(int i = 0; i < data.length - 1; i++) {
			obj[i][0] = data[i];
		}
		return obj;		
	}


       public  void close() throws IOException  {
            wb = null;
            ws = null;
            wr = null;
            data = null;
	}
	
}

class TestReturn2D{
	public static void main (String args[]) throws IOException
	{
		ReadingProperties rp = new ReadingProperties();
		String excelPath= rp.readConfigProperties("excel.path");
		
		Return2D return2d = new Return2D();
		return2d.open(System.getProperty("user.dir")+excelPath, "TestData");
		return2d.getData();

		Object[][] data = return2d.getData();
		
	}
}
