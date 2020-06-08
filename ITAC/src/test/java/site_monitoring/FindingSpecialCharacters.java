package site_monitoring;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.internal.ResultMap;

import com.library.CSV_Report;
import com.library.HTML_Reports;
import com.library.Logger;
import com.library.UtilClass;
import com.library.WebCrawler;


public class FindingSpecialCharacters{

	public static String execution = null;  // or InputFile (excel)
	public static int  MAX_DEPTH = 2;
	public static String Domain = null;
	public static String input_csv_filePath = System.getProperty("user.dir")+"\\InputData\\csvdata\\findingspecialcharacters.csv";
	public static String filePath = System.getProperty("user.dir")+"\\testConfig.properties";

	public static LinkedHashMap<String, Integer> resultMap = new LinkedHashMap<>();

	public static LinkedHashMap<String, LinkedHashMap<String,String>> htmlmap = new LinkedHashMap<>();

	public static int passCount=0;
	public static int failCount=0;
	public static int passvalue =0;
	public static int failvalue=0;
	Instant starttime = Instant.now();
	public static Instant Endtime = Instant.now();
	public static String str_starttime =null;
	public static int rowcount=0;
	static Response response;
	public static String csv_file = System.getProperty("user.dir")+"\\Results\\FindSpecialChars_csvReport.csv";

	@BeforeClass
	public void beforeClass() {
		System.out.println("********* FindSpecialCharacters Execution Started ********* ");
		System.out.println("Execution Thread ID = " +Thread.currentThread().getId());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		String date = sdf.format(dt);

		System.out.println("==============================================================================================");
		System.out.println("Test Execution of Finding Speical Characters in Assets Started At : "+date);
		System.out.println("==============================================================================================");

		Date dtt = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		String timestamp = df.format(dtt);

		SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		str_starttime = df1.format(dtt);

		String filePathOflogger = System.getProperty("user.dir")+"\\Results\\Logs\\";
		String fileNameOflogger = "SpecialCharacters"+timestamp+".log";
		Logger.initializeLogger(filePathOflogger, fileNameOflogger);
	}

