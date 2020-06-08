package site_monitoring;

import org.testng.annotations.Test;

import com.library.HTML_Reports;
import com.library.UtilClass;
import com.library.WebCrawler;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util. LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.annotations.AfterClass;

public class FindingMissingAssets {

	public static String execution = null;  // or InputFile (excel)
	public static String Domain = null;
	public static String input_csv_filePath_Prod56 = System.getProperty("user.dir")+"\\InputData\\csvdata\\findingmissingassets_Prod.csv";
	public static String input_csv_filePath_Test61 = System.getProperty("user.dir")+"\\InputData\\csvdata\\findingmissingassets_Test.csv";
	public static String filePath = System.getProperty("user.dir")+"\\testConfig.properties";
	static String HTML_filePath = System.getProperty("user.dir")+"\\Results\\HTML_results\\All_reports\\";

	static Response response;

	public static String str_starttime =null;
	public static Instant Starttime =null;
	public static Instant Endtime =null;
	static Instant starttime = Instant.now();

	LinkedHashMap<String, LinkedHashMap<String, String>> htmlmap = new LinkedHashMap<>();
	LinkedHashMap<String, LinkedHashMap<String, String>> failmap = new LinkedHashMap<>();

	public static String result_filePath = System.getProperty("user.dir")+"\\Results\\FindMissingAssets.xlsx";

	Instant startTime = Instant.now();

	@BeforeClass
	public void beforeClass() {
		System.out.println("********* FindMissingAssests Execution Started ********* ");
		System.out.println("Execution Thread ID = " +Thread.currentThread().getId());

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		String date = sdf.format(dt);

		System.out.println("==============================================================================================");
		System.out.println("Test Execution Started At : "+date);
		System.out.println("==============================================================================================");
		
		Date dtt = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		String timestamp = df.format(dtt);

		SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		str_starttime = df1.format(dtt);
		
	}

