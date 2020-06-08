package site_monitoring;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.library.HTML_Reports;
import com.library.UtilClass;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import utilities.GetDriver;

public class Comparison implements Runnable {
	//public static WebDriver driver = null;
	public static String input_csv_filePath_Prod = System.getProperty("user.dir")+"\\InputData\\csvdata\\pagecomparison_Prod.csv";
	public static String input_csv_filePath_Test = System.getProperty("user.dir")+"\\InputData\\csvdata\\pagecomparison_Test.csv";
	private final String currentbrowser;
	public static Instant starttime = Instant.now();
	public static Instant Starttime =null;
	public static String str_starttime = null;
	public static LinkedHashMap<String, LinkedHashMap<String, String>> FinalMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, LinkedHashMap<String, String>> MissingMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, String> Shelvesnotfound = new LinkedHashMap<>();
	static Response response;
	public static LinkedHashMap<String, Integer> PasscountMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, Integer> FailcountMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, Integer> ExceptioncountMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> ContentFinalMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, Integer> PassMap = new LinkedHashMap<>();
	public static LinkedHashMap<String, Integer> FailMap = new LinkedHashMap<>();
	public static String pagelements_prod = null;
	public static String pagelements_test = null;

	Comparison(String currentbrowser) {
		this.currentbrowser = currentbrowser;
	}
	@Override
	public void run() {
		try {

			BufferedImage expectedImage;
			BufferedImage actualImage;
			int totalshelves = 0;
			int missingcount = 0;
			int exceptioncount = 0;
			String SS_Status = null;

			Date dtt = new Date();
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			str_starttime = df1.format(dtt);

			System.out.println("---------------- Execution started for " + currentbrowser + " ---------------------");
			ContentFinalMap.clear();

			//******** Reading the Prod and Test URL's and adding them to list *************
			UtilClass u = new UtilClass();
			List<String> ProdURL_list = u.readFromCsv(input_csv_filePath_Prod); // read
			System.out.println( currentbrowser + " - Total no of Prod URL's to execute = " +(ProdURL_list.size()-1));
			List<String> TestURL_list = u.readFromCsv(input_csv_filePath_Test);  // read
			System.out.println(currentbrowser + "- Total no of Test URL's provided: " + (TestURL_list.size() -1));

			//************ Capturing the System time and creating Base folder path for HTML Results ***********
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
			String timestamp = sdf1.format(new Date());
			String HTMLResultBasePath = System.getProperty("user.dir")+"\\Results\\HTML_results\\";
			String HTMLReportPath = HTMLResultBasePath + "Page_Comparison_Results" + "\\" + timestamp + "\\";
			File F = new File(HTMLReportPath);
			F.mkdirs();

			// ************ Creating base folder for screenshots? *****************//
			String Ss_basepath = System.getProperty("user.dir")+ "\\Screenshots\\";
			String Ss_Filepath = "Comparison";
			String FinalPath = Ss_basepath + Ss_Filepath;

			//**************************** Creating folders for reports and Screen Shots ****************************//

			Globalvariables.PC_browserlist.add(currentbrowser);
			System.out.println("Creating folder path for " + currentbrowser + " browser");
			String browserpath = FinalPath + "\\" + currentbrowser + "\\" + timestamp;
			File file1 = new File(browserpath);
			file1.mkdirs();
			String Reports_Screenshotspath = file1.getAbsolutePath();
			System.out.println("Screenshots Path:  "+Reports_Screenshotspath);
			Globalvariables.PC_ScreenshotPath = 	Reports_Screenshotspath;

			//********** Creating HTML file using Browser name **************
			String htmlfilename = null;
			if(currentbrowser.contains(".")){
				htmlfilename = currentbrowser.replace(".", "");
			}else {
				htmlfilename = currentbrowser;
			}
			String HTMLReportName = htmlfilename+".html";

			// Declaring pass and fail count - before starting with execution
			int passcount = 0;
			int failcount = 0;


			try {

				for(int i=1;i<ProdURL_list.size();i++) {

					try{

						String Produrl = ProdURL_list.get(i).toString();
						String Testurl = TestURL_list.get(i).toString();

						int ProdStatus = GetStatus(Produrl);
						int TestStatus = GetStatus(Testurl);

						if((ProdStatus == 200 || ProdStatus == 301 || ProdStatus == 302) 
								&& (TestStatus == 200 || TestStatus == 301 || TestStatus == 302)) {

							Globalvariables.PC_urls++;
							WebDriver driver;
							System.out.println("Creating folder for current browser and Page");
							String exactpath = browserpath + "\\" + "URL_" + i;
							File file2 = new File(exactpath);
							file2.mkdirs();
							String ScreenShotsPath = file2.getAbsolutePath();
							LinkedHashMap <String,String> ResultMapping = new LinkedHashMap<>();
							try {
								//Prod URL
								driver = GetDriver.getdriver(currentbrowser);
								driver.get(Produrl);
								if(currentbrowser.contains("Emulator") || currentbrowser.contains("Real")) {
									String ProdScreenshotpath = mobilegetScreenshot(ScreenShotsPath, "ProdURL"+i, driver);
									System.out.println(ProdScreenshotpath);
									
								}else {
									String ProdScreenshotpath = getScreenshot(ScreenShotsPath, "ProdURL"+i, driver);
									System.out.println(ProdScreenshotpath); 
									driver.quit();
									driver = GetDriver.getdriver(currentbrowser);
								}
								//Test URL
								
								driver.get(Testurl);
								if(currentbrowser.contains("Emulator") || currentbrowser.contains("Real")) {
									String TestScreenshotpath = mobilegetScreenshot(ScreenShotsPath, "TestURL"+i, driver);
									System.out.println(TestScreenshotpath); 
								}else {
									String TestScreenshotpath = getScreenshot(ScreenShotsPath, "TestURL"+i, driver);
									System.out.println(TestScreenshotpath); 
									driver.quit();
								}
								String status =	compareimagess(ScreenShotsPath, currentbrowser,i); 
								ResultMapping.put("URL"+ "_"+ i, status+"~"+ScreenShotsPath);
								FinalMap.put(currentbrowser+"!"+"ParentURL_"+i+":"+Produrl, ResultMapping);
								if(status.contains("Pass")) {
									passcount++;
								}else {
									failcount++;
								}
							}
							catch (Exception e) {
								System.out.println(e.getMessage());
							}

						}		
					}catch(Exception eee){
						System.out.println("Exception in URL launching");
					}

				}

				SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				Date dt1 = new Date();
				String str_endtime = sdf2.format(dt1);

				System.out.println("Total Pass Count: " + passcount);
				System.out.println("Total Fail Count: " + failcount);
				System.out.println(" ---------------------------------------------------------------------------------------------------------");
				System.out.println(" -------------------------------------------------------------------------------------------------------------------------");

				int total = passcount+failcount;

				Globalvariables.PC_HTMLReportPath = HTMLReportPath;
				Globalvariables.PC_HTMLReportName = HTMLReportName;
				Globalvariables.PC_FinalMap =FinalMap;
				Globalvariables.PC_passcount =passcount;
				Globalvariables.PC_failcount =failcount;
				Globalvariables.PC_Filename = "Page Comparison";
				Globalvariables.PC_str_starttime = (str_starttime);
				Globalvariables.PC_str_endtime = str_endtime;
				Globalvariables.PC_starttime = starttime;
				Globalvariables.PC_Summary = "Page Comparison";
				Globalvariables.PC_totalcount = total;
				Globalvariables.PC_MissingMap = MissingMap;
				Globalvariables.PC_exceptioncount = exceptioncount;
				Globalvariables.PC_missingcount = missingcount;

				PasscountMap.put("Pass"+currentbrowser, passcount);
				FailcountMap.put("Fail"+currentbrowser, failcount);
				ExceptioncountMap.put("Exception"+currentbrowser, exceptioncount);

				Globalvariables.PC_ContentFinalMap.put(currentbrowser, FinalMap);
				ContentFinalMap.put(currentbrowser, FinalMap);
				Globalvariables.PC_PassMap.put(currentbrowser, passcount);
				Globalvariables.PC_FailMap.put(currentbrowser,failcount);
				//Globalvariables.PC_ExceptionMap.put(currentbrowser, ExceptioncountMap);
				//Globalvariables.PC_ContentMissingMap.put(currentbrowser, MissingMap);

				HTML_Reports.PageComparison_HTMLReport(HTMLReportPath, HTMLReportName,FinalMap,"Page Comparison",str_starttime,str_endtime,starttime,"Page Comparison",total,passcount,failcount,
						MissingMap,exceptioncount,missingcount,ContentFinalMap, PassMap, FailMap, Globalvariables.PC_browsercount, Globalvariables.PC_browserlist, Globalvariables.PC_urls);

				totalshelves = 0;
				exceptioncount = 0;
				missingcount = 0;

			}catch (Exception ee) {
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				Date dt1 = new Date();
				String str_endtime = sdf2.format(dt1);
				System.out.println("*******************Exception in Main thread********************");

				int total = passcount+failcount;

				Globalvariables.PC_HTMLReportPath = HTMLReportPath;
				Globalvariables.PC_HTMLReportName = HTMLReportName;
				Globalvariables.PC_FinalMap =FinalMap;
				Globalvariables.PC_Filename = "Page Comparison";
				Globalvariables.PC_str_starttime = str_starttime;
				Globalvariables.PC_str_endtime = str_endtime;
				Globalvariables.PC_starttime = starttime;
				Globalvariables.PC_Summary = "Page Comparison";
				Globalvariables.PC_totalcount = total;
				Globalvariables.PC_MissingMap = MissingMap;
				Globalvariables.PC_exceptioncount = exceptioncount;
				Globalvariables.PC_missingcount = missingcount;

				PasscountMap.put("Pass"+currentbrowser, passcount);
				FailcountMap.put("Fail"+currentbrowser, failcount);
				ExceptioncountMap.put("Exception"+currentbrowser, exceptioncount);

				Globalvariables.PC_ContentFinalMap.put(currentbrowser, FinalMap);
				Globalvariables.PC_PassMap.put(currentbrowser, passcount);
				Globalvariables.PC_FailMap.put(currentbrowser,failcount);

				HTML_Reports.PageComparison_HTMLReport(HTMLReportPath, HTMLReportName,FinalMap,"Page Comparison",str_starttime,str_endtime,starttime,"Page Comparison",total,passcount,failcount,
						MissingMap,exceptioncount,missingcount,ContentFinalMap, PassMap, FailMap, Globalvariables.PC_browsercount, Globalvariables.PC_browserlist, Globalvariables.PC_urls);
			}
		}
		catch(Exception e) {
			System.out.println("Exception in the main run");
		}

	}


