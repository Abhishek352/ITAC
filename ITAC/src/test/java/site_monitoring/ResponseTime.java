package site_monitoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.library.Logger;

public class ResponseTime {

	static WebDriver driver;
	public static Instant Starttime =null;
	public static String str_starttime =null;
	public static String str_endtime = null;
	public static Instant Endtime =null;
	static List<String> list = new ArrayList<String>();
	static LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();

	@BeforeClass
	public void beforeMethod() {
		System.out.println("********* Health Checks Execution Started ********* ");
		System.out.println("Execution Thread ID = " +Thread.currentThread().getId());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		str_starttime = sdf.format(dt);
		Globalvariables.HC_str_starttime = str_starttime;

		SimpleDateFormat sdf1= new SimpleDateFormat("yyyyMMdd_HHmmss");
		String timestamp = sdf1.format(dt);

		Starttime = Instant.now();
		Globalvariables.HC_starttime = Starttime;

		System.out.println("==============================================================================================");
		System.out.println("Test Execution Started At : "+Starttime);
		System.out.println("==============================================================================================");

		String filePathOflogger = System.getProperty("user.dir")+"\\Results\\Logs\\";
		String fileNameOflogger = "HealthChecks"+timestamp+".log";
		Logger.initializeLogger(filePathOflogger, fileNameOflogger);
	}

	@Test
	public static void Responsetime() throws Exception{

		PageLoadTime();
		String Pageloadtimeoutput = System.getProperty("user.dir")+"\\Results\\PageLoadTime.csv";
		Globalvariables.PageLoadTimeList = Pageloadtimecsvtomapping3(Pageloadtimeoutput);

		Endtime = Instant.now();
		Globalvariables.HC_Endtime = Endtime;
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		str_endtime = sdf1.format(dt);
		Globalvariables.HC_str_endtime = str_endtime;
	}

	public static void PageLoadTime() throws Exception{

		list = readFromCsv(System.getProperty("user.dir")+"\\InputData\\csvdata\\PageLoadTime.csv");
		String pageurl = null;
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver_237.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().setPosition(new Point(-2000, 0));

		driver.get("https://www.google.com");
		System.out.println("Started launching url's");
		
		Globalvariables.ResponseTimeurls = list.size();
		
		int statuscode = 404;
		for(int a=0; a<list.size(); a++) {
			try {
				double  time_seconds=0;
				pageurl = list.get(a).toString();  
				long startTime = System.currentTimeMillis();
				driver.get(pageurl);
				String title= null;
				DecimalFormat df = new DecimalFormat("#.##");
				try {
					statuscode = GetStatus(pageurl);
					title = driver.getTitle();
					long endTime = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					time_seconds   =  (double) totalTime/1000;
					
				}catch(Exception e) {
					statuscode = 404;
				}
				int urlno = a+1;
				System.out.println("URL " + urlno + " - "+ pageurl + "  & Status = "+statuscode + " & Time: "+ time_seconds);
				result.put(pageurl+"~"+df.format(time_seconds), statuscode);
			}catch (Exception mes) {
				System.out.println("Please re-check the domain: We're unable to launch it ~ " + pageurl + "  ~ Status = "+ statuscode);
				result.put(pageurl+"~"+"1", statuscode);
			}

		}
		driver.quit();
		writecsv(result);
		System.out.println("Completed execution - Please check results sheet");
		result.clear();
	}

	@SuppressWarnings("finally")
	public static List<String> readFromCsv(String csv_file2) throws FileNotFoundException {
		ArrayList<String> locallist = new ArrayList<String>();
		@SuppressWarnings("resource")
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

	public static int GetStatus (String url) throws Exception {

		int Status = 0;
		Response response;
		try {
			response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).execute();
			Status = response.statusCode();
			if(Status == 403){
				response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").timeout(10000).execute();
				Status = response.statusCode();
			}
		}catch (Exception e) {
			//	System.out.println("Unable to get status for link: " + url);
			Status = 404;
		}

		return Status;
	}

	public static void writecsv(LinkedHashMap<String, Integer>rmap) throws Exception {

		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String fileAddress = System.getProperty("user.dir")+"\\Results\\PageLoadTime.csv";
		FileWriter fw = null;
		
		
		FileInputStream reader;
		Properties prop = new Properties();
		reader = new FileInputStream(System.getProperty("user.dir")+"\\testConfig.properties");

		prop.load(reader);
		String exprt =	prop.getProperty("ResponseTime");
		double ExpectedResponseTime = Double.valueOf(exprt);
		
		try {
			fw = new FileWriter(fileAddress);
			for(Entry<String, Integer> keys : rmap.entrySet()) {
				String status;
				String[] key = keys.getKey().split("~");
				String parenturl = key[0];
				double responsetime = Double.valueOf(key[1]);
				
				if(responsetime<ExpectedResponseTime){
					status = "Pass";
					Globalvariables.ResponseTimePass++;
				}else{
					status = "Fail";
					Globalvariables.ResponseTimefail++;
				}
				fw.append(parenturl +COMMA_DELIMITER +responsetime+" Secs"+COMMA_DELIMITER+ keys.getValue()+COMMA_DELIMITER+status);
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

	public static List<String> Pageloadtimecsvtomapping3(String csv_file) throws Exception{

		List<String> urlslist = new ArrayList<String>();		
		int n=0;

		BufferedReader br = new BufferedReader(new FileReader(csv_file));

		String line;
		while((line = br.readLine()) !=null) {
			String[] b = line.split(",");
			n++;
			urlslist.add(b[0]+"~"+b[1]+"~"+b[2]+"~"+b[3]);
		}
		br.close();
		return urlslist;
	}

	@AfterClass
	public void afterClass() {
		//		System.out.println("\nthis is AfterMethod");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		String enddate = sdf.format(dt);
		Instant endtime = Instant.now();
		Duration diff = Duration.between(Starttime, endtime);

		System.out.println("==============================================================================================");
		System.out.println("Test Execution Completion At : "+enddate);
		System.out.println("Total Execution Time : " +diff.toMinutes() +" minutes");
		System.out.println("==============================================================================================");
		Logger.logTestInfo("Total Execution Time : " +diff.toMinutes() +" minutes");
		Logger.logTestInfo("************* Completed Test Execution *************");
	}

}
