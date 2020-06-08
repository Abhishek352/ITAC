package com.library;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelFileReader {
	XSSFSheet sheet = null;
	int columnIndexByCoumnName = 1;
	LinkedHashMap<String, String> hashtable= new LinkedHashMap<String, String>();
	public static XSSFWorkbook workbook1=null;	
	//public static String resultsFilePath=null;
	public static String testDataFilePath=null;
	public static String sheetName1=null;
	public static String testPlanFilePath=null;

	public void testMain(Object[] args) {
		
	}

	public void prepareTestPlanSheet(String fileName, String sheetName){
		testPlanFilePath=fileName;
		XSSFWorkbook workbook;
		try {
			File file=new File(testPlanFilePath);
			closeExcelIfOpen(file);
			FileInputStream input = new FileInputStream(file);

			workbook = new XSSFWorkbook(input);
			workbook1 = workbook;
			sheet = workbook.getSheet(sheetName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeExcelIfOpen(File file) throws IOException{
		File sameFileName = new File(file.getAbsolutePath());
		if(!(file.renameTo(sameFileName))){
			System.out.println("Closing file "+file.getName());
			Runtime.getRuntime().exec("cmd /c taskkill /f /im excel.exe");
		}
	}

	public void prepareTestDataSheet(String fileName, String sheetName){
		//System.out.println("Sheet Open");
		testDataFilePath=fileName;
		XSSFWorkbook workbook;
		FileInputStream input=null;
		try {			
			File file=new File(testDataFilePath);
			closeExcelIfOpen(file);
			input= new FileInputStream(file);
			workbook = new XSSFWorkbook(input);
			workbook1 = workbook;
			sheet = workbook.getSheet(sheetName);
			sheetName1=sheetName;
			//System.out.println("Sheet : "+fileName);
		} catch (Exception e) {
			System.out.println("Test data Sheet Crashed");
			e.printStackTrace();
		}
		finally{
			try {
				input.close();
				//System.out.println("Sheet Closed");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public XSSFSheet getXSSFSheet(){
		return sheet;
	}

	public int getTotalNoOfRows(){
		return sheet.getPhysicalNumberOfRows();
	}

	public int getColumnIndexByColumnName(String columnName) {
		int defColIndex=-1;
		try {			
			Row row = sheet.getRow(0);
			Iterator<Cell> cells = row.cellIterator();
			while (cells.hasNext()) {
				Cell cell = cells.next();
				String value = convertToString(cell);
				if (value.equalsIgnoreCase(columnName)) {
					defColIndex = cell.getColumnIndex();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return defColIndex;
	}

	public int getRowIndexByRowName(String rowName) {
		int reqRowIndex=-1;
		try {			
			Row row = sheet.getRow(0);
			Iterator<Cell> cells = row.cellIterator();
			while (cells.hasNext()) {
				Cell cell = cells.next();
				String value = convertToString(cell);
				if (value.equalsIgnoreCase(rowName)) {
					reqRowIndex = cell.getRowIndex();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return reqRowIndex;
	}

	public String getCellValue(int rowIndex, int columnIndex){
		if(rowIndex == -1){
			System.out.println("Row is not identified with specified text");
			return "";
		}
		Row row = sheet.getRow(rowIndex);
		Cell cell = row.getCell(columnIndex);
		if(cell == null){
			return "";
		}
		return convertToString(cell);
	}

	public void setCellValue(int rowIndex, int columnIndex,String value){
		FileOutputStream fos = null;
		try{
			if(rowIndex == -1){
				System.out.println("Row is not identified with specified text");
			}
			Row row = sheet.getRow(rowIndex);
			Cell cell = row.createCell(columnIndex);
			cell.setCellValue(value);
			System.out.println("OK");
			fos = new FileOutputStream(new File(testDataFilePath));
			workbook1.write(fos);
			fos.flush();
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(null != fos && null != workbook1) {
				try {
					fos.close();
					workbook1 = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method is used to get the index of the row which contains the string that is passed to the method
	 * @param propertyName: The string to search for in the excel sheet, whose row index is required.
	 * @return: Returns the index of the row which contains the string passed
	 */
	public int getRowIndexBy(String propertyName) {
		int rowIndex= -1;
		for(int i=1;i<=sheet.getLastRowNum();i++){
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(0);
			String value = convertToString(cell);
			if (value.equalsIgnoreCase(propertyName)) {
				rowIndex = row.getRowNum();
				break;
			}
		}
		return rowIndex;
	}

	private String convertToString(Cell cell){
		Object value="";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			value = cell.getNumericCellValue();
			break;
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		}
		if(value == null){
			value="";
		}

		return value.toString();
	}

	public String getValueByProperty(String propertyName){
		int rowIndex = getRowIndexBy(propertyName);
		String value = getCellValue(rowIndex, columnIndexByCoumnName);
		return value;
	}

	public String getValueBy(int rowIndex, String columnName){
		int colIndex = getColumnIndexByColumnName(columnName);
		String value = getCellValue(rowIndex, colIndex);
		return value;
	}


	public String getValueByRowText(String rowIdentifier,String columnIdentifier){
		int rowIndex=getRowIndexBy(rowIdentifier);
		int colIndex = getColumnIndexByColumnName(columnIdentifier);
		String value = getCellValue(rowIndex, colIndex);
		return value;
	}
	
	public void setCellValueByRowColIdentifier1(int rowId,int colId,String value){
		FileOutputStream fos = null;
		try{
			FileInputStream fis = new FileInputStream(new File(testDataFilePath));
			XSSFWorkbook workbook12 = new XSSFWorkbook (fis);
			sheet = workbook12.getSheet(sheetName1);
			//int colIndex=getColumnIndexByColumnName(colId);
			//int rowIndex=getRowIndexBy(rowId);
			//System.out.println(colId);
			//System.out.println(rowId);
			Cell reqCell=getCell(rowId, colId);				
			reqCell.setCellValue(value);
			//System.out.println("testDataFilePath : "+testDataFilePath);
			fos = new FileOutputStream(new File(testDataFilePath));
			//System.out.println(workbook12.getActiveSheetIndex());
			fis.close();
			/*workbook1.write(fos);
			fos.flush();
			fos.close();*/
			//fos =new FileOutputStream(new File(testDataFilePath));
			workbook12.write(fos);
			fos.flush();
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setCellValueByRowColIdentifier1(String rowId,String colId,String value){
		FileOutputStream fos = null;
		try{
			FileInputStream fis = new FileInputStream(new File(testDataFilePath));
			XSSFWorkbook workbook12 = new XSSFWorkbook (fis);
			sheet = workbook12.getSheet(sheetName1);
			int colIndex=getColumnIndexByColumnName(colId);
			int rowIndex=getRowIndexBy(rowId);
			System.out.println(colIndex);
			System.out.println(rowIndex);
			Cell reqCell=getCell(rowIndex, colIndex);				
			reqCell.setCellValue(value);
			System.out.println("testDataFilePath : "+testDataFilePath);
			fos = new FileOutputStream(new File(testDataFilePath));
			System.out.println(workbook12.getActiveSheetIndex());
			fis.close();
			/*workbook1.write(fos);
			fos.flush();
			fos.close();*/
			//fos =new FileOutputStream(new File(testDataFilePath));
			workbook12.write(fos);
			fos.flush();
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getTcIDByColIdentifierAndColValue(String colValue){
		int rowIndex= -1;
		int colIndex=getColumnIndexByColumnName("TestScript_Name");
		System.out.println("colIndex : "+colIndex);
		for(int i=1;i<=sheet.getPhysicalNumberOfRows();i++){		
			if(getCellValue(i, colIndex).equalsIgnoreCase(colValue)){
				rowIndex=i;
				System.out.println("rowIndex : "+rowIndex);
				break;
			}
		}
		int reqColIndex=getColumnIndexByColumnName("TC_ID");
		return getCellValue(rowIndex, reqColIndex);
	}

	public Cell getCell(int rowIndex, int columnIndex){
		Row row = sheet.getRow(rowIndex);
		Cell cell = row.getCell(columnIndex);
		if(null != cell) {
			return cell;
		} else {
			cell = row.createCell(columnIndex);
			return cell;
		}
	}

	public int getRowCount(){
		int rowCount=-1;
		try{
			rowCount = sheet.getPhysicalNumberOfRows();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;	
	}

	public void setCellValueByRowColIdentifier(String rowId,String colId,String value){
		FileOutputStream fos = null;
		try{
			int colIndex=getColumnIndexByColumnName(colId);
			int rowIndex=getRowIndexBy(rowId);
			Cell reqCell=getCell(rowIndex, colIndex);				
			reqCell.setCellValue(value);
			fos = new FileOutputStream(new File(testDataFilePath));
			workbook1.write(fos);
			fos.flush();
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(null != fos && null != workbook1) {
				try {
					fos.close();
					workbook1 = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}