	@SuppressWarnings("null")


	private static int GetStatus (String url) throws Exception {

		int Status = 0;
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



	public static String CaptureScreenShot(WebDriver driver, String screenShotsPath, String ScreenShotName, int Urlno) throws Exception {

		String status = "No";
		try {

			//Thread.sleep(1000);
			Screenshot ScreenShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
			ImageIO.write(ScreenShot.getImage(), "PNG", new File(screenShotsPath + "\\" + ScreenShotName));
			System.out.println("Captured screenshot for page");
			status = "Yes";

		}catch (Exception e) {

			e.printStackTrace();
			status = "Yes";

		}

		return status;

	}

	public String IE_ScreenShots (WebDriver driver, String ObjectIdentifier, String screenShotsPath, String ScreenShotName, int Urlno, String ImplementationType) throws Exception{

		String status = "No";
		try {
			/*TakesScreenshot scrShot =((TakesScreenshot)driver.findElement(By.cssSelector(ObjectIdentifier)));
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile=new File(screenShotsPath+"\\"+ScreenShotName+".png");
			FileUtils.copyFile(SrcFile, DestFile);*/

			/*WebElement element = driver.findElement(By.cssSelector(ObjectIdentifier));
			Thread.sleep(2000);
			Screenshot ScreenShot = new AShot().takeScreenshot(driver, element);

			ImageIO.write(ScreenShot.getImage(), "PNG", new File(screenShotsPath + "\\" + ScreenShotName));*/

			WebElement element = driver.findElement(By.cssSelector(ObjectIdentifier));
			Thread.sleep(2000);

			// Get the location of element on the page
			Point point = element.getLocation();

			// Get width and height of the element
			int eleWidth = element.getSize().getWidth();
			int eleHeight = element.getSize().getHeight();

			// Get entire page screenshot
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			BufferedImage  fullImg = ImageIO.read(screenshot);


			// Crop the entire page screenshot to get only element screenshot

			BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
					eleWidth, eleHeight);
			ImageIO.write(eleScreenshot, "png", screenshot);

			// Copy the element screenshot to disk
			File screenshotLocation = new File(screenShotsPath + "\\" + ScreenShotName);
			FileUtils.copyFile(screenshot, screenshotLocation);
			System.out.println("Captured screenshot for Shelf: " + ObjectIdentifier);
			status = "Yes";

		}catch (Exception e) {
			System.out.println("Heyyy unable to capture the screenshot, as the page is not loaded properly. ");
			//exceptioncount++;
			status = "No";
		}

		return status;
	}

