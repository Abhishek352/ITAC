package com.library;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class CSV_Report {

	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";


	public static void generateCSV_Phase1(String fileAddress,LinkedHashMap<String, Integer> rmap) {

		//System.out.println("csv result writing started...");
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileAddress);
			for(Entry<String, Integer> keys : rmap.entrySet()) {
				fw.append(keys.getKey().split("~")[0] +COMMA_DELIMITER +keys.getKey().split("~")[1] +COMMA_DELIMITER +keys.getValue());
				fw.append(NEW_LINE_SEPARATOR);
			}
		}catch(Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		}finally {
			try{
				fw.flush();
				fw.close();
				System.out.println("  ********************************* Completed writting results to CSV file  *********************************");
			}catch(IOException i) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				i.printStackTrace();
			}
		}
	}

	public static void generateCSVAssets(String fileAddress,LinkedHashMap<String, LinkedHashMap<String, String>> rmap) {

		//System.out.println("csv result writing started...");
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileAddress);
			for(Entry<String, LinkedHashMap<String, String>> specialChar : rmap.entrySet()){

				if(specialChar.getKey().contains("ParentURL_")){
					String parenturl = specialChar.getKey().toString().replace("ParentURL_", "");
					
					for(Entry<String, String> specialChar1 : specialChar.getValue().entrySet()){ 
						
						if(specialChar1.getKey().contains("desktopview")){
							String assetname = specialChar1.getKey().toString().replace("desktopview_", "");
							fw.append(parenturl +COMMA_DELIMITER +assetname+COMMA_DELIMITER+specialChar1.getValue());
							fw.append(NEW_LINE_SEPARATOR);
						}else if(specialChar1.getKey().contains("mobileview")){
							String assetname = specialChar1.getKey().toString().replace("mobileview_", "");
							fw.append(parenturl +COMMA_DELIMITER +assetname+COMMA_DELIMITER+specialChar1.getValue());
							fw.append(NEW_LINE_SEPARATOR);
						}else{
							fw.append(parenturl +COMMA_DELIMITER +specialChar1.getKey()+COMMA_DELIMITER+specialChar1.getValue());
							fw.append(NEW_LINE_SEPARATOR);
						}
						
						}
					}
				}

		}catch(Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		}finally {
			try{
				fw.flush();
				fw.close();
				System.out.println("  ********************************* Completed writting results to CSV file  *********************************");
			}catch(IOException i) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				i.printStackTrace();
			}
		}
	}

	public static void domain_generateCSV_Phase1(String fileAddress,LinkedHashMap<String, Integer> rmap) {

		//		System.out.println(" ********************************* Started writting results to CSV file *********************************");
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileAddress);
			for(Entry<String, Integer> keys : rmap.entrySet()) {
				fw.append(keys.getKey() +COMMA_DELIMITER +keys.getValue());
				fw.append(NEW_LINE_SEPARATOR);
			}
		}catch(Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		}finally {
			try{
				fw.flush();
				fw.close();
				System.out.println("  ********************************* Completed writting results to CSV file  *********************************");
			}catch(IOException i) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				i.printStackTrace();
			}
		}
	}

	public static void generateCSV_Phase2(String fileAddress,LinkedHashMap<String, String> rmap) {

		System.out.println(" ********************************* Started writting results to CSV file *********************************");
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileAddress);
			for(Entry<String, String> keys : rmap.entrySet()) {
				//	fw.append(keys.getKey());
				fw.append(keys.getKey() + COMMA_DELIMITER + keys.getValue());
				fw.append(NEW_LINE_SEPARATOR);
			}
		}catch(Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		}finally {
			try{
				fw.flush();
				fw.close();
				System.out.println("  ********************************* Completed writting results to CSV file  *********************************");
			}catch(IOException i) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				i.printStackTrace();
			}
		}
	}

}
