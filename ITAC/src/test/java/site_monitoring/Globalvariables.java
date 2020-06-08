package site_monitoring;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.poifs.filesystem.Entry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.library.CSV_Report;
import com.library.HTML_Reports;

public class Globalvariables {

	public static String imagepath = System.getProperty("user.dir")+"\\drivers";
	public static int BL_success=0;
	public static int BL_fails=0;
	public static int BL_urls=0;
	public static LinkedHashMap<String,Integer> BL_passmap = new LinkedHashMap<String,Integer>();
	public static LinkedHashMap<String,Integer> BL_failmap = new LinkedHashMap<String,Integer>();
	public static int SC_success=0;
	public static int SC_fails=0;
	public static int SC_urls=0;
	public static LinkedHashMap<String,String> SC_passmap = new LinkedHashMap<String,String>();
	public static LinkedHashMap<String,String> SC_failmap = new LinkedHashMap<String,String>();
	public static int MA_failsintest=0;
	public static int MA_failsinprod=0;
	public static int MA_urls=0;
	public static int MA_total=0;
	public static int DA_dred=0;
	public static int DA_damber=0;
	public static int DA_dgreen=0;
	public static int DA_urls=0;
	public static int DA_dtotal=0;
	public static int DA_mred=0;
	public static int DA_mamber=0;
	public static int DA_mgreen=0;
	public static int DA_mtotal=0;
	public static int PC_success=0;
	public static int PC_fails=0;
	public static int PC_urls=0;
	public static int PC_browsercount=0;
	public static LinkedHashMap<String,LinkedHashMap<String,Integer>> executionmap = new LinkedHashMap<String,LinkedHashMap<String,Integer>>();
	public static String str_starttime =null;
	public static Instant Starttime =null;
	
	public static String BL_html_result_filePath;
	public static LinkedHashMap<String, Integer> BL_finalresult = new LinkedHashMap<String, Integer>();
	public static String BL_Filename;
	public static String BL_str_starttime =null;
	public static String BL_str_endtime = null;
	public static Instant BL_starttime =null;
	public static String BL_Summary;
	public static int BL_finallistsize=0;
	public static int BL_totalchildlinks=0;
	public static int BL_fail=0;
	public static int BL_redirect=0;
	public static int BL_unauthorized=0;
	public static int BL_forbidden=0;
	public static int BL_badrequest=0;
	public static int BL_pagenotfound=0;
	public static int BL_server=0;
	public static int BL_others=0;
	public static int BL_parentURLfail=0;
	public static int BL_successes=0;
	public static int BL_permanentredirect=0;
	public static int BL_paymentrequired=0;
	public static int BL_status200=0;
	public static int BL_serviceunavailable=0;
	public static LinkedHashMap<String,LinkedHashMap<String,String>> BL_failparentchildmap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
	public static Instant BL_Endtime =null;
	
	public static String DA_HTML_filePath;
	public static LinkedHashMap<String, LinkedHashMap<String, String>> DA_Newmap = new LinkedHashMap<>();
	public static String DA_Filename;
	public static String DA_str_starttime =null;
	public static String DA_str_endtime = null;
	public static Instant DA_starttime = null;
	public static String DA_Summary;
	public static Instant DA_Endtime =null;
	
	public static String MA_HTML_filePath;
	public static LinkedHashMap<String, LinkedHashMap<String, String>> MA_htmlmap = new LinkedHashMap<>();
	public static String MA_Filename;
	public static String MA_str_starttime =null;
	public static String MA_str_endtime = null;
	public static Instant MA_starttime = null;
	public static String MA_Summary;
	public static Instant MA_Endtime =null;
	
	public static String SC_htmlfilename;
	public static LinkedHashMap<String, LinkedHashMap<String, String>> SC_htmlmap = new LinkedHashMap<>();
	public static String SC_Filename;
	public static String SC_str_starttime =null;
	public static String SC_str_endtime = null;
	public static Instant SC_starttime = null;
	public static String SC_Summary;
	public static int SC_rowcount=0;
	public static int SC_passvalue=0;
	public static int SC_failvalue=0;
	public static Instant SC_Endtime =null;
	
	public static String PC_HTMLReportPath;
	public static String PC_HTMLReportName;
	public static LinkedHashMap<String, LinkedHashMap<String, String>> PC_FinalMap = new LinkedHashMap<>();
	public static String PC_Filename;
	public static String PC_str_starttime =null;
	public static String PC_str_endtime = null;
	public static Instant PC_starttime = null;
	public static String PC_Summary;
	public static int PC_passcount = 0;
	public static int PC_failcount = 0;
	public static int PC_totalcount = 0;
	public static int PC_exceptioncount = 0;
	public static int PC_missingcount = 0;
	public static LinkedHashMap<String, LinkedHashMap<String, String>> PC_MissingMap = new LinkedHashMap<>();
	public static List<String> PC_browserlist = new ArrayList<String>();
	public static Instant PC_Endtime =null;
	public static String PC_ScreenshotPath;
	
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> PC_ContentFinalMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, Integer> PC_PassMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, Integer> PC_FailMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, LinkedHashMap<String, Integer>> PC_ExceptionMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> PC_ContentMissingMap = new LinkedHashMap<>();
	
