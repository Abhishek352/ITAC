package global_suite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.testng.TestNG;

import site_monitoring.Globalvariables;
import utilities.CreatingCSV;

public class XmlGenerate {

	public static LinkedList<String> tests = new LinkedList<>();
	public static String xmlFileName = "testngRunner.xml";
	public static String xmlfilepath = System.getProperty("user.dir")+"\\"+xmlFileName;
	public static String browser;
	public static String str_starttime =null;
	public static Instant Starttime =null;

	public static void generateXml() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		str_starttime = sdf.format(dt);
		Starttime = Instant.now();
		
		Globalvariables.str_starttime = str_starttime;
		Globalvariables.Starttime = Starttime;
		
		Properties prop = new Properties();
		FileInputStream input = new FileInputStream(System.getProperty("user.dir")+"\\testConfig.properties");
		
		prop.load(input);

		String[] Task = prop.getProperty("TasksSelected").split(",");
		List<String> tasklist = Arrays.asList(Task);
		
		tests.clear();
		
		for(int i=0;i<tasklist.size();i++){
			
			//System.out.println(tasklist.get(i));
			switch (tasklist.get(i)){

			case "BrokenLinks":
				tests.add("site_monitoring.BrokenLinks");
				break;
			case "FindingMissingAssets":
				tests.add("site_monitoring.FindingMissingAssets");
				break;
			case "PageComparison":
				tests.add("site_monitoring.PageComparison");
				break;
			case "FindingSpecialCharacters":
				tests.add("site_monitoring.FindingSpecialCharacters");
				break;
			case "GetAssetSize":
				tests.add("site_monitoring.DisplayAssetSizeDriver");
				break;
			case "ResponseTime":
				tests.add("site_monitoring.ResponseTime");
				break;
			case "SampleTest":
				tests.add("global_suite.SampleTest");
				break;
			}
		}

		System.out.println("Started with Test Execution");
		CreatingCSV.convertExcelToCsv();
		generateXML(tests,xmlFileName);  // generate xml
		String xml = System.getProperty("user.dir")+"\\"+xmlFileName;
		try {
			TriggerTestNGxml(xml);    // trigger xml 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void TriggerTestNGxml(String xmlp) {
		System.out.println("\n***Test Runner Started***");
		try {
			TestNG runner = new TestNG();
			List<String> suitefiles = new ArrayList<String>();
			suitefiles.add(xmlp);
			runner.setTestSuites(suitefiles);
			runner.run();
		} catch (Exception e) {
		}
		System.out.println("***Test Runner End***");
	}

	private static void generateXML(LinkedList<String> tests, String fileName) {
		try {
			//System.out.println("Generating testNG xml started...");
			File file = new File(xmlfilepath);
			StringBuilder htmlStringBuilder=new StringBuilder();
			htmlStringBuilder.append("<?xml version='1.0' encoding='UTF-8'?>\n");
			htmlStringBuilder.append("<!DOCTYPE suite SYSTEM 'http://testng.org/testng-1.0.dtd'>\n");
			htmlStringBuilder.append("<suite name='Suite' thread-count='8' parallel='true'>\n");
			htmlStringBuilder.append("<test name='Cybernation'>");
			htmlStringBuilder.append("<parameter name='browser' value=\""+browser +"\"></parameter>");
			htmlStringBuilder.append("<classes>\n");
			for(String s : tests) {
				htmlStringBuilder.append("<class name =\""+s +"\">");
				htmlStringBuilder.append("</class>\n");
			}

			htmlStringBuilder.append("</classes></test></suite>");
			OutputStream outputStream = new FileOutputStream(file);
			Writer writer = new OutputStreamWriter(outputStream);
			writer.write(htmlStringBuilder.toString());
			writer.close();
			//System.out.println("testNGxml path : " +file);
			//System.out.println("testNG xml ready to trigger...");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
