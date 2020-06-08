package site_monitoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.library.CSV_Report;
import com.library.HTML_Reports;
import com.library.Logger;
import com.library.UtilClass;

import utilities.Screenshots;


public class BrokenLinks {

	public static String execution = null;  // or InputFile (excel)
	public static int  MAX_DEPTH = 1000;
	public static String Domain = null;
	public static String input_csv_filePath = System.getProperty("user.dir")+"//InputData//csvdata//brokenlinks.csv";
	public static String filePath = System.getProperty("user.dir")+"//testConfig.properties";
	public static String html_result_filePath = System.getProperty("user.dir")+"\\Results\\HTML_results\\All_reports\\";
	public static String csv_file = System.getProperty("user.dir")+"\\Results\\BrokenLinks_csvReport.csv";
	public static int success=0;
	public static int fails=0;
	public static int status200=0;
	public static int redirect=0;
	public static int permanentredirect=0;
	public static int badrequest=0;
	public static int unauthorized=0;
	public static int paymentrequired=0;
	public static int pagenotfound=0;
	public static int server=0;
	public static int serviceunavailable=0;
	public static int forbidden=0;
	public static int others=0;
	public static int norun=0;
	public static int parentURLpass=0;
	public static int parentURLfail=0;
	public static LinkedHashMap<String,Integer> passresults = new LinkedHashMap<String,Integer>();
	public static LinkedHashMap<String,Integer> failresults = new LinkedHashMap<String,Integer>();
	public static ArrayList<String> failedParentURLs = new ArrayList<String>();
	public static ArrayList<String> CompleteList = new ArrayList<String>();
	static int Status;
	static Response response;
	static String FilePath;
	static String fbindicator = "false"; static String twindicator = "false"; static String mailindicator = "false"; static String printindicator = "false"; static String youtoindicator = "false";static String youindicator = "false";
	static String fbindicator_Mobile = "false"; static String twindicator_Mobile = "false"; static String mailindicator_Mobile = "false"; static String printindicator_Mobile = "false";static String youtoindicator_Mobile = "false";static String youindicator_Mobile = "false";

	public static ArrayList<String> BrokenLinks_CompleteList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_UniqueList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_InternalList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_ExternalList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_AssetsList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_ExceptionList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_JunkList = new ArrayList<>();

	public static ArrayList<String> BrokenLinks_UniqueList_Mobile = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_InternalList_Mobile = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_ExternalList_Mobile = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_AssetsList_Mobile = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_ExceptionList_Mobile = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_JunkList_Mobile = new ArrayList<>();

	public static ArrayList<String> BrokenLinks_UniqueList_Final = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_ParentList_Final = new ArrayList<>();
	public static LinkedHashMap<String,String> parentchildmap = new LinkedHashMap<String,String>();
	public static ArrayList<String> finalfaillist = new ArrayList<>();
	public static LinkedHashMap<String,String> CompleteMapping_Desktop = new LinkedHashMap<String,String>();
	public static LinkedHashMap<String,String> CompleteMapping_Mobile = new LinkedHashMap<String,String>();
	public static int urllist;
	public static LinkedHashMap<String,LinkedHashMap<String, Integer>> InputResultMap = new LinkedHashMap<String,LinkedHashMap<String, Integer>>();
	public static List<String> passlist = new ArrayList<String>();
	public static List<String> faillist = new ArrayList<String>();
	public static LinkedHashMap<String,LinkedHashMap<String,String>> failparentchildmap = new LinkedHashMap<String,LinkedHashMap<String,String>>();

	public static LinkedHashMap<String, Integer>inputfinalresulttotal = new LinkedHashMap<String, Integer>();
	public static ArrayList<String> inputfinallist = new ArrayList<>();
	public static LinkedHashMap<String,LinkedHashMap<String,String>> inputfailparentchildmap = new LinkedHashMap<String,LinkedHashMap<String,String>>();

	Instant starttime = Instant.now();
	static int status = 0;
	static String SitePath = null;
	public static Instant Starttime =null;
	public static Instant Endtime =null;
	public static String str_starttime =null;


	@BeforeClass
	public void beforeMethod() {
		System.out.println("********* BrokenLinks Execution Started ********* ");
		System.out.println("Execution Thread ID = " +Thread.currentThread().getId());


		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		str_starttime = sdf.format(dt);
		SimpleDateFormat sdf1= new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		String timestamp = sdf1.format(dt);
		Starttime = Instant.now();

		/*		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		String enddate = sdf.format(dt);
		Instant endtime = Instant.now();
		Duration diff = Duration.between(Starttime, endtime);*/

		System.out.println("==============================================================================================");
		System.out.println("Test Execution for Broken Links execution started At : "+ str_starttime);
		System.out.println("==============================================================================================");

		String filePathOflogger = System.getProperty("user.dir")+"\\Results\\Logs\\";
		String fileNameOflogger = "BrokenLinks"+timestamp+".log";
		Logger.initializeLogger(filePathOflogger, fileNameOflogger);
	}

	@Test
	public void  ValidateBrokenLinks() throws Exception {

		File file = new File(filePath);
		FileReader fr = new FileReader(file);
		Properties prop = new Properties();
		prop.load(fr);

		execution = prop.getProperty("BrokenLinks-Input");
		System.out.println("execution_type : "  +execution);

		Domain = prop.getProperty("BrokenLinks-Domain");

		if(execution.equalsIgnoreCase("BrokenLinks_Domain")) {

			System.out.println("Given Domain for validation: " + Domain);

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date dt = new Date();
			str_starttime = sdf.format(dt);

			Starttime = Instant.now();
			System.out.println("**** Test Execution started using Domain URL ****");

			//	System.out.println("fetching childlinks...");
			List<String> Finalurllist = new ArrayList<String>();
			try{
				Finalurllist = GetChildPages(Domain);
			}catch(Exception e){
				System.out.println("Exception in fetching links while crawling");
				//System.out.println(e.getMessage());
			}
			System.out.println("*************************************** Getting status of each link ***************************************");
			try{
				brokenLinks_domain(Finalurllist);	
			}catch(Exception e){
				System.out.println("Exception in getting the status of the links");
			}
			ClearingListValues();

		}else {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date dt = new Date();
			str_starttime = sdf.format(dt);

			Starttime = Instant.now();

			List<String> list = readFromCsv(input_csv_filePath);  // read
			System.out.println("No of Parent Links to execute : "+list.size());

			try{
				BrokenLinks_input();
			}catch(Exception e){
				System.out.println("Exception in getting the status of the links");
			}
			
			/*for(int i=0;i<list.size();i++) {

				try{
					System.out.println("Started execution for Parent URL: " + i);
					finalresult = brokenLinks(i, list.get(i)); 
					InputResultMap.put("ParentURL_"+list.get(i), finalresult);
				}catch(Exception e){
					System.out.println("Exception in getting status of the links");
					//System.out.println(e.getMessage());
				}

				Logger.logTestInfo("====================================================================================================");
			}*/


			System.out.println("\n*********************** Test Execution Results writing started ***********************");

			//int totalnoofchildlinks = finalresult.size();

			/*SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date dt1 = new Date();
			String str_endtime = sdf1.format(dt1);

			String fileName="BrokenLinks.html";

			HTML_Reports.BrokenLinks_Input(fileName,InputResultMap,"BrokenLinks", str_starttime,
					str_endtime,Starttime,"Summary", urllist, passlist.size(), faillist.size());*/

			ClearingListValues();
			System.out.println("\n*********************** Test Execution Results writing Completed ***********************");
		}

	}

