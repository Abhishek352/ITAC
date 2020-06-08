package com.library;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

public class Logger {

	private static String logFilePath = null;
	private static BufferedWriter out = null;
	private static String logFileName = null;
	public static LinkedHashMap<String, String> htmlResultMap=new LinkedHashMap<String, String>();

	public static void initializeLogger(String filePath, String fileName) {
		try {
		//	System.out.println("Logger Initialized");
			logFilePath = filePath;
			logFileName = fileName;
			File newFile = new File(logFilePath + logFileName);
			newFile.createNewFile();
			newFile.setWritable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void logTestResultMap(LinkedHashMap<String, Integer> Rmap) {
		try {
			Date currentTimeStamp = new Date();
			SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy, HH:mm:ss");
			out = new BufferedWriter(new FileWriter(logFilePath + logFileName, true));
			out.write("\r\n" + "[" + format.format(currentTimeStamp) + "]" + "[Map]:: ");	
			for(String key : Rmap.keySet()) {
				out.newLine();
				out.write(key +"::" +Rmap.get(key));
			}
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void logTestResultMap_ss(LinkedHashMap<String, String> Rmap) {
		try {
			Date currentTimeStamp = new Date();
			SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy, HH:mm:ss");
			out = new BufferedWriter(new FileWriter(logFilePath + logFileName, true));
			out.write("\r\n" + "[" + format.format(currentTimeStamp) + "]" + "[Map]:: ");	
			for(String key : Rmap.keySet()) {
				out.newLine();
				out.write(key +"::" +Rmap.get(key));
			}
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static void logTestInfo(String text) {
		try {
			Date currentTimeStamp = new Date();
			SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy, HH:mm:ss");
			out = new BufferedWriter(new FileWriter(logFilePath + logFileName, true));
			out.write("\r\n" + "[" + format.format(currentTimeStamp) + "]" + "[INFO]:: ");	
//			System.out.println("\r" + "[" + format.format(currentTimeStamp) + "]" + "[INFO]:: " +text);
			out.write(text);
			out.newLine();
			out.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