	@Test
	public void findmissingassets() throws Exception {

		File file = new File(filePath);
		FileReader fr = new FileReader(file);
		Properties prop = new Properties();
		prop.load(fr);

		execution = prop.getProperty("MissingAssets-Input");

		Domain = prop.getProperty("MissingAssets-Prod-URl");

		//clearResultShests(); 

		if(execution.equalsIgnoreCase("MissingAssets_Domain")) {

			System.out.println("**** Test Execution started from Domain URL ****");
			htmlmap.clear();

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date dt = new Date();
			str_starttime = sdf.format(dt);
			Starttime = Instant.now();

			String testURL_61 = prop.getProperty("MissingAssets-Test-URl");

			if(!testURL_61.startsWith("http")) {
				testURL_61 = "http://" + testURL_61;
			}

			WebCrawler wb = new WebCrawler();

			List<String> Finalurllist = wb.GetAssetsList(Domain);
			System.out.println("\nTotal no of Parent URL's to execute = " +Finalurllist.size() +"\n");

			for(int i=0;i<Finalurllist.size();i++) {

				Globalvariables.MA_urls = Finalurllist.size();
				
				LinkedHashMap<String, String> AEM56_resultMap = new  LinkedHashMap<>();
				LinkedHashMap<String, String> AEM61_resultMap = new  LinkedHashMap<>();
				LinkedHashMap<String, String> missingtestMap = new  LinkedHashMap<String, String>();
				LinkedHashMap<String, String> missingprodMap = new  LinkedHashMap<String, String>();
				LinkedHashMap<String, String> globalmap = new  LinkedHashMap<String, String>();

				String sheetname = "URL"+i;
				String parenturl = Finalurllist.get(i);

				try {
					String finalURL_56=null;
					String finalURL_61=null;
					String childurl = null;
					String baseurl_61 = null;

					// remove baseURL from parent if contains - get childurl
					if(parenturl.contains("//") && parenturl.contains(".html")) {
						String u1 = parenturl.split("//")[1];
						int index = u1.indexOf('/')+1;
						childurl = u1.substring(index);
					}else {
						childurl = parenturl;
					}
					// split testURL_61 and get the base URL and append to childurl of parentURL
					String htt = testURL_61.split("//")[0];
					String s1 = testURL_61.split("//")[1];
					String s2 = s1.split("/")[0];
					baseurl_61 = htt+"//"+s2;

					finalURL_56 = parenturl;
					if(!parenturl.equals(childurl)){
						finalURL_61 = baseurl_61 + "/" +childurl;
					}else{
						finalURL_61 = baseurl_61;
					}
					System.out.println("Production URL:: " +finalURL_56);
					System.out.println("Test URL :: " +finalURL_61);

					// get AEM 5.6 links
					int status = GetStatus(finalURL_56);

					if((status == 301) || (status == 302)) {
						Response resp = Jsoup.connect(finalURL_56).execute();
						finalURL_56 = resp.url().toString();
						status = GetStatus(finalURL_56);
					}

					System.out.println("launching Prod URL");
					ArrayList<String> AEM56_links = new ArrayList<String>();
					ArrayList<String> AEM61_links = new ArrayList<String>();

					if(status == 200){
						AEM56_links = getAllLinks(finalURL_56);
						System.out.println("Prod_links size : "+AEM56_links.size());

						AEM56_links = RemoveBlankValues(AEM56_links);

						for(int k=0;k<AEM56_links.size();k++) {
							AEM56_resultMap.put("Prod-assets_"+AEM56_links.get(k),	null);
						}
						///Launching Test URL
						System.out.println("launching Test URL");

						int status_61 = GetStatus(finalURL_61);
						if((status_61 == 301) || (status_61 == 302)) {
							Response resp = Jsoup.connect(finalURL_61).execute();
							finalURL_61 = resp.url().toString();
							status_61 = GetStatus(finalURL_61);
						}
						if((status_61 == 200)){

							AEM61_links = getAllLinks(finalURL_61);
							System.out.println("6.1_links size : "+AEM61_links.size());

							AEM61_links = RemoveBlankValues(AEM61_links);

							for(int j=0;j<AEM61_links.size();j++) {
								AEM61_resultMap.put("Test-assets_"+AEM61_links.get(j),	null);
							}

							System.out.println("Map 6.1 size:" +AEM61_resultMap.size());
							System.out.println(AEM61_resultMap);
						}else{
							failmap.put("Page not found: "+"~"+finalURL_61,	null);
						}

						System.out.println("\n-----Comparison Result---------");
						ArrayList<String> missingintest = new ArrayList<String>();
						ArrayList<String> missinginprod = new ArrayList<String>();
						//	ArrayList<String> aem56 = new ArrayList<String>(AEM56_resultMap.keySet());
						//	ArrayList<String> aem61 = new ArrayList<String>(AEM61_resultMap.keySet());

						missingintest.addAll(AEM56_links);
						missingintest.addAll(AEM61_links);
						missingintest.removeAll(AEM61_links); // unique values present in 5.6

						missinginprod.addAll(AEM56_links);
						missinginprod.addAll(AEM61_links);
						missinginprod.removeAll(AEM56_links);  // unique values present in 6.1
						
						HashSet<String> uniquelist = new HashSet<String>();
						uniquelist.addAll(AEM61_links);
						uniquelist.addAll(AEM56_links);
						
						for(int u=0;u<uniquelist.size();u++){
							Globalvariables.MA_total++;
						}
						
						try {
							for(int j=0;j<missingintest.size();j++) {
								String testmissing = missingintest.get(j).toString().replace("Prod-assets_", "");
								missingtestMap.put("MissingTest_"+testmissing, null);
								Globalvariables.MA_failsintest++;
							}

							for(int k=0;k<missinginprod.size();k++) {
								String prodmissing = missinginprod.get(k).toString().replace("Test-assets_", "");
								missingprodMap.put("MissingProd_"+prodmissing, null);
								Globalvariables.MA_failsinprod++;
							}
							
							
							// write results data to excel
						//	writeResults(sheetname,parenturl,AEM56_resultMap,AEM61_resultMap,missingtestMap, missingprodMap);

							globalmap.putAll(AEM56_resultMap);
							globalmap.putAll(AEM61_resultMap);
							globalmap.putAll(missingprodMap);
							globalmap.putAll(missingtestMap);
							//htmlmap.put("ParentURL_"+finalURL_56, globalmap);
							
							if(missingtestMap.isEmpty() && missingprodMap.isEmpty()){
								htmlmap.put("ParentURL_"+finalURL_56, globalmap);
							}else if(missingtestMap.isEmpty()){
								htmlmap.put("ProdFailParentURL_"+finalURL_56, globalmap);
							}else if(missingprodMap.isEmpty()){
								htmlmap.put("TestFailParentURL_"+finalURL_56, globalmap);
							}else{
								htmlmap.put("FailParentURL_"+finalURL_56, globalmap);
							}

							AEM56_resultMap.clear();
							AEM61_resultMap.clear();
							missingtestMap.clear();
							missingprodMap.clear();
							AEM56_links.clear();
							AEM61_links.clear();
							uniquelist.clear();

							System.out.println("*********************** Completed execution for URL - Printing the results ************************");

						}catch(Exception e) {
							System.out.println("error:: exception in writing results");
						}
					}else{
						htmlmap.put("Exception_"+ finalURL_56 + " ~ " + status,	null);
					}

				}catch(Exception e) {

					System.out.println("Unable to establish connection for the: "+ (parenturl));
				}
			}

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date dt1 = new Date();
			String str_endtime = sdf1.format(dt1);
			
			int urlscount = Globalvariables.MA_urls;
			int failcount = Globalvariables.MA_failsinprod+Globalvariables.MA_failsintest;
			int passcount = Globalvariables.MA_total-failcount;
			Endtime = Instant.now();
			Globalvariables.MA_HTML_filePath = HTML_filePath;
			Globalvariables.MA_htmlmap = htmlmap;
			Globalvariables.MA_Filename = "Missing Assets";
			Globalvariables.MA_str_starttime = str_starttime;
			Globalvariables.MA_str_endtime = str_endtime;
			Globalvariables.MA_starttime = starttime;
			Globalvariables.MA_Summary = "Missing Assets";
			Globalvariables.MA_Endtime = Endtime;

			HTML_Reports.MissingAssets_HTMLReport(HTML_filePath,htmlmap,"Missing Assets", str_starttime, str_endtime, starttime,"Missing Assets", Endtime);

		//	htmlmap.clear();
			failmap.clear();

			System.out.println("Results filepath :: " +result_filePath +"\n");
		}else {
			UtilClass u = new UtilClass();
			System.out.println("**** Test Execution started from InputData ****");
			htmlmap.clear();
			List<String> Prod56_list = u.readFromCsv(input_csv_filePath_Prod56);  // read
		//	System.out.println("\nTotal no of URL's to execute = " +Prod56_list.size() +"\n");

			List<String> Test61_list = u.readFromCsv(input_csv_filePath_Test61);  // read

			for(int i=1 ;i<Prod56_list.size();i++) {
				
				Globalvariables.MA_urls = Prod56_list.size()-1;
				
				LinkedHashMap<String, String> AEM56_resultMap = new  LinkedHashMap<>();
				LinkedHashMap<String, String> AEM61_resultMap = new  LinkedHashMap<>();
				LinkedHashMap<String, String> missingtestMap = new  LinkedHashMap<String, String>();
				LinkedHashMap<String, String> missingprodMap = new  LinkedHashMap<String, String>();
				LinkedHashMap<String, String> globalmap = new  LinkedHashMap<String, String>();

				String sheetname = "URL"+i;
				String parenturl = Prod56_list.get(i);

				System.out.println(sheetname +" : " +parenturl);
				try {
					String finalURL_56=Prod56_list.get(i);
					String finalURL_61=Test61_list.get(i);

					// get AEM 5.6 links
					
					if(!finalURL_56.startsWith("http")) {
						finalURL_56 = "http://" + finalURL_56;
					}
					
					if(!finalURL_61.startsWith("http")) {
						finalURL_61 = "http://" + finalURL_61;
					}
					
					System.out.println("Status of Prod url: " + GetStatus(finalURL_56));
					
					
					int status = GetStatus(finalURL_56);

					if((status == 301) || (status == 302)) {
						Response resp = Jsoup.connect(finalURL_56).execute();
						finalURL_56 = resp.url().toString();
						status = GetStatus(finalURL_56);
					}

					ArrayList<String> AEM56_links = new ArrayList<String>();
					ArrayList<String> AEM61_links = new ArrayList<String>();

					if(status == 200){
						AEM56_links = getAllLinks(finalURL_56);
						System.out.println("Prod_links size : "+AEM56_links.size());

						AEM56_links = RemoveBlankValues(AEM56_links);

						for(int k=0;k<AEM56_links.size();k++) {
							AEM56_resultMap.put("Prod-assets_"+AEM56_links.get(k),	null);
						}
						///Launching Test URL

						int status_61 = GetStatus(finalURL_61);
						if((status_61 == 301) || (status_61 == 302)) {
							Response resp = Jsoup.connect(finalURL_61).execute();
							finalURL_61 = resp.url().toString();
							status_61 = GetStatus(finalURL_61);
						}
						if((status_61 == 200)){

							AEM61_links = getAllLinks(finalURL_61);
							System.out.println("6.1_links size : "+AEM61_links.size());

							AEM61_links = RemoveBlankValues(AEM61_links);

							for(int j=0;j<AEM61_links.size();j++) {
								AEM61_resultMap.put("Test-assets_"+AEM61_links.get(j),	null);
							}

						}else{
							failmap.put("Page not found: "+"~"+finalURL_61,	null);
						}

						System.out.println("\n-----Comparison Result---------");
						ArrayList<String> missingintest = new ArrayList<String>();
						ArrayList<String> missinginprod = new ArrayList<String>();
						//	ArrayList<String> aem56 = new ArrayList<String>(AEM56_resultMap.keySet());
						//	ArrayList<String> aem61 = new ArrayList<String>(AEM61_resultMap.keySet());

						missingintest.addAll(AEM56_links);
						missingintest.addAll(AEM61_links);
						missingintest.removeAll(AEM61_links); // unique values present in 5.6

						missinginprod.addAll(AEM56_links);
						missinginprod.addAll(AEM61_links);
						missinginprod.removeAll(AEM56_links);  // unique values present in 6.1

						HashSet<String> uniquelist = new HashSet<String>();
						uniquelist.addAll(AEM61_links);
						uniquelist.addAll(AEM56_links);
						
						for(int un=0;un<uniquelist.size();un++){
							Globalvariables.MA_total++;
						}
						
						
						try {
							for(int j=0;j<missingintest.size();j++) {
								String testmissing = missingintest.get(j).toString().replace("Prod-assets_", "");
								missingtestMap.put("MissingTest_"+testmissing, null);
								Globalvariables.MA_failsintest++;
							}

							for(int k=0;k<missinginprod.size();k++) {
								String prodmissing = missinginprod.get(k).toString().replace("Test-assets_", "");
								missingprodMap.put("MissingProd_"+prodmissing, null);
								Globalvariables.MA_failsinprod++;
							}

							// write results data to excel
						//	writeResults(sheetname,parenturl,AEM56_resultMap,AEM61_resultMap,missingtestMap, missingprodMap);

							globalmap.putAll(AEM56_resultMap);
							globalmap.putAll(AEM61_resultMap);
							globalmap.putAll(missingprodMap);
							globalmap.putAll(missingtestMap);
							//htmlmap.put("ParentURL_"+finalURL_56, globalmap);
							
							if(missingtestMap.isEmpty() && missingprodMap.isEmpty()){
								htmlmap.put("ParentURL_"+finalURL_56, globalmap);
							}else{
								htmlmap.put("FailParentURL_"+finalURL_56, globalmap);
							}
							
							AEM56_resultMap.clear();
							AEM61_resultMap.clear();
							missingtestMap.clear();
							missingprodMap.clear();
							AEM56_links.clear();
							AEM61_links.clear();
							uniquelist.clear();

							System.out.println("*********************** Completed execution for URL - Printing the results ************************");

						}catch(Exception e) {
							System.out.println("error:: exception in writing results");
						}
					}else{
						htmlmap.put("Exception_"+ finalURL_56 + " ~ " + status,	null);
					}

				}catch(Exception e) {
					System.out.println("Unable to establish the connection: "+parenturl);
				}
			}
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date dt1 = new Date();
			String str_endtime = sdf1.format(dt1);
			Endtime = Instant.now();
			Globalvariables.MA_HTML_filePath = HTML_filePath;
			Globalvariables.MA_htmlmap = htmlmap;
			Globalvariables.MA_Filename = "Missing Assets";
			Globalvariables.MA_str_starttime = str_starttime;
			Globalvariables.MA_str_endtime = str_endtime;
			Globalvariables.MA_starttime = starttime;
			Globalvariables.MA_Summary = "Missing Assets";
			Globalvariables.MA_Endtime = Endtime;

			HTML_Reports.MissingAssets_HTMLReport(HTML_filePath,htmlmap,"Missing Assets", str_starttime, str_endtime, starttime,"Missing Assets", Endtime);

			//htmlmap.clear();
			failmap.clear();

			System.out.println("Results filepath :: " +result_filePath +"\n");
		}
	}