	public static String CaptureScreenShotXpath(WebDriver driver,String ObjectIdentifier, String screenShotsPath, String ScreenShotName, int Urlno, String ImplementationType) throws Exception {

		String status = "No";
		try {
			Thread.sleep(2000);
			WebElement element = driver.findElement(By.xpath(ObjectIdentifier));
			Screenshot ScreenShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1500)).takeScreenshot(driver, element);

			ImageIO.write(ScreenShot.getImage(), "PNG", new File(screenShotsPath + "\\" + ScreenShotName));
			System.out.println("Captured screenshot for Shelf: " + ObjectIdentifier);
			status = "Yes";

		}catch (Exception e) {
			System.out.println("Could'nt capture screenshot for Shelf: " + ObjectIdentifier);
			//exceptioncount++;
			status = "No";
		}
		return status;
	}

	public static String mobilegetScreenshot(String FolderPath, String ScreenshotName,WebDriver driver) throws IOException {

		String screenshotpath = FolderPath + "\\" + ScreenshotName + ".png";
		JavascriptExecutor js =  (JavascriptExecutor) driver;
		Float value = 1.5f;
		try {
		String ratio = (js.executeScript("return window.devicePixelRatio;")).toString();
		value = Float.parseFloat(ratio+"f");
		}catch(Exception e) {
			System.out.println("Unable to get the device pixel ratio");
		}
		Screenshot Expected = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(value),100)).takeScreenshot(driver);
		//	Screenshot Expected = new AShot().takeScreenshot(driver);
		ImageIO.write(Expected.getImage(), "PNG", new File(FolderPath + "\\" + ScreenshotName + ".png"));
		System.out.println("Mobile screen Captured");
		return screenshotpath;

	}

	public static String getScreenshot(String FolderPath, String ScreenshotName,WebDriver driver) throws IOException {

		String screenshotpath = FolderPath + "\\" + ScreenshotName + ".png";
		JavascriptExecutor js =  (JavascriptExecutor) driver;
		Float value = 1.5f;
		try {
		String ratio = (js.executeScript("return window.devicePixelRatio;")).toString();
		value = Float.parseFloat(ratio+"f");
		}catch(Exception e) {
			System.out.println("Unable to get the device pixel ratio");
		}
		Screenshot Expected = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(value), 1000)).takeScreenshot(driver);
		//	Screenshot Expected = new AShot().takeScreenshot(driver);
		ImageIO.write(Expected.getImage(), "PNG", new File(FolderPath + "\\" + ScreenshotName + ".png"));
		System.out.println("Captured");
		return screenshotpath;

	}


	public static String compareimagess(String Screenshotpath, String Browsername, int i) {

		String Status = null;
		try {
			ImageDiffer imgDiff = new ImageDiffer();

			BufferedImage actualImage = ImageIO.read(new File(Screenshotpath+"\\TestURL"+i+".png"));
			BufferedImage expectedImage = ImageIO.read(new File(Screenshotpath+"\\ProdURL"+i+".png"));

			ImageDiff diff = null;
			boolean result = true;

			try{
				System.out.println("Getting difference");
				diff = imgDiff.makeDiff(actualImage , expectedImage);
				result = diff.hasDiff();
			}catch(Exception eee){
				System.out.println("Unable to get the differences in images");
			}
			// If Result value is True - Then there is difference between images

			if(result) { 
				Status = "Fail";
				String diffname = "Diff-"+Browsername;
				System.out.println("Generating Difference img");
				ImageIO.write(diff.getMarkedImage(), "PNG", new File(Screenshotpath +"\\" + diffname + i+".png")); 
				System.out.println("Difference img generated");
				System.out.println(Status);
			}else { 
				Status = "Pass";
				System.out.println(Status); 
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return Status;

	}


}
