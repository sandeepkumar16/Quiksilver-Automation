package com.quiksilver.util;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*
 * This class is otpional experimental class; create spreadsheet manually and update path in config.properties
 * if needed.
 */

public class InitTestXls {

	static ReadingProperties rp = new ReadingProperties();
	static String excelPath = rp.readConfigProperties("excel.path");
	
	//for some reason column B aka 1 is not readable??? but if i replace it manually - then it is readable??

	//'Loyalty', 'Additional info', are optional
	private static final String[] titles = {
		"FNAME", " LNAME", "PHONE", "EMAIL", "ZIP", "ADDRESS1", "ADDRESS2",  "LOYALTY","ADDITIONAL INFO" };

	//sample data to fill the sheet.
	private static final String[][] data = {
		//Belgium De Brouckèreplein
		{"Fluid", "QA", "9255431122", "test@yahoo.com", "L75 1WX", "31 De Brouckereplein", null, null, "please leave it with my neighbour"},
		//Czech Rep
		{"Fluid", "QA", "9255431122", "test@gmail.com", "W1D 3DH", "123 Richmond Mews", null, null, "here is my note"},

		//Toldbodgade 24- 28, Copenhagen 1253, Denmark  011 45 33 74 14 14
		{"Fluid", "QA", "9255431122", "test@hotmail.com", "SE3 0RL", " 498 Montpelier Row", "apt 35", null, null},

		//Swissotel Tallinn  Tornimae 3, Tallinn 10145, Estonia
		{"Fluid", "QA", "9255431122", "fluid@gmail.com", "SW13 9LJ", "31 De 14A Saint Anns Road", null, null, "test from Fluid"},

		//Hotel Espana Carrer Sant Pau 9-11, 08001 Barcelona, Spain
		{"Fluid", "QA", "9255431122", "fluid1@yahoo.com", "M1 2JZ", "40 Laystall St", null, null, "fluid Manchester address"},

		//10 Syngrou Avenue, Athens 11742, Greece
		{"Fluid", "QA", "9255431122", "fluid2@yahoo.com", " M32 9DH", "1515 sunset Rd", "apt C", null, null},
		
/*IF NEED TO INITIALIZE MORE ROWS WITH TEST DATA simply uncomment this lines and replace values
 * //Via Tommaso Grossi 1, 20121 Milan, Italy
		{"fluid", "super", "442 453 3741", "test@yahoo.com", "20121", "Via Tommaso Grossi", null, "1", "Milan", "Italy"},

		//Veca Ostmala 40, Liepaja 3401, Latvia 
		{"fluid", "awesome", "4424533748", "test@msn.com", "3401", "Veca Ostmala", null, "40", "Liepaja", "Latvia"},

		////Grand Rue, 9, Clervaux 9710, Luxembourg
		{"fluid", "super", "4424533748", "test@latvia.com", "9710", "Grand Rue", null, "9", "Clervaux", "Luxembourg"},

		//  Erzsebet korut 8, Budapest 1073, Hungary
		{"fluid", "great", "4424533748", "testHungary@gmail.com", "1073", "Erzsebet korut", null, "8", "Budapest", "Hungary"},

		//	            Amstel 144, Amsterdam 1017 AE, The Netherlands SPECIAL ZIP!
		{"fluid", "regLname", "4424533738", "test_amsterdam@gmail.com", "1017 AE", "Amstel", null, "144", "Amsterdam", "Netherlands"},

		// Aleje Jerozolimskie 45, Warsaw 00-692 , Poland SPECIAL ZIP!
		{"fluid", "regLname", "4424533738", "test_poland@gmail.com", "00-692", "Aleje Jerozolimskie", null, "45", "Warsaw", "Poland"},

		//Rua Padre Manuel da Nobrega 111, Porto 4350-226 , Portugal SPECIAL ZIP!
		{"FLUID", "regLname", "4524533738", "test_poRtugal@gmail.com", "4350-226", "Rua Padre Manuel da Nobrega", null, "111", "Porto", "Portugal"},

		//Hlavna 1, Kosice 040 01, Slovakia
		{"FLUID", "regLname", "4524533738", "test_slovakia@gmail.com", "040 01", "Hlavna", null, "1", "Kosice","Slovakia"},

		// Slovenska Cesta 15, Ljubljana 1000, Slovenia 
		{"FLUID", "regLname", "4524533738", "testSlovenia@gmail.com", "1000", "Slovenska Cesta", null, "15", "Ljubljana", "Slovenia"},

		//Pohjoisesplanadi 29, Helsinki 00100, Finland SPECIAL ZIP!
		{"FLUID", "regLname", "4524533738", "testFinland@hotmail.com", "00100", "Pohjoisesplanadi", null, "29", "Helsinki", "Finland"},

		//Nils Ericssons Plan 4, Stockholm 11164, Sweden
		{"FLUID", "regLname", "4524532738", "testSweden@hotmail.com", "11164", "Nils Ericssons Plan", null, "4", "Stockholm", "Sweden"},

	//Via Tommaso Grossi 1, 20121 Milan, Italy
		{"fluid", "super", "442 453 3741", "test@yahoo.com", "20121", "Via Tommaso Grossi", null, "1", "Milan", "Italy"},

		//Veca Ostmala 40, Liepaja 3401, Latvia 
		{"fluid", "awesome", "4424533748", "test@msn.com", "3401", "Veca Ostmala", null, "40", "Liepaja", "Latvia"},

		////Grand Rue, 9, Clervaux 9710, Luxembourg
		{"fluid", "super", "4424533748", "test@latvia.com", "9710", "Grand Rue", null, "9", "Clervaux", "Luxembourg"},

		//  Erzsebet korut 8, Budapest 1073, Hungary
		{"fluid", "great", "4424533748", "testHungary@gmail.com", "1073", "Erzsebet korut", null, "8", "Budapest", "Hungary"},

		//	            Amstel 144, Amsterdam 1017 AE, The Netherlands SPECIAL ZIP!
		{"fluid", "regLname", "4424533738", "test_amsterdam@gmail.com", "1017 AE", "Amstel", null, "144", "Amsterdam", "Netherlands"},

		// Aleje Jerozolimskie 45, Warsaw 00-692 , Poland SPECIAL ZIP!
		{"fluid", "regLname", "4424533738", "test_poland@gmail.com", "00-692", "Aleje Jerozolimskie", null, "45", "Warsaw", "Poland"},

		//Rua Padre Manuel da Nobrega 111, Porto 4350-226 , Portugal SPECIAL ZIP!
		{"FLUID", "regLname", "4524533738", "test_poRtugal@gmail.com", "4350-226", "Rua Padre Manuel da Nobrega", null, "111", "Porto", "Portugal"},

		//Hlavna 1, Kosice 040 01, Slovakia
		{"FLUID", "regLname", "4524533738", "test_slovakia@gmail.com", "040 01", "Hlavna", null, "1", "Kosice","Slovakia"},

		// Slovenska Cesta 15, Ljubljana 1000, Slovenia 
		{"FLUID", "regLname", "4524533738", "testSlovenia@gmail.com", "1000", "Slovenska Cesta", null, "15", "Ljubljana", "Slovenia"},

		//Pohjoisesplanadi 29, Helsinki 00100, Finland SPECIAL ZIP!
		{"FLUID", "regLname", "4524533738", "testFinland@hotmail.com", "00100", "Pohjoisesplanadi", null, "29", "Helsinki", "Finland"},

		//Nils Ericssons Plan 4, Stockholm 11164, Sweden
		{"FLUID", "regLname", "4524532738", "testSweden@hotmail.com", "11164", "Nils Ericssons Plan", null, "4", "Stockholm", "Sweden"},
*/
	};

		
	public static void main(String[] args) throws Exception {
		Workbook wb;

		if(args.length > 0 && args[0].equals("-xls")) wb = new HSSFWorkbook();
		else wb = new XSSFWorkbook();

		Map<String, CellStyle> styles = createStyles(wb);
		Sheet sheet = wb.createSheet("TestData");

		//turn off gridlines
		sheet.setDisplayGridlines(true);
		sheet.setPrintGridlines(true);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);