	@Test
	public void  findSpecialCharacters() throws Exception {

		File file = new File(filePath);
		FileReader fr = new FileReader(file);
		Properties prop = new Properties();
		prop.load(fr);

		execution = prop.getProperty("SpecialCharacters-Input");
		System.out.println("execution_type : "  +execution);

		Domain = prop.getProperty("SpecialCharacters-Domain");
		System.out.println("SpecialCharacters-Domain :: " +Domain);

		if(execution.equalsIgnoreCase("SpecialCharacters_Domain")) {

			WebCrawler wb = new WebCrawler();
			System.out.println("**** Test Execution started from Domain URL ****");
			htmlmap.clear();
			System.out.println("fetching childlinks...");

			List<String> Final_ParentUrllist = new ArrayList<String>();

			try{

				Final_ParentUrllist = wb.GetAssetsList(Domain);

			}catch(Exception e){
				System.out.println(e.getMessage());
			}


			System.out.println("No of Parent links in this doamin : " +Final_ParentUrllist.size());

			ArrayList<String> finalchildlinks_local_desktop = new ArrayList<String>();
			ArrayList<String> finalchildlinks_local_mobile = new ArrayList<String>();
			HashSet<String> uniquelist = new HashSet<String>();
			ArrayList<String> finalchildlinks_local = new ArrayList<String>();
			LinkedHashMap<String, String> individualResultMap = new LinkedHashMap<>();
			LinkedHashMap<String, LinkedHashMap<String, String>> FinalResultMap = new LinkedHashMap<>();

			rowcount = Final_ParentUrllist.size();

			for(int i=0; i<Final_ParentUrllist.size(); i++) {
				try{

					Globalvariables.SC_urls = Final_ParentUrllist.size();

					String parenturl = Final_ParentUrllist.get(i);

					int status = GetStatus(parenturl);

					if((status == 301) || (status == 302)) {
						Response resp = Jsoup.connect(parenturl).execute();
						parenturl = resp.url().toString();
						status = GetStatus(parenturl);
					}

					System.out.println("Status of this URL:"+ parenturl+":  " + status);
					try{
						if(!(status == 404)){

							finalchildlinks_local_desktop = getAllLinks(i,Final_ParentUrllist.get(i), "desktop");
							uniquelist.addAll(finalchildlinks_local_desktop);

							finalchildlinks_local_mobile = getAllLinks(i,Final_ParentUrllist.get(i), "mobile");
							uniquelist.addAll(finalchildlinks_local_mobile);

							finalchildlinks_local.addAll(uniquelist);

							System.out.println("Size of finalchildlinks: " + finalchildlinks_local.size());

							for(int j=0;j<finalchildlinks_local.size();j++) {
								individualResultMap.put(finalchildlinks_local.get(j), null);
							}
							FinalResultMap.put("ParentURL_"+Final_ParentUrllist.get(i), individualResultMap);

							validation(FinalResultMap);

						}else {
							htmlmap.put("NoAssets_"+parenturl +" : " +"No assets to display__"+status, null);
						}
					}catch(Exception e){
						System.out.println("Exception in getting the links");
					}
				}catch(Exception e){
					System.out.println("Exception in getting the links");
				}finally{
					finalchildlinks_local.clear();
					individualResultMap.clear();
					FinalResultMap.clear();
				}
			}

			/*for(Entry<String, LinkedHashMap<String, String>> b : htmlmap.entrySet()) {
				System.out.println("*****************************************");
				System.out.println(b.getKey() + ":  "+b.getValue());
			}*/

			String fileName="FindingSpecialCharacters.html";
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date dt1 = new Date();
			String str_endtime = sdf1.format(dt1);
			Endtime = Instant.now();

			String fileAddress = System.getProperty("user.dir")+"\\Results\\Findingspecialcharacters.csv";

			CSV_Report.generateCSVAssets(fileAddress, htmlmap);

			Globalvariables.SC_htmlfilename = fileName;
			Globalvariables.SC_htmlmap = htmlmap;
			Globalvariables.SC_Filename = "Finding Special Characters";
			Globalvariables.SC_str_starttime = str_starttime;
			Globalvariables.SC_str_endtime = str_endtime;
			Globalvariables.SC_starttime = starttime;
			Globalvariables.SC_Summary = "Finding Special Characters";
			Globalvariables.SC_rowcount = rowcount;
			Globalvariables.SC_passvalue = passvalue;
			Globalvariables.SC_failvalue = failvalue;
			Globalvariables.SC_Endtime = Endtime;


			HTML_Reports.SpecialCharacters_HTMLReport(fileName,htmlmap,"Finding Special Characters",str_starttime,str_endtime,starttime,"Finding Special Characters",rowcount,passvalue,failvalue, Endtime);

		}else {
			System.out.println("**** Test Execution started from Input File path ****");
			htmlmap.clear();
			UtilClass u = new UtilClass();
			List<String> list = u.readFromCsv(input_csv_filePath);  // read
			System.out.println("No of Parent Links to execute : "+list.size());
			LinkedHashMap<String, String> individualResultMap = new LinkedHashMap<>();
			LinkedHashMap<String, LinkedHashMap<String, String>> FinalResultMap = new LinkedHashMap<>();
			for(int i=0;i<list.size();i++) {

				Globalvariables.SC_urls = list.size();

				String parenturl = list.get(i);
				rowcount = list.size();
				System.out.println("------------------------------------------------------------------------------------");
				System.out.println("URL"+i +" : " +parenturl);

				ArrayList<String> finalchildlinks_local_desktop = new ArrayList<String>();
				ArrayList<String> finalchildlinks_local_mobile = new ArrayList<String>();
				HashSet<String> uniquelist = new HashSet<String>();
				ArrayList<String> finalchildlinks_local = new ArrayList<String>();
				try {

					if(!parenturl.startsWith("http")) {
						parenturl = "http://" + parenturl;
					}

					int status = GetStatus(parenturl);
					if((status == 301) || (status == 302)) {
						Response resp = Jsoup.connect(parenturl).execute();
						parenturl = resp.url().toString();
						status = GetStatus(parenturl);
					}

					System.out.println("Status of this URL:"+ parenturl+":  " + status);
					try{
						if(!(status == 404)){
							System.out.println("Status of this URL: " + GetStatus(parenturl));

							finalchildlinks_local_desktop = getAllLinks(i,parenturl, "desktop");
							uniquelist.addAll(finalchildlinks_local_desktop);

							finalchildlinks_local_mobile = getAllLinks(i,parenturl, "mobile");
							uniquelist.addAll(finalchildlinks_local_mobile);

							finalchildlinks_local.addAll(uniquelist);

							for(int j=0;j<finalchildlinks_local.size();j++) {
								/*String txt = finalchildlinks_local.get(j);
								if(txt.length()>0) */
								individualResultMap.put(finalchildlinks_local.get(j), null);
							}
							FinalResultMap.put("ParentURL_"+parenturl, individualResultMap);

							validation(FinalResultMap);

						}else{
							htmlmap.put("NoAssets_"+parenturl +" : " +"No assets to display__"+status, null);
						}
					}catch(Exception e) {
						System.out.println("Exception in getting links");
					}

				}catch(Exception e){
					System.out.println("Exception in getting status");
				}finally {
					finalchildlinks_local.clear();
					individualResultMap.clear();
					FinalResultMap.clear();
				}
			}

			/*for(Entry<String, LinkedHashMap<String, String>> b : htmlmap.entrySet()) {
				System.out.println("*****************************************");
				System.out.println(b.getKey() + ":  "+b.getValue());
			}*/

			String fileName="FindingSpecialCharacters.html";
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date dt1 = new Date();
			String str_endtime = sdf1.format(dt1);
			Endtime = Instant.now();

			String fileAddress = System.getProperty("user.dir")+"\\Results\\Findingspecialcharacters.csv";

			CSV_Report.generateCSVAssets(fileAddress, htmlmap);

			Globalvariables.SC_htmlfilename = fileName;
			Globalvariables.SC_htmlmap = htmlmap;
			Globalvariables.SC_Filename = "Finding Special Characters";
			Globalvariables.SC_str_starttime = str_starttime;
			Globalvariables.SC_str_endtime = str_endtime;
			Globalvariables.SC_starttime = starttime;
			Globalvariables.SC_Summary = "Finding Special Characters";
			Globalvariables.SC_rowcount = rowcount;
			Globalvariables.SC_passvalue = passvalue;
			Globalvariables.SC_failvalue = failvalue;
			Globalvariables.SC_Endtime = Endtime;

			HTML_Reports.SpecialCharacters_HTMLReport(fileName,htmlmap,"Finding Special Characters",str_starttime,str_endtime,starttime,"Finding Special Characters",rowcount,passvalue,failvalue, Endtime);

		}
	}

