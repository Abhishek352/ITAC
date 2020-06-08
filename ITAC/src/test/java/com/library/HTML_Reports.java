package com.library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

import site_monitoring.Globalvariables;

public class HTML_Reports {

	//private static String TitleOfPage ="RBS_HtmlResultReport";
	private static String ConfigTableHeader= "Config Details";	
	private static String TitleTestResults = "Test Execution Results";
	public static String imagepath = System.getProperty("user.dir")+"\\drivers\\downArrow2.png";


	public static void BrokenLinks(String BL_filePath,LinkedHashMap<String, Integer> BL_map, String BL_TestName,String BL_str_starttime,String BL_str_endtime,
			Instant BL_starttime,String BL_SummaryHeader,int BL_TotalURL,int BL_childlinks,
			int BL_fails,int BL_r,int BL_u,int BL_f,int BL_b,int BL_p,int BL_i,int BL_o,int BL_n,int BL_s,int BL_pr, int BL_py,int 
			BL_status200, LinkedHashMap<String, LinkedHashMap<String, String>> BL_pcmap, Instant BL_Endtime, int BL_503) throws IOException {

		try {

			System.out.println("HTML Report Generation Started");
			//resultsMap = Logger.map;
			StringBuilder htmlStringBuilder=new StringBuilder();
			htmlStringBuilder.append("<!DOCTYPE html><html><head><title>"+BL_TestName+"</title>"
					+ "<link href='global_styles.css' rel='stylesheet' type='text/css'>"
					+ "</head>"
					+ "<body class='broken_input'>");

			Instant endtime = Instant.now();
			Duration difftime = Duration.between(BL_starttime, BL_Endtime);
			System.out.println(BL_starttime);
			System.out.println(endtime);
			long sec = difftime.getSeconds();

			String elapsedTime = null;
			elapsedTime = sec+" seconds";

			long min = 0;
			if(sec>=60) {
				min = difftime.toMinutes();
				elapsedTime=min+" minutes";
			}

			long hrs=0;
			if(min>=60) {
				hrs=difftime.toHours();
				elapsedTime=hrs+" hours";
			}

			// Summary table
			htmlStringBuilder.append("<div class='header' style='background-color:#5ea5e0'><div class='clearfix'><h1>Broken Links HTML Report</h1></div></div>");

			//main content div starts here
			htmlStringBuilder.append("<div class='main clearfix'>");

			// Side Bar tables
			htmlStringBuilder.append("<div class='sidenav floatright'>");

			htmlStringBuilder.append("<h2>"+ConfigTableHeader+"</h2><table class='excution'>"                              
					+ "<tr><td> Test Name : </td><td>" +BL_TestName +"</td></tr>"
					+ "<tr><td> Start Time </td> <td>"+BL_str_starttime+"</td></tr>"
					+ "<tr><td> End Time </td> <td>"+BL_str_endtime+"</td></tr>"
					+ "<tr><td> Total Execution Time </td> <td>"+elapsedTime+"</td></tr>"
					//	+ "<tr><td> User Name</td><td>"+System.getProperty("user.name")+ "</td></tr>"
					//	+ "<tr><td>Operating System </td><td>"+System.getProperty("os.name")+  "</td></tr>"
					+  "</table>");

			// Config table2
			htmlStringBuilder.append("<h2>"+ BL_SummaryHeader+"</h2><table class='excution'>"
					+ "<tr><td> No of URL's Executed </td><td>"+BL_TotalURL+"</td></tr>"
					+ "<tr><td>Total Pass</td><td>"+BL_s+"</td></tr>"
					+ "<tr><td>Total Fail</td><td>"+BL_fails+"</td></tr>"
					+  "</table>");

			htmlStringBuilder.append("</div>");


			htmlStringBuilder.append("<div class='singlePage floatleft'>");
			htmlStringBuilder.append("<div id='horiz_buttons' class='clearfix'>"
					+ "<div class='btn-group floatright'>"
					+ "<button id='show_all' type='button' class='btn btn-secondary active'>Show All</button>"
					+ "<button id='pass_all' type='button' class='btn btn-secondary'>Pass</button>"
					+ "<button id='fail_all' type='button' class='btn btn-secondary'>Fail</button></div>"
					+ "</div>");




			// TestResult table
			htmlStringBuilder.append("<div class='accordionWrapper'>"
					+ "<div class='title_accor clearfix'><span class='floatleft'>No of URL's Executed: </span>"
					+ "<span class='floatleft'>"+BL_TotalURL+"</span> </div>"
					+ "<div><ul class='accordion'>");	

			htmlStringBuilder.append("<li class='open'><h3 class='accordionItemHead'><span>Total Passed :</span> <span>"+BL_s+"</span>"
					+ "</h3><div class='pass_all failAccordian accordionItemPanel'>");
			int page200 = 0;
			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(200)){
					if(page200 == 0) {
						htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>200-Success :</span> <span>"+BL_status200+"</span>"
								+ "</h4><div class='accordionItemPanel'>");
					}

					htmlStringBuilder.append("<p>");
					String d=key;
					htmlStringBuilder.append("<a href="+d+" target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page200++;

					if(page200==BL_status200) {
						htmlStringBuilder.append("</div></div>");
					}

				}
			}
			int page301 = 0;
			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(301)){
					if(page301 == 0) {
						htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>301-Redirection :</span> <span>"+BL_r+"</span>"
								+ "</h4><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p>");
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page301++;

					if(page301==BL_r) {
						htmlStringBuilder.append("</div></div>");
					}

				}
			}
			int page302 = 0;
			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(302)){
					if(page302 == 0) {
						htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>302-Permanent Redirection :</span> <span>"+BL_pr+"</span>"
								+ "</h4><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p>");
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page302++;
					if(page302==BL_pr) {
						htmlStringBuilder.append("</div></div>");
					}
				}
			}
			htmlStringBuilder.append("</div></li>");



			htmlStringBuilder.append("<li class='close'><h3 class='accordionItemHead'><span>Total Failed :</span> <span>"+BL_fails+"</span>"
					+ "</h3><div class='fail_all failAccordian accordionItemPanel'>");
			int page400 = 0;

			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(400)){
					if(page400 == 0) {
						htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>400-Bad Request :</span> <span>"+BL_b+"</span>"
								+ "</h4><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p>");
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page400++;
					if( page400 == BL_b) {
						htmlStringBuilder.append("</div></div>");
					}
				}
			}
			int page401 = 0;

			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(401)){
					if(page401 == 0) {
						htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>401-UnAuthorized :</span> <span>"+BL_u+"</span>"
								+ "</h4><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p>");
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page401++;
					if(page401 == BL_u) {
						htmlStringBuilder.append("</div></div>");
					}

				}
			}
			int page402 = 0;

			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(402)){
					if(page402 == 0) {
						htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>402-Payment Required :</span> <span>"+BL_py+"</span>"
								+ "</h4><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p>");
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page402++;
					if(page402 == BL_py) {
						htmlStringBuilder.append("</div></div>");
					}
				}
			}
			int page403 = 0;

			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(403)){
					if(page403 == 0) {
						htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>403-Forbidden :</span> <span>"+BL_f+"</span>"
								+ "</h4><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p>");
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page403++;
					if(page403 == BL_f) {
						htmlStringBuilder.append("</div></div>");
					}
				}
			}
			int page404 = 0;

			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(404)){
					if(page404 == 0) {
						htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>404-Page Not Found :</span> <span>"+BL_p+"</span>"
								+ "</h4><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p>");
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page404++;
					if( page404==BL_p) {
						htmlStringBuilder.append("</div></div>");
					}
				}

			}
			int page500 = 0;
			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(500)){
					if(page500 == 0) {
						htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>500-Internal Server Error :</span> <span>"+BL_i+"</span>"
								+ "</h4><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p>");
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page500++;
					if(page500 == BL_i) {
						htmlStringBuilder.append("</div></div>");
					}
				}
			}
			
			int page503 = 0;
			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(503)){
					if(page503 == 0) {
						int status = BL_map.get(key);
						String servicemsg = Getservicemsg(status);
					//	htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>"+status+"-"+servicemsg+":</span> <span>"+BL_i+"</span>"
							//	+ "</h4><div class='accordionItemPanel'>");
				//	}
						htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>503-Service Unavailable:</span> <span>"+BL_503+"</span>"
								+ "</h4><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p>");
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page503++;
					if(page503 == BL_503) {
						htmlStringBuilder.append("</div></div>");
					}
				}
			}

			htmlStringBuilder.append("</div></li>");


			int page0 = 0;
			for(String key : BL_map.keySet()) {
				if(BL_map.get(key).equals(0)){
					if(page0 == 0) {
						htmlStringBuilder.append("<li class='close'><h3 class='accordionItemHead'><span>Others :</span> <span>"+BL_o+"</span>"
								+ "</h3><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p> page0" + page0);
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					page0++;
					if(page0 == BL_o) {
						htmlStringBuilder.append("</div></li>");
					}
				}
			}
			/*int norunUrls = 0;	
			for(String key : BL_map.keySet()) {
				if(!BL_map.get(key).equals(404)&&(!BL_map.get(key).equals(301)&&(!BL_map.get(key).equals(302)&&(!BL_map.get(key).equals(400)&&(!BL_map.get(key).equals(500)&&(!BL_map.get(key).equals(401)&&(!BL_map.get(key).equals(402)&&(!BL_map.get(key).equals(403)&&(!BL_map.get(key).equals(200)&&(!BL_map.get(key).equals(0))))))))))){
					if(norunUrls == 0) {
						htmlStringBuilder.append("<li class='close'><h3 class='accordionItemHead'><span>No run Urls :</span> <span>"+BL_n+"</span>"
								+ "</h3><div class='accordionItemPanel'>");
					}
					htmlStringBuilder.append("<p> norunUrls" + norunUrls);
					String d=key;
					htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
					htmlStringBuilder.append("</p>");

					norunUrls++;
					if(norunUrls==BL_n) {
						htmlStringBuilder.append("</div></li>");
					}
				}
			}*/


			htmlStringBuilder.append("</ul></div>"
					+ "</div><!--endof accordionWrapper-->");	

			if(BL_fails > 0) {
				htmlStringBuilder.append("<div class='assets'><div class='title_accor clearfix'><span class='floatleft'> Fail "+TitleTestResults+"</span></div>");
				htmlStringBuilder.append("<ul class='accordion fail_all'>");

				for(Entry<String, LinkedHashMap<String, String>> specialChar : BL_pcmap.entrySet()){

					htmlStringBuilder.append("<li class='close'>");
					String parenturl = specialChar.getKey().toString();
					htmlStringBuilder.append("<h3 align='left'>" + parenturl+" </h3>");
					htmlStringBuilder.append("<div class='asset_Innerwrapper clearfix'><table>");
					for(Entry<String, String> specialChar1 : specialChar.getValue().entrySet()){
						String d = specialChar1.getKey();
						htmlStringBuilder.append("<tr>");
						htmlStringBuilder.append("<td> <a href="+d+">"+specialChar1.getKey()+"</a></td>");
						htmlStringBuilder.append("</tr>");
					}
					htmlStringBuilder.append("</table><!--endof clearfix-->");
					htmlStringBuilder.append("</div><!--endof clearfix-->");
					htmlStringBuilder.append("</li><!--endof asset_wrapper-->");
				} 

				htmlStringBuilder.append("</ul><!--endof assets ul-->");
				htmlStringBuilder.append("</div><!--endof assets parent-->");
			}

			htmlStringBuilder.append("</div><!--endof singlePage-->");	







			htmlStringBuilder.append("</div><!--endof main-->");


			htmlStringBuilder.append("<div class='footer'><footer align='center'>&copy; Copyright 2019, All Rights Reserved</footer></div>");
			htmlStringBuilder.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>"
					+ "<script type='text/javascript' language='javascript' src='reports.js'></script>");
			htmlStringBuilder.append("</body></html>");

			String filePathAndName = BL_filePath+"BrokenLinks.html";
			System.out.println("filepathAndName = " +filePathAndName);

			File resFile=new File(filePathAndName);
			OutputStream outputStream = new FileOutputStream(resFile.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);
			writer.write(htmlStringBuilder.toString());
			writer.close();

			System.out.println("HTML Report Generation Completed");

		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void SpecialCharacters_HTMLReport(String SC_fileName,LinkedHashMap<String, LinkedHashMap<String, String>> SC_map, String SC_TestName,
			String SC_str_starttime,String SC_str_endtime,Instant SC_starttime,String SC_SummaryHeader,int SC_TotalParentURL,
			int SC_passCount,int SC_failCount, Instant SC_Endtime) throws IOException {

		try {

			System.out.println("HTML Report Generation Started");
			//resultsMap = Logger.map;
			StringBuilder htmlStringBuilder=new StringBuilder();
			htmlStringBuilder.append("<!DOCTYPE html><html><head><title>"+SC_TestName+"</title>"
					+ "<link href='global_styles.css' rel='stylesheet' type='text/css'>"
					+ "</head>"
					+ "<body class='special_characters'>");

			Instant endtime = Instant.now();
			Duration difftime = Duration.between(SC_starttime, SC_Endtime);
			System.out.println(SC_starttime);
			System.out.println(endtime);
			long sec = difftime.getSeconds();

			String elapsedTime = null;
			elapsedTime = sec+" seconds";

			long min = 0;
			if(sec>=60) {
				min = difftime.toMinutes();
				elapsedTime=min+" minutes";
			}

			long hrs=0;
			if(min>=60) {
				hrs=difftime.toHours();
				elapsedTime=hrs+" hours";
			}

			// Summary table

			htmlStringBuilder.append("<div class='header' style='background-color:#5ea5e0'><div class='clearfix'><h1>Special Characters HTML Report</h1></div></div>");
			//main content div starts here
			htmlStringBuilder.append("<div class='main clearfix'>");

			// Side Bar tables
			htmlStringBuilder.append("<div class='sidenav floatright'>");

			htmlStringBuilder.append("<h2>"+ConfigTableHeader+"</h2><table class='excution'>"                              
					+ "<tr><td> Test Name : </td><td>" +SC_TestName +"</td></tr>"
					+ "<tr><td> Start Time </td> <td>"+SC_str_endtime+"</td></tr>"
					+ "<tr><td> End Time </td> <td>"+SC_str_endtime+"</td></tr>"
					+ "<tr><td> Total Execution Time </td> <td>"+elapsedTime+"</td></tr>"
					/*+ "<tr><td> User Name</td><td>"+System.getProperty("user.name")+ "</td></tr>"
					+ "<tr><td>Operating System </td><td>"+System.getProperty("os.name")+  "</td></tr>"*/
					+  "</table>");

			// Config table2
			htmlStringBuilder.append("<h2>"+ SC_SummaryHeader+"</h2><table class='excution'>"
					+ "<tr><td> No of URL's Executed </td><td>"+SC_TotalParentURL+"</td></tr>"
					+ "<tr><td>Total Pass</td><td>"+SC_passCount+"</td></tr>"
					+ "<tr><td>Total Fail</td><td>"+SC_failCount+"</td></tr>"
					+  "</table>");

			htmlStringBuilder.append("</div>");
			//single page wrapper
			htmlStringBuilder.append("<div class='singlePage floatleft'>");
			//buttons
			htmlStringBuilder.append("<div id='horiz_buttons' class='clearfix'>"
					+ "<div class='btn-group floatright'>"
					+ "<button id='show_all' type='button' class='btn btn-secondary active'>Show All</button>"
					+ "<button id='pass_all' type='button' class='btn btn-secondary'>Pass</button>"
					+ "<button id='fail_all' type='button' class='btn btn-secondary'>Fail</button></div>"
					+ "</div>");


			// TestResult table
			htmlStringBuilder.append("<div class='assets' id='FindingSpecialCharacters'>");

			htmlStringBuilder.append("<ul class='accordion'>");

			for(Entry<String, LinkedHashMap<String, String>> specialChar : SC_map.entrySet()){

				if(specialChar.getKey().contains("ParentURL_")){
					htmlStringBuilder.append("<li class='close'>");
					String parenturl = specialChar.getKey().toString().replace("ParentURL_", "");
					htmlStringBuilder.append("<h3 align='left'>" + parenturl+" </h3>");            
					htmlStringBuilder.append("<div class='asset_Innerwrapper clearfix'><table><tr><th style='width:80%;'>Assets List</th><th style='width:20%;'>Status</th></tr>");                        
					for(Entry<String, String> specialChar1 : specialChar.getValue().entrySet()){ 
						if(specialChar1.getValue().contains("fail")){
							htmlStringBuilder.append("<tr class='fail_tr'>");
							htmlStringBuilder.append("<td style='width:80%; color:#ED2424'>"+specialChar1.getKey()+"</td>");
							htmlStringBuilder.append("<td align='center' style='width:20%; color:#ED2424'>"+specialChar1.getValue() +"</td>");
							htmlStringBuilder.append("</tr>");
						}else {
							htmlStringBuilder.append("<tr class='pass_tr'>");
							htmlStringBuilder.append("<td style='width:80%; color:#03c74a'>"+specialChar1.getKey()+"</td>");
							htmlStringBuilder.append("<td align='center' style='width:20%; color:#03c74a'>"+specialChar1.getValue() +"</td>");
							htmlStringBuilder.append("</tr>");
						}
					}
					htmlStringBuilder.append("</table><!--endof clearfix-->");
					htmlStringBuilder.append("</div><!--endof clearfix-->");
					htmlStringBuilder.append("</li><!--endof asset_wrapper-->");
				}   
			}         

			htmlStringBuilder.append("</ul><!--endof assets ul-->");
			htmlStringBuilder.append("</div><!--endof assets-->");

			htmlStringBuilder.append("</div><!--endof singlePage-->");


			htmlStringBuilder.append("</div><!--endof main-->");


			htmlStringBuilder.append("<div class='footer'><footer align='center'>&copy; Copyright 2019, All Rights Reserved</footer></div>");

			htmlStringBuilder.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>"
					+ "<script type='text/javascript' language='javascript' src='reports.js'></script>");
			htmlStringBuilder.append("</body></html>");

			String filepath = System.getProperty("user.dir")+"\\Results\\HTML_results\\All_reports\\";
			String filePathAndName = filepath + SC_fileName;
			System.out.println("filepathAndName = " +filePathAndName);

			File resFile=new File(filePathAndName);
			OutputStream outputStream = new FileOutputStream(resFile.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);
			writer.write(htmlStringBuilder.toString());
			writer.close();

			System.out.println("HTML Report Generation Completed");

		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void AssetSizes_HTMLReport (String DA_filePath,LinkedHashMap<String, LinkedHashMap<String, String>> DA_map, String DA_TestName,String DA_str_starttime,String DA_str_endtime,
			Instant DA_starttime,String DA_SummaryHeader, Instant DA_Endtime) throws IOException {

		try {
			System.out.println(" ==================== HTML Report Generation Started  ==================== ");
			StringBuilder htmlStringBuilder=new StringBuilder();

			htmlStringBuilder.append("<!DOCTYPE html><html><head><title>"+DA_TestName+"</title>"
					+ "<link href='global_styles.css' rel='stylesheet' type='text/css'>"
					+ "</head>"
					+ "<body class='assets_sizes'>");

			Duration difftime = Duration.between(DA_starttime, DA_Endtime);
			/*System.out.println(DA_starttime);
			System.out.println(endtime);*/
			long sec = difftime.getSeconds();

			String elapsedTime = null;
			elapsedTime = sec+" seconds";

			long min = 0;
			if(sec>=60) {
				min = difftime.toMinutes();
				elapsedTime=min+" minutes";
			}

			long hrs=0;
			if(min>=60) {
				hrs=difftime.toHours();
				elapsedTime=hrs+" hours";
			}

			// Summary table
			htmlStringBuilder.append("<div class='header' style='background-color:#5ea5e0'><div class='clearfix'><h1>Asset Sizes HTML Report</h1></div></div>");
			//main content div starts here
			htmlStringBuilder.append("<div class='main clearfix'>");
			// Side Bar tables
			htmlStringBuilder.append("<div class='sidenav floatright'>");
			htmlStringBuilder.append("<h2>"+ ConfigTableHeader+"</h2>"
					+ "<table class='excution'>"
					+ "<tr><td>Test Name</td><td>"+ DA_TestName +"</td></tr>"
					+ "<tr><td>Start Time</td><td>"+ DA_str_starttime +"</td></tr>"
					+ "<tr><td>End Time</td><td>"+DA_str_endtime+"</td></tr>"
					+ "<tr><td>Execution Time</td><td>"+elapsedTime+"</td></tr>"
					/*+ "<tr><td>User Name</td><td>"+System.getProperty("user.name")+ "</td></tr>"
					+ "<tr><td>Operating System</td><td>"+System.getProperty("os.name")+ "</td></tr>"*/
					+  "</table>");
			
			String configfilePath = System.getProperty("user.dir")+"\\testConfig.properties";
			File file = new File(configfilePath);
			FileReader fr = new FileReader(file);
			Properties prop = new Properties();
			prop.load(fr);
			
			int Redvalue = Integer.parseInt(prop.getProperty("Red"));
			int Ambervalue = Integer.parseInt(prop.getProperty("Amber"));
			
			//Redvalue = Redvalue*1024;
			//Ambervalue = Ambervalue*1024;

			htmlStringBuilder.append("<h2>Size Chart</h2>"
					+ "<table class=''><tbody><tr><td><ul class='sizeChart'>"
					+ "<li><span class='sizeRed'></span> <strong>&gt;"+Redvalue+"kb</strong></li>"
					+ "<li><span class='sizePass'></span> <strong>"+Ambervalue+"kb to "+Redvalue+"kb</strong></li>"
					+ "<li><span class='sizeAmber'></span> <strong>&lt; "+Ambervalue+"kb</strong></li>"
					+ "</ul></td></tr></tbody></table>");

			htmlStringBuilder.append("</div>");

			//single page wrapper 
			htmlStringBuilder.append("<div class='singlePage floatleft'>");
			htmlStringBuilder.append("<div id='horiz_buttons' class='clearfix'>"
					+ "<div class='btn-group floatright'>"
					+ "<button id='show_all' type='button' class='btn btn-secondary active'>Show All</button>"
					+ "<button id='pass_all' type='button' class='btn btn-secondary'>Pass</button>"
					+ "<button id='fail_all' type='button' class='btn btn-secondary'>Fail</button></div>"
					+ "</div>");

			htmlStringBuilder.append("<div class='assets' id='AssetSizes'>");
			htmlStringBuilder.append("<ul class='accordion'>");

			for(Entry<String, LinkedHashMap<String, String>> res : DA_map.entrySet()){
				if(res.getKey().contains("ParentURL_")) {

					String parenturl = res.getKey().toString().replace("ParentURL_", "");
					htmlStringBuilder.append("<li class='close'><h3 align='left'>" + parenturl+" </h3><div class='asset_Innerwrapper clearfix'>");				
					htmlStringBuilder.append("<div class='floatleft'><table><tr><th style='width: 80%;'>Desktop</th><th style='width: 20%;'>Size</th></tr>");

					for(Entry<String, String> innerResult :res.getValue().entrySet()) {

						if(innerResult.getKey().contains("desktopview_")){
							if(innerResult.getKey().contains("Red")){

								htmlStringBuilder.append("<tr class='totalRecords fail_tr'>");
								String assetname = innerResult.getKey().toString().split("Reddesktopview_")[1];
								String assetsize = innerResult.getValue().toString();
								htmlStringBuilder.append("<td align='left' style='width: 80%;color:#ED2424'>" + assetname +"</td>");
								htmlStringBuilder.append("<td align='center' style='width: 20%;color:#ED2424'>"+ assetsize +"</td>");
								htmlStringBuilder.append("</tr>");
							}

						}


					}
					for(Entry<String, String> innerResult :res.getValue().entrySet()) {

						if(innerResult.getKey().contains("desktopview_")){
							if(innerResult.getKey().contains("Amber")){  //Now Amber values are consider as pass, so green color is given

								htmlStringBuilder.append("<tr class='totalRecords pass_tr'>");
								String assetname = innerResult.getKey().toString().split("Amberdesktopview_")[1];
								String assetsize = innerResult.getValue().toString();
								htmlStringBuilder.append("<td align='left' style='width: 80%;color:#03c74a'>" + assetname +"</td>");
								htmlStringBuilder.append("<td align='center' style='width: 20%;color:#03c74a'>"+ assetsize +"</td>");
								htmlStringBuilder.append("</tr>");
							}

						}


					}
					for(Entry<String, String> innerResult :res.getValue().entrySet()) {

						if(innerResult.getKey().contains("desktopview_")){//Now Amber values are consider as partially pass, so amber color is given

							if(!innerResult.getKey().contains("Reddesktopview_") && !innerResult.getKey().contains("Amberdesktopview_")){
								htmlStringBuilder.append("<tr class='totalRecords fail_tr'>");

								String assetname = innerResult.getKey().toString().split("desktopview_")[1];
								String assetsize = innerResult.getValue().toString();
								htmlStringBuilder.append("<td align='left' style='width: 80%;color:#ED9D24'> "  + assetname +"</td>");
								htmlStringBuilder.append("<td align='center' style='width: 20%;color:#ED9D24'>" + assetsize +"</td>");
								htmlStringBuilder.append("</tr>");
							}

						}


					}


					htmlStringBuilder.append("</table></div><!--endof floatleft-->"
							+ "<div class='floatright'><table><tr><th style='width: 80%;'>Mobile</th><th style='width: 20%;'>Size</th></tr>");

					for(Entry<String, String> innerResult :res.getValue().entrySet()) {
						if(innerResult.getKey().contains("mobileview_")){	
							if(innerResult.getKey().contains("Red")){

								htmlStringBuilder.append("<tr class='totalRecords fail_tr'>");
								String assetname = innerResult.getKey().toString().split("Redmobileview_")[1];
								String assetsize = innerResult.getValue().toString();
								htmlStringBuilder.append("<td align='left' style='width: 80%;color:#ED2424'>"+ assetname +"</td>");
								htmlStringBuilder.append("<td align='center' style='width: 20%;color:#ED2424'>"+ assetsize +"</td>");
								htmlStringBuilder.append("</tr>");
							}

						}

					}
					for(Entry<String, String> innerResult :res.getValue().entrySet()) {
						if(innerResult.getKey().contains("mobileview_")){							 
							if(innerResult.getKey().contains("Amber")){  //Now Amber values are consider as pass, so green color is given

								htmlStringBuilder.append("<tr class='totalRecords pass_tr'>");
								String assetname = innerResult.getKey().toString().split("Ambermobileview_")[1];
								String assetsize = innerResult.getValue().toString();
								htmlStringBuilder.append("<td align='left' style='width: 80%;color:#03c74a'>"+ assetname +"</td>");
								htmlStringBuilder.append("<td align='center' style='width: 20%;color:#03c74a'>"+ assetsize +"</td>");
								htmlStringBuilder.append("</tr>");
							}

						}

					}
					for(Entry<String, String> innerResult :res.getValue().entrySet()) {
						if(innerResult.getKey().contains("mobileview_")){	//Now Amber values are consider as partially pass, so amber color is given			 
							if(!innerResult.getKey().contains("Red") && !innerResult.getKey().contains("Amber")){

								htmlStringBuilder.append("<tr class='totalRecords fail_tr'>");
								String assetname = innerResult.getKey().toString().split("mobileview_")[1];
								String assetsize = innerResult.getValue().toString();
								htmlStringBuilder.append("<td align='left' style='width: 80%;color:#ED9D24'>"+ assetname +"</td>");
								htmlStringBuilder.append("<td align='center' style='width: 20%;color:#ED9D24'>"+ assetsize +"</td>");
								htmlStringBuilder.append("</tr>");
							}

						}

					}

					htmlStringBuilder.append("</table></div><!--endof floatright-->");

					htmlStringBuilder.append("</div><!--endof clearfix-->");
					htmlStringBuilder.append("</li><!--endof asset_wrapper-->");

				}

			}

			htmlStringBuilder.append("</ul><!--endof assets ul-->");
			htmlStringBuilder.append("</div><!--endof assets div-->");

			htmlStringBuilder.append("</div><!--endof singlePage-->");
			htmlStringBuilder.append("</div><!--endof Main div-->");

			htmlStringBuilder.append("<div class='footer'><footer align='center'>&copy; Copyright 2019, All Rights Reserved</footer></div>");
			htmlStringBuilder.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>"
					+ "<script type='text/javascript' language='javascript' src='reports.js'></script>");
			htmlStringBuilder.append("</body></html>");

			String fileName="AssetSizes.html";
			System.out.println("fileName = " +fileName);

			String filePathAndName = DA_filePath + fileName;
			System.out.println("filepathAndName = " +filePathAndName);

			File resFile=new File(filePathAndName);
			if(!resFile.exists()) {
				resFile.createNewFile();
			}
			OutputStream outputStream = new FileOutputStream(resFile.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);
			writer.write(htmlStringBuilder.toString());
			writer.close();

			System.out.println("HTML Report Generation Completed");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void MissingAssets_HTMLReport (String MA_filePath,LinkedHashMap<String, LinkedHashMap<String, String>> MA_map, String MA_TestName,String MA_str_starttime,String MA_str_endtime,
			Instant MA_starttime,String MA_SummaryHeader, Instant MA_Endtime) throws IOException {

		try {
			System.out.println(" ==================== HTML Report Generation Started  ==================== ");
			StringBuilder htmlStringBuilder=new StringBuilder();

			htmlStringBuilder.append("<!DOCTYPE html><html><head><title>"+MA_TestName+"</title>"
					+ "<link href='global_styles.css' rel='stylesheet' type='text/css'>"
					+ "</head>"
					+ "<body class='missing_assets'>");
			Duration difftime = Duration.between(MA_starttime, MA_Endtime);
			/*System.out.println(MA_starttime);
			System.out.println(endtime);*/
			long sec = difftime.getSeconds();

			String elapsedTime = null;
			elapsedTime = sec+" seconds";

			long min = 0;
			if(sec>=60) {
				min = difftime.toMinutes();
				elapsedTime=min+" minutes";
			}

			long hrs=0;
			if(min>=60) {
				hrs=difftime.toHours();
				elapsedTime=hrs+" hours";
			}

			// Summary table
			htmlStringBuilder.append("<div class='header' style='background-color:#5ea5e0'><div class='clearfix'><h1>Missing Assets HTML Report</h1></div></div>");

			//main content div starts here
			htmlStringBuilder.append("<div class='main clearfix'>");

			// Side Bar tables
			htmlStringBuilder.append("<div class='sidenav floatright'>");
			htmlStringBuilder.append("<h2>"+ ConfigTableHeader+"</h2>"
					+ "<table class='excution'>"
					+ "<tr><td>Test Name</td><td>"+ MA_TestName +"</td></tr>"
					+ "<tr><td>Start Time</td><td>"+ MA_str_starttime +"</td></tr>"
					+ "<tr><td>End Time</td><td>"+MA_str_endtime+"</td></tr>"
					+ "<tr><td>Execution Time</td><td>"+elapsedTime+"</td></tr>"
					/*+ "<tr><td>User Name</td><td>"+System.getProperty("user.name")+ "</td></tr>"
					+ "<tr><td>Operating System</td><td>"+System.getProperty("os.name")+ "</td></tr>"*/
					+  "</table>");

			htmlStringBuilder.append("</div>");


			htmlStringBuilder.append("<div class='singlePage floatleft'>");
			htmlStringBuilder.append("<div id='horiz_buttons' class='clearfix'>"
					+ "<div class='btn-group floatright'>"
					+ "<button id='show_all' type='button' class='btn btn-secondary active'>Show All</button>"
					+ "<button id='pass_all' type='button' class='btn btn-secondary'>Pass</button>"
					+ "<button id='fail_all' type='button' class='btn btn-secondary'>Fail</button></div>"
					+ "</div>");

			htmlStringBuilder.append("<div class='assets'>");


			htmlStringBuilder.append("<ul class='accordion'>");
			String className;
			String parenturl;
			for(Entry<String, LinkedHashMap<String, String>> res : MA_map.entrySet()){
				if(res.getKey().contains("FailParentURL_")) {
					className = "fail_all";
					parenturl = res.getKey().toString().replace("FailParentURL_", "");
				}else if(res.getKey().contains("ProdFailParentURL_")){
					className = "prodfail fail_all";
					parenturl = res.getKey().toString().replace("ProdFailParentURL_", "");
				}else if(res.getKey().contains("TestFailParentURL_")){
					className = "testfail fail_all";
					parenturl = res.getKey().toString().replace("TestFailParentURL_", "");
				}else {
					className = "pass_all";
					parenturl = res.getKey().toString().replace("ParentURL_", "");
				}

				if(res.getKey().contains("ParentURL_")) {


					if(className.contains("fail_all")) {
						htmlStringBuilder.append("<li class='close'><h3 align='left'>" + parenturl+" </h3>");
						htmlStringBuilder.append("<div class='asset_Innerwrapper clearfix "+className+" totalRecords'>");				

						htmlStringBuilder.append("<div class='floatleft'><table><tr><th>Missing Assets in Test</th></tr>");						
						for(Entry<String, String> innerResult :res.getValue().entrySet()) {
							if(innerResult.getKey().contains("MissingTest_")){							 
								String assetname = innerResult.getKey().toString().split("MissingTest_")[1];
								htmlStringBuilder.append("<tr><td align='left'>" + assetname +"</td></tr>");	
							}

						}
						htmlStringBuilder.append("</table></div><!--endof floatright-->");

						htmlStringBuilder.append("<div class='floatleft'><table><tr><th>Missing Assets in Prod</th></tr>");
						for(Entry<String, String> innerResult :res.getValue().entrySet()) {
							if(innerResult.getKey().contains("MissingProd_")){							 
								String assetname = innerResult.getKey().toString().split("MissingProd_")[1];
								htmlStringBuilder.append("<tr><td align='left'>" + assetname +"</td></tr>");	
							}

						}
						htmlStringBuilder.append("</table></div><!--endof floatright-->");

						htmlStringBuilder.append("</div><!--endof clearfix-->");
						htmlStringBuilder.append("</li><!--endof asset_wrapper-->");
					}else {
						htmlStringBuilder.append("<li class='close'><strong class="+className+">" + parenturl+" </strong></li>");

					}


				}

			}

			htmlStringBuilder.append("</ul><!--endof assets ul-->");
			htmlStringBuilder.append("</div><!--endof assets -->");

			htmlStringBuilder.append("</div><!--endof singlePage-->");

			htmlStringBuilder.append("</div><!--endof Main -->");

			htmlStringBuilder.append("<div class='footer'><footer align='center'>&copy; Copyright 2019, All Rights Reserved</footer></div>");
			htmlStringBuilder.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>"
					+ "<script type='text/javascript' language='javascript' src='reports.js'></script>");
			htmlStringBuilder.append("</body></html>");

			String fileName="MissingAssets.html";
			System.out.println("fileName = " +fileName);

			String filePathAndName = MA_filePath + fileName;
			System.out.println("filepathAndName = " +filePathAndName);

			File resFile=new File(filePathAndName);
			if(!resFile.exists()) {
				resFile.createNewFile();
			}
			OutputStream outputStream = new FileOutputStream(resFile.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);
			writer.write(htmlStringBuilder.toString());
			writer.close();

			System.out.println("HTML Report Generation Completed");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void PageComparison_HTMLReport(String PC_HTMLReportPath, String PC_fileName, LinkedHashMap<String, LinkedHashMap<String, String>> PC_map, 
			String PC_TestName,	String PC_str_starttime,String PC_str_endtime,Instant PC_starttime,String PC_SummaryHeader,int PC_TotalShelves,
			int PC_passCount,int PC_failCount, LinkedHashMap<String, LinkedHashMap<String, String>> PC_fmap, int PC_exceptioncount, int PC_missingcount,
			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> PC_ContentFinalMap, 
			LinkedHashMap<String, Integer> PC_PassMap, LinkedHashMap<String, Integer> PC_FailMap, int PC_browsercount, List<String> PC_browserlist, int PC_urls
			) throws IOException {

		try {

			String csspath = System.getProperty("user.dir")+"\\Results\\HTML_results\\All_reports\\global_styles.css";
			String jspath = System.getProperty("user.dir")+"\\Results\\HTML_results\\All_reports\\reports.js";

			System.out.println("HTML Report Generation Started");
			//resultsMap = Logger.map;
			StringBuilder htmlStringBuilder=new StringBuilder();
			htmlStringBuilder.append("<!DOCTYPE html><html><head><title>"+PC_TestName+"</title>"
					+ "<link href="+csspath+" rel='stylesheet' type='text/css'>"
					+ "<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>"
					+ "<script type='text/javascript' language='javascript' src="+jspath+"></script>"
					+ "</head>"
					+ "<body class='page_comparison'>");

			Instant endtime = Instant.now();
			Duration difftime = Duration.between(PC_starttime, endtime);
			System.out.println(PC_starttime);
			System.out.println(endtime);
			long sec = difftime.getSeconds();

			String elapsedTime = null;
			elapsedTime = sec+" seconds";

			long min = 0;
			if(sec>=60) {
				min = difftime.toMinutes();
				elapsedTime=min+" minutes";
			}

			long hrs=0;
			if(min>=60) {
				hrs=difftime.toHours();
				elapsedTime=hrs+" hours";
			}

			// Summary table
			htmlStringBuilder.append("<div class='header' style='background-color:#5ea5e0'><div class='clearfix'><h1>Page Comparison HTML Report</h1></div></div>");

			//main content div starts here
			htmlStringBuilder.append("<div class='main clearfix'>");

			// Side Bar tables
			htmlStringBuilder.append("<div class='sidenav floatright'>");
			htmlStringBuilder.append("<h2>"+ ConfigTableHeader+"</h2>"
					+ "<table class='excution'>"                              
					+ "<tr><td> Test Name : </td><td>" +PC_TestName +"</td></tr>"
					+ "<tr><td> Start Time </td> <td>"+PC_str_starttime+"</td></tr>"
					+ "<tr><td> End Time </td> <td>"+PC_str_endtime+"</td></tr>"
					+ "<tr><td> Total Execution Time </td> <td>"+elapsedTime+"</td></tr>"
					+ "<tr><td> User Name</td><td>"+System.getProperty("user.name")+ "</td></tr>"
					+  "</table>");
			// Config table
			htmlStringBuilder.append("<h2>"+ PC_SummaryHeader+"</h2><table class='excution'>"
					+ "<tr><td> No of Shelves Executed </td><td>"+PC_TotalShelves+"</td></tr>"
					+ "<tr><td>Total Pass</td><td>"+PC_passCount+"</td></tr>"
					+ "<tr><td>Total Fail</td><td>"+PC_failCount+"</td></tr>"
					+ "<tr><td>Total Exception </td><td>"+PC_exceptioncount+"</td></tr>"
					+ "<tr><td>Total Missing Shelves</td><td>"+PC_missingcount+"</td></tr>"
					+  "</table>");

			htmlStringBuilder.append("</div>");

			htmlStringBuilder.append("<div class='singlePage floatleft'>");
			//buttons
			htmlStringBuilder.append("<div id='horiz_buttons' class='clearfix'>"
					+ "<div class='btn-group floatright'>"
					+ "<button id='show_all' type='button' class='btn btn-secondary active'>Show All</button>"
					+ "<button id='pass_all' type='button' class='btn btn-secondary'>Pass</button>"
					+ "<button id='fail_all' type='button' class='btn btn-secondary'>Fail</button></div>"
					+ "</div>");

			// TestResult table
			htmlStringBuilder.append("<div class='assets' id='PageComparison'>");
			htmlStringBuilder.append("<ul class='accordion'>");

			for(Entry<String, LinkedHashMap<String, LinkedHashMap<String, String>>>  res : PC_ContentFinalMap.entrySet()){		
				htmlStringBuilder.append("<li class='close'><h3 class='accordionItemHead'><div class='clearfix'><span class='floatleft'>"+res.getKey()+"</span> <div class='floatright'>");
				for(Entry<String, Integer> passRes :PC_PassMap.entrySet()) {
					if(passRes.getKey().equals(res.getKey())) {
						htmlStringBuilder.append(" <span> Pass Count: " + passRes.getValue()+"</span>");
					}
				}
				for(Entry<String, Integer> failRes :PC_FailMap.entrySet()) {
					if(failRes.getKey().equals(res.getKey())) {
						htmlStringBuilder.append(" <span> Fail Count: " + failRes.getValue()+"</span>");
					}
				}
				htmlStringBuilder.append("</div></div></h3><div class='failAccordian accordionItemPanel'>");

				for(Entry<String, LinkedHashMap<String, String>> innerResult :res.getValue().entrySet()) {
					String parenturl = innerResult.getKey().toString().replace("ParentURL_", "");
					htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead' align='left'>" + parenturl+" </h3>"
							+ "<div class='accordionItemPanel'>");				
					htmlStringBuilder.append("<table><tr><th style='width: 80%;'>Shelf Name</th><th style='width: 20%;'>Status</th></tr>");
					for(Entry<String, String> statusRes :innerResult.getValue().entrySet()) {
						String[] screenshotpath = statusRes.getValue().split("~"); 

						if(statusRes.getValue().contains("Fail")){

							htmlStringBuilder.append("<tr class='totalRecords fail_tr'>");
							htmlStringBuilder.append("<td style='color:#E15037'><a href='"+screenshotpath[1]+"' target='_blank'>"+statusRes.getKey()+"</a></td>");
							htmlStringBuilder.append("<td align='center' style='width:20%;color:#E15037'>"+screenshotpath[0] +"</td>");
							htmlStringBuilder.append("</tr>");

						}else {
							htmlStringBuilder.append("<tr class='totalRecords pass_tr'>");
							htmlStringBuilder.append("<td style='color:#03c74a'><a href='"+screenshotpath[1]+"' target='_blank'>"+statusRes.getKey()+"</a></td>");
							htmlStringBuilder.append("<td align='center' style='width:20%;color:#03c74a'>"+screenshotpath[0] +"</td>");
							htmlStringBuilder.append("</tr>");
						}
					}
					htmlStringBuilder.append("</table><!--endof table-->");
					htmlStringBuilder.append("</div><!--endof accordionItemPanel-->");
					htmlStringBuilder.append("</div><!--endof close-->");
				}
				htmlStringBuilder.append("</li><!--endof asset_Innerwrapper-->");
			}
			htmlStringBuilder.append("</ul><!--endof assets ul-->");
			htmlStringBuilder.append("</div><!--endof assets-->");

			htmlStringBuilder.append("</div><!--endof singlePage-->");	
			htmlStringBuilder.append("</div><!--endof Mains -->");


			htmlStringBuilder.append("<div class='footer'><footer align='center'>&copy; Copyright 2019, All Rights Reserved</footer></div>");
			htmlStringBuilder.append("</body></html>");

			//String filepath = System.getProperty("user.dir")+"\\Results\\HTML_results\\";
			String filepath = PC_HTMLReportPath;
			String filePathAndName = filepath + PC_fileName;
			System.out.println("filepathAndName = " +filePathAndName);

			File resFile=new File(filePathAndName);
			OutputStream outputStream = new FileOutputStream(resFile.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);
			writer.write(htmlStringBuilder.toString());
			writer.close();

			System.out.println("HTML Report Generation Completed");

		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void Global_HTMLReport(int count,LinkedHashMap<String, LinkedHashMap<String, Integer>> map, String starttime, Instant istarttime, String BL_filePath,LinkedHashMap<String, Integer> BL_map, String BL_TestName,String BL_str_starttime,String BL_str_endtime,
			Instant BL_starttime,String BL_SummaryHeader,int BL_TotalURL,int BL_childlinks,
			int BL_fails,int BL_r,int BL_u,int BL_f,int BL_b,int BL_p,int BL_i,int BL_o,int BL_n,int BL_s,int BL_pr, int BL_py,int BL_status200, LinkedHashMap<String, LinkedHashMap<String, String>> BL_pcmap, Instant BL_Endtime, int BL_503,
			String SC_fileName,LinkedHashMap<String, LinkedHashMap<String, String>> SC_map, String SC_TestName,
			String SC_str_starttime,String SC_str_endtime,Instant SC_starttime,String SC_SummaryHeader,int SC_TotalParentURL,
			int SC_passCount,int SC_failCount, Instant SC_Endtime,
			String DA_filePath,LinkedHashMap<String, LinkedHashMap<String, String>> DA_map, String DA_TestName,String DA_str_starttime,String DA_str_endtime,
			Instant DA_starttime,String DA_SummaryHeader, Instant DA_Endtime,
			String MA_filePath,LinkedHashMap<String, LinkedHashMap<String, String>> MA_map, String MA_TestName,String MA_str_starttime,String MA_str_endtime,
			Instant MA_starttime,String MA_SummaryHeader, Instant MA_Endtime,
			String PC_HTMLReportPath, String PC_fileName, LinkedHashMap<String, LinkedHashMap<String, String>> PC_map, String PC_TestName,
			String PC_str_starttime,String PC_str_endtime,Instant PC_starttime,String PC_SummaryHeader,int PC_TotalShelves,
			int PC_passCount,int PC_failCount, LinkedHashMap<String, LinkedHashMap<String, String>> PC_fmap, int PC_exceptioncount, int PC_missingcount,
			List<String> JourneyFlowsUrlsList, List<String> PageLoadTimeList, int RT_passCount, int RT_failCount, int RT_urls, LinkedHashMap<String, LinkedHashMap<String, String>> HC_Mortgagesmap, String HC_str_starttime, String HC_str_endtime, Instant HC_Starttime, Instant HC_Endtime,
			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> PC_ContentFinalMap, 
			LinkedHashMap<String, Integer> PC_PassMap, LinkedHashMap<String, Integer> PC_FailMap, int PC_browsercount, List<String> PC_browserlist, int PC_urls, String PC_ScreenshotPath,
			LinkedHashMap<String, LinkedHashMap<String, String>> RP_ToolsMap, List<String> RP_Brandslist, int RP_Toolscount, int RP_Browsercount,
			int RP_NWToolscount, int RP_RBSToolscount, int RP_ULSTERToolscount, int RP_NWIToolscount, int RP_IOMToolscount,
			String RP_str_starttime, String RP_str_endtime, Instant RP_Starttime, Instant RP_Endtime) throws IOException {

		try {

			System.out.println("HTML Report Generation Started");
			//resultsMap = Logger.map;
			StringBuilder htmlStringBuilder=new StringBuilder();			
			htmlStringBuilder.append("<!DOCTYPE html><html><head><title>Global Report</title><style>"

					+ "</style>"
					+ "<link href='All_reports\\global_styles.css' rel='stylesheet' type='text/css'>"
					//+ "<script type='text/javascript' language='javascript' src='All_reports\\reports.js'></script>"
					+ "</head>"
					+ "<body class='global_report'>");

			Instant endtime = Instant.now();
			Duration difftime = Duration.between(istarttime, endtime);
			System.out.println(starttime);
			System.out.println(endtime);
			long sec = difftime.getSeconds();

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date dt1 = new Date();
			String str_endtime = sdf1.format(dt1);

			String elapsedTime = null;
			elapsedTime = sec+" seconds";

			long min = 0;
			if(sec>=60) {
				min = difftime.toMinutes();
				elapsedTime=min+" minutes";
			}

			long hrs=0;
			if(min>=60) {
				hrs=difftime.toHours();
				elapsedTime=hrs+" hours";
			}


			htmlStringBuilder.append("<div class='header' style='background-color:#5ea5e0'><div class='clearfix'><h1>Interactive Test Automation Cartridge</h1></div></div>");

			String circlesclass;

			if(count==1){
				circlesclass = "one_circles";
			}else if(count==2){
				circlesclass = "two_circles";
			}else if(count==3){
				circlesclass = "three_circles";
			}else{
				circlesclass = "four_more";
			}

			//main content div starts here
			htmlStringBuilder.append("<div class='main'>");
			// TestResult table
			htmlStringBuilder.append("<div id='global_assets' class='clearfix'>"
					+ "<div class='global_wrapper  "+circlesclass+"'><div class='clearfix'>");
			for(Entry<String, LinkedHashMap<String, Integer>> exec : map.entrySet()){
				String idName = exec.getKey().toString().split("~")[0];
				String displayName = exec.getKey().toString().split("~")[1];
				htmlStringBuilder.append("<div class='content-grid "+idName+"'>"
						+ "<div class='links'>");
				int urls = 0;
				String passcount = null;
				String failcount =	null;
				String pass =	"pass";
				for(Entry<String, Integer> execvalues : exec.getValue().entrySet()){

					if(execvalues.getKey().contains("url")){
						urls = execvalues.getValue();
					}else if(execvalues.getKey().contains("Pass")){
						if(execvalues.getKey().contains("Missing")){
							String n = execvalues.getKey().split("~")[0];
							passcount = (n+": ")+execvalues.getValue();
							pass = "fail";
						}else{
							passcount = (execvalues.getKey()+": ")+execvalues.getValue();
						}
					}else{
						failcount = (execvalues.getKey()+": ")+execvalues.getValue();
					}
				}
				htmlStringBuilder.append("<a href='#"+idName+"-"+pass+"' class='anchor_button'>"+passcount+" </a>" 
						+ "<a href='#"+idName+"-fail' class='anchor_button'>"+failcount+" </a>"
						+ "</div>");

				htmlStringBuilder.append("<div class='urls'><h2>"+displayName+"</h2><p>Total Urls excuted:"+urls+"</p></div>");
				htmlStringBuilder.append("</div>");
			}
			if(!(JourneyFlowsUrlsList.isEmpty()) || !(HC_Mortgagesmap.isEmpty())) {
				htmlStringBuilder.append("<div class='content-grid healthChecks'> <div class='links'>");
				int taskcount=0;
						if(!(HC_Mortgagesmap.isEmpty())){
							htmlStringBuilder.append("<button id='#HealthChecks-MortgageTool' class='anchor_button'>Mortgage tools</button>");		
							taskcount++;
						}
						/*if(!(PageLoadTimeList.isEmpty())){
						htmlStringBuilder.append("<button id='#HealthChecks-pageLoadTime' class='anchor_button'>Response Time</button>");
							taskcount++;
						}*/
						if(!(JourneyFlowsUrlsList.isEmpty())){
						htmlStringBuilder.append("<button id='#HealthChecks-journeyFlowURLs' class='anchor_button'>Journey Flows</button>");
							taskcount++;
						}
						htmlStringBuilder.append("</div>");
				htmlStringBuilder.append("<div class='urls'><h2>Health<br />Checks</h2><p>Total tasks executed:"+taskcount+"</p></div>");
				htmlStringBuilder.append("</div>");
			}
			if(!(PageLoadTimeList.isEmpty())) {
				int PC_browserlistcount = PC_browserlist.size();
				htmlStringBuilder.append("<div class='content-grid responseTime'>");
				htmlStringBuilder.append("<div class='links'>");
				htmlStringBuilder.append("<a href='#responseTime-pass' class='anchor_button'>Pass: "+RT_passCount+"</a>");
				htmlStringBuilder.append("<a href='#responseTime-fail' class='anchor_button'>Fail: "+RT_failCount+" </a>");
				htmlStringBuilder.append("</div>");
				htmlStringBuilder.append("<div class='urls'><h2>Response<br />Time</h2><p>Total URLs excuted: "+RT_urls+"</p></div>");
				htmlStringBuilder.append("</div>");
			}
			if(!(PC_browserlist.isEmpty())) {
				int PC_browserlistcount = PC_browserlist.size();
				htmlStringBuilder.append("<div class='content-grid pageComparison'>");
				htmlStringBuilder.append("<div class='links'>");
				htmlStringBuilder.append("<a href='#PageComparison-pass' class='anchor_button'>Pass: "+PC_passCount+"</a>");
				htmlStringBuilder.append("<a href='#PageComparison-fail' class='anchor_button'>Fail: "+PC_failCount+" </a>");
				htmlStringBuilder.append("</div>");
				htmlStringBuilder.append("<div class='urls'><h2>Page<br />Comparison</h2><p>Total URLs excuted: "+PC_urls+" in "+PC_browserlistcount+" Browsers</p></div>");
				htmlStringBuilder.append("</div>");
			}
			if(!(RP_Brandslist.isEmpty())) {
				int brandListCount = RP_Brandslist.size();
				htmlStringBuilder.append("<div class='content-grid pageComparison'>");
				htmlStringBuilder.append("<div class='links'>");
				for(int bc=0; bc<brandListCount; bc++) {
					htmlStringBuilder.append("<a href='#RegressionPack-show-"+RP_Brandslist.get(bc)+"' class='anchor_button'>"+RP_Brandslist.get(bc)+" </a>");
				}				
				htmlStringBuilder.append("</div>");
				htmlStringBuilder.append("<div class='urls'><h2>Regression<br />Pack</h2><p>Total tools excuted: "+RP_Toolscount+" in "+RP_Browsercount+" Browsers</p></div>");
				htmlStringBuilder.append("</div>");
			}

			htmlStringBuilder.append("</div></div> <!--endof global_wrapper -->");
			// config table
			htmlStringBuilder.append("<div class='inner_sidebar'>");
			htmlStringBuilder.append("<h2>"+ ConfigTableHeader+"</h2> <div class='button_border clearfix'>"
					+ "<span class='button_arrow'></span>"
					+ "<table class='excution'>"                              
					+ "<tr><td> Test Name : </td><td>Global Report</td></tr>"
					+ "<tr><td> Start Time </td> <td>"+starttime+"</td></tr>"
					+ "<tr><td> End Time </td> <td>"+str_endtime+"</td></tr>"
					+ "<tr><td> Total Execution Time </td> <td>"+elapsedTime+"</td></tr>"
					+ "<tr><td> User Name</td><td>"+System.getProperty("user.name")+ "</td></tr>"
					+ "<tr><td>Operating System </td><td>"+System.getProperty("os.name")+  "</td></tr>"
					+  "</table> </div>");

			htmlStringBuilder.append("</div><!--endof inner_sidebar -->"
					+ "</div><!--endof global_assets -->");


			//show_records
			htmlStringBuilder.append("<div id='show_records'>"
					+ "<div id='horiz_buttons' class='clearfix'>"
					+ "<div class='floatleft'><a href='#global_assets' class='anchor_button' id=\"backToGlobal\">Back to Global Report</a></div>"
					+ "<div class='btn-group floatright'>"
					+ "<button id='show_all' type='button' class='btn btn-secondary active'>Show All</button>"
					+ "<button id='pass_all' type='button' class='btn btn-secondary'>Pass</button>"
					+ "<button id='fail_all' type='button' class='btn btn-secondary'>Fail</button></div></div>"

					+ "<div class='clearfix tab_records'>"
					+ "<div id='sidebar'>"
					+ "<ul class='components'>");

			/*---- Broken links ---------*/
			if( !(BL_map.isEmpty()) ) {
				String BrokenLinksET = elapsedtime(BL_starttime, BL_Endtime);

				htmlStringBuilder.append("<li class='active'>"
						+ "<a href='#BrokenLinks' class='dropdown-toggle'>Broken Links</a>"
						+ "<div class='config_div' style='display:block'>"
						+ "<div class='button_border clearfix'><span class='button_arrow'></span>"
						+ "<table class='excution'>"                              
						+ "<tr><td> Start Time </td> <td>"+BL_str_starttime+"</td></tr>"
						+ "<tr><td> End Time </td> <td>"+BL_str_endtime+"</td></tr>"
						+ "<tr><td> Total Execution Time </td> <td>"+BrokenLinksET+"</td></tr>"
						+"<tr><td> No of URL's Executed </td><td>"+BL_TotalURL+"</td></tr>"
						+ "<tr><td>Total Pass</td><td>"+BL_s+"</td></tr>"
						+ "<tr><td>Total Fail</td><td>"+BL_fails+"</td></tr>"
						+  "</table></div> </div><!--endof config_div -->"
						+ "</li>");
			}
			if( !(SC_map.isEmpty()) ) {
				String SpecialCharactersET = elapsedtime(SC_starttime, SC_Endtime);

				htmlStringBuilder.append("<li class=''>"
						+ "<a href='#FindingSpecialCharacters' class='dropdown-toggle'>Finding Special charectors</a>"
						+ "<div class='config_div'>"
						+ "<div class='button_border clearfix'><span class='button_arrow'></span>"
						+ "<table class='excution'>"                              
						+ "<tr><td> Start Time </td> <td>"+SC_str_starttime+"</td></tr>"
						+ "<tr><td> End Time </td> <td>"+SC_str_endtime+"</td></tr>"
						+ "<tr><td> Total Execution Time </td> <td>"+SpecialCharactersET+"</td></tr>"
						+ "<tr><td> No of URL's Executed </td><td>"+SC_TotalParentURL+"</td></tr>"
						+ "<tr><td>Total Pass</td><td>"+SC_passCount+"</td></tr>"
						+ "<tr><td>Total Fail</td><td>"+SC_failCount+"</td></tr>"
						+  "</table></div>"
						+ "</div><!--endof config_div -->"
						+ "</li>");
			}
			/*---- Start Missing Assets---------*/
			if( !(MA_map.isEmpty()) ) {
				String MissingAssetsET = elapsedtime(MA_starttime, MA_Endtime);
				htmlStringBuilder.append("<li class=''>"
						+ "<a href='#MissingAssets' class='dropdown-toggle'>Missing assets</a>"
						+ "<div class='config_div'>"
						+ "<div class='button_border clearfix'><span class='button_arrow'></span>"
						+ "<table class='excution'>"                              
						+ "<tr><td> Start Time </td> <td>"+MA_str_starttime+"</td></tr>"
						+ "<tr><td> End Time </td> <td>"+MA_str_endtime+"</td></tr>"
						+ "<tr><td> Total Execution Time </td> <td>"+MissingAssetsET+"</td></tr>"
						+ "<tr><td> No of URL's Executed </td><td>"+Globalvariables.MA_urls+"</td></tr>"
						//+ "<tr><td>Total Pass</td><td>"+Globalvariables.MA_success+"</td></tr>"
						+ "<tr><td>Total Fail</td><td>"+Globalvariables.MA_fails+"</td></tr>"
						+"</table></div> </div><!--endof config_div -->"
						+ "</li>");
			}

			/*---- Asset Sizes---------*/
			if( !(DA_map.isEmpty()) ) {
				
				String configfilePath = System.getProperty("user.dir")+"\\testConfig.properties";
				File file = new File(configfilePath);
				FileReader fr = new FileReader(file);
				Properties prop = new Properties();
				prop.load(fr);
				
				int Redvalue = Integer.parseInt(prop.getProperty("Red"));
				int Ambervalue = Integer.parseInt(prop.getProperty("Amber"));
				
				//Redvalue = Redvalue*1024;
				//Ambervalue = Ambervalue*1024;
 
				String AssetsizesET = elapsedtime(DA_starttime, DA_Endtime);
				htmlStringBuilder.append("<li>"
						+ "<a href='#AssetSizes' class='dropdown-toggle'>Asset Sizes</a>"
						+ "<div class='config_div'>"
						+ "<div class='button_border clearfix'><span class='button_arrow'></span>"
						+ "<table class='excution'>"                              
						+ "<tr><td> Start Time </td> <td>"+DA_str_starttime+"</td></tr>"
						+ "<tr><td> End Time </td> <td>"+DA_str_endtime+"</td></tr>"
						+ "<tr><td> Total Execution Time </td> <td>"+AssetsizesET+"</td></tr>"
						+ "<tr><td> No of URL's Executed </td><td>"+Globalvariables.DA_urls+"</td></tr>"
						+ "<tr><td>Total Pass</td><td>"+Globalvariables.DA_passcount+"</td></tr>"
						+ "<tr><td>Total Fail</td><td>"+Globalvariables.DA_failcount+"</td></tr>"
						+  "</table> </div>"
						+ "<ul class='sizeChart'>"
						+ "<li><span class='sizeRed'></span> <strong>&gt; "+Redvalue+"kb</strong></li>"
						+ "<li><span class='sizePass'></span> <strong>"+Ambervalue+"kb to "+Redvalue+"kb</strong></li>"
						+ "<li><span class='sizeAmber'></span> <strong>&lt; "+Ambervalue+"kb</strong></li>"
						+ "</ul>"
						+ "</div><!--endof config_div -->"
						+ "</li>");
			}
			if(!(JourneyFlowsUrlsList.isEmpty()) || !(HC_Mortgagesmap.isEmpty())) {
				String HealthchecksET = elapsedtime(HC_Starttime, HC_Endtime);

				htmlStringBuilder.append("<li>"
						+ "<a href='#HealthChecks' class='dropdown-toggle'>Health Checks</a>"
						+ "<div class='config_div'>"
						+ "<div class='button_border clearfix'><span class='button_arrow'></span>"
						+ "<table class='excution'>"                              
						+ "<tr><td> Start Time </td> <td>"+HC_str_starttime+"</td></tr>"
						+ "<tr><td> End Time </td> <td>"+HC_str_endtime+"</td></tr>"
						+ "<tr><td> Total Execution Time </td> <td>"+HealthchecksET+"</td></tr>"
						+  "</table></div> </div><!--endof config_div -->"
						+ "</li>");
			}
			/*---- Page Comparison---------*/
			if(!(PC_browserlist.isEmpty())) {
				//String PCET = elapsedtime(PC_Starttime, PC_Endtime);
				int PC_browserlistcount = PC_browserlist.size();
				htmlStringBuilder.append("<li>"
						+ "<a href='#PageComparison' class='dropdown-toggle'>Comparison</a>"
						+ "<div class='config_div'>"
						+ "<div class='button_border clearfix'><span class='button_arrow'></span>"
						+ "<table class='excution'>"                              
						+ "<tr><td> Start Time </td> <td>"+PC_str_starttime+"</td></tr>"
						+ "<tr><td> End Time </td> <td>"+PC_str_endtime+"</td></tr>"
						+ "<tr><td> Total URLs </td> <td>"+PC_urls+"</td></tr>"
						+ "<tr><td> Total Browser </td> <td>"+PC_browserlistcount+"</td></tr>"
						//	+ "<tr><td> Total Execution Time </td> <td>"+PCET+"</td></tr>"
						+  "</table></div> </div><!--endof config_div -->"
						+ "</li>");
			}
			/*---- Page Comparison---------*/
			if(!(PageLoadTimeList.isEmpty())) {
				String HealthchecksET = elapsedtime(HC_Starttime, HC_Endtime);
				htmlStringBuilder.append("<li>"
						+ "<a href='#responseTime' class='dropdown-toggle'>Response Time</a>"
						+ "<div class='config_div'>"
						+ "<div class='button_border clearfix'><span class='button_arrow'></span>"
						+ "<table class='excution'>"                              
						+ "<tr><td> Start Time </td> <td>"+HC_str_starttime+"</td></tr>"
						+ "<tr><td> End Time </td> <td>"+HC_str_endtime+"</td></tr>"
						+ "<tr><td> Total URLs </td> <td>"+RT_urls+"</td></tr>"
						+ "<tr><td> Total Execution Time </td> <td>"+HealthchecksET+"</td></tr>"
						+  "</table></div> </div><!--endof config_div -->"
						+ "</li>");
			}
			/*---- RP Tools---------*/
			if(!(RP_Brandslist.isEmpty())) {
				String RPCET = elapsedtime(RP_Starttime, RP_Endtime);
				htmlStringBuilder.append("<li>"
						+ "<a href='#RegressionPack' class='dropdown-toggle'>Regression Pack</a>"
						+ "<div class='config_div'>"
						+ "<div class='button_border clearfix'><span class='button_arrow'></span>"
						+ "<table class='excution'>"                              
						+ "<tr><td> Start Time </td> <td>"+RP_str_starttime+"</td></tr>"
						+ "<tr><td> End Time </td> <td>"+RP_str_endtime+"</td></tr>"
						+ "<tr><td> Total tool's </td> <td>"+RP_Toolscount+"</td></tr>"
						+ "<tr><td> Total Browser </td> <td>"+RP_Browsercount+"</td></tr>"
						+ "<tr><td> Total Execution Time </td> <td>"+RPCET+"</td></tr>"
						+  "</table></div> </div><!--endof config_div -->"
						+ "</li>");
			}

			htmlStringBuilder.append("</ul><!--endof components -->"
					+"</div><!--endof sidebar -->");

			htmlStringBuilder.append("<div id='show_content' class='clearfix'>");

			/*---- BrokenLinks---------*/
			if( !(BL_map.isEmpty()) ) {
				htmlStringBuilder.append("<div class='report_wrapper' id='BrokenLinks'><h2>Broken Links</h2>");
				htmlStringBuilder.append("<div class='accordionWrapper'>"
						+ "<div class='title_accor clearfix'><span class='floatleft'>No of URL's Executed: </span>"
						+ "<span class='floatleft'>"+BL_TotalURL+"</span> </div>"
						+ "<div><ul class='accordion'>");	

				htmlStringBuilder.append("<li class='open'><h3 class='accordionItemHead'><span>Total Passed :</span> <span>"+BL_s+"</span>"
						+ "</h3><div class='pass_all failAccordian accordionItemPanel'>");
				int page200 = 0;
				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(200)){
						if(page200 == 0) {
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>200-Success :</span> <span>"+BL_status200+"</span>"
									+ "</h4><div class='accordionItemPanel'>");
						}

						htmlStringBuilder.append("<p>");
						String d=key;
						htmlStringBuilder.append("<a href="+d+" target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page200++;

						if(page200==BL_status200) {
							htmlStringBuilder.append("</div></div>");
						}

					}
				}
				int page301 = 0;
				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(301)){
						if(page301 == 0) {
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>301-Redirection :</span> <span>"+BL_r+"</span>"
									+ "</h4><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p>");
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page301++;

						if(page301==BL_r) {
							htmlStringBuilder.append("</div></div>");
						}

					}
				}
				int page302 = 0;
				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(302)){
						if(page302 == 0) {
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>302-Permanent Redirection :</span> <span>"+BL_pr+"</span>"
									+ "</h4><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p>");
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page302++;
						if(page302==BL_pr) {
							htmlStringBuilder.append("</div></div>");
						}
					}
				}
				htmlStringBuilder.append("</div></li>");



				htmlStringBuilder.append("<li class='close'><h3 class='accordionItemHead'><span>Total Failed :</span> <span>"+BL_fails+"</span>"
						+ "</h3><div class='fail_all failAccordian accordionItemPanel'>");
				int page400 = 0;

				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(400)){
						if(page400 == 0) {
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>400-Bad Request :</span> <span>"+BL_b+"</span>"
									+ "</h4><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p>");
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page400++;
						if( page400 == BL_b) {
							htmlStringBuilder.append("</div></div>");
						}
					}
				}
				int page401 = 0;

				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(401)){
						if(page401 == 0) {
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>401-UnAuthorized :</span> <span>"+BL_u+"</span>"
									+ "</h4><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p>");
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page401++;
						if(page401 == BL_u) {
							htmlStringBuilder.append("</div></div>");
						}

					}
				}
				int page402 = 0;

				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(402)){
						if(page402 == 0) {
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>402-Payment Required :</span> <span>"+BL_py+"</span>"
									+ "</h4><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p>");
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page402++;
						if(page402 == BL_py) {
							htmlStringBuilder.append("</div></div>");
						}
					}
				}
				int page403 = 0;

				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(403)){
						if(page403 == 0) {
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>403-Forbidden :</span> <span>"+BL_f+"</span>"
									+ "</h4><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p>");
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page403++;
						if(page403 == BL_f) {
							htmlStringBuilder.append("</div></div>");
						}
					}
				}
				int page404 = 0;

				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(404)){
						if(page404 == 0) {
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>404-Page Not Found :</span> <span>"+BL_p+"</span>"
									+ "</h4><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p>");
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page404++;
						if( page404==BL_p) {
							htmlStringBuilder.append("</div></div>");
						}
					}

				}
				int page500 = 0;
				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(500)){
						if(page500 == 0) {
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>500-Internal Server Error :</span> <span>"+BL_i+"</span>"
									+ "</h4><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p>");
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page500++;
						if(page500 == BL_i) {
							htmlStringBuilder.append("</div></div>");
						}
					}
				}
				
				int page503 = 0;
				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(503)){
						if(page503 == 0) {
							int status = BL_map.get(key);
							String servicemsg = Getservicemsg(status);
						//	htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>"+status+"-"+servicemsg+":</span> <span>"+BL_i+"</span>"
								//	+ "</h4><div class='accordionItemPanel'>");
					//	}
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead'><span>503-Service Unavailable:</span> <span>"+BL_503+"</span>"
									+ "</h4><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p>");
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page503++;
						if(page503 == BL_503) {
							htmlStringBuilder.append("</div></div>");
						}
					}
				}

				htmlStringBuilder.append("</div></li>");


				
				int page0 = 0;
				for(String key : BL_map.keySet()) {
					if(BL_map.get(key).equals(0)){
						if(page0 == 0) {
							htmlStringBuilder.append("<li class='close'><h3 class='accordionItemHead'><span>Others :</span> <span>"+BL_o+"</span>"
									+ "</h3><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p> page0" + page0);
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						page0++;
						if(page0 == BL_o) {
							htmlStringBuilder.append("</div></li>");
						}
					}
				}
				/*int norunUrls = 0;	
				for(String key : BL_map.keySet()) {
					if(!BL_map.get(key).equals(404)&&(!BL_map.get(key).equals(301)&&(!BL_map.get(key).equals(302)&&(!BL_map.get(key).equals(400)&&(!BL_map.get(key).equals(500)&&(!BL_map.get(key).equals(401)&&(!BL_map.get(key).equals(402)&&(!BL_map.get(key).equals(403)&&(!BL_map.get(key).equals(200)&&(!BL_map.get(key).equals(0))))))))))){
						if(norunUrls == 0) {
							htmlStringBuilder.append("<li class='close'><h3 class='accordionItemHead'><span>No run Urls :</span> <span>"+BL_n+"</span>"
									+ "</h3><div class='accordionItemPanel'>");
						}
						htmlStringBuilder.append("<p> norunUrls" + norunUrls);
						String d=key;
						htmlStringBuilder.append("<a href="+d+"  target='_blank'>"+key+"</a>");
						htmlStringBuilder.append("</p>");

						norunUrls++;
						if(norunUrls==BL_n) {
							htmlStringBuilder.append("</div></li>");
						}
					}
				}*/


				htmlStringBuilder.append("</ul></div>"
						+ "</div><!--endof accordionWrapper-->");	

				if(BL_fails > 0) {
					htmlStringBuilder.append("<div class='assets'><div class='title_accor clearfix'><span class='floatleft'> Fail "+TitleTestResults+"</span></div>");
					htmlStringBuilder.append("<ul class='accordion fail_all'>");

					for(Entry<String, LinkedHashMap<String, String>> specialChar : BL_pcmap.entrySet()){

						htmlStringBuilder.append("<li class='close'>");
						String parenturl = specialChar.getKey().toString();
						htmlStringBuilder.append("<h3 align='left'>" + parenturl+" </h3>");
						htmlStringBuilder.append("<div class='asset_Innerwrapper clearfix'><table>");
						for(Entry<String, String> specialChar1 : specialChar.getValue().entrySet()){
							String d = specialChar1.getKey();
							htmlStringBuilder.append("<tr>");
							htmlStringBuilder.append("<td> <a href="+d+">"+specialChar1.getKey()+"</a></td>");
							htmlStringBuilder.append("</tr>");
						}
						htmlStringBuilder.append("</table><!--endof clearfix-->");
						htmlStringBuilder.append("</div><!--endof clearfix-->");
						htmlStringBuilder.append("</li><!--endof asset_wrapper-->");
					} 

					htmlStringBuilder.append("</ul><!--endof assets ul-->");
					htmlStringBuilder.append("</div><!--endof assets parent-->");
				}


				htmlStringBuilder.append("</div>");
			}
			/*---- FindingSpecialCharacters---------*/
			if( !(SC_map.isEmpty()) ) {
				htmlStringBuilder.append("<div class='report_wrapper' id='FindingSpecialCharacters'><h2>Special Characters</h2>");
				// TestResult table
				htmlStringBuilder.append("<div class='assets'>");
				htmlStringBuilder.append("<ul class='accordion'>");

				for(Entry<String, LinkedHashMap<String, String>> specialChar : SC_map.entrySet()){

					if(specialChar.getKey().contains("ParentURL_")){
						htmlStringBuilder.append("<li class='close'>");
						String parenturl = specialChar.getKey().toString().replace("ParentURL_", "");
						htmlStringBuilder.append("<h3 align='left'>" + parenturl+" </h3>");            
						htmlStringBuilder.append("<div class='asset_Innerwrapper clearfix'><table><tr><th style='width:80%;'>Assets List</th><th style='width:20%;'>Status</th></tr>");                        
						for(Entry<String, String> specialChar1 : specialChar.getValue().entrySet()){ 
							if(specialChar1.getValue().contains("Fail")){
								htmlStringBuilder.append("<tr class='fail_tr'>");
								htmlStringBuilder.append("<td style='width:80%; color:#ED2424'>"+specialChar1.getKey()+"</td>");
								htmlStringBuilder.append("<td align='center' style='width:20%; color:#ED2424'>"+specialChar1.getValue() +"</td>");
								htmlStringBuilder.append("</tr>");
							}else {
								htmlStringBuilder.append("<tr class='pass_tr'>");
								htmlStringBuilder.append("<td style='width:80%; color:#03c74a'>"+specialChar1.getKey()+"</td>");
								htmlStringBuilder.append("<td align='center' style='width:20%; color:#03c74a'>"+specialChar1.getValue() +"</td>");
								htmlStringBuilder.append("</tr>");
							}
						}
						htmlStringBuilder.append("</table><!--endof clearfix-->");
						htmlStringBuilder.append("</div><!--endof clearfix-->");
						htmlStringBuilder.append("</li><!--endof asset_wrapper-->");
					}   
				}      

				htmlStringBuilder.append("</ul><!--endof assets ul-->");
				htmlStringBuilder.append("</div><!--endof assets-->");
				htmlStringBuilder.append("</div><!--endof SC report_wrapper-->");
			}
			/*---- End of Special Charector ---------*/

			/*---- Start of Missing Assets---------*/
			if( !(MA_map.isEmpty()) ) {
				htmlStringBuilder.append("<div class='report_wrapper' id='MissingAssets'><h2>Missing Assets</h2>");			
				htmlStringBuilder.append("<div class='assets'>");
				htmlStringBuilder.append("<ul class='accordion'>");
				String className;
				String parenturl;
				for(Entry<String, LinkedHashMap<String, String>> res : MA_map.entrySet()){
					if(res.getKey().contains("FailParentURL_")) {
						className = "fail_all";
						parenturl = res.getKey().toString().replace("FailParentURL_", "");
					}else if(res.getKey().contains("ProdFailParentURL_")){
						className = "prodfail fail_all";
						parenturl = res.getKey().toString().replace("ProdFailParentURL_", "");
					}else if(res.getKey().contains("TestFailParentURL_")){
						className = "testfail fail_all";
						parenturl = res.getKey().toString().replace("TestFailParentURL_", "");
					}else {
						className = "pass_all";
						parenturl = res.getKey().toString().replace("ParentURL_", "");
					}

					if(res.getKey().contains("ParentURL_")) {


						if(className.contains("fail_all")) {
							htmlStringBuilder.append("<li class='close'><h3 align='left'>" + parenturl+" </h3>");
							htmlStringBuilder.append("<div class='asset_Innerwrapper clearfix "+className+" totalRecords'>");				

							htmlStringBuilder.append("<div class='floatleft'><table><tr><th>Missing Assets in Test</th></tr>");						
							for(Entry<String, String> innerResult :res.getValue().entrySet()) {
								if(innerResult.getKey().contains("MissingTest_")){							 
									String assetname = innerResult.getKey().toString().split("MissingTest_")[1];
									htmlStringBuilder.append("<tr><td align='left'>" + assetname +"</td></tr>");	
								}

							}
							htmlStringBuilder.append("</table></div><!--endof floatright-->");

							htmlStringBuilder.append("<div class='floatleft'><table><tr><th>Missing Assets in Prod</th></tr>");
							for(Entry<String, String> innerResult :res.getValue().entrySet()) {
								if(innerResult.getKey().contains("MissingProd_")){							 
									String assetname = innerResult.getKey().toString().split("MissingProd_")[1];
									htmlStringBuilder.append("<tr><td align='left'>" + assetname +"</td></tr>");	
								}

							}
							htmlStringBuilder.append("</table></div><!--endof floatright-->");

							htmlStringBuilder.append("</div><!--endof clearfix-->");
							htmlStringBuilder.append("</li><!--endof asset_wrapper-->");
						}else {
							htmlStringBuilder.append("<li class='close'><strong class="+className+">" + parenturl+" </strong></li>");

						}


					}

				}

				htmlStringBuilder.append("</ul><!--endof assets ul-->");
				htmlStringBuilder.append("</div><!--endof assets -->");
				htmlStringBuilder.append("</div><!--endof MA report_wrapper-->");
			}
			/*---- end of Missing Assets---------*/
			/*---- Start of AssetSizes---------*/
			if( !(DA_map.isEmpty()) ) {
				htmlStringBuilder.append("<div class='report_wrapper' id='AssetSizes'><h2>Asset Sizes</h2>");
				htmlStringBuilder.append("<div class='assets'>");
				htmlStringBuilder.append("<ul class='accordion'>");

				for(Entry<String, LinkedHashMap<String, String>> res : DA_map.entrySet()){
					if(res.getKey().contains("ParentURL_")) {

						String parenturl = res.getKey().toString().replace("ParentURL_", "");
						htmlStringBuilder.append("<li class='close'><h3 align='left'>" + parenturl+" </h3><div class='asset_Innerwrapper clearfix'>");				
						htmlStringBuilder.append("<div class='floatleft'><table><tr><th style='width: 80%;'>Desktop</th><th style='width: 20%;'>Size</th></tr>");

						for(Entry<String, String> innerResult :res.getValue().entrySet()) {

							if(innerResult.getKey().contains("desktopview_")){
								if(innerResult.getKey().contains("Red")){

									htmlStringBuilder.append("<tr class='totalRecords fail_tr'>");
									String assetname = innerResult.getKey().toString().split("Reddesktopview_")[1];
									String assetsize = innerResult.getValue().toString();
									htmlStringBuilder.append("<td align='left' style='width: 80%;color:#ED2424'>" + assetname +"</td>");
									htmlStringBuilder.append("<td align='center' style='width: 20%;color:#ED2424'>"+ assetsize +"</td>");
									htmlStringBuilder.append("</tr>");
								}

							}


						}
						for(Entry<String, String> innerResult :res.getValue().entrySet()) {

							if(innerResult.getKey().contains("desktopview_")){
								if(innerResult.getKey().contains("Amber")){ //Now Amber values are consider as pass, so green color is given

									htmlStringBuilder.append("<tr class='totalRecords pass_tr'>");
									String assetname = innerResult.getKey().toString().split("Amberdesktopview_")[1];
									String assetsize = innerResult.getValue().toString();
									htmlStringBuilder.append("<td align='left' style='width: 80%;color:#03c74a'>" + assetname +"</td>");
									htmlStringBuilder.append("<td align='center' style='width: 20%;color:#03c74a'>"+ assetsize +"</td>");
									htmlStringBuilder.append("</tr>");
								}

							}


						}
						for(Entry<String, String> innerResult :res.getValue().entrySet()) {

							if(innerResult.getKey().contains("desktopview_")){ //Now Amber values are consider as partially pass, so amber color is given

								if(!innerResult.getKey().contains("Reddesktopview_") && !innerResult.getKey().contains("Amberdesktopview_")){
									htmlStringBuilder.append("<tr class='totalRecords fail_tr'>");

									String assetname = innerResult.getKey().toString().split("desktopview_")[1];
									String assetsize = innerResult.getValue().toString();
									htmlStringBuilder.append("<td align='left' style='width: 80%;color:#ED9D24'> "  + assetname +"</td>");
									htmlStringBuilder.append("<td align='center' style='width: 20%;color:#ED9D24'>" + assetsize +"</td>");
									htmlStringBuilder.append("</tr>");
								}

							}


						}


						htmlStringBuilder.append("</table></div><!--endof floatleft-->"
								+ "<div class='floatright'><table><tr><th style='width: 80%;'>Mobile</th><th style='width: 20%;'>Size</th></tr>");

						for(Entry<String, String> innerResult :res.getValue().entrySet()) {
							if(innerResult.getKey().contains("mobileview_")){	
								if(innerResult.getKey().contains("Red")){

									htmlStringBuilder.append("<tr class='totalRecords fail_tr'>");
									String assetname = innerResult.getKey().toString().split("Redmobileview_")[1];
									String assetsize = innerResult.getValue().toString();
									htmlStringBuilder.append("<td align='left' style='width: 80%;color:#ED2424'>"+ assetname +"</td>");
									htmlStringBuilder.append("<td align='center' style='width: 20%;color:#ED2424'>"+ assetsize +"</td>");
									htmlStringBuilder.append("</tr>");
								}

							}

						}
						for(Entry<String, String> innerResult :res.getValue().entrySet()) {
							if(innerResult.getKey().contains("mobileview_")){							 
								if(innerResult.getKey().contains("Amber")){  //Now Amber values are consider as pass, so green color is given

									htmlStringBuilder.append("<tr class='totalRecords pass_tr'>");
									String assetname = innerResult.getKey().toString().split("Ambermobileview_")[1];
									String assetsize = innerResult.getValue().toString();
									htmlStringBuilder.append("<td align='left' style='width: 80%;color:#03c74a'>"+ assetname +"</td>");
									htmlStringBuilder.append("<td align='center' style='width: 20%;color:#03c74a'>"+ assetsize +"</td>");
									htmlStringBuilder.append("</tr>");
								}

							}

						}
						for(Entry<String, String> innerResult :res.getValue().entrySet()) {
							if(innerResult.getKey().contains("mobileview_")){		//Now Amber values are consider as partially pass, so amber color is given				 
								if(!innerResult.getKey().contains("Red") && !innerResult.getKey().contains("Amber")){

									htmlStringBuilder.append("<tr class='totalRecords fail_tr'>");
									String assetname = innerResult.getKey().toString().split("mobileview_")[1];
									String assetsize = innerResult.getValue().toString();
									htmlStringBuilder.append("<td align='left' style='width: 80%;color:#ED9D24'>"+ assetname +"</td>");
									htmlStringBuilder.append("<td align='center' style='width: 20%;color:#ED9D24'>"+ assetsize +"</td>");
									htmlStringBuilder.append("</tr>");
								}

							}

						}

						htmlStringBuilder.append("</table></div><!--endof floatright-->");

						htmlStringBuilder.append("</div><!--endof clearfix-->");
						htmlStringBuilder.append("</li><!--endof asset_wrapper-->");

					}

				}
				htmlStringBuilder.append("</ul><!--endof assets ul-->");
				htmlStringBuilder.append("</div><!--endof assets div-->");
				htmlStringBuilder.append("</div><!--endof DA report_wrapper-->");
			}/*---- eND OF aSSET SIZES---------*/

			/*---- Start of Health Checks---------*/
			if(!(JourneyFlowsUrlsList.isEmpty()) || !(HC_Mortgagesmap.isEmpty())) {
				htmlStringBuilder.append("<div class='report_wrapper' id='HealthChecks'><h2>Health Checks</h2>");
				
				htmlStringBuilder.append( "<div class='hc_buttons clearfix'><div class='btn-group floatright'>");
				if(!(HC_Mortgagesmap.isEmpty())){
				htmlStringBuilder.append( "<button id='show_MortgageTool' type='button' class='btn btn-secondary'>Mortgage Tool</button>");
				}
				/*if(!(PageLoadTimeList.isEmpty())){
				htmlStringBuilder.append( "<button id='show_pageLoadTime' type='button' class='btn btn-secondary'>Response Time</button>");
				}*/
				if(!(JourneyFlowsUrlsList.isEmpty())){
				htmlStringBuilder.append( "<button id='show_journeyFlowURLs' type='button' class='btn btn-secondary active'>Journey Flows</button>");
				}
				htmlStringBuilder.append( "</div></div>");
				
				
				if(!(JourneyFlowsUrlsList.isEmpty())) {
				// TestResult table
				htmlStringBuilder.append("<div id='journeyFlowURLs' class='toggleDiv'>"
						+ "<div class='assets'><div class='title_accor clearfix'><span class='floatleft'> Journey Flow URLs</span></div>");

				//List<String> JourneyFlowsUrlsList
				int Journeyflowscount = JourneyFlowsUrlsList.size();
				htmlStringBuilder.append("<div class='asset_Innerwrapper clearfix'>"
						+ "<table><tr><th>URLs</th><th>CAO/OLAF URLs</th><th>Time Stamp</th><th>Journey Type</th></tr>");  
				for(int i=0;i<Journeyflowscount;i++){

					String[] urlsresult = JourneyFlowsUrlsList.get(i).split("~");
					//String url1 = urlsresult[0].toString();

					htmlStringBuilder.append("<tr class=''>");
					htmlStringBuilder.append("<td style='width:35%;'>"+urlsresult[0].toString()+"</td>");
					htmlStringBuilder.append("<td style='width:40%;'>"+urlsresult[1].toString() +"</td>");
					htmlStringBuilder.append("<td style='width:10%;'>"+urlsresult[2].toString()+"</td>");
					htmlStringBuilder.append("<td style='width:15%;'>"+urlsresult[3].toString()+"</td>");
					htmlStringBuilder.append("</tr>");					
				} 
				
				htmlStringBuilder.append("</table><!--endof table-->");
				htmlStringBuilder.append("</div><!--endof asset_Innerwrapper-->");
				htmlStringBuilder.append("</div><!--endof assets-->");
				htmlStringBuilder.append("</div><!--endof toggleDiv-->");
				
				}
				
				
				//List<String> Mortgage tool
				if(!(HC_Mortgagesmap.isEmpty())) {
				htmlStringBuilder.append("<div id='MortgageTool' class='toggleDiv'>"
						+ "<div class='assets'><div class='title_accor clearfix'><span class='floatleft'>Mortgage Tool</span></div>");
				htmlStringBuilder.append("<ul class='accordion'>");
				for(Entry<String, LinkedHashMap<String, String>> res : HC_Mortgagesmap.entrySet()){

					htmlStringBuilder.append("<li class='close'><h3 align='left'>" + res.getKey()+" </h3><div class='asset_Innerwrapper clearfix'>");				
					htmlStringBuilder.append("<table><tr><th style='width: 80%;'>Test Case</th><th style='width: 20%;'>Status</th></tr>");

					for(Entry<String, String> innerResult :res.getValue().entrySet()) {

						if(innerResult.getValue().toString().contains("Pass")){
							htmlStringBuilder.append("<tr class='pass_tr'>");
							htmlStringBuilder.append("<td align='left' style='width: 85%; color:#03c74a'>" + innerResult.getKey() +"</td>");
							htmlStringBuilder.append("<td align='center' style='width: 15%; color:#03c74a'>"+ innerResult.getValue() +"</td>");
							htmlStringBuilder.append("</tr>");
						}else {
							htmlStringBuilder.append("<tr class='fail_tr'>");
							htmlStringBuilder.append("<td align='left' style='width:85%; color:#ED2424'>" + innerResult.getKey() +"</td>");
							htmlStringBuilder.append("<td align='center' style='width:15%; color:#ED2424'>"+ innerResult.getValue() +"</td>");
							htmlStringBuilder.append("</tr>");
						}


					}
					htmlStringBuilder.append("</table><!--endof assets-->");
					htmlStringBuilder.append("</div><!--endof asset_Innerwrapper-->");
					htmlStringBuilder.append("</li><!--endof asset_Innerwrapper-->");

				}
				htmlStringBuilder.append("</ul><!--endof assets ul-->");
				htmlStringBuilder.append("</div><!--endof assets-->");
				htmlStringBuilder.append("</div><!--endof toggleDiv-->");
				//end of toggle div
				}
				htmlStringBuilder.append("</div><!--endof HC report_wrapper-->");
			}
			/*---- End of HEALTH CHECKS ---------*/

			/*---- Start of PageComparison---------*/
			if(!(PC_map.isEmpty())){
				htmlStringBuilder.append("<div class='report_wrapper' id='PageComparison'><h2>Comparison</h2>");

				htmlStringBuilder.append("<div class='assets'>");

				htmlStringBuilder.append("<ul class='accordion'>");

				for(Entry<String, LinkedHashMap<String, LinkedHashMap<String, String>>>  res : PC_ContentFinalMap.entrySet()){	

					htmlStringBuilder.append("<li class='close'><h3 class='accordionItemHead'><div class='clearfix'><span class='floatleft'>"+res.getKey()+"</span> <div class='floatright'>");
					for(Entry<String, Integer> passRes :PC_PassMap.entrySet()) {
						if(passRes.getKey().equals(res.getKey())) {
							htmlStringBuilder.append(" <span> Shelves Passed: " + passRes.getValue()+"</span>");
						}
					}
					for(Entry<String, Integer> failRes :PC_FailMap.entrySet()) {
						if(failRes.getKey().equals(res.getKey())) {
							htmlStringBuilder.append(" <span> Shelves Failed: " + failRes.getValue()+"</span>");
						}
					}
					htmlStringBuilder.append("</div></div></h3><div class='failAccordian accordionItemPanel'>");

					for(Entry<String, LinkedHashMap<String, String>> innerResult :res.getValue().entrySet()) {

						String parenturl0 = innerResult.getKey().toString().replace("ParentURL_", "");

						if(parenturl0.contains(res.getKey())) {
							String[] parenturl = parenturl0.split("!");
							htmlStringBuilder.append("<div class='close'><h4 class='accordionItemHead' align='left'>" + parenturl[1]+"</h3>"
									+ "<div class='accordionItemPanel'>");				
							htmlStringBuilder.append("<table><tr><th style='width: 80%;'>Shelf Name</th><th style='width: 20%;'>Status</th></tr>");
							for(Entry<String, String> statusRes :innerResult.getValue().entrySet()) {
								String[] screenshotpath = statusRes.getValue().split("~"); 
								if(statusRes.getValue().contains("Fail")){
									htmlStringBuilder.append("<tr class='totalRecords fail_tr'>");
									htmlStringBuilder.append("<td style='color:#E15037'><a href='"+screenshotpath[1]+"' target='_blank'>"+statusRes.getKey()+"</a></td>");
									htmlStringBuilder.append("<td align='center' style='width:20%;color:#E15037'>"+screenshotpath[0] +"</td>");
									htmlStringBuilder.append("</tr>");
								}else {
									htmlStringBuilder.append("<tr class='totalRecords pass_tr'>");
									htmlStringBuilder.append("<td style='color:#03c74a'><a href='"+screenshotpath[1]+"' target='_blank'>"+statusRes.getKey()+"</a></td>");
									htmlStringBuilder.append("<td align='center' style='width:20%;color:#03c74a'>"+screenshotpath[0] +"</td>");
									htmlStringBuilder.append("</tr>");
								}
							}
							htmlStringBuilder.append("</table><!--endof table-->");
							htmlStringBuilder.append("</div><!--endof accordionItemPanel-->");
							htmlStringBuilder.append("</div><!--endof close-->");
						}
					}
						htmlStringBuilder.append("</li><!--endof asset_Innerwrapper-->");
				}
				htmlStringBuilder.append("</ul><!--endof assets ul-->");
				htmlStringBuilder.append("</div><!--endof assets-->");


				htmlStringBuilder.append("</div>");
			}
			/*---- end of PageComparison---------*/
			/*---- Start of responseTime---------*/
			if(!(PageLoadTimeList.isEmpty())){
				htmlStringBuilder.append("<div class='report_wrapper' id='responseTime'><h2>Response Time</h2>");

				htmlStringBuilder.append("<div class='assets'>");

				htmlStringBuilder.append("<ul class='accordion'>");
				htmlStringBuilder.append("<li class='open'><h3 align='left'>Response Time</h3>");
				int Pageloadflowscount = PageLoadTimeList.size();
				htmlStringBuilder.append("<div  class='asset_Innerwrapper clearfix both_all'>"
						+ "<table><tr><th>URLs</th><th>Response Time</th><th>Status</th></tr>"); 
				for(int i=0;i<Pageloadflowscount;i++){

					String[] pageListsresult = PageLoadTimeList.get(i).split("~");

					if(pageListsresult[3].toString().contains("Fail")){
						htmlStringBuilder.append("<tr class='fail_tr'>");
						htmlStringBuilder.append("<td style='width:70%; color:#ED2424'><a href="+pageListsresult[0].toString()+" target='_blank'>"+pageListsresult[0].toString()+"</a></td>");
						htmlStringBuilder.append("<td style='width:15%; color:#ED2424'>"+pageListsresult[1].toString() +"</td>");
						//htmlStringBuilder.append("<td style='width:15%; color:#ED2424'>"+pageListsresult[2].toString()+"</td>");
						htmlStringBuilder.append("<td style='width:15%; color:#ED2424'>"+pageListsresult[3].toString()+"</td>");
						htmlStringBuilder.append("</tr>");	
					}else {
						htmlStringBuilder.append("<tr class='pass_tr'>");
						htmlStringBuilder.append("<td style='width:70%; color:#03c74a'><a href="+pageListsresult[0].toString()+" target='_blank'>"+pageListsresult[0].toString()+"</a></td>");
						htmlStringBuilder.append("<td style='width:15%; color:#03c74a'>"+pageListsresult[1].toString() +"</td>");
						//htmlStringBuilder.append("<td style='width:15%; color:#03c74a'>"+pageListsresult[2].toString()+"</td>");
						htmlStringBuilder.append("<td style='width:15%; color:#03c74a'>"+pageListsresult[3].toString()+"</td>");
						htmlStringBuilder.append("</tr>");							
					}
				}     

				htmlStringBuilder.append("</table><!--endof table-->");
				htmlStringBuilder.append("</div><!--endof asset_Innerwrapper-->");
				htmlStringBuilder.append("</li><!--endof Li-->");		
				
				
				htmlStringBuilder.append("<li class='close'><h3 align='left'>Response Code</h3>");
				int Pageloadflowscount1 = PageLoadTimeList.size();
				htmlStringBuilder.append("<div  class='asset_Innerwrapper clearfix both_all'>"
						+ "<table><tr><th>URLs</th><th>Response code</th><th>Status</th></tr>");  
				for(int i=0;i<Pageloadflowscount1;i++){

					String[] pageListsresult = PageLoadTimeList.get(i).split("~");

					if(!(pageListsresult[2].equals("200"))){
						htmlStringBuilder.append("<tr class='fail_tr'>");
						htmlStringBuilder.append("<td style='width:70%; color:#ED2424'><a href="+pageListsresult[0].toString()+" target='_blank'>"+pageListsresult[0].toString()+"</a></td>");
						//htmlStringBuilder.append("<td style='width:15%; color:#ED2424'>"+pageListsresult[1].toString() +"</td>");
						htmlStringBuilder.append("<td style='width:15%; color:#ED2424'>"+pageListsresult[2].toString()+"</td>");
						htmlStringBuilder.append("<td style='width:15%; color:#ED2424'>Fail</td>");
						htmlStringBuilder.append("</tr>");	
					}else {
						htmlStringBuilder.append("<tr class='pass_tr'>");
						htmlStringBuilder.append("<td style='width:70%; color:#03c74a'><a href="+pageListsresult[0].toString()+" target='_blank'>"+pageListsresult[0].toString()+"</a></td>");
						//htmlStringBuilder.append("<td style='width:15%; color:#03c74a'>"+pageListsresult[1].toString() +"</td>");
						htmlStringBuilder.append("<td style='width:15%; color:#03c74a'>"+pageListsresult[2].toString()+"</td>");
						htmlStringBuilder.append("<td style='width:15%; color:#03c74a'>Pass</td>");
						htmlStringBuilder.append("</tr>");							
					}
				}     

				htmlStringBuilder.append("</table><!--endof table-->");
				htmlStringBuilder.append("</div><!--endof asset_Innerwrapper-->");
				htmlStringBuilder.append("</li><!--endof Li-->");	
				
				
				htmlStringBuilder.append("</ul><!--endof assets ul-->");
				htmlStringBuilder.append("</div><!--endof assets-->");


				htmlStringBuilder.append("</div>");
			}
			/*---- end of responseTime---------*/

			/*---- Start of toolS---------*/
			if(!(RP_ToolsMap.isEmpty())){
				htmlStringBuilder.append("<div class='report_wrapper' id='RegressionPack'><h2>Regression Pack</h2>");
				if( !(JourneyFlowsUrlsList.isEmpty()) ) {
					htmlStringBuilder.append("<div class='hc_buttons clearfix'><div class='btn-group floatright'>"
							+ "<button id='show_RegresPc' type='button' class='btn btn-secondary'>Mortgage Tool</button>"
							+ "<button id='show_jrnyFlowURLs' type='button' class='btn btn-secondary active'>Journey Flows</button>"

					+ "</div></div>");
				}
				htmlStringBuilder.append("<div id='RegresPc' class='toggleDiv'>");
				//List<String> Mortgage tool
				htmlStringBuilder.append("<div class='RC_brands clearfix'>");
				int brandListCount = RP_Brandslist.size();
				for(int bc=0; bc<brandListCount; bc++) {
					String brandName = RP_Brandslist.get(bc).toString().replace(" ", "_");
					htmlStringBuilder.append("<div class='"+brandName+"'> <span data-brand-type='"+brandName+"'>"+RP_Brandslist.get(bc)+"</span>");
					//htmlStringBuilder.append("<span id='pass_"+RP_Brandslist.get(bc)+"'> Pass Count </span>");
					htmlStringBuilder.append("</div>");
				}
				htmlStringBuilder.append("</div>");

				htmlStringBuilder.append("<div class='assets'>");

				htmlStringBuilder.append("<ul class='accordion'>");
				int toolCount = 0;
				List<String> browsersList = new ArrayList<>();
				List<String> branList = new ArrayList<>();
				int toolCount_nw = 0;
				int browserToolCount = (RP_Toolscount/RP_Browsercount);
				int browser_brand_tool_count=0;
				for(Entry<String, LinkedHashMap<String, String>>  res : RP_ToolsMap.entrySet()){		
					String[] brow_brand = res.getKey().toString().split("~");
					String browser = brow_brand[1].toString();
					String brand = brow_brand[0].toString();
					String tool = brow_brand[2].toString();
					if(brand.equals("Natwest")) {
						browser_brand_tool_count = (RP_NWToolscount/RP_Browsercount);
					}
					if(brand.equals("RBS")) {
						browser_brand_tool_count = (RP_RBSToolscount/RP_Browsercount);
					}
					/*if(brand.equals("Ulster")) {
					browser_brand_tool_count = (RP_ULSTERToolscount/RP_Browsercount);
				}
				if(brand.equals("Natwest International")) {
					browser_brand_tool_count = (RP_NWIToolscount/RP_Browsercount);
				}
				if(brand.equals("IOM")) {
					browser_brand_tool_count = (RP_IOMToolscount/RP_Browsercount);
				}*/

					if(!(branList.contains(brand))) {
						toolCount_nw = 0;
					}

					if(!(browsersList.contains(browser))) {
						toolCount = 0;
					}

					if(toolCount == 0) {	
						htmlStringBuilder.append("<li class='close'><h3 class='accordionItemHead'>"+browser+"</h3><div class='failAccordian accordionItemPanel'>");
						browsersList.add(browser);

					}
					if(toolCount_nw == 0) {					
						htmlStringBuilder.append("<div class='"+brand+"'>");
						branList.add(brand);
					}

					htmlStringBuilder.append("<div class='close' id='"+brand+"_"+tool+"'><h4 class='accordionItemHead' align='left'>" + tool +" </h4>"
							+ "<div class='accordionItemPanel'><div>");				
					htmlStringBuilder.append("<table><tr><th style='width: 80%;'>Test Case</th><th style='width: 20%;'>Status</th></tr>");
					for(Entry<String, String> statusRes :res.getValue().entrySet()) {
						if(statusRes.getValue().contains("Fail")){
							String brandFail_count = "fail_"+brand+"_"+tool; 
							htmlStringBuilder.append("<tr class='totalRecords fail_tr "+brandFail_count+"'>");
							htmlStringBuilder.append("<td style='color:#E15037'>"+statusRes.getKey()+"</td>");
							htmlStringBuilder.append("<td align='center' style='width:20%;color:#E15037'>"+statusRes.getValue() +"</td>");
							htmlStringBuilder.append("</tr>");
						}else {
							String brandPass_count = "pass_"+brand+"_"+tool; 
							htmlStringBuilder.append("<tr class='totalRecords pass_tr "+brandPass_count+"'>");
							htmlStringBuilder.append("<td style='color:#03c74a'>"+statusRes.getKey()+"</td>");
							htmlStringBuilder.append("<td align='center' style='width:20%;color:#03c74a'>"+statusRes.getValue() +"</td>");
							htmlStringBuilder.append("</tr>");
						}
					}
					htmlStringBuilder.append("</table><!--endof table-->");
					htmlStringBuilder.append("</div></div><!--endof accordionItemPanel-->");
					htmlStringBuilder.append("</div><!--endof close-->");

					toolCount_nw ++;
					if(toolCount_nw == browser_brand_tool_count) {			
						htmlStringBuilder.append("</div><!--endof brand-->");	
					}
					toolCount ++;

					if(toolCount == browserToolCount) {						
						htmlStringBuilder.append("</li><!--endof asset_Innerwrapper-->");
						branList.clear();
					}


				}
				htmlStringBuilder.append("</ul><!--endof assets ul-->");
				htmlStringBuilder.append("</div><!--endof assets-->");

				htmlStringBuilder.append("</div><!--endof regressionPack-->");
				// TestResult table
				if( !(JourneyFlowsUrlsList.isEmpty()) ) {

					htmlStringBuilder.append("<div id='jrnyFlowURLs' class='toggleDiv'>"
							+ "<div class='assets'><div class='title_accor clearfix'><span class='floatleft'> Journey Flow URLs</span></div>");

					//List<String> JourneyFlowsUrlsList
					int Journeyflowscount = JourneyFlowsUrlsList.size();
					htmlStringBuilder.append("<div class='asset_Innerwrapper clearfix'>"
							+ "<table><tr><th>URLs</th><th>CAO/OLAF URLs</th><th>Time Stamp</th><th>Journey Type</th></tr>");  
					for(int i=0;i<Journeyflowscount;i++){

						String[] urlsresult = JourneyFlowsUrlsList.get(i).split("~");
						//String url1 = urlsresult[0].toString();

						htmlStringBuilder.append("<tr class=''>");
						htmlStringBuilder.append("<td style='width:35%;'>"+urlsresult[0].toString()+"</td>");
						htmlStringBuilder.append("<td style='width:40%;'>"+urlsresult[1].toString() +"</td>");
						htmlStringBuilder.append("<td style='width:10%;'>"+urlsresult[2].toString()+"</td>");
						htmlStringBuilder.append("<td style='width:15%;'>"+urlsresult[3].toString()+"</td>");
						htmlStringBuilder.append("</tr>");					
					}      

					htmlStringBuilder.append("</table><!--endof table-->");
					htmlStringBuilder.append("</div><!--endof asset_Innerwrapper-->");
					htmlStringBuilder.append("</div><!--endof assets-->");
					htmlStringBuilder.append("</div><!--endof jrnyFlowursl-->");

				}

				htmlStringBuilder.append("</div>");
			}
			/*---- end of toolS---------*/



			htmlStringBuilder.append("</div><!--endof show_content -->");

			htmlStringBuilder.append("</div><!--endof tab_records -->");
			htmlStringBuilder.append("</div><!--endof show_records -->");

			htmlStringBuilder.append("</div><!--endof Mains -->");

			htmlStringBuilder.append("<div class='footer'><footer align='center'>&copy; Copyright 2019, All Rights Reserved</footer></div>");

			htmlStringBuilder.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>"
					+ "<script type='text/javascript' language='javascript' src='All_reports\\reports.js'></script>");


			htmlStringBuilder.append("</body></html>");

			String filepath = System.getProperty("user.dir")+"\\Results\\HTML_results\\";
			//  String filepath = HTMLReportPath;
			String filePathAndName = filepath + "Globalreport.html";
			System.out.println("filepathAndName = " +filePathAndName);

			File resFile=new File(filePathAndName);
			OutputStream outputStream = new FileOutputStream(resFile.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);
			writer.write(htmlStringBuilder.toString());
			writer.close();

			System.out.println("HTML Report Generation Completed");

		}catch(IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static String Getservicemsg(int status){
		
		String servicemsg = null;
		
		switch(status){
			case 100:
				servicemsg = "Continue";
			//case 	
			
		}
		
		
		return servicemsg ;
		
	}
	
	
 	public static String elapsedtime(Instant starttime, Instant endtime) {

		Duration difftime = Duration.between(starttime, endtime);
		/*System.out.println(starttime);
		System.out.println(endtime);*/
		long sec = difftime.getSeconds();

		String elapsedTime = null;
		elapsedTime = sec+" seconds";

		long min = 0;
		if(sec>=60) {
			min = difftime.toMinutes();
			elapsedTime=min+" minutes";
		}

		long hrs=0;
		if(min>=60) {
			hrs=difftime.toHours();
			elapsedTime=hrs+" hours";
		}
		return elapsedTime;
	}


}