package site_monitoring;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.library.Logger;

public class PageComparison {

	private static final int MYTHREADS = 10;
	public static ArrayList<String> browserslist = new ArrayList<String>();
	public static String filePath_properties = System.getProperty("user.dir")+"\\testConfig.properties";
	public static String str_starttime = null;
	public static String browserList;
	public static Instant starttime = Instant.now();

	@BeforeClass
	public void beforeClass() throws IOException {
		System.out.println("********* Image Comparison Execution Started in new Thread ********* ");
		System.out.println("Execution Thread ID = " +Thread.currentThread().getId());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt = new Date();
		String date = sdf.format(dt);

		System.out.println("==============================================================================================");
		System.out.println("Test Execution for Page Comparison Started At : "+date);
		System.out.println("==============================================================================================");

		Date dtt = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String timestamp = df.format(dtt);

		SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Comparison.str_starttime = df1.format(dtt);

		String filePathOflogger = System.getProperty("user.dir")+"\\Results\\Logs\\";
		String fileNameOflogger = "PageComparison"+timestamp+".log";
		Logger.initializeLogger(filePathOflogger, fileNameOflogger);
	}

	@Test
	public void ImageComparison() throws Exception {


		File fileprop = new File(filePath_properties);
		FileReader fr = new FileReader(fileprop);
		Properties prop = new Properties();
		prop.load(fr);

		String browserExe = prop.getProperty("PageComparison-Browser");
		System.out.println("img_browser = " +browserExe);

		if(browserExe.equalsIgnoreCase("Local Browser")) {
			browserList = prop.getProperty("PageComparison-Browser-Local");
		}else if(browserExe.contains("Stack")){
			browserList = prop.getProperty("PageComparison-BrowserStack");
		}else if(browserExe.contains("Emulator")) {
			browserList = prop.getProperty("PageComparison-Emulator");
		}else {
			browserList = prop.getProperty("PageComparison-Real-Device");
		}
		String[] browsername = browserList.split(",");
		int browsercount = browsername.toString().length();
		Globalvariables.PC_browsercount = browsercount;
		
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);

		try {
			for (String currentbrowser: browsername) {
				Runnable worker = new Comparison(currentbrowser);
				executor.execute(worker);
			}
			executor.shutdown();
			// Wait until all threads are finish
			while (!executor.isTerminated()) {

			}
			System.out.println("\nFinished all threads");

		}catch (Exception e) {

		}
		
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
		System.out.println("Test Execution for Page Comparison Completion At : "+enddate);
		System.out.println("Total Execution Time : " +diff.toMinutes() +" minutes");
		System.out.println("==============================================================================================");
		Logger.logTestInfo("************* Completed Test Execution *************");
	}


}