	private static ArrayList<String> getAllLinks(int c, String parenturl, String view) throws InterruptedException, IOException {

		 ArrayList<String> finalchildlinks = new ArrayList<String>();
		 ArrayList<String> imglinks = new ArrayList<String>(); 
		 ArrayList<String> alinks = new ArrayList<String>();
		 ArrayList<String> elinks = new ArrayList<String>();

		try {

			Document document;
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
					imgtext = imgtext.substring(imgtext.lastIndexOf('/')+1);
					imglinks.add(imgtext);
				}else {
					imgtext = imgtext.substring(imgtext.lastIndexOf('/')+1);
					imglinks.add(imgtext);
				}
			}

			Elements atags = document.select("a[href]");

			for(int i=0;i<atags.size();i++) {
				String href = atags.get(i).absUrl("href");
				if(href!=null && ((href.contains(".pdf")) || (href.contains(".PDF")) || (href.contains(".txt")) 
						|| (href.contains(".doc")) || (href.contains(".xlsx")) || (href.contains(".xls")))) {
					href = href.substring(href.lastIndexOf('/')+1);
					alinks.add(href);
				}
			}

			Elements embed = document.select("embed");
			for(int k=0;k<embed.size();k++) {
				String embedtags = embed.get(k).absUrl("src");
				if(embedtags!=null && ((embedtags.contains(".swf")))){
					embedtags = embedtags.substring(embedtags.lastIndexOf('/')+1);
					elinks.add(embedtags);
				}
			}

		}catch(NullPointerException e1) {
			System.out.println("exception");
		}finally {
			System.out.println("valid images : " + imglinks.size());
			System.out.println("valid anchortags : " + alinks.size());
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

	private static int GetStatus (String url) throws Exception {

		int Status = 0;
		try {
			response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).execute();
			Status = response.statusCode();

		} catch (Exception e) {
			try {
				response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).execute();
				Status = response.statusCode();
			}catch (Exception ee) {
				//System.out.println("Unable to get status for link: " + url);
				Status = 404;
			}

		}
		return Status;
	}

	private static LinkedHashMap<String, LinkedHashMap<String, String>> validation (LinkedHashMap<String, LinkedHashMap<String, String>> map) throws Exception {

		LinkedHashMap<String, String> ResultMap = new LinkedHashMap<>();

		try{

			for(Entry<String, LinkedHashMap<String, String>> s : map.entrySet()) {
				try{
					for(Entry<String, String> a : s.getValue().entrySet()) {
						try{
							
							File file = new File(filePath);
							FileReader fr = new FileReader(file);
							Properties prop = new Properties();
							prop.load(fr);
							
							String specailchars = prop.getProperty("SpecialCharacters-TextField");
							
							String spechar = "["+specailchars+"]";
							Pattern specialChar = Pattern.compile(spechar);
							//Pattern specialChar = Pattern.compile("[@$%^&*()?#+\"'~]");
							Matcher m = specialChar.matcher(a.getKey());
							boolean b = m.find();
							if(b==true) {
								failvalue++;
								Globalvariables.SC_fails++;
								ResultMap.put(a.getKey(), "Fail");
								Globalvariables.SC_failmap.put(a.getKey(), "Fail");
							}else {
								passvalue++;
								Globalvariables.SC_success++;
								ResultMap.put(a.getKey(), "Pass");
								Globalvariables.SC_passmap.put(a.getKey(), "Pass");
							}
						}catch(Exception e){
							System.out.println(e.getMessage());
						}
					}
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				htmlmap.put(s.getKey(), ResultMap);
			}

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return htmlmap;
	}

	@AfterClass

	public void afterClass() throws IOException {
		System.out.println("\nthis is AfterClass");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		String enddate = sdf.format(dt);

		Instant endtime = Instant.now();
		Duration diff = Duration.between(starttime, endtime);
		System.out.println("==============================================================================================");
		System.out.println("Test Execution of Finding Special Characters Completied At : "+enddate);
		System.out.println("Total Execution Time : " +diff.toMinutes() +" minutes");
		System.out.println("==============================================================================================");
		Logger.logTestInfo("************* Completed Test Execution *************");
	}

}
