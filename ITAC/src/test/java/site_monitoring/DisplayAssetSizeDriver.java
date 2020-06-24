package site_monitoring;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.Connection.Response;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.net.InternetDomainName;
import com.library.CSV_Report;
import com.library.HTML_Reports;
import com.library.UtilClass;
import com.library.WebCrawler;
import com.lowagie.text.Element;

import utilities.GetDriver;


public class DisplayAssetSizeDriver{

	public static String Domain = null;
	public static String execution = null;  // or InputFile (excel)
	public static HashMap<String, Integer> resultMap = new HashMap<>();
	public static String input_csv_filePath = System.getProperty("user.dir")+"\\InputData\\csvdata\\displayassetsize.csv";
	static String HTML_filePath = System.getProperty("user.dir")+"\\Results\\HTML_results\\All_reports\\";
	public static String filePath = System.getProperty("user.dir")+"\\testConfig.properties";

	//public static ArrayList<String> finalchildlinks = new ArrayList<String>(); 
	//public static ArrayList<String> imglinks = new ArrayList<String>(); 
	//public static ArrayList<String> alinks = new ArrayList<String>(); 
	//public static LinkedHashMap<String, String> FinalResultMap = new LinkedHashMap<>();
	//public static LinkedHashMap<String, String> FinalResultMap_mMobile = new LinkedHashMap<>();

	public static LinkedHashMap<String, LinkedHashMap<String, String>> Newmap = new LinkedHashMap<>();

	public static String result_filePath_Web = System.getProperty("user.dir")+"\\Results\\DisplayAssetSize_Web.xlsx";
	public static String result_filePath_mMobile = System.getProperty("user.dir")+"\\Results\\DisplayAssetSize_mMobile.xlsx";
	public static String str_starttime =null;
	public static Instant Starttime =null;
	public static Instant Endtime =null;
	static Instant starttime = Instant.now();
	static Cell cellvalue;
	static Row rowvalue;
	static int rownumber = 0;

	@BeforeClass
	public void beforeClass() {
		System.out.println("********* DisplayAssetSize Execution Started ********* ");
		System.out.println("Execution Thread ID = " +Thread.currentThread().getId());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		String date = sdf.format(dt);

		System.out.println("==============================================================================================");
		System.out.println("Test Execution for finding Asset Sizes Started At : "+date);
		System.out.println("==============================================================================================");

		Date dtt = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		String timestamp = df.format(dtt);

		SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		str_starttime = df1.format(dtt);

	}

	@Test
	public void  SizeOfAssets() throws IOException {

		File file = new File(filePath);
		FileReader fr = new FileReader(file);
		Properties prop = new Properties();
		prop.load(fr);

		execution = prop.getProperty("AssetSizes-Input");
		System.out.println("execution_type : "  +execution);

		Domain = prop.getProperty("AssetSizes_Domain");
		System.out.println("AssetSizes_Domain :: " +Domain);

		if(execution.equalsIgnoreCase("AssetSizes_Domain")) {
			Newmap.clear();
			DomainExecution();   //To get asset sizes by crawling the website first
		}else {
			Newmap.clear();
			InputExecution();   //To get asset sizes of the pages provided in the input file
		}
		//=================================================
		//  Write to text file
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		String timestamp = df.format(date);
	}

	private static void InputExecution() throws IOException {
		// from datasheet
		List<String> list = readFromCsv(input_csv_filePath);  // read
		System.out.println("No of Parent Links to execute : "+list.size());

		//Getting the assets (images and documents) for each desktop and mobile view
		for(int i=0;i<list.size();i++) {

			Globalvariables.DA_urls = list.size();

			ArrayList<String> Desktop_childlinks = new ArrayList<String>();
			ArrayList<String> Mobile_childlinks = new ArrayList<String>();
			LinkedHashMap<String, String> Desktop_ResultMap = new LinkedHashMap<>();
			LinkedHashMap<String, String> Mobile_ResultMap = new LinkedHashMap<>();

			int status = 0;
			String pageurl = list.get(i);

			if(!pageurl.startsWith("http")) {
				pageurl = "http://" + pageurl;
			}
			// *******************************Getting the status of the child link - before getting its assets list *************************************//
			try{
				System.out.println("Started capturing assetsizes for URL: "+i +": "+ pageurl);
				status = GetStatus(pageurl);

				if((status == 301) || (status == 302)) {
					System.out.println("Status of given domain " + pageurl + " is: " + status);
					System.out.println("Given domain has a redirection, taking the redirected url for execution");
					Response resp = Jsoup.connect(pageurl).execute();
					pageurl = resp.url().toString();
					status = GetStatus(pageurl);
					System.out.println("Redirected url: " + pageurl);
				}else if((status != 200)){
					System.out.println( pageurl + " Status is: " + "'" + status +  "'" + " adding it as unable to execute list");
				}
			}
			catch (Exception e){
				status = 404;
				System.out.println(pageurl + " Status is: " + "'" + status +  "'" + " adding it as unable to execute list");
			}


			// **************************** Getting the Assets list of each of the child page *************************************//

			if(status == 200 || status == 304) {
				
				try {
					
					String Pagetype = GetImplementationType(pageurl);
					
					/*if(Pagetype.equals("responsive")){
						Desktop_childlinks = ResponsivegetAllLinks(pageurl, "desktop");
						Mobile_childlinks = ResponsivegetAllLinks(pageurl, "mobile");
					}else{*/
						Desktop_childlinks = getAllLinks(pageurl, "desktop");
						Mobile_childlinks = getAllLinks(pageurl, "mobile");
					
					Desktop_childlinks = UtilClass.RemoveBlankValues(Desktop_childlinks);
					Mobile_childlinks = UtilClass.RemoveBlankValues(Mobile_childlinks);

				}catch(Exception e) {
					System.out.println("Exception in getting the assets list of: " + pageurl);
				}

				// **************************** Getting the size of each Assets and adding it to the map *************************************//			
				
				List<String> uniqlist = new ArrayList<String>();
				List<String> m_uniqlist = new ArrayList<String>();
				
				try {

					for(int j=0;j<Desktop_childlinks.size();j++) {

						HashSet<String>uniquelist = new HashSet<String>(Desktop_childlinks);

						Globalvariables.DA_dtotal = uniquelist.size();

						long size = 0;
						long updatedsize =0;
						String assetname = Desktop_childlinks.get(j).toString();
						String AssetSize = null;
						String units = null;
						try {
							if(assetname.contains(" ")) {
								assetname = assetname.replaceAll(" ", "%20");
							}

							URL url = new URL(assetname);
							URLConnection conn = url.openConnection();
							size = conn.getContentLength();

							//System.out.println("****************Assets Size************: "+size);
							if(size<1024) {
								updatedsize = size;
								units = "B";
							}else {
								updatedsize = size/1024;
								units = "KB";
							}
							AssetSize = updatedsize+units;

						}catch (Exception e) {
						//	System.out.println("Got exception for: " + assetname);
							size=-1;
						}
						
						//for any asset whose size is -1
						if((size == -1) || (size == 0)) {
							try{
								//System.out.println("Downloading the file to check its size");
								String filePath = System.getProperty("user.dir")+"\\InputData";
								String fileUrl = assetname;
								String fileName = assetname.substring(assetname.lastIndexOf("/")+ 1);
								FileUtils.copyURLToFile(new URL(fileUrl), new File(filePath + "\\" + fileName));   //Downloading and saving the file in a folder
								File file =new File(filePath + "\\" + fileName);
								if(file.exists()){
									//System.out.println("size of the file: " + file.length());
									size = file.length();
									file.delete();
								}
							}catch(Exception e){
							//	System.out.println("Exception in asset sizes "+e.getMessage());
								//Desktop_ResultMap.put("Reddesktopview_" + assetname, "null");
							}
						}
						
						assetname = assetname.substring(assetname.lastIndexOf('/')+1);
						
						File file = new File(filePath);
						FileReader fr = new FileReader(file);
						Properties prop = new Properties();
						prop.load(fr);
						
						int Redvalue = Integer.parseInt(prop.getProperty("Red"));
						int Ambervalue = Integer.parseInt(prop.getProperty("Amber"));
						
						Redvalue = Redvalue*1024;
						
						Ambervalue = Ambervalue*1024;
					
						if(!(uniqlist.contains(assetname))){
							if(size>Redvalue){
								uniqlist.add(assetname);
								Desktop_ResultMap.put("Reddesktopview_" + assetname, AssetSize);
								Globalvariables.DA_dred++;
							}else if(size>Ambervalue && size<Redvalue){
								uniqlist.add(assetname);
								Desktop_ResultMap.put("Amberdesktopview_" + assetname, AssetSize);
								Globalvariables.DA_dgreen++;
							}else if(size>0 && size<Ambervalue){
								uniqlist.add(assetname);
								Desktop_ResultMap.put("desktopview_" + assetname, AssetSize);
								Globalvariables.DA_damber++;
							}
						}
						//Desktop_ResultMap.put("desktopview_" + assetname, AssetSize);
						//Desktop_ResultMap.put(Desktop_childlinks.get(j), null);
					}
					
					for(int k=0;k<Mobile_childlinks.size();k++) {

						Globalvariables.DA_mtotal = Mobile_childlinks.size();

						long m_size = 0;
						long updatedsize_m_size = 0;
						String m_units = null;
						String m_AssetSize = null;
						String m_assetname = Mobile_childlinks.get(k).toString();

						try {
							if(m_assetname.contains(" ")) {
								m_assetname = m_assetname.replaceAll(" ", "%20");
							}
							URL url = new URL(m_assetname.toString());
							URLConnection conn = url.openConnection();
							m_size = conn.getContentLength();


							if(m_size<1024) {
								updatedsize_m_size = m_size;
								m_units = "B";
							}else {
								updatedsize_m_size = m_size/1024;
								m_units = "KB";
							}
							m_AssetSize = updatedsize_m_size + m_units;
						}catch (Exception e) {
							//System.out.println("Got exception for: " + m_AssetSize);
							m_size=-1;
						}

						//for any asset whose size is -1
						if((m_size == -1) || (m_size == 0)) {
						try{
							//	System.out.println("Downloading the file to check its size");
							String filePath = System.getProperty("user.dir")+"\\InputData";
							String fileUrl = m_assetname;
							String fileName = m_assetname.substring(m_assetname.lastIndexOf("/")+ 1);
							FileUtils.copyURLToFile(new URL(fileUrl), new File(filePath + "\\" + fileName));   //Downloading and saving the file in a folder
							File file =new File(filePath + "\\" + fileName);
							if(file.exists()){
								//System.out.println("size of the file: " + file.length());
								m_size = file.length();
								file.delete();
							}
							}catch(Exception e){
								//System.out.println("Exception in asset sizes "+e.getMessage());
								//Mobile_ResultMap.put("Redmobileview_" + m_assetname, "null");
							}
						}
						
						m_assetname = m_assetname.substring(m_assetname.lastIndexOf('/')+1);
						
						File file = new File(filePath);
						FileReader fr = new FileReader(file);
						Properties prop = new Properties();
						prop.load(fr);
						
						int Redvalue = Integer.parseInt(prop.getProperty("Red"));
						int Ambervalue = Integer.parseInt(prop.getProperty("Amber"));
						
						Redvalue = Redvalue*1024;
						Ambervalue = Ambervalue*1024;
						
						if(!(m_uniqlist.contains(m_assetname))){
						
						
						if(m_size>Redvalue){
							m_uniqlist.add(m_assetname);
							Mobile_ResultMap.put("Redmobileview_" + m_assetname, m_AssetSize);
							Globalvariables.DA_mred++;
						}else if(m_size>Ambervalue && m_size<Redvalue){
							m_uniqlist.add(m_assetname);
							Mobile_ResultMap.put("Ambermobileview_" + m_assetname, m_AssetSize);
							Globalvariables.DA_mgreen++;
						}else if(m_size>1 && m_size<Ambervalue){
							m_uniqlist.add(m_assetname);
							Mobile_ResultMap.put("mobileview_" + m_assetname, m_AssetSize);
							Globalvariables.DA_mamber++;
						}
						}
						//Mobile_ResultMap.put("mobileview_" + m_assetname, m_AssetSize);

					}

					// **************************** Adding all the details to the final map *************************************//	
					LinkedHashMap<String, String> addingresults = new LinkedHashMap<String, String>();
					addingresults.putAll(Desktop_ResultMap);
					addingresults.putAll(Mobile_ResultMap);
					Newmap.put("ParentURL_"+ pageurl, addingresults);
					Desktop_ResultMap.clear();
					Mobile_ResultMap.clear();
					Desktop_childlinks.clear();
					Mobile_childlinks.clear();

				}catch(Exception e) {
					System.out.println("Error :: exception in write results");
					e.printStackTrace();
				}

			}else {
				Newmap.put("Exception_"+ pageurl + " ~ " + status, Desktop_ResultMap);
			}
		}

		System.out.println("*********************** Completed execution - Printing the results ************************");

		for(Entry<String, LinkedHashMap<String, String>> res : Newmap.entrySet()){
			String key = res.getKey().toString();
			String newkey = null;
			String newvalue = null;

			for(java.util.Map.Entry<String, String> newres : res.getValue().entrySet()){
				newkey = newres.getKey().toString();
				newvalue = newres.getValue().toString();
				//System.out.println(key + " ~ " + newkey + " ~ " + newvalue);
			}
		//	System.out.println(key + " ~ " + newkey + " ~ " + newvalue);
		}

		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt1 = new Date();
		String str_endtime = sdf1.format(dt1);
		Endtime = Instant.now();

		Globalvariables.DA_HTML_filePath = HTML_filePath;
		Globalvariables.DA_Newmap = Newmap;
		Globalvariables.DA_Filename = "Display Asset Sizes";
		Globalvariables.DA_str_starttime = str_starttime;
		Globalvariables.DA_str_endtime =str_endtime;
		Globalvariables.DA_starttime = starttime;
		Globalvariables.DA_Summary = "Asset Sizes";
		Globalvariables.DA_Endtime = Endtime;
		
		String fileAddress = System.getProperty("user.dir")+"\\Results\\DisplayAssetSizes.csv";

		CSV_Report.generateCSVAssets(fileAddress, Newmap);
		
		HTML_Reports.AssetSizes_HTMLReport(HTML_filePath,Newmap,"Display Asset Sizes",str_starttime,str_endtime,starttime,"Display Asset Sizes",Endtime);
		//SizeOfAssets_ResulstWritting(result_filePath_Web,FinalResultMap);
		list.clear();
	}