	@SuppressWarnings({ "resource", "finally" })
	private static List<String> readFromCsv(String csv_file2) throws FileNotFoundException {
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

	private static void BrokenLinks_input() throws Exception {

		try {

			List<String> list = readFromCsv(input_csv_filePath);  // read
			System.out.println("No of Parent Links to execute : "+list.size());

			urllist = list.size();


			for(int j=0;j<list.size();j++) {
				String parenturl = list.get(j);
				System.out.println("Parent url : " +parenturl);
				List<String> finallist = new ArrayList<String>();
				List<String> inputfaillist = new ArrayList<String>();

				if(!parenturl.startsWith("http")) {
					parenturl = "http://" + parenturl;
				}
				//To handle domains which have redirections
				
				int statuscode = GetStatus(parenturl);
				if((statuscode == 301) || (statuscode == 302)) {
					System.out.println("Status of given domain: " + statuscode);
					System.out.println("Given domain has a redirection, taking the redirected url for execution");
					Response resp = Jsoup.connect(parenturl).execute();
					parenturl = resp.url().toString();
					System.out.println("Redirected url: " + parenturl);
					statuscode = GetStatus(parenturl);
				}else if((statuscode == 401) || (statuscode == 402) || (statuscode == 403) || (statuscode == 404) || (statuscode == 503) || (statuscode == 500) ){
					System.out.println( parenturl + " - is not a valid, please re-check and start again. Current status of this domain: " + status);
				}

				if(statuscode==200) {
					parentURLpass++;
					//System.out.println("Parent link is valid - execution continuing...");

					finallist = finalLinksList(parenturl); 
					inputfinallist.addAll(finallist);

					System.out.println("no of child links on page: " + finallist.size());
					LinkedHashMap<String,Integer> passresults = new LinkedHashMap<String,Integer>();
					LinkedHashMap<String,Integer> failresults = new LinkedHashMap<String,Integer>();
					for(int i=0;i<finallist.size();i++) {

						//Globalvariables.BL_urls = finallist.size();
						Globalvariables.BL_urls = urllist;
						//if(!(inputfinallist.contains(finallist.get(i)))){
						
						//System.out.println(finallist.get(i));

						int status = GetStatus(finallist.get(i));  // getting link status

						String statusresult = Integer.toString(status);
						
						if(status==200) {
							passresults.put(finallist.get(i), status);
							passlist.add(statusresult);
							String key = finallist.get(i);
							int value = status;
							Logger.logTestInfo(key +"~" +value);
						}else if(status==301){
							String key = finallist.get(i);
							passlist.add(statusresult);
							int value = status;
							Logger.logTestInfo(key +"~" +value);
							passresults.put(finallist.get(i), status);
						}else if(status==302){
							String key = finallist.get(i);
							passlist.add(statusresult);
							int value = status;
							Logger.logTestInfo(key +"~" +value); 
							passresults.put(finallist.get(i), status);
						}else if(status==400){
							String key = finallist.get(i);
							faillist.add(statusresult);
							inputfaillist.add(key);
							int value = status;
							Logger.logTestInfo(key +"~" +value);
							failresults.put(finallist.get(i), status);
						}else if(status==401){
							String key = finallist.get(i);
							faillist.add(statusresult);
							int value = status;
							inputfaillist.add(key);
							Logger.logTestInfo(key +"~" +value);
							failresults.put(finallist.get(i), status);
						}else if(status==402){
							String key = finallist.get(i);
							faillist.add(statusresult);
							inputfaillist.add(key);
							int value = status;
							Logger.logTestInfo(key +"~" +value);
							failresults.put(finallist.get(i), status);
						}else if(status==403){
							String key = finallist.get(i);
							faillist.add(statusresult);
							inputfaillist.add(key);
							int value = status;
							Logger.logTestInfo(key +"~" +value);
							failresults.put(finallist.get(i), status);
						}else if(status==404){
							String key = finallist.get(i);
							faillist.add(statusresult);
							inputfaillist.add(key);
							int value = status;
							Logger.logTestInfo(key +"~" +value);
							failresults.put(finallist.get(i), status);
						}else if(status==500) {
							String key = finallist.get(i);
							faillist.add(statusresult);
							inputfaillist.add(key);
							int value = status;
							Logger.logTestInfo(key +"~" +value);
							failresults.put(finallist.get(i), status);
						}else {
							String key = finallist.get(i);
							faillist.add(statusresult);
							inputfaillist.add(key);
							int value = status;
							Logger.logTestInfo(key +"~" +value);
							failresults.put(finallist.get(i), status);
						}
					}
					LinkedHashMap<String, Integer>inputfinalresult = new LinkedHashMap<String, Integer>();
					inputfinalresult.putAll(passresults);
					inputfinalresult.putAll(failresults);
					Globalvariables.BL_passmap.putAll(passresults);
					Globalvariables.BL_failmap.putAll(failresults);
					
					LinkedHashMap<String, String> failparentmap = new LinkedHashMap<String, String>();

					failparentmap.put(parenturl, null);

					for(int f=0;f<inputfaillist.size();f++){
						String childfaillink = inputfaillist.get(f);
						inputfailparentchildmap.put(childfaillink, failparentmap);
					}

					System.out.println("Completed getting status of all child links");
					inputfinalresulttotal.putAll(inputfinalresult);
				}

			} 
			
			for(String entry : inputfinalresulttotal.keySet()) {
				if(inputfinalresulttotal.get(entry).equals(200)){
					success++;
					Globalvariables.BL_success++;
					status200++;
				}else if(inputfinalresulttotal.get(entry).equals(301)) {
					success++;
					Globalvariables.BL_success++;
					redirect++;
				}else if(inputfinalresulttotal.get(entry).equals(302)) {
					success++;
					Globalvariables.BL_success++;
					permanentredirect++;
				}else if(inputfinalresulttotal.get(entry).equals(400)) {
					fails++;
					Globalvariables.BL_fails++;
					badrequest++;
				}else if(inputfinalresulttotal.get(entry).equals(401)) {
					fails++;
					Globalvariables.BL_fails++;
					unauthorized++;
				}else if(inputfinalresulttotal.get(entry).equals(402)) {
					fails++;
					Globalvariables.BL_fails++;
					paymentrequired++;
				}else if(inputfinalresulttotal.get(entry).equals(403)) {
					fails++;
					Globalvariables.BL_fails++;
					forbidden++;
				}else if(inputfinalresulttotal.get(entry).equals(404)) {
					fails++;
					Globalvariables.BL_fails++;
					pagenotfound++;
				}else if(inputfinalresulttotal.get(entry).equals(500)) {
					fails++;
					Globalvariables.BL_fails++;
					server++;
				}else if(inputfinalresulttotal.get(entry).equals(503)) {
					fails++;
					Globalvariables.BL_fails++;
					serviceunavailable++;
				}else {
					fails++;
					Globalvariables.BL_fails++;
					others++;
				}
			}
			
			
			
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date dt1 = new Date();
			String str_endtime = sdf2.format(dt1);

			Endtime = Instant.now();
			
			CSV_Report.domain_generateCSV_Phase1(csv_file, inputfinalresulttotal);
			
			Globalvariables.BL_html_result_filePath = html_result_filePath;
			Globalvariables.BL_finalresult = inputfinalresulttotal;
			Globalvariables.BL_Filename = "BrokenLinks";
			Globalvariables.BL_str_starttime = str_starttime;
			Globalvariables.BL_str_endtime = str_endtime;
			Globalvariables.BL_starttime = Starttime;
			Globalvariables.BL_Summary = "Broken Links";
			Globalvariables.BL_finallistsize = urllist;
			Globalvariables.BL_fail =fails;
			Globalvariables.BL_redirect = redirect;
			Globalvariables.BL_unauthorized = unauthorized;
			Globalvariables.BL_forbidden = forbidden;
			Globalvariables.BL_badrequest = badrequest;
			Globalvariables.BL_pagenotfound = pagenotfound;
			Globalvariables.BL_server = server;
			Globalvariables.BL_others = others;
			Globalvariables.BL_parentURLfail = parentURLfail;
			Globalvariables.BL_successes = success;
			Globalvariables.BL_permanentredirect = permanentredirect;
			Globalvariables.BL_paymentrequired = paymentrequired;
			Globalvariables.BL_status200 = status200;
			Globalvariables.BL_serviceunavailable = serviceunavailable;
			Globalvariables.BL_failparentchildmap = inputfailparentchildmap;
			Globalvariables.BL_Endtime = Endtime;

			HTML_Reports.BrokenLinks( html_result_filePath, inputfinalresulttotal, "BrokenLinks", str_starttime,
					str_endtime, Starttime, "BrokenLinks", urllist, 0, fails, redirect, unauthorized, forbidden,
					badrequest,pagenotfound,server,others,parentURLfail, success,permanentredirect,paymentrequired,status200,
					Globalvariables.BL_failparentchildmap,Endtime, serviceunavailable);


		}catch (Exception e) {
			fails++;
			parentURLfail++;
			//failedParentURLs.add(parenturl);
			e.printStackTrace();
		}
	}

	private static void createFile(String path , List<String> URLsList, int Size) throws IOException {
		try{
			FileWriter writer = new FileWriter(path);
			for (int i=0; i<Size; i++) {
				String str = URLsList.get(i).toString();
				writer.write(str);
				//This prevent creating a blank like at the end of the file**
				if(i < Size-1) {
					writer.write("\n");
				}
			}
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static List<String> finalLinksList(String parenturl) throws Exception {

		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> mlist = new ArrayList<String>();
		ArrayList<String> flist = new ArrayList<String>();	
		try {
			//Document document = Jsoup.connect(parenturl).get();
			Document document = Jsoup.connect(parenturl).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36").timeout(10000).get();
			Elements linksOnPage = document.select("a[href]");
			for(int i=0; i<linksOnPage.size(); i++) {
				String childlink = linksOnPage.get(i).attr("abs:href");

				//filtering duplicate links in same page

				if((!list.contains(childlink) && !childlink.isEmpty() && childlink!=null && !childlink.contains("javascript")) || childlink.endsWith(".pdf") || childlink.endsWith(".PDF") || childlink.endsWith(".doc") || childlink.endsWith(".xlsx") || childlink.endsWith(".docx") || childlink.endsWith(".txt") || childlink.endsWith(".xls")) {

					if((childlink.contains("https://www.facebook.com")) || (childlink.contains("https://twitter.com")) || (childlink.contains("mailto:")) || (childlink.contains("print") || (childlink.contains("https://www.youtube.com")) || (childlink.contains("https://youtu.be")))) {
						if((childlink.contains("https://www.facebook.com") && fbindicator.equals("false"))){
							list.add(childlink); 
							fbindicator = "true";
						}else if((childlink.contains("https://twitter.com") && twindicator.equals("false"))){
							list.add(childlink); 
							twindicator = "true";
						}else if((childlink.contains("mailto:") && mailindicator.equals("false"))){
							list.add(childlink); 
							mailindicator = "true";
						}else if((childlink.contains("print") && printindicator.equals("false"))){
							list.add(childlink); 
							printindicator = "true";
						}else if((childlink.contains("https://www.youtube.com") && youindicator.equals("false"))){
							list.add(childlink); 
							youindicator = "true";
						}else if((childlink.contains("https://youtu.be") && youtoindicator.equals("false"))){
							list.add(childlink); 
							youtoindicator = "true";
						}
					}else {
						if(!list.contains(childlink)) {
							list.add(childlink);
						}
					}
				}else {
					if(!list.contains(childlink)) {
						list.add(childlink);
					}
				}
			}

			Document mdocument = Jsoup.connect(parenturl).userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0 Mobile/15E148 Safari/604.1").timeout(60000).get();
			Elements mlinksOnPage = mdocument.select("a[href]");
			for(int i=0; i<mlinksOnPage.size(); i++) {
				String mchildlink = mlinksOnPage.get(i).attr("abs:href");

				//filtering duplicate links in same page

				if((!mlist.contains(mchildlink) && !mchildlink.isEmpty() && mchildlink!=null && !mchildlink.contains("javascript")) || mchildlink.endsWith(".pdf") || mchildlink.endsWith(".PDF") || mchildlink.endsWith(".doc") || mchildlink.endsWith(".xlsx") || mchildlink.endsWith(".docx") || mchildlink.endsWith(".txt") || mchildlink.endsWith(".xls")) {

					if((mchildlink.contains("https://www.facebook.com")) || (mchildlink.contains("https://twitter.com")) || (mchildlink.contains("mailto:")) || (mchildlink.contains("print") || (mchildlink.contains("https://www.youtube.com")) || (mchildlink.contains("https://youtu.be")))) {
						if((mchildlink.contains("https://www.facebook.com") && fbindicator_Mobile.equals("false"))){
							mlist.add(mchildlink); 
							fbindicator_Mobile = "true";
						}else if((mchildlink.contains("https://twitter.com") && twindicator_Mobile.equals("false"))){
							mlist.add(mchildlink); 
							twindicator_Mobile = "true";
						}else if((mchildlink.contains("mailto:") && mailindicator_Mobile.equals("false"))){
							mlist.add(mchildlink); 
							mailindicator_Mobile = "true";
						}else if((mchildlink.contains("print") && printindicator_Mobile.equals("false"))){
							mlist.add(mchildlink); 
							printindicator_Mobile = "true";
						}else if((mchildlink.contains("https://www.youtube.com") && youindicator_Mobile.equals("false"))){
							mlist.add(mchildlink); 
							youindicator_Mobile = "true";
						}else if((mchildlink.contains("https://youtu.be") && youtoindicator_Mobile.equals("false"))){
							mlist.add(mchildlink); 
							youtoindicator_Mobile = "true";
						}
					}else {
						if(!mlist.contains(mchildlink)) {
							mlist.add(mchildlink);
						}
					}
				}else {
					if(!mlist.contains(mchildlink)) {
						mlist.add(mchildlink);
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally {
			Logger.logTestInfo("****Child links ****");
			for(int i=0;i<list.size();i++) {
				String text = list.get(i);
				Logger.logTestInfo(text);
			}
			for(int i=0;i<mlist.size();i++) {
				String text = mlist.get(i);
				Logger.logTestInfo(text);
			}
		}
		//System.out.println("Returning the list value back to main function");
		list = UtilClass.RemoveBlankValues(list); 
		mlist = UtilClass.RemoveBlankValues(mlist); 
		String Path = Screenshots.CreateFolder(System.getProperty("user.dir")+"\\Results\\WebCrawler\\BrokenLinks");
		String CompleteList_Path = Path + "\\CompleteList" + ".txt";
		flist.addAll(list);
		for(int i=0;i<mlist.size();i++){
			if(!flist.contains(mlist.get(i).toString())){
				flist.add(mlist.get(i).toString());
			}
		}

		createFile(CompleteList_Path, flist, flist.size());
		return flist;
	}

	private static void brokenLinks_domain(List<String> finalList) throws Exception {


		Date dt = new Date();
		SimpleDateFormat sdf1= new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		String timestamp = sdf1.format(dt);
		String filePathOflogger = System.getProperty("user.dir")+"\\Results\\Logs\\";
		String fileNameOflogger = "BrokenLinks"+timestamp+".log";
		Logger.initializeLogger(filePathOflogger, fileNameOflogger);

		for(int i=0;i<finalList.size();i++) {

			Globalvariables.BL_urls = finalList.size();

			String url = finalList.get(i);
			try{
				status = GetStatus(url);

				if(status==200) {
					passresults.put(url, status);
					Logger.logTestInfo(url +"~" +status);
				}else if(status==301){
					passresults.put(url, status);
					Logger.logTestInfo(url +"~" +status);
				}else if(status==302){
					passresults.put(url, status);
					Logger.logTestInfo(url +"~" +status);
				}else if(status==400) {
					failresults.put(url, status);
					Logger.logTestInfo(url +"~" +status);
				}else if(status==401) {
					failresults.put(url, status);
					Logger.logTestInfo(url +"~" +status);
				}else if(status==402){
					failresults.put(url, status);
					Logger.logTestInfo(url +"~" +status);
				}else if(status==403){
					failresults.put(url, status);
					Logger.logTestInfo(url +"~" +status);
				}else if(status==404) {
					failresults.put(url, status);
					Logger.logTestInfo(url +"~" +status);
				}else if(status==500) {
					failresults.put(url, status);
					Logger.logTestInfo(url +"~" +status);
				}else {
					failresults.put(url, status);
					Logger.logTestInfo(url +"~" +status);
				}
			}catch(Exception e) {
				failresults.put(url, status);
				Logger.logTestInfo(url +"~" +status);
				continue;
			}

		}
		LinkedHashMap<String, Integer> finalresult = new LinkedHashMap<String, Integer>();
		finalresult.putAll(passresults);
		finalresult.putAll(failresults);
		Globalvariables.BL_passmap.putAll(passresults);
		Globalvariables.BL_failmap.putAll(failresults);

		for(String entry : finalresult.keySet()) {
			if(finalresult.get(entry).equals(200)){
				success++;
				Globalvariables.BL_success++;
				status200++;
			}else if(finalresult.get(entry).equals(301)) {
				success++;
				Globalvariables.BL_success++;
				redirect++;
			}else if(finalresult.get(entry).equals(302)) {
				success++;
				Globalvariables.BL_success++;
				permanentredirect++;
			}else if(finalresult.get(entry).equals(400)) {
				fails++;
				Globalvariables.BL_fails++;
				badrequest++;
			}else if(finalresult.get(entry).equals(401)) {
				fails++;
				Globalvariables.BL_fails++;
				unauthorized++;
			}else if(finalresult.get(entry).equals(402)) {
				fails++;
				Globalvariables.BL_fails++;
				paymentrequired++;
			}else if(finalresult.get(entry).equals(403)) {
				fails++;
				Globalvariables.BL_fails++;
				forbidden++;
			}else if(finalresult.get(entry).equals(404)) {
				fails++;
				Globalvariables.BL_fails++;
				pagenotfound++;
			}else if(finalresult.get(entry).equals(500)) {
				fails++;
				Globalvariables.BL_fails++;
				server++;
			}else if(finalresult.get(entry).equals(503)) {
				fails++;
				Globalvariables.BL_fails++;
				serviceunavailable++;
			}else {
				fails++;
				Globalvariables.BL_fails++;
				others++;
			}
		}

		Logger.logTestResultMap(finalresult);

		System.out.println("*************************************** Completed getting status of each link ***************************************");

		ArrayList<String> maplist = new ArrayList<String>();

		maplist.addAll(CompleteMapping_Desktop.keySet());
		maplist.addAll(CompleteMapping_Mobile.keySet());
		ArrayList<String> parentlist = new ArrayList<String>(new HashSet<String>(maplist));


		ArrayList<String> initalfaillist = new ArrayList<String>();
		initalfaillist.addAll(failresults.keySet());

		for(int k=0; k<initalfaillist.size(); k++) {
			if(initalfaillist.get(k).startsWith("https://")) {
				initalfaillist.add(initalfaillist.get(k).replace("https://", "http://"));
			}
		}

		ArrayList<String> faillist = new ArrayList<String>();
		for(int p=0; p<parentlist.size(); p++) {
			for (int f=0; f<initalfaillist.size(); f++) {
				String faillink = parentlist.get(p).split("~")[1];
				faillink = faillink.replaceAll(" ", "");
				if(faillink.equals(initalfaillist.get(f))) {
					faillist.add(parentlist.get(p));
				}
			}
		}

		LinkedHashMap<String, String> failparentmap = new LinkedHashMap<String, String>();

		for(int p=0; p<faillist.size(); p++) {
			String childlink = (faillist.get(p).split("~"))[1];
			for(int c=0; c<faillist.size(); c++) {
				String failparentlink = (faillist.get(c).split("~"))[0];
				String failchildlink = (faillist.get(c).split("~"))[1];
				if(failchildlink.equals(childlink)){
					failparentmap.put(failparentlink, null);
				}
			}
			failparentchildmap.put(childlink, failparentmap);
		}
		
		// write to csv file
		CSV_Report.domain_generateCSV_Phase1(csv_file, finalresult);

		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt1 = new Date();
		String str_endtime = sdf2.format(dt1);
		Endtime = Instant.now();

		Globalvariables.BL_html_result_filePath = html_result_filePath;
		Globalvariables.BL_finalresult = finalresult;
		Globalvariables.BL_Filename = "BrokenLinks";
		Globalvariables.BL_str_starttime = str_starttime;
		Globalvariables.BL_str_endtime = str_endtime;
		Globalvariables.BL_starttime = Starttime;
		Globalvariables.BL_Summary = "Broken Links";
		Globalvariables.BL_finallistsize = finalList.size();
		Globalvariables.BL_fail =fails;
		Globalvariables.BL_redirect = redirect;
		Globalvariables.BL_unauthorized = unauthorized;
		Globalvariables.BL_forbidden = forbidden;
		Globalvariables.BL_badrequest = badrequest;
		Globalvariables.BL_pagenotfound = pagenotfound;
		Globalvariables.BL_server = server;
		Globalvariables.BL_others = others;
		Globalvariables.BL_parentURLfail = parentURLfail;
		Globalvariables.BL_successes = success;
		Globalvariables.BL_permanentredirect = permanentredirect;
		Globalvariables.BL_paymentrequired = paymentrequired;
		Globalvariables.BL_status200 = status200;
		Globalvariables.BL_serviceunavailable = serviceunavailable;
		Globalvariables.BL_failparentchildmap = failparentchildmap;
		Globalvariables.BL_Endtime = Endtime;

		int totalnoofchildlinks=0;
		HTML_Reports.BrokenLinks( html_result_filePath, finalresult, "BrokenLinks", str_starttime,
				str_endtime, Starttime, "BrokenLinks", urllist, 0, fails, redirect, unauthorized, forbidden,
				badrequest,pagenotfound,server,others,parentURLfail, success,permanentredirect,paymentrequired,status200,
				Globalvariables.BL_failparentchildmap,Endtime,serviceunavailable);

	}

	private static int GetStatus (String url) throws Exception {

		int Status = 0;
		try {
			response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).execute();
			//response = Jsoup.connect(url).execute();
			Status = response.statusCode();
			if(Status == 403){
				response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").timeout(10000).execute();
				Status = response.statusCode();
			}
		}catch (Exception e) {
			System.out.println("Unable to get status for link: " + url);
			Status = 404;
		//	e.printStackTrace();
		}

		return Status;
	}

	private  ArrayList<String> GetChildPages(String domain) throws Exception {

		try {

			if(!domain.startsWith("http")) {
				domain = "http://" + domain;
			}

			try{
				int status = GetStatus(domain);
				if((status == 301) || (status == 302) || (status == 304)) {
					Response resp = Jsoup.connect(domain).execute();
					System.out.println("Status of given domain " + domain + " is: " + status);
					System.out.println("Given domain has a redirection, taking the redirected url for execution");
					domain = resp.url().toString();
					System.out.println("Redirected url: " + domain);
					status = GetStatus(domain);
				}else if((status == 401) || (status == 402) || (status == 403) || (status == 404)){
					System.out.println( domain + " - is not a valid, please re-check and start again. Current status of this domain: " + status);
				}
			}
			catch (Exception e){
				System.out.println(" Hey Bro!!! Are you kidding me, Unable to establish the connection to domain");
			}
			//To capture and save the SitePath for domains
			if(domain.endsWith(".html")) {
				SitePath = domain.replace(".html", "");
			}else if(domain.endsWith(".ashx")) {
				SitePath = domain.replace(".ashx", "");
			}else if(domain.endsWith(".php")) {
				SitePath = domain.replace(".php", "");
			}else {
				SitePath = domain;
			}

			if((GetStatus(domain) == 200) || (GetStatus(domain) == 304)) {

				System.out.println("********************************  Started Crawling website for domain:  " + domain + " ********************************");
				@SuppressWarnings("unused")

				ArrayList<String> desktopchildpages = new ArrayList<String>();
				ArrayList<String> mobilechildpages = new ArrayList<String>();
				ArrayList<String> totalchildpages = new ArrayList<String>();

				try{
					desktopchildpages = BrokenLinks_ChildPages(domain, 0); //For Desktop
				}catch(Exception e){
					System.out.println("Exception in getting the links of dekstop");
					//System.out.println(e.getMessage());
				}
/*
				try{
					mobilechildpages = BrokenLinks_ChildPages_Mobile(domain, 0);  //For Mobile
				}catch(Exception e){
					System.out.println("Exception in getting the links of mobile");
					//System.out.println(e.getMessage());
				}
*/

				System.out.println("******************************** Completed crawling website**********************************************");

				totalchildpages.addAll(desktopchildpages);
				totalchildpages.addAll(mobilechildpages);
				BrokenLinks_UniqueList_Final = new ArrayList<String>(new HashSet<String>(totalchildpages));

				System.out.println("Removing empty values from the list");
				//	System.out.println("==============================================================================================");
				BrokenLinks_UniqueList_Final = UtilClass.RemoveBlankValues(BrokenLinks_UniqueList_Final);
				BrokenLinks_CompleteList = UtilClass.RemoveBlankValues(BrokenLinks_CompleteList);
				BrokenLinks_UniqueList = UtilClass.RemoveBlankValues(BrokenLinks_UniqueList);
				BrokenLinks_InternalList = UtilClass.RemoveBlankValues(BrokenLinks_InternalList);
				BrokenLinks_ExternalList = UtilClass.RemoveBlankValues(BrokenLinks_ExternalList);
				BrokenLinks_AssetsList = UtilClass.RemoveBlankValues(BrokenLinks_AssetsList);
				BrokenLinks_ExceptionList = UtilClass.RemoveBlankValues(BrokenLinks_ExceptionList);
				BrokenLinks_UniqueList_Mobile = UtilClass.RemoveBlankValues(BrokenLinks_UniqueList_Mobile);
				BrokenLinks_InternalList_Mobile = UtilClass.RemoveBlankValues(BrokenLinks_InternalList_Mobile);
				BrokenLinks_ExternalList_Mobile = UtilClass.RemoveBlankValues(BrokenLinks_ExternalList_Mobile);
				BrokenLinks_AssetsList_Mobile = UtilClass.RemoveBlankValues(BrokenLinks_AssetsList_Mobile);
				BrokenLinks_ExceptionList_Mobile = UtilClass.RemoveBlankValues(BrokenLinks_ExceptionList_Mobile);
				//	System.out.println("==============================================================================================");


				FilePath = Screenshots.CreateFolder(System.getProperty("user.dir")+"\\Results\\WebCrawler\\BrokenLinks");

				String BL_CompleteList_Path = FilePath + "\\CompleteList" + ".txt";
				String BL_UniqueList_Path = FilePath + "\\UniqueList" + ".txt";
				String BL_InternalList_Path = FilePath + "\\InternalList" + ".txt";
				String BL_ExternalList_Path = FilePath + "\\ExternalList" + ".txt";
				String BL_AssetsList_Path = FilePath + "\\AssetsList" + ".txt";
				String BL_ExceptionLinks_Path = FilePath + "\\ExceptionList" + ".txt";
				String BL_UniqueList_Path_Mobile = FilePath + "\\UniqueList_Mobile" + ".txt";
				String BL_InternalList_Path_Mobile = FilePath + "\\InternalList_Mobile" + ".txt";
				String BL_ExternalList_Path_Mobile = FilePath + "\\ExternalList_Mobile" + ".txt";
				String BL_AssetsList_Path_Mobile = FilePath + "\\AssetsList_Mobile" + ".txt";
				String BL_ExceptionLinks_Path_Mobile = FilePath + "\\ExceptionList_Mobile" + ".txt";
				String BL_FinalLinks_Path = FilePath + "\\FinalList" + ".txt";

				/*	System.out.println("Started Writting the list of URL's and Docs to a Text file");
				System.out.println("==============================================================================================");*/

				createFile(BL_CompleteList_Path, BrokenLinks_CompleteList, BrokenLinks_CompleteList.size());
				createFile(BL_UniqueList_Path, BrokenLinks_UniqueList, BrokenLinks_UniqueList.size());
				createFile(BL_InternalList_Path, BrokenLinks_InternalList, BrokenLinks_InternalList.size());
				createFile(BL_ExternalList_Path, BrokenLinks_ExternalList, BrokenLinks_ExternalList.size());
				createFile(BL_AssetsList_Path, BrokenLinks_AssetsList, BrokenLinks_AssetsList.size());
				createFile(BL_ExceptionLinks_Path, BrokenLinks_ExceptionList, BrokenLinks_ExceptionList.size());
				createFile(BL_UniqueList_Path_Mobile, BrokenLinks_UniqueList_Mobile, BrokenLinks_UniqueList_Mobile.size());
				createFile(BL_InternalList_Path_Mobile, BrokenLinks_InternalList_Mobile, BrokenLinks_InternalList_Mobile.size());
				createFile(BL_ExternalList_Path_Mobile, BrokenLinks_ExternalList_Mobile, BrokenLinks_ExternalList_Mobile.size());
				createFile(BL_AssetsList_Path_Mobile, BrokenLinks_AssetsList_Mobile, BrokenLinks_AssetsList_Mobile.size());
				createFile(BL_ExceptionLinks_Path_Mobile, BrokenLinks_ExceptionList_Mobile, BrokenLinks_ExceptionList_Mobile.size());
				createFile(BL_FinalLinks_Path, BrokenLinks_UniqueList_Final, BrokenLinks_UniqueList_Final.size());

				//	System.out.println("Completed Writting the list of URL's and Docs to a Text file");
				System.out.println("==============================================================================================");
			//	System.out.println("Number of links in completefile (duplicate): " + BrokenLinks_CompleteList.size());

				ArrayList<String> interlist = new ArrayList<String>();
				ArrayList<String> templist;
				interlist.addAll(BrokenLinks_InternalList);interlist.addAll(BrokenLinks_InternalList_Mobile);
				templist = new ArrayList<String>(new HashSet<String>(interlist));
			//	System.out.println("Number of links in Internal: " + templist.size());
				interlist.clear();templist.clear();

				interlist.addAll(BrokenLinks_ExternalList);interlist.addAll(BrokenLinks_ExternalList_Mobile);
				templist = new ArrayList<String>(new HashSet<String>(interlist));
			//	System.out.println("Number of links in External: " + templist.size());
				interlist.clear();templist.clear();

				interlist.addAll(BrokenLinks_AssetsList);interlist.addAll(BrokenLinks_AssetsList_Mobile);
				templist = new ArrayList<String>(new HashSet<String>(interlist));
			//	System.out.println("Number of links in Assets: " + templist.size());
				interlist.clear();templist.clear();

				/*interlist.addAll(BrokenLinks_ExceptionList);interlist.addAll(BrokenLinks_ExceptionList_Mobile);
				templist = new ArrayList<String>(new HashSet<String>(interlist));
				System.out.println("Number of links in Exception: " + templist.size());
				interlist.clear();templist.clear();*/

				/*				BrokenLinks_InternalList = new ArrayList<String>(new HashSet<String>(BrokenLinks_InternalList_Mobile));
				BrokenLinks_ExternalList = new ArrayList<String>(new HashSet<String>(BrokenLinks_ExternalList_Mobile));
				BrokenLinks_AssetsList = new ArrayList<String>(new HashSet<String>(BrokenLinks_AssetsList_Mobile));
				BrokenLinks_ExceptionList = new ArrayList<String>(new HashSet<String>(BrokenLinks_ExceptionList_Mobile));
				//	System.out.println("Number of links in Unique list: " + BrokenLinks_UniqueList.size());
				System.out.println("Number of links in Internal: " + BrokenLinks_InternalList.size());
				System.out.println("Number of links in External: " + BrokenLinks_ExternalList.size());
				System.out.println("Number of links in Assets: " + BrokenLinks_AssetsList.size());
				System.out.println("Number of links in Exception: " + BrokenLinks_ExceptionList.size());
				System.out.println("Number of links in Unique list of Mobile : " + BrokenLinks_UniqueList_Mobile.size());
				System.out.println("Number of links in Internal of Mobile: " + BrokenLinks_InternalList_Mobile.size());
				System.out.println("Number of links in External of Mobile: " + BrokenLinks_ExternalList_Mobile.size());
				System.out.println("Number of links in Assets of Mobile: " + BrokenLinks_AssetsList_Mobile.size());
				System.out.println("Number of links in exception of Mobile: " + BrokenLinks_ExceptionList_Mobile.size());*/
			//	System.out.println("Number of Unique links for execution: " + BrokenLinks_UniqueList_Final.size());

				//System.out.println("==============================================================================================");

			}else{
				System.out.println("Unable to perform execution as the status of this domain: "+ status);
			}
		}catch (Exception e) {
			System.out.println("Got exception in between " + e.getMessage());
		}
		return BrokenLinks_UniqueList_Final;
	}

	@SuppressWarnings("null")
	private ArrayList<String> BrokenLinks_ChildPages(String URL, int depth) throws Exception {

		try{

			BrokenLinks_CompleteList.add(URL);

			if ((!BrokenLinks_UniqueList.contains(URL)) && (depth < MAX_DEPTH)) {
				//	System.out.println(">> Depth: " + depth + " [" + URL + "]");

				try {
					BrokenLinks_UniqueList.add(URL);
					BrokenLinks_InternalList.add(URL);

					Document document = Jsoup.connect(URL).followRedirects(true).ignoreHttpErrors(true).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36").timeout(90000).get();	
					Elements linksOnPage = document.select("a[href]");
					depth++;

					for(int i = 0; i<linksOnPage.size();i++) {

						String childlink = linksOnPage.get(i).attr("abs:href");

						CompleteMapping_Desktop.put(URL+" ~ "+childlink, null);

						if(URL.startsWith("https://") && childlink.startsWith("http://")) {
							childlink = childlink.replace("http://", "https://");
						}else {

						}

						try {
							if(childlink.startsWith(SitePath)) {

								//to identify and move documents to documents list		
								if ((childlink.endsWith(".pdf") || childlink.endsWith(".PDF") || childlink.endsWith(".doc") || childlink.endsWith(".xlsx") || childlink.endsWith(".docx") || childlink.endsWith(".txt"))) {

									BrokenLinks_CompleteList.add(childlink);
									if(!BrokenLinks_AssetsList.contains(childlink)) {
										BrokenLinks_AssetsList.add(childlink);
										BrokenLinks_UniqueList.add(childlink);
									}else {
										//System.out.println("its not a problem");
									}
								}
								//to remove the links which have target within the page		
								if(childlink.contains(".html#") || childlink.contains( SitePath +"#") || childlink.contains(SitePath + "/#")) {
									childlink = childlink.split("#")[0];
								}

								BrokenLinks_ChildPages(childlink, depth);

							}else if ((childlink.endsWith(".pdf") || childlink.endsWith(".PDF") || childlink.endsWith(".doc") || childlink.endsWith(".xlsx") || childlink.endsWith(".docx") || childlink.endsWith(".txt"))) {

								BrokenLinks_CompleteList.add(childlink);
								if(!BrokenLinks_AssetsList.contains(childlink)) {
									BrokenLinks_AssetsList.add(childlink);
									BrokenLinks_UniqueList.add(childlink);
								}
							}else {
								BrokenLinks_CompleteList.add(childlink);
								if((childlink.contains("https://www.facebook.com")) || (childlink.contains("https://twitter.com")) || (childlink.contains("mailto:")) || (childlink.contains("print") || (childlink.contains("https://www.youtube.com")) || (childlink.contains("https://youtu.be")))) {
									BrokenLinks_JunkList.add(childlink);
									if((childlink.contains("https://www.facebook.com") && fbindicator.equals("false"))){
										BrokenLinks_UniqueList.add(childlink);
										BrokenLinks_ExternalList.add(childlink);
										fbindicator = "true";
									}else if((childlink.contains("https://twitter.com") && twindicator.equals("false"))){
										BrokenLinks_UniqueList.add(childlink);
										BrokenLinks_ExternalList.add(childlink);
										twindicator = "true";
									}else if((childlink.contains("mailto:") && mailindicator.equals("false"))){
										BrokenLinks_UniqueList.add(childlink);
										BrokenLinks_ExternalList.add(childlink);
										mailindicator = "true";
									}else if((childlink.contains("print") && printindicator.equals("false"))){
										BrokenLinks_UniqueList.add(childlink);
										BrokenLinks_ExternalList.add(childlink);
										printindicator = "true";
									}else if((childlink.contains("https://www.youtube.com") && youindicator.equals("false"))){
										BrokenLinks_UniqueList.add(childlink);
										BrokenLinks_ExternalList.add(childlink);
										youindicator = "true";
									}else if((childlink.contains("https://youtu.be") && youtoindicator.equals("false"))){
										BrokenLinks_UniqueList.add(childlink);
										BrokenLinks_ExternalList.add(childlink);
										youtoindicator = "true";
									}
								}else {
									BrokenLinks_CompleteList.add(childlink);
									if(!BrokenLinks_ExternalList.contains(childlink)) { 
										BrokenLinks_ExternalList.add(childlink);
										BrokenLinks_UniqueList.add(childlink);

									}else {
										//	System.out.println("This link already exists in the list");
									}
								}
							}

						}catch(Exception e) {
							if(!BrokenLinks_ExceptionList.contains(childlink)) {
								BrokenLinks_ExceptionList.add(childlink);
								BrokenLinks_UniqueList.add(childlink);
							}
						}
					}
				} catch (IOException e) {
					if(!BrokenLinks_ExceptionList.contains(URL)) {
						BrokenLinks_ExceptionList.add(URL);
						BrokenLinks_UniqueList.add(URL);
					}
				}
			}


		}catch(Exception e){
			//System.out.println(e.getMessage());
		}

		return BrokenLinks_UniqueList;
	}

	private ArrayList<String> BrokenLinks_ChildPages_Mobile(String mURL, int mobiledepth) throws Exception {

		try{

			BrokenLinks_CompleteList.add(mURL);

			if ((!BrokenLinks_UniqueList_Mobile.contains(mURL)) && (mobiledepth < MAX_DEPTH)) {
				//	System.out.println(">> Depth: " + mobiledepth + " [" + mURL + "]");

				try {
					BrokenLinks_UniqueList_Mobile.add(mURL);
					BrokenLinks_InternalList_Mobile.add(mURL);

					Document document = Jsoup.connect(mURL).followRedirects(true).ignoreHttpErrors(true).ignoreContentType(true).userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0 Mobile/15E148 Safari/604.1").timeout(90000).get();
					Elements linksOnPage = document.select("a[href]");

					mobiledepth++;

					for(int i = 0; i<linksOnPage.size();i++) {

						String childlink = linksOnPage.get(i).attr("abs:href");
						CompleteMapping_Mobile.put(mURL+" ~ "+childlink, null);

						if(mURL.startsWith("https://") && childlink.startsWith("http://")) {
							childlink = childlink.replace("http://", "https://");
						}else {
							//System.out.println("no need to replace");
						}
						try {
							if(childlink.startsWith(SitePath)) {

								//to identify and move documents to documents list		
								if ((childlink.endsWith(".pdf") || childlink.endsWith(".PDF") || childlink.endsWith(".doc") || childlink.endsWith(".xlsx") || childlink.endsWith(".docx") || childlink.endsWith(".txt"))) {

									BrokenLinks_CompleteList.add(childlink);
									if(!BrokenLinks_AssetsList_Mobile.contains(childlink)) {
										BrokenLinks_AssetsList_Mobile.add(childlink);
										BrokenLinks_UniqueList_Mobile.add(childlink);
									}else {
										//System.out.println("its not a problem");
									}
								}
								//to remove the links which have target within the page		
								if(childlink.contains(".html#") || childlink.contains( SitePath +"#") || childlink.contains(SitePath + "/#")) {
									childlink = childlink.split("#")[0];
								}

								BrokenLinks_ChildPages_Mobile(childlink, mobiledepth);

							}else if ((childlink.endsWith(".pdf") || childlink.endsWith(".PDF") || childlink.endsWith(".doc") || childlink.endsWith(".xlsx") || childlink.endsWith(".docx") || childlink.endsWith(".txt"))) {

								BrokenLinks_CompleteList.add(childlink);
								if(!BrokenLinks_AssetsList_Mobile.contains(childlink)) {
									BrokenLinks_AssetsList_Mobile.add(childlink);
									BrokenLinks_UniqueList_Mobile.add(childlink);
								}
							}else {
								BrokenLinks_CompleteList.add(childlink);
								if((childlink.contains("https://www.facebook.com")) || (childlink.contains("https://twitter.com")) || (childlink.contains("mailto:")) || (childlink.contains("print") || (childlink.contains("https://www.youtube.com")) || (childlink.contains("https://youtu.be")))) {
									BrokenLinks_JunkList_Mobile.add(childlink);
									if((childlink.contains("https://www.facebook.com") && fbindicator_Mobile.equals("false"))){
										BrokenLinks_UniqueList_Mobile.add(childlink);
										BrokenLinks_ExternalList_Mobile.add(childlink);
										fbindicator_Mobile = "true";
									}else if((childlink.contains("https://twitter.com") && twindicator_Mobile.equals("false"))){
										BrokenLinks_UniqueList_Mobile.add(childlink);
										BrokenLinks_ExternalList_Mobile.add(childlink);
										twindicator_Mobile = "true";
									}else if((childlink.contains("mailto:") && mailindicator_Mobile.equals("false"))){
										BrokenLinks_UniqueList_Mobile.add(childlink);
										BrokenLinks_ExternalList_Mobile.add(childlink);
										mailindicator_Mobile = "true";
									}else if((childlink.contains("print") && printindicator_Mobile.equals("false"))){
										BrokenLinks_UniqueList_Mobile.add(childlink);
										BrokenLinks_ExternalList_Mobile.add(childlink);
										printindicator_Mobile = "true";
									}else if((childlink.contains("https://www.youtube.com") && youindicator_Mobile.equals("false"))){
										BrokenLinks_UniqueList_Mobile.add(childlink);
										BrokenLinks_ExternalList_Mobile.add(childlink);
										youindicator_Mobile = "true";
									}else if((childlink.contains("https://youtu.be") && youtoindicator_Mobile.equals("false"))){
										BrokenLinks_UniqueList_Mobile.add(childlink);
										BrokenLinks_ExternalList_Mobile.add(childlink);
										youtoindicator_Mobile = "true";
									}
								}else {
									BrokenLinks_CompleteList.add(childlink);
									if(!BrokenLinks_ExternalList_Mobile.contains(childlink)) { 
										BrokenLinks_ExternalList_Mobile.add(childlink);
										BrokenLinks_UniqueList_Mobile.add(childlink);
									}else {
										//	System.out.println("This link already exists in the list");
									}
								}
							}

						}catch(Exception e) {
							if(!BrokenLinks_ExceptionList_Mobile.contains(childlink)) {
								BrokenLinks_ExceptionList_Mobile.add(childlink);
								BrokenLinks_UniqueList_Mobile.add(childlink);
							}
						}
					}
				} catch (IOException e) {
					if(!BrokenLinks_ExceptionList_Mobile.contains(mURL)) {
						BrokenLinks_ExceptionList_Mobile.add(mURL);
						BrokenLinks_UniqueList_Mobile.add(mURL);
					}
				}
			}


		}catch(Exception e){
			//System.out.println(e.getMessage());
		}
		//	System.out.println("No of links in Mobile view: " + BrokenLinks_UniqueList_Mobile.size());
		return BrokenLinks_UniqueList_Mobile;
	}

	private void ClearingListValues() {

		System.out.println("Clearing the list values, getting ready for next execution");

		success=0;
		status200=0;
		fails=0;
		redirect=0;
		permanentredirect=0;
		badrequest=0;
		unauthorized=0;
		paymentrequired=0;
		pagenotfound=0;
		server=0;
		forbidden=0;
		others=0;
		norun=0;
		parentURLpass=0;
		parentURLfail=0;
		passresults.clear();
		failresults.clear();
		failedParentURLs.clear();
		CompleteList.clear();
		BrokenLinks_CompleteList.clear();
		BrokenLinks_UniqueList.clear();
		BrokenLinks_InternalList.clear();
		BrokenLinks_ExternalList.clear();
		BrokenLinks_AssetsList.clear();
		BrokenLinks_ExceptionList.clear();
		BrokenLinks_JunkList.clear();
		BrokenLinks_UniqueList_Mobile.clear();
		BrokenLinks_InternalList_Mobile.clear();
		BrokenLinks_ExternalList_Mobile.clear();
		BrokenLinks_AssetsList_Mobile.clear();
		BrokenLinks_ExceptionList_Mobile.clear();
		BrokenLinks_JunkList_Mobile.clear();
		BrokenLinks_ParentList_Final.clear();
		parentchildmap.clear();
		fbindicator = "false";
		twindicator = "false";
		mailindicator = "false";
		printindicator = "false";
		fbindicator_Mobile = "false";
		twindicator_Mobile = "false";
		mailindicator_Mobile = "false";
		printindicator_Mobile = "false";
		youindicator_Mobile ="false";
		youtoindicator_Mobile ="false";
		youindicator="false";
		youtoindicator="false";
		BrokenLinks_UniqueList_Final.clear();
		finalfaillist.clear();
		status = 0;
		CompleteMapping_Mobile.clear();
		CompleteMapping_Desktop.clear();
		InputResultMap.clear();
		passlist.clear();
		faillist.clear();
	}

	@AfterClass
	public void afterClass() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		String enddate = sdf.format(dt);
		Instant endtime = Instant.now();
		Duration diff = Duration.between(starttime, endtime);

		//System.out.println("==============================================================================================");
		System.out.println("Test Execution for Broken Links execution completed at : "+ enddate);
		System.out.println("Total Execution Time : " +diff.toMinutes() +" minutes");
		System.out.println("==============================================================================================");
		Logger.logTestInfo("Total Execution Time : " +diff.toMinutes() +" minutes");
		Logger.logTestInfo("************* Completed Test Execution *************");
	}

}