		//the following three statements are required only for HSSF
		sheet.setAutobreaks(true);
		printSetup.setFitHeight((short)1);
		printSetup.setFitWidth((short)1);

		//the header row: centered text in 48pt font
		Row headerRow = sheet.createRow(0);
		headerRow.setHeightInPoints(12.75f);
		for (int i = 0; i < titles.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(styles.get("header"));
		}

		Row row;
		Cell cell;
		int rownum = 1;
		for (int i = 0; i < data.length; i++, rownum++) {
			row = sheet.createRow(rownum);
			if(data[i] == null) continue;

			for (int j = 0; j < data[i].length; j++) {
				cell = row.createCell(j);
				String styleName;
			//	boolean isHeader = i == 0 || data[i-1] == null;
				
								switch(j){
				case 0:
					if(row==headerRow) {
						styleName = "cell_b";
						cell.setCellValue(data[i][j]);
						//     cell.setCellValue(Double.parseDouble(data[i][j]));
					} else {
						styleName = "cell_normal";
						cell.setCellValue(data[i][j]);
					}
					break;

				default:
					styleName = "cell_normal";
					cell.setCellValue(data[i][j]);

				}

				cell.setCellStyle(styles.get(styleName));
			}
		}

		//set column widths, the width is measured in units of 1/256th of a character width
		//sheet.setColumnWidth(0, 256*15);
	//	sheet.setColumnWidth(2, 256*18);
		for (int i = 0; i < titles.length; i++) {
			
			
			if (i==3)
				sheet.setColumnWidth(i, 256*23);			
			else if(i==4)
				sheet.setColumnWidth(i, 256*9);			
			else if(i==6)
				sheet.setColumnWidth(i, 256*9);	
			else if(i==7)
				sheet.setColumnWidth(i, 256*9);
			else 
				sheet.setColumnWidth(i, 256*16);								
			}
				
		sheet.setZoom(3, 2);

		// Write the output to a file
		String file = System.getProperty("user.dir")+excelPath;
		//if(wb instanceof XSSFWorkbook) file += "x";
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}

	/**
	 * create a library of cell styles
	 */
	private static Map<String, CellStyle> createStyles(Workbook wb){
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		DataFormat df = wb.createDataFormat();

		CellStyle style;
		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("header", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("header_date", style);

		Font font1 = wb.createFont();
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font1);
		styles.put("cell_b", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(font1);
		styles.put("cell_b_centered", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(font1);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_b_date", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(font1);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_g", style);

		Font font2 = wb.createFont();
		font2.setColor(IndexedColors.BLUE.getIndex());
		font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font2);
		styles.put("cell_bb", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(font1);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_bg", style);

		Font font3 = wb.createFont();
		font3.setFontHeightInPoints((short)14);
		font3.setColor(IndexedColors.DARK_BLUE.getIndex());
		font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font3);
		style.setWrapText(true);
		styles.put("cell_h", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		styles.put("cell_normal", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		styles.put("cell_normal_centered", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setWrapText(true);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_normal_date", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setIndention((short)1);
		style.setWrapText(true);
		styles.put("cell_indented", style);

		style = createBorderedStyle(wb);
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styles.put("cell_blue", style);

		return styles;
	}

	private static CellStyle createBorderedStyle(Workbook wb){
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
}