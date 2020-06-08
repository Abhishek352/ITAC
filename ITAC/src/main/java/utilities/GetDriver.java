package utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class GetDriver {

	public static WebDriver driver;

	public static WebDriver getdriver(String BrowserName) throws Exception {

		Properties prop = new Properties();
		FileInputStream reader;
		reader = new FileInputStream(System.getProperty("user.dir")+"\\testConfig.properties");

		prop.load(reader);

		prop.getProperty("username");

		switch(BrowserName){

		case "Local_Chrome": 
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver_237.exe");
			driver = new ChromeDriver();
			break;

		case "Local_Firefox":
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;

		case "Local_IE": 
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\drivers\\IEDriverServer_32bit.exe");
			driver = new InternetExplorerDriver();
			break;

		case "Local_Edge": 
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+"\\drivers\\MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
			break;


		case "Win10_Firefox_66.0": 
			driver = browserstack(BrowserName);
			break;
		case "Win10_Chrome_73.0":
			driver = browserstack(BrowserName);
			break;
		case "Win10_MSEdge_17.0":
			driver = browserstack(BrowserName);
			break;
		case "Win10_IE11_11.0":
			driver = browserstack(BrowserName);
			break;	
		case "Win8.1_IE11_11.0":
			driver = browserstack(BrowserName);
			break;
		case "Win8.1_Chrome_70.0":
			driver = browserstack(BrowserName);
			break;
		case "Win7_IE11_11.0":
			driver = browserstack(BrowserName);
			break;
		case "Win7_IE10_10.0":
			driver = browserstack(BrowserName);
			break;
		case "Win7_Chrome_70.0":
			driver = browserstack(BrowserName);
			break;
		case "Win7_Firefox_63.0": 
			driver = browserstack(BrowserName);
			break;
		case "Win7_IE9_9.0":
			driver = browserstack(BrowserName);
			break;	
		case "Win7_IE8_8.0":
			driver = browserstack(BrowserName);
			break;	
		case "Mac Mojave_Safari_12.0":
			driver = browserstack(BrowserName);
			break;		
		case "iPhoneXS_12":
			driver = browserstack(BrowserName);
			break;
		case "GooglePixel_9.0":
			driver = browserstack(BrowserName);
			break;
		case "Emulator Browser-Android":
			driver = RealDeviceDriver.getdriver(BrowserName);
			break;
		case "Emulator App-Android":
			driver = RealDeviceDriver.getdriver(BrowserName);
			break;
		case "RealDevice Browser-Android":
			driver = RealDeviceDriver.getdriver(BrowserName);
			break;
		case "RealDevice App-Android":
			driver = RealDeviceDriver.getdriver(BrowserName);
			break;
		case "RealDevice Browser-iOS":
			driver = RealDeviceDriver.getdriver(BrowserName);
			break;
		case "RealDevice App-iOS":
			driver = RealDeviceDriver.getdriver(BrowserName);
			break;
		case "Emulator Browser-iOS":
			driver = RealDeviceDriver.getdriver(BrowserName);
			break;
		case "Emulator App-iOS":
			driver = RealDeviceDriver.getdriver(BrowserName);
			break;
		}
		
		if((BrowserName.contains("Real")) ||(BrowserName.contains("Emulator")) ||(BrowserName.contains("iPhone"))
				|| (BrowserName.contains("Google")) || (BrowserName.contains("Mac"))){
			
		}else{
			driver.manage().window().maximize();
		}
		return driver;

	}

	private static WebDriver browserstack(String BrowserName) throws Exception {

		String browser = BrowserName;
		String os = null;
		String os_version = null;
		String browsername = null;
		String browserversion = null;
		String device = null;

		if(browser.contains("Win10")) {
			os = "Windows";
			os_version = "10";
		}else if(browser.contains("Win7")) {
			os = "Windows";
			os_version = "7";
		}else if(browser.contains("Win8.1")) {
			os = "Windows";
			os_version = "8.1";
		}else if(browser.contains("Mac") && browser.contains("Safari")) {
			os = "OS X";
			os_version = "Mojave";
			browsername = "Safari";
			browserversion = "12.0";
		}else if(browser.contains("Mac") && browser.contains("Chrome")) {
			os = "OS X";
			os_version = "High Sierra";
			browsername = "Chrome";
			browserversion = "11.0";
		}

		if(browser.contains("Chrome")) {
			browsername = "Chrome";
			browserversion = "73.0";
		}else if(browser.contains("Firefox")) {
			browsername = "Firefox";
			browserversion = "66.0";
		}else if(browser.contains("IE10")) {
			browsername = "IE";
			browserversion = "10.0";
		}else if(browser.contains("IE11")) {
			browsername = "IE";
			browserversion = "11.0";
		}else if(browser.contains("MSEdge")) {
			browsername = "Edge";
			browserversion = "17.0";
		}

		if(browser.contains("iPhone")){
			os_version = "12";
			device = "iPhone XS";
		}else if(browser.contains("Google")){
			os_version = "9.0";
			device = "Google Pixel 3 XL";
		}

		try {

			Properties prop = new Properties();
			FileInputStream reader;
			reader = new FileInputStream(System.getProperty("user.dir")+"\\testConfig.properties");

			prop.load(reader);
			String USERNAME =	prop.getProperty("BrowserStack-Username");
			String AUTOMATE_KEY = prop.getProperty("BrowserStack-Password");

			String cmd = System.getProperty("user.dir")+"\\drivers\\BrowserStackLocal.exe --key "+ AUTOMATE_KEY +" --local-identifier Test123"; // --local-identifier Test123

			//System.out.println(cmd);

			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(cmd);
			String line;
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			System.out.println("Establishing connection with BrowserStack for: "+BrowserName);

			while ((line = input.readLine()) != null ) {
				Thread.sleep(5000);
				//	System.out.println(line);
				if(line.contains("You can now access")) {
				
					System.out.println("Connection to BrowsrStack is established successfully");
					break;
			
				}else {
					
					System.out.println("Couldnt establish connection to BrowserStack, let's wait for 2 more seconds and try again for : "+BrowserName);
			
				}
			
			}
			
			input.close();

			//	String USERNAME = "kalyankorrapati2";
			//	String AUTOMATE_KEY = "nkpF2aA96s6hWVFKiepw";
			//	String USERNAME = "mohamedibrahim22";
			//	String AUTOMATE_KEY = "5fhGwyAERRwEGj3gdWGx";
			String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
			//System.out.println(URL);

			DesiredCapabilities caps = new DesiredCapabilities();

			if(browser.contains("iPhone") || browser.contains("Google")){

				System.out.println("Device: " + device);
				System.out.println("OS Version: " + os_version);

				caps.setCapability("os_version", os_version);
				caps.setCapability("device", device);
				caps.setCapability("real_mobile", "true");
				caps.setCapability("name", "iPhone7");
				caps.setCapability("browserstack.debug", "true");
				caps.setCapability("browserstack.networkLogs", "true");
				caps.setCapability("browserstack.local", "true");
				caps.setCapability("browserstack.localIdentifier", "Test123");
				caps.setCapability("resolution", "1600x1200");
				
			}else{
				
				System.out.println("OS: " + os);
				System.out.println("OS Version: " + os_version);
				System.out.println("Browser: " + browsername);
				System.out.println("Browser Version: " + browserversion);

				caps.setCapability("os", os);
				caps.setCapability("os_version", os_version);
				caps.setCapability("browser", browsername);
				caps.setCapability("browser_version", browserversion);
				caps.setCapability("resolution", "1024x768");
				caps.setCapability("name", "FullPageSS");
				caps.setCapability("browserstack.debug", "true");
				caps.setCapability("browserstack.networkLogs", "true");
				caps.setCapability("browserstack.local", "true");
				caps.setCapability("browserstack.localIdentifier", "Test123");
				caps.setCapability("acceptSslCerts", "true");
				
			}

			driver = new RemoteWebDriver(new URL(URL), caps);
			System.out.println("Able to launch remote driver " + BrowserName);

		} catch (Exception e) {

			System.out.println("Some exception has occured, trying to connect to BrowserStack again for: "+BrowserName);
			browserstack(browser);

		}

		return driver;
		
	}

}