	public static int MA_success=0;
	public static int MA_fails=0;
	public static int DA_passcount=0;
	public static int DA_failcount=0;
	
	public static int ResponseTimePass=0;
	public static int ResponseTimefail=0;
	public static int ResponseTimeurls=0;
	
	public static List<String> JourneyFlowsUrlsList = new ArrayList<String>();
	public static List<String> PageLoadTimeList = new ArrayList<String>();
	public static LinkedHashMap<String, LinkedHashMap<String, String>> HC_Mortgagesmap = new LinkedHashMap<>();
	public static String HC_str_starttime =null;
	public static String HC_str_endtime = null;
	public static Instant HC_starttime = null;
	public static Instant HC_Endtime =null;
	
	public static LinkedHashMap<String, LinkedHashMap<String, String>> RP_ToolsMap = new LinkedHashMap<>();
	public static List<String> RP_Brandslist = new ArrayList<String>();
	public static int RP_Toolscount=0;
	public static int RP_NWToolscount=0;
	public static int RP_RBSToolscount=0;
	public static int RP_ULSTERToolscount=0;
	public static int RP_NWIToolscount=0;
	public static int RP_IOMToolscount=0;
	public static int RP_Browsercount=0;
	public static String RP_str_starttime =null;
	public static String RP_str_endtime = null;
	public static Instant RP_starttime = null;
	public static Instant RP_Endtime =null;
	
	public static void main(String args []) throws Exception {
	
		MA_success = ( MA_total - (MA_failsintest+MA_failsinprod));
		MA_fails = MA_failsintest+MA_failsinprod;
		
		DA_passcount = DA_dgreen+DA_mgreen;
		DA_failcount = DA_dred+DA_damber+DA_mred+DA_mamber;
		
		int count=0;
		
		LinkedHashMap<String,Integer> BLmap = new LinkedHashMap<String,Integer>();
		LinkedHashMap<String,Integer> SCmap = new LinkedHashMap<String,Integer>();
		LinkedHashMap<String,Integer> MAmap = new LinkedHashMap<String,Integer>();
		LinkedHashMap<String,Integer> DAmap = new LinkedHashMap<String,Integer>();
		LinkedHashMap<String,Integer> PCmap = new LinkedHashMap<String,Integer>();
		
		if(!(BL_urls==0)){
			BLmap.put("Pass", BL_success);
			BLmap.put("Fail", BL_fails);
			BLmap.put("BL_urls", BL_urls);
			executionmap.put("BrokenLinks"+"~"+"Broken<br />Links", BLmap);
			count++;
		}else{
			System.out.println("Broken links not executed");
		}
		if(!(SC_urls==0)){
			SCmap.put("Pass", SC_success);
			SCmap.put("Fail", SC_fails);
			SCmap.put("SC_urls", SC_urls);
			executionmap.put("FindingSpecialCharacters"+"~"+"Finding Special Characters ", SCmap);
			count++;
		}else{
			System.out.println("Special Characters not executed");
		}
		if(!(MA_urls==0)){
			MAmap.put("Missing in Test"+"~"+"Pass", MA_failsintest);
			MAmap.put("Missing in Prod", MA_failsinprod);
			MAmap.put("MA_urls", MA_urls);
			executionmap.put("MissingAssets"+"~"+"Missing<br />Assets", MAmap);
			count++;
		}else{
			System.out.println("Missing Assets not executed");
		}
		if(!(DA_urls==0)){
			DAmap.put("Pass", DA_passcount);
			DAmap.put("Fail", DA_failcount);
			DAmap.put("DA_urls", DA_urls);
			executionmap.put("AssetSizes"+"~"+"Asset<br />Sizes", DAmap);
			count++;
		}else{
			System.out.println("Display asset sizes not executed");
		}
		if(!(PC_browserlist.isEmpty())){
			/*PCmap.put("PC_pass", PC_success);
			PCmap.put("PC_fail", PC_fails);
			PCmap.put("PC_urls", PC_urls);
			executionmap.put("PageComparison"+"~"+"Page Comparison", PCmap);*/
			count++;
		}else{
			System.out.println("Page Comparison not executed");
		}
		if(!(PageLoadTimeList.isEmpty())){
			count++;
		}else{
			System.out.println("Response Time not executed");
		}
		if((!(JourneyFlowsUrlsList.isEmpty())) || (!(HC_Mortgagesmap.isEmpty()))){
			count++;
		}else{
			System.out.println("HealthChecks not executed");
		}
		
		HTML_Reports.Global_HTMLReport(count,executionmap, str_starttime, Starttime, BL_html_result_filePath, BL_finalresult,BL_Filename, BL_str_starttime,
				BL_str_endtime,BL_starttime,BL_Summary, BL_finallistsize,BL_totalchildlinks,BL_fails,BL_redirect,BL_unauthorized,BL_forbidden,
				BL_badrequest,BL_pagenotfound,BL_server,BL_others,BL_parentURLfail, BL_success,BL_permanentredirect,BL_paymentrequired,BL_status200,BL_failparentchildmap,BL_Endtime,BL_serviceunavailable,
				SC_htmlfilename,SC_htmlmap,SC_Filename,SC_str_starttime,SC_str_endtime,SC_starttime,SC_Summary,SC_rowcount,SC_passvalue,SC_failvalue, SC_Endtime,
				DA_HTML_filePath,DA_Newmap,DA_Filename,DA_str_starttime,DA_str_endtime,DA_starttime,DA_Summary,DA_Endtime,
				MA_HTML_filePath,MA_htmlmap,MA_Filename,MA_str_starttime,MA_str_endtime,MA_starttime,MA_Summary,MA_Endtime,
				PC_HTMLReportPath, PC_HTMLReportName,PC_FinalMap,PC_Filename,PC_str_starttime,PC_str_endtime,PC_starttime,PC_Summary,PC_totalcount,PC_passcount,PC_failcount,
				PC_MissingMap,PC_exceptioncount,PC_missingcount, JourneyFlowsUrlsList, PageLoadTimeList,ResponseTimePass,ResponseTimefail,ResponseTimeurls, HC_Mortgagesmap, HC_str_starttime, HC_str_endtime, HC_starttime, HC_Endtime,
				PC_ContentFinalMap, PC_PassMap, PC_FailMap, PC_browsercount, PC_browserlist, PC_urls, PC_ScreenshotPath, RP_ToolsMap, RP_Brandslist, RP_Toolscount, RP_Browsercount,
				RP_NWToolscount, RP_RBSToolscount, RP_ULSTERToolscount, RP_NWIToolscount, RP_IOMToolscount, RP_str_starttime, RP_str_endtime, RP_starttime, RP_Endtime);
	}
	
