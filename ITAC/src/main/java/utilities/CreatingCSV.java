package utilities;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVWriter;

public class CreatingCSV {

	private static List<List<XSSFCell>> cellGrid1;
	private static List<List<XSSFCell>> cellGrid2;

	public static void convertExcelToCsv() throws Exception {
		//System.out.println("================= Started creating new CSV Files =================");
		
		File directory = new File(System.getProperty("user.dir")+"\\InputData\\csvdata");
		
		if(directory.exists()){
			directory.delete();
		//	System.out.println("Directory Deleted");
		}
		directory.mkdir();
		//System.out.println("Directory Created");
			
		try {
			cellGrid1 = new ArrayList<List<XSSFCell>>();
			cellGrid2 = new ArrayList<List<XSSFCell>>();
			FileInputStream myInput = new FileInputStream(System.getProperty("user.dir")+"\\InputData\\Testinputfile.xlsx");
			@SuppressWarnings("resource")
			XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
			
			
			for (int i=0; i<myWorkBook.getNumberOfSheets(); i++) {

				XSSFSheet mySheet = myWorkBook.getSheetAt(i);
				Iterator<?> rowIter = mySheet.rowIterator();

				while (rowIter.hasNext()) {
					XSSFRow myRow = (XSSFRow) rowIter.next();
					Iterator<?> cellIter = myRow.cellIterator();
					List<XSSFCell> cellRowList1 = new ArrayList<XSSFCell>();
					List<XSSFCell> cellRowList2 = new ArrayList<XSSFCell>();

					while (cellIter.hasNext()) {
						XSSFCell myCell = (XSSFCell) cellIter.next();

						//	System.out.println("column index: "+ myCell.getColumnIndex());

						if(myCell.getColumnIndex() == 0) {
							//	System.out.println("value of index should be 1 is it? "+ myCell.getColumnIndex());
							cellRowList1.add(myCell);
							//	System.out.println("Value added to rowlist2: " + myCell);
						}else {
							//	System.out.println("value of index should be 0 is it?: "+ myCell.getColumnIndex());
							cellRowList2.add(myCell);
							//	System.out.println("Value added to rowlist1: " + myCell);
						}
						
					}
					cellGrid1.add(cellRowList1);
					cellGrid2.add(cellRowList2);
				}
				//System.out.println("Completed copying data from excel for : " + myWorkBook.getSheetName(i) + " sheet");

				if((myWorkBook.getSheetName(i).equalsIgnoreCase("PageComparison"))){

					//System.out.println("Started writting data to CSV file for Page Comparison data");
					//System.out.println("Size of " + "Produrls sheet: " +  cellGrid1.size());
					//System.out.println("Size of " + "Testurls sheet: " +  cellGrid2.size());
					String CSVPath1 = System.getProperty("user.dir")+"\\InputData\\csvdata\\pagecomparison_Prod.csv";
					Convert(CSVPath1, cellGrid1);
					String CSVPath2 = System.getProperty("user.dir")+"\\InputData\\csvdata\\pagecomparison_Test.csv";
					Convert(CSVPath2, cellGrid2);
					//System.out.println("Clearing cell data");
					cellGrid1.clear();
					cellGrid2.clear();
				}else if((myWorkBook.getSheetName(i).equalsIgnoreCase("MissingAssets"))) {
					//System.out.println("Started writting data to CSV file for Missing Assets data");
					//System.out.println("Size of " + "56urls sheet: " +  cellGrid1.size());
					//System.out.println("Size of " + "61urls sheet: " +  cellGrid2.size());
					String CSVPath1 = System.getProperty("user.dir")+"\\InputData\\csvdata\\findingmissingassets_Prod.csv";
					Convert(CSVPath1, cellGrid1);
					String CSVPath2 = System.getProperty("user.dir")+"\\InputData\\csvdata\\findingmissingassets_Test.csv";
					Convert(CSVPath2, cellGrid2);
				//	System.out.println("Clearing cell data");
					cellGrid1.clear();
					cellGrid2.clear();
				}
				else {
				/*	System.out.println("Started writting data to CSV file for " + myWorkBook.getSheetName(i) + " data");
					System.out.println("Size of " + myWorkBook.getSheetName(i) + " sheet: " +  cellGrid1.size());*/
					String CSVPath1 = System.getProperty("user.dir")+"\\InputData\\csvdata\\" + myWorkBook.getSheetName(i)+ ".csv";
					Convert(CSVPath1, cellGrid1);
					//System.out.println("Clearing cell data");
					cellGrid1.clear();
					cellGrid2.clear();
				}
				
				//System.out.println("Completed Copying data to CSV file for Sheet: " + myWorkBook.getSheetName(i) );
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	//	System.out.println("================= Completed creating new CSV Files =================");
		

	}

	@SuppressWarnings({ "unused", "resource" })
	private static void Convert (String CSVPath, List<List<XSSFCell>> celldata) throws Exception {

		CSVWriter csvWriter = new CSVWriter(new FileWriter(CSVPath));
		File file = new File(CSVPath);
		PrintStream stream = new PrintStream(file);
		for (int k = 0; k < celldata.size(); k++) {
			List<XSSFCell> cellRowList = celldata.get(k);
			for (int j = 0; j < cellRowList.size(); j++) {
				XSSFCell myCell = (XSSFCell) cellRowList.get(j);
				String stringCellValue = myCell.toString();
				stream.print(stringCellValue);
				//	stream.print(stringCellValue + ";");
			}
			stream.println("");
		}
		celldata.clear();
		csvWriter.close();
	}
}