	private ArrayList<String> getAllLinks(String ParentURL) throws Exception {


		ArrayList<String> finalchildlinks = new ArrayList<String>(); 
		ArrayList<String> imglinks = new ArrayList<String>(); 
		ArrayList<String> alinks = new ArrayList<String>(); 

		try {
			
			Document document = Jsoup.connect(ParentURL).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36").timeout(90000).get();
			Elements imgtags = document.select("img");
			System.out.println("no of img tags : " +imgtags.size() );

			for(int i=0;i<imgtags.size();i++) {
				String imgtext = imgtags.get(i).attr("src");
				if(imgtext!=null) {
					imgtext = imgtext.substring(imgtext.lastIndexOf('/')+1);
					imglinks.add(imgtext);
				}
			}

			Elements atags = document.select("a[href]");
			System.out.println("no of anchor tags : " +atags.size() );

			for(int i=0;i<atags.size();i++) {
				String href = atags.get(i).attr("href");
				if(href!=null && ((href.contains(".pdf")) || (href.contains(".txt")) || (href.contains(".doc")) || (href.contains(".xlsx")) || (href.contains(".xls")))) {
					href = href.substring(href.lastIndexOf('/')+1);
					alinks.add(href);
				}
			}
		}catch(Exception e) {
			System.out.println("exception in getAlllinks");
			//			e.printStackTrace();
		}

		finally {
			finalchildlinks.addAll(imglinks);
			finalchildlinks.addAll(alinks);
		}
		return finalchildlinks;
	}

	private static int GetStatus (String url) throws Exception {

		int Status = 0;
		try {
			//System.out.println( "Validating status for: " + url);
			//response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).execute();
			response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").timeout(10000).execute();
			Status = response.statusCode();

		} catch (Exception e) {
			System.out.println("Unable to get status for link: " + url);
			Status = 404;
		}
		return Status;
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

	@AfterClass
	public void afterClass() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		String enddate = sdf.format(dt);

		System.out.println("==============================================================================================");
		System.out.println("Test Execution Completion At : "+enddate);
		Instant endTime = Instant.now();
		Duration diff = Duration.between(startTime, endTime);
		System.out.println("Total Execution Time : " +diff.toMinutes() +" minutes");
		System.out.println("==============================================================================================");

	}

}