	public static void clearvalues(){
		BL_urls=0;SC_urls=0;MA_urls=0;DA_urls=0;
		executionmap.clear(); str_starttime=null; Starttime =null; BL_html_result_filePath=null; BL_finalresult.clear(); BL_Filename=null; BL_str_starttime=null;
		BL_str_endtime=null;BL_starttime=null;BL_Summary=null;BL_finallistsize=0;BL_totalchildlinks=0;BL_fails=0;BL_redirect=0;BL_unauthorized=0;BL_forbidden=0;
		BL_badrequest=0;BL_pagenotfound=0;BL_server=0;BL_others=0;BL_parentURLfail=0;BL_success=0;BL_permanentredirect=0;BL_paymentrequired=0;BL_status200=0;BL_failparentchildmap.clear();BL_Endtime=null;BL_serviceunavailable=0;
		SC_htmlfilename=null;SC_htmlmap.clear();SC_Filename=null;SC_str_starttime=null;SC_str_endtime=null;SC_starttime=null;SC_Summary=null;SC_rowcount=0;SC_passvalue=0;SC_failvalue=0;SC_Endtime=null;SC_success=0;SC_fails=0;SC_urls=0;
		DA_HTML_filePath=null;DA_Newmap.clear();DA_Filename=null;DA_str_starttime=null;DA_str_endtime=null;DA_starttime=null;DA_Summary=null;DA_Endtime=null;
		MA_HTML_filePath=null;MA_htmlmap.clear();MA_Filename=null;MA_str_starttime=null;MA_str_endtime=null;MA_starttime=null;MA_Summary=null;MA_Endtime=null;
		PC_HTMLReportPath=null;PC_HTMLReportName=null;PC_FinalMap.clear();PC_Filename=null;PC_str_starttime=null;PC_str_endtime=null;PC_starttime=null;PC_Summary=null;PC_totalcount=0;PC_passcount=0;PC_failcount=0;
		PC_MissingMap.clear();PC_exceptioncount=0;PC_missingcount=0;JourneyFlowsUrlsList.clear();PageLoadTimeList.clear();HC_Mortgagesmap.clear();HC_str_starttime=null;HC_str_endtime=null;HC_starttime=null;HC_Endtime=null;
		PC_ContentFinalMap.clear();PC_PassMap.clear();PC_FailMap.clear();PC_browsercount=0;PC_browserlist.clear();PC_urls=0;PC_ScreenshotPath=null;RP_ToolsMap.clear();RP_Brandslist.clear();RP_Toolscount=0;RP_Browsercount=0;
		RP_NWToolscount=0;RP_RBSToolscount=0;RP_ULSTERToolscount=0;RP_NWIToolscount=0;RP_IOMToolscount=0;RP_str_starttime=null;RP_str_endtime=null;RP_starttime=null;RP_Endtime=null;
		ResponseTimefail=0;ResponseTimePass=0;ResponseTimeurls=0;
		System.out.println("Global Values Cleared");
	}
}
