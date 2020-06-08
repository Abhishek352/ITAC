package global_suite;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.library.Logger;

import site_monitoring.Globalvariables;
import utilities.GetDriver;

import io.appium.java_client.AppiumDriver;

import io.appium.java_client.MobileElement;

import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.ios.IOSDriver;

import io.appium.java_client.remote.AndroidMobileCapabilityType;

import io.appium.java_client.remote.AutomationName;

import io.appium.java_client.remote.IOSMobileCapabilityType;

import io.appium.java_client.remote.MobileCapabilityType;

import io.appium.java_client.remote.MobilePlatform;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class SampleTest {

	static WebDriver driver;
	public static Instant Starttime =null;
	public static String str_starttime =null;
	public static String str_endtime = null;
	public static Instant Endtime =null;


	@BeforeClass
	public void beforeMethod() {
		System.out.println("********* Sample Test Execution Started ********* ");
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
	}

	@Test
	public static void HealthCheck() throws Exception{

		SampleTest();

	}

	private static void SampleTest() throws Exception {
		try {
			Properties prop = new Properties();
			FileInputStream reader;
			reader = new FileInputStream(System.getProperty("user.dir")+"\\testConfig.properties");

			prop.load(reader);

			String[] browser = prop.getProperty("P2-RegressionPack-Browser-Local").split(",");
			List<String> browserlist = Arrays.asList(browser);

			String Browsername = prop.getProperty("P2-RegressionPack-Browser-Local");

			WebDriver driver;
			for(int i=0;i<browserlist.size();i++){
				String browsername = browserlist.get(i);
				driver = GetDriver.getdriver(browsername);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				if(browsername.contains("browser")) {
					driver.get("https://personal.natwest.com");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

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
