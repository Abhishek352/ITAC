package com.library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;

public class UtilClass {

	@SuppressWarnings({ "resource", "finally" })
	public List<String> readFromCsv(String csv_file2) throws FileNotFoundException {
		ArrayList<String> locallist = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(new File(csv_file2)));
		String line;
		try {
			while ((line = br.readLine()) != null) {
				locallist.add(line);
			}
		}catch(Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		}finally {
			return locallist;
		}
	}
public static ArrayList<String> RemoveBlankValues(ArrayList<String> InitialList) {
		
		ArrayList<String> newlist = new ArrayList<>();
		
		for(int i=0; i<InitialList.size(); i++) {
			
			String value = InitialList.get(i);
			if((!value.equals(null)) && (value.length()>0)) {
				newlist.add(value);
			}
		}
		return newlist;
}

public static List<String> RemoveBlankListValues(List<String> InitialList) {
	
	ArrayList<String> newlist = new ArrayList<>();
	
	for(int i=0; i<InitialList.size(); i++) {
		
		String value = InitialList.get(i);
		if((!value.equals(null)) && (value.length()>0)) {
			newlist.add(value);
		}
	}
	return newlist;
}

public static ArrayList<XSSFCell> RemoveBlankCells(List<XSSFCell> cellRowList1) {
	
	ArrayList<XSSFCell> newlist = new ArrayList<>();
	
	for(int i=0; i<cellRowList1.size(); i++) {
		
		XSSFCell value = cellRowList1.get(i);
		if((!value.equals(null)) && (value.toString().length()>0)) {
			newlist.add(value);
		}
	}
	return newlist;
}
}