	private static void DomainExecution() throws IOException {


		WebCrawler wb = new WebCrawler();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		str_starttime = sdf.format(dt);
		Starttime = Instant.now();

		// Fetching the child links by crawling the given domain
		System.out.println("**** Test Execution started - Fetching the child links of this domain ****");
		List<String> Finalurllist = wb.GetAssetsList(Domain);
		System.out.println("Completed crawling the website and got the child links, total childlinks: " + Finalurllist.size());


		//Getting the assets (images and documents) for each desktop and mobile view
		for(int i=0;i<Finalurllist.size();i++) {

			Globalvariables.DA_urls = Finalurllist.size();

			ArrayList<String> Desktop_childlinks = new ArrayList<String>();
			ArrayList<String> Mobile_childlinks = new ArrayList<String>();
			LinkedHashMap<String, String> Desktop_ResultMap = new LinkedHashMap<>();
			LinkedHashMap<String, String> Mobile_ResultMap = new LinkedHashMap<>();

			int status = 0;
			String pageurl = Finalurllist.get(i);

			// *******************************Getting the status of the child link - before getting its assets list *************************************//

			try{
				System.out.println("Started capturing assetsizes for URL: "+i +": "+ pageurl);
				status = GetStatus(pageurl);

				if((status == 301) || (status == 302)) {
					System.out.println("Status of given domain " + pageurl + " is: " + status);
					System.out.println("Given domain has a redirection, taking the redirected url for execution");
					Response resp = Jsoup.connect(pageurl).execute();
					pageurl = resp.url().toString();
					status = GetStatus(pageurl);
					System.out.println("Redirected url: " + pageurl);
				}else if((status != 200)){
					System.out.println( pageurl + " Status is: " + "'" + status +  "'" + " adding it as unable to execute list (not 200)");

				}
			}
			catch (Exception e){
				status = 404;
				System.out.println(pageurl + " Status is: " + "'" + status +  "'" + " adding it as unable to execute list (exception)");
			}


			// **************************** Getting the Assets list of each of the child page *************************************//

			if(status == 200) {
				try {
					
					String Pagetype = GetImplementationType(pageurl);
					
					if(Pagetype.equals("responsive")){
						Desktop_childlinks = ResponsivegetAllLinks(pageurl, "desktop");
						Mobile_childlinks = ResponsivegetAllLinks(pageurl, "mobile");
					}else{
						Desktop_childlinks = getAllLinks(pageurl, "desktop");
						Mobile_childlinks = getAllLinks(pageurl, "mobile");
					}

					Desktop_childlinks = UtilClass.RemoveBlankValues(Desktop_childlinks);
					Mobile_childlinks = UtilClass.RemoveBlankValues(Mobile_childlinks);

				}catch(Exception e) {
					System.out.println("Exception in getting the assets list of: " + pageurl);
				}

				// **************************** Getting the size of each Assets and adding it to the map *************************************//			


				List<String> uniqlist = new ArrayList<String>();
				List<String> m_uniqlist = new ArrayList<String>();
				
				try {

					for(int j=0;j<Desktop_childlinks.size();j++) {

						Globalvariables.DA_dtotal = Desktop_childlinks.size();

						long size = 0;
						long updatedsize = 0;
						String assetname = Desktop_childlinks.get(j).toString();
						String AssetSize = null;
						String units = null;
						try {
							if(assetname.contains(" ")) {
								assetname = assetname.replaceAll(" ", "%20");
							}
							URL url = new URL(assetname);
							URLConnection conn = url.openConnection();
							size = conn.getContentLength();

							if(size<1024) {
								updatedsize = size;
								units = "B";
							}else {
								updatedsize = size/1024;
								units = "KB";
							}
							AssetSize = updatedsize+units;

						}catch (Exception e) {
							//System.out.println("Got exception for: " + assetname);
							size=-1;
						}
						
						//To handle files when their size is -1
						if((size == -1) || (size == 0)) {
							try{
							String filePath = System.getProperty("user.dir")+"\\InputData";
							String fileUrl = assetname;
							String fileName = assetname.substring(assetname.lastIndexOf("/")+ 1);
						//	System.out.println("Downloading the file: " + fileName);
							FileUtils.copyURLToFile(new URL(fileUrl), new File(filePath + "\\" + fileName));   //Downloading and saving the file in a folder
							File file =new File(filePath + "\\" + fileName);
							if(file.exists()){
								//	System.out.println("size of the file: " + file.length());
								size = file.length();
								file.delete();
							}
							}catch(Exception e){
								//System.out.println("Exception in asset sizes "+e.getMessage());
							}
						}
						
						assetname = assetname.substring(assetname.lastIndexOf('/')+1);
						
						File file = new File(filePath);
						FileReader fr = new FileReader(file);
						Properties prop = new Properties();
						prop.load(fr);
						
						int Redvalue = Integer.parseInt(prop.getProperty("Red"));
						int Ambervalue = Integer.parseInt(prop.getProperty("Amber"));
						
						Redvalue = Redvalue*1024;
						Ambervalue = Ambervalue*1024;
						
						if(!(uniqlist.contains(assetname))){
							if(size>Redvalue){
								uniqlist.add(assetname);
								Desktop_ResultMap.put("Reddesktopview_" + assetname, AssetSize);
								Globalvariables.DA_dred++;
							}else if(size>Ambervalue && size<Redvalue){
								uniqlist.add(assetname);
								Desktop_ResultMap.put("Amberdesktopview_" + assetname, AssetSize);
								Globalvariables.DA_dgreen++;
							}else if(size>1 && size<Ambervalue){
								uniqlist.add(assetname);
								Desktop_ResultMap.put("desktopview_" + assetname, AssetSize);
								Globalvariables.DA_damber++;
							}
						}

						//Desktop_ResultMap.put("desktopview_" + assetname, AssetSize);
					}

					for(int k=0;k<Mobile_childlinks.size();k++) {

						Globalvariables.DA_mtotal = Mobile_childlinks.size();

						long m_size = 0;
						long updatedm_size = 0;
						String m_units = null;
						String m_AssetSize = null;
						String m_assetname = Mobile_childlinks.get(k).toString();

						try {
							if(m_assetname.contains(" ")) {
								m_assetname = m_assetname.replaceAll(" ", "%20");
							}
							URL url = new URL(m_assetname.toString());
							URLConnection conn = url.openConnection();
							m_size = conn.getContentLength();

							if(m_size<1024) {
								updatedm_size = m_size;
								m_units = "B";
							}else {
								updatedm_size = m_size/1024;
								m_units = "KB";
							}
							m_AssetSize = updatedm_size + m_units;
						}catch (Exception e) {
						//	System.out.println("Got exception for: " + m_AssetSize);
							m_size=-1;
						}
						//To handle files when their size is -1
						if((m_size == -1) || (m_size == 0)) {
							try{
							String filePath = System.getProperty("user.dir")+"\\InputData";
							String fileUrl = m_assetname;
							String fileName = m_assetname.substring(m_assetname.lastIndexOf("/")+ 1);
							//System.out.println("Downloading the file: " + fileName);
							FileUtils.copyURLToFile(new URL(fileUrl), new File(filePath + "\\" + fileName));   //Downloading and saving the file in a folder
							File file =new File(filePath + "\\" + fileName);
							if(file.exists()){
							//	System.out.println("size of the file: " + file.length());
								m_size = file.length();
								file.delete();
							}
							}catch(Exception e){
							//	System.out.println("Exception in asset sizes: "+e.getMessage());
							}
						}
						
						m_assetname = m_assetname.substring(m_assetname.lastIndexOf('/')+1);
						
						File file = new File(filePath);
						FileReader fr = new FileReader(file);
						Properties prop = new Properties();
						prop.load(fr);
						
						int Redvalue = Integer.parseInt(prop.getProperty("Red"));
						int Ambervalue = Integer.parseInt(prop.getProperty("Amber"));
						
						Redvalue = Redvalue*1024;
						Ambervalue = Ambervalue*1024;
						
						if(!(m_uniqlist.contains(m_assetname))){
						
							if(m_size>Redvalue){
								m_uniqlist.add(m_assetname);
								Mobile_ResultMap.put("Redmobileview_" + m_assetname, m_AssetSize);
								Globalvariables.DA_mred++;
							}else if(m_size>Ambervalue && m_size<Redvalue){
								m_uniqlist.add(m_assetname);
								Mobile_ResultMap.put("Ambermobileview_" + m_assetname, m_AssetSize);
								Globalvariables.DA_mgreen++;
							}else if(m_size>1 && m_size<Ambervalue){
								m_uniqlist.add(m_assetname);
								Mobile_ResultMap.put("mobileview_" + m_assetname, m_AssetSize);
								Globalvariables.DA_mamber++;
							}
						}

						//Mobile_ResultMap.put("mobileview_" + m_assetname, m_AssetSize);
					}

					// **************************** Adding all the details to the final map *************************************//	
					LinkedHashMap<String, String> addingresults = new LinkedHashMap<String, String>();
					addingresults.putAll(Desktop_ResultMap);
					addingresults.putAll(Mobile_ResultMap);
					Newmap.put("ParentURL_"+ pageurl, addingresults);
					Desktop_ResultMap.clear();
					Mobile_ResultMap.clear();
					Desktop_childlinks.clear();
					Mobile_childlinks.clear();

					System.out.println("Completed Captruing Asset Sizes for: " + pageurl);

				}catch(Exception e) {
					System.out.println("Error :: exception in write results");
					e.printStackTrace();
				}

			}else {
				Newmap.put("Exception_"+ pageurl + " ~ " + status, Desktop_ResultMap);
			}
		}

		System.out.println("*********************** Completed execution - Printing the results ************************");

		for(Entry<String, LinkedHashMap<String, String>> res : Newmap.entrySet()){

			try {
				String key = res.getKey().toString();
				String newkey = null;
				String newvalue = null;

				for(java.util.Map.Entry<String, String> newres : res.getValue().entrySet()){
					newkey = newres.getKey().toString();
					newvalue = newres.getValue().toString();
					//System.out.println(key + " ~ " + newkey + " ~ " + newvalue);
				}
			//	System.out.println(key + " ~ " + newkey + " ~ " + newvalue);

			}catch (Exception e) {
				System.out.println("Exception in printing the results");
			}
		}

		System.out.println("*********************** Completed execution - Printing the results ************************");

		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt1 = new Date();
		String str_endtime = sdf1.format(dt1);
		Endtime = Instant.now();
		Globalvariables.DA_HTML_filePath = HTML_filePath;
		Globalvariables.DA_Newmap = Newmap;
		Globalvariables.DA_Filename = "Display Asset Sizes";
		Globalvariables.DA_str_starttime = str_starttime;
		Globalvariables.DA_str_endtime =str_endtime;
		Globalvariables.DA_starttime = starttime;
		Globalvariables.DA_Summary = "Asset Sizes";
		Globalvariables.DA_Endtime = Endtime;
		
		String fileAddress = System.getProperty("user.dir")+"\\Results\\DisplayAssetSizes.csv";

		CSV_Report.generateCSVAssets(fileAddress, Newmap);

		HTML_Reports.AssetSizes_HTMLReport(HTML_filePath,Newmap,"Display Asset Sizes",str_starttime,str_endtime,starttime,"Display Asset Sizes",Endtime);
		//SizeOfAssets_ResulstWritting(result_filePath_Web,FinalResultMap);

		Finalurllist.clear();
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
	
	private static ArrayList<String> getAllLinks(String parenturl, String view) throws InterruptedException, IOException {

		ArrayList<String> finalchildlinks = new ArrayList<String>(); 
		ArrayList<String> imglinks = new ArrayList<String>(); 
		ArrayList<String> alinks = new ArrayList<String>();
		ArrayList<String> elinks = new ArrayList<String>();

		try {

			Document document;
			Connection connect;
			if(view.equalsIgnoreCase("desktop")) {
				document = Jsoup.connect(parenturl).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36").timeout(90000).get();
			}else {
				document = Jsoup.connect(parenturl).userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0 Mobile/15E148 Safari/604.1").timeout(90000).get();
			}
			Elements imgtags = document.select("img");
			for(int i=0;i<imgtags.size();i++) {
				String imgtext = imgtags.get(i).absUrl("src");
				if((imgtext.contains("/etc/")) || (imgtext.endsWith(".png") || (imgtext.endsWith("jpg")) || (imgtext.endsWith("jpeg"))
						|| (imgtext.endsWith(".tif")) || (imgtext.endsWith(".gif")) || (imgtext.endsWith(".bmp"))
						|| (imgtext.endsWith(".eps")) || (imgtext.contains("/content/dam/"))) ) {
					imglinks.add(imgtext);
				}else {
				//	imgtext = imgtext.substring(imgtext.lastIndexOf('/')+1);
					imglinks.add(imgtext);
					
				}
			}

			Elements atags = document.select("a[href]");

			for(int i=0;i<atags.size();i++) {
				String href = atags.get(i).absUrl("href");
				if(href!=null && ((href.contains(".pdf")) || (href.contains(".PDF")) || (href.contains(".txt")) || (href.contains(".doc")) 
						|| (href.contains(".xlsx")) || (href.contains(".xls")))) {
					alinks.add(href);
				}
			}
			
			Elements embed = document.select("embed");
			
			for(int k=0;k<embed.size();k++) {
			String embedtags = embed.get(k).absUrl("src");
			if(embedtags!=null && ((embedtags.contains(".swf")))){
				elinks.add(embedtags);
			}
					
			}
		}catch(Exception e) {
			System.out.println("exception in getAlllinks");
		}
		
		finally {
			finalchildlinks.clear();
			finalchildlinks.addAll(imglinks);
			finalchildlinks.addAll(alinks);
			finalchildlinks.addAll(elinks);
			imglinks.clear();
			alinks.clear();
			elinks.clear();
		}

		return finalchildlinks;
	}
	
	

	private static ArrayList<String> ResponsivegetAllLinks(String parenturl, String view) throws Exception {

		ArrayList<String> finalchildlinks = new ArrayList<String>(); 
		ArrayList<String> imglinks = new ArrayList<String>(); 
		ArrayList<String> alinks = new ArrayList<String>();
		ArrayList<String> elinks = new ArrayList<String>();
		
		WebDriver driver;
		driver = GetDriver.getdriver("Local_Chrome");
		driver.manage().window().setPosition(new Point(-2000, 0));
		
	   String domainname = getUrlDomainName(parenturl);

		try {
			
			Document document;
			if(view.equalsIgnoreCase("desktop")) {
				String html;
				driver.get(parenturl);
				html = driver.getPageSource();
				driver.quit();
				document = Jsoup.parse(html);
			}else {
				String html;
				parenturl = parenturl.replace(".html", ".m.html");
				driver.manage().window().setSize(new Dimension(375,667));
				driver.get(parenturl);
				html = driver.getPageSource();
				driver.quit();
				document = Jsoup.parse(html);
			}
			
			Elements imgtags = document.select("img");
			for(int i=0;i<imgtags.size();i++) {
				String imgtext = imgtags.get(i).attr("src");
				if((imgtext.contains("/etc/")) || (imgtext.endsWith(".png") || (imgtext.endsWith("jpg")) || (imgtext.endsWith("jpeg"))
						|| (imgtext.endsWith(".tif")) || (imgtext.endsWith(".gif")) || (imgtext.endsWith(".bmp"))
						|| (imgtext.endsWith(".eps")) || (imgtext.contains("/content/dam/"))) ) {
					
					if(imgtext.contains("http")){
						imglinks.add(imgtext);
					}else{
						imglinks.add("https://"+domainname+imgtext);
					}
				}else {
					if(imgtext.contains("http")){
						imglinks.add(imgtext);
					}else{
						imglinks.add("https://"+domainname+imgtext);
					}
				}
			}

			Elements atags = document.select("a[href]");

			for(int i=0;i<atags.size();i++) {
				String href = atags.get(i).attr("href");
				if(href!=null && ((href.contains(".pdf")) || (href.contains(".PDF")) || (href.contains(".txt")) || (href.contains(".doc")) 
						|| (href.contains(".xlsx")) || (href.contains(".xls")))) {
					if(href.contains("http")){
						alinks.add(href);
					}else{
						alinks.add("https://"+domainname+href);
					}
					
				}
			}
			
			Elements embed = document.select("embed");
			
			for(int k=0;k<embed.size();k++) {
			String embedtags = embed.get(k).attr("src");
			if(embedtags!=null && ((embedtags.contains(".swf")))){
				if(embedtags.contains("http")){
					elinks.add(embedtags);
				}else{
					elinks.add("https://"+domainname+embedtags);
				}
			}
					
			}
		}catch(Exception e) {
			System.out.println("exception in getAlllinks");
		}
		
		finally {
			finalchildlinks.clear();
			finalchildlinks.addAll(imglinks);
			finalchildlinks.addAll(alinks);
			finalchildlinks.addAll(elinks);
			imglinks.clear();
			alinks.clear();
			elinks.clear();
		}

		return finalchildlinks;
	}

	private static int GetStatus (String url) {
		int Status;
		Response response;
		try {
			response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).execute();
			Status = response.statusCode();
			if(Status == 403){
				response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36").timeout(60000).execute();
				Status = response.statusCode();
			}
		}catch (Exception e) {
			System.out.println("Unable to get status for link: " + url);
			Status = 404;
		}

		if(Status == 0) {
			Status = 404;
		}

		return Status;
	}
	
	private static String GetImplementationType(String pageurl) throws Exception {

		Document doc = Jsoup.connect(pageurl).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36").timeout(90000).get();
		String header = doc.select("h1").attr("class");
		String ImplementationType = null;
		// ------------------------------ To Identify the Implementation Type - Adaptive or Responsive -------------------------------//
		if(header.contains("global-header")){
			ImplementationType = "adaptive";
			System.out.println("This is adaptive");
		}else {
			ImplementationType = "responsive";
			System.out.println("This is responsive");
		}
		return ImplementationType;
	}

	public static String getUrlDomainName(String url) {
		  String domainName = new String(url);

		  int index = domainName.indexOf("://");

		  if (index != -1) {
		    // keep everything after the "://"
		    domainName = domainName.substring(index + 3);
		  }

		  index = domainName.indexOf('/');

		  if (index != -1) {
		    // keep everything before the '/'
		    domainName = domainName.substring(0, index);
		  }

		  // check for and remove a preceding 'www'
		  // followed by any sequence of characters (non-greedy)
		  // followed by a '.'
		  // from the beginning of the string
		  domainName = domainName.replaceFirst("^www.*?\\.", "");

		  return domainName;
		}
	
	@AfterClass
	public void afterClass() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		String enddate = sdf.format(dt);

		rownumber = 0;
		Instant endtime = Instant.now();
		Duration diff = Duration.between(starttime, endtime);
		System.out.println("==============================================================================================");
		System.out.println("Test Execution of Finding Asset Sizes Completed at : "+enddate);
		System.out.println("Total Execution Time : " +diff.toMinutes() +" minutes");
		System.out.println("==============================================================================================");
	}

}