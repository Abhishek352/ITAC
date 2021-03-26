package utilities;

import java.io.BufferedReader;
import java.io.File;
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
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class RealDeviceDriver {

	public static WebDriver driver;

	public static WebDriver getdriver(String Device) throws Exception {
		
		Properties prop = new Properties();
		FileInputStream reader;
		reader = new FileInputStream(System.getProperty("user.dir")+"\\testConfig.properties");

		prop.load(reader);
		
		String DeviceName = prop.getProperty("Device Name");
		String PlatformVersion = prop.getProperty("Platform Version");
		String DriverPath = prop.getProperty("Driver Path");
		String AppName = prop.getProperty("App Name");
		
		String localHub = "http://127.0.0.1:4723/wd/hub";
		
		DesiredCapabilities caps = new DesiredCapabilities();
		
		if(Device.contains("Emulator Browser-Android")) {
			
			
			//caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 3 XL API 29");//P
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);
	        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
	        //caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");//P
	        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, PlatformVersion);//P
	        caps.setCapability("noReset", true);
	        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
	        caps.setCapability(AndroidMobileCapabilityType.BROWSER_NAME,"Chrome");
			caps.setCapability(AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT,true);
			caps.setCapability("autoGrantPermissions",true);
			caps.setCapability("autoAcceptAlerts", true);
			caps.setCapability("unicodeKeyboard", true);
			caps.setCapability("resetKeyboard", true);
			caps.setCapability("gpsEnabled", true);
			caps.setCapability("locationServicesAuthorized", true);
			caps.setCapability("permissions", "{\"io.cloudgrey.the-app\": {\"location\": \"inuse\"}}");
			caps.setCapability(AndroidMobileCapabilityType.SUPPORTS_LOCATION_CONTEXT,true);
			caps.setCapability(AndroidMobileCapabilityType.SUPPORTS_ALERTS,true);
			caps.setCapability(AndroidMobileCapabilityType.ACCEPT_SSL_CERTS,true);
			caps.setCapability(AndroidMobileCapabilityType.SUPPORTS_JAVASCRIPT,true);
			caps.setCapability(AndroidMobileCapabilityType.SUPPORTS_LOCATION_CONTEXT,false);
			caps.setCapability(AndroidMobileCapabilityType.GPS_ENABLED,false);
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("w3c", false);
			caps.merge(chromeOptions);
			
			System.out.println("Emulator browser initiated");
			
		}else if(Device.equalsIgnoreCase("Emulator App-Android")) {
			
			File apkPath = new File(System.getProperty("user.dir")+"\\ApkFiles");
		//	File apkFile = new File(apkPath, "HelloWorld.apk");//P
			File apkFile = new File(apkPath, AppName);//P
		//	caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 3 XL API 29");//P
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);//P
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
		//	caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");//P
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, PlatformVersion);//P
			//caps.setCapability("noReset", true);
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			caps.setCapability("autoGrantPermissions",false);
			caps.setCapability("autoAcceptAlerts", true);
			caps.setCapability(MobileCapabilityType.APP,apkFile.getAbsolutePath());
			caps.setCapability("unicodeKeyboard", true);
			caps.setCapability("resetKeyboard", true);
			caps.setCapability("gpsEnabled", false);

			System.out.println("Emulator app initiated");
			
		}else if(Device.equalsIgnoreCase("RealDevice Browser-Android")){

			try {
				File devicepath = new File(System.getProperty("user.dir")+"\\RealDevice_ChromeDriver\\chromedriver.exe");
				File actualdriverpath = new File("C:\\Users\\s.abhishek\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\node_modules\\appium-chromedriver\\chromedriver\\win\\chromedriver.exe");
																		//P
				FileUtils.copyFile(devicepath, actualdriverpath);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			//caps.setCapability(MobileCapabilityType.DEVICE_NAME, "gy85a6ssib4ssohy");//P
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);//P
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
			caps.setCapability("appPackage", "com.android.chrome");
			caps.setCapability("appActivity", "com.google.android.apps.chrome.Main");
			
			System.out.println("Real device browser initiated");

		}else if(Device.equalsIgnoreCase("RealDevice App-Android")){
			
			File apkPath = new File(System.getProperty("user.dir")+"\\ApkFiles");
			//File apkFile = new File(apkPath, "HelloWorld.apk");//P
			File apkFile = new File(apkPath, AppName);//P
		//	caps.setCapability(MobileCapabilityType.DEVICE_NAME, "gy85a6ssib4ssohy");//P
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);//P
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			caps.setCapability("autoGrantPermissions",false);
			caps.setCapability("autoAcceptAlerts", true);
			caps.setCapability(MobileCapabilityType.APP,apkFile.getAbsolutePath());
			caps.setCapability("unicodeKeyboard", true);
			caps.setCapability("resetKeyboard", true);
			caps.setCapability("gpsEnabled", false);
			
			System.out.println("Real device app initiated");
			
		}else if(Device.contains("Emulator Browser-iOS")) {
			caps = new DesiredCapabilities();
			caps.setCapability(MobileCapabilityType.APPIUM_VERSION,"v1.15.0");
			caps.setCapability(CapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
			//caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8");//P
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);//P
			//caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.1");//P
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, PlatformVersion);//P
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
			caps.setCapability(IOSMobileCapabilityType.BROWSER_NAME, "Safari");
			caps.setCapability(IOSMobileCapabilityType.SAFARI_ALLOW_POPUPS, true);
			caps.setCapability("w3c",false);
			caps.setCapability(IOSMobileCapabilityType.SAFARI_ALLOW_POPUPS,true);
			caps.setCapability("autoAcceptAlerts", true);
			caps.setCapability("locationServicesEnabled", true);
			caps.setCapability("locationServicesAuthorized",true);
			caps.setCapability("autoAcceptAlerts", true);
			caps.setCapability("locationServicesEnabled", true);
			caps.setCapability("locationServicesAuthorized", true);

			System.out.println("Emulator browser initiated");

		}else if(Device.equalsIgnoreCase("Emulator App-iOS")) {
			File fss = new File(System.getProperty("user.dir")+"\\ApkFiles");
			File fins = new File(fss, AppName);//P
			caps.setCapability(CapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
			//caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8");
			//caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11 Pro");//P
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);//P
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.1");//P
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, PlatformVersion);//P
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
			caps.setCapability(MobileCapabilityType.APP, fins.getAbsolutePath());
			caps.setCapability(IOSMobileCapabilityType.SAFARI_ALLOW_POPUPS,true);
			//caps.setCapability("autoAcceptAlerts", true);
			caps.setCapability("locationServicesEnabled", true);
			caps.setCapability("locationServicesAuthorized",true);

			System.out.println("iOS Emulator app initiated");

		}else if(Device.equalsIgnoreCase("RealDevice Browser-iOS")){

			try {
				File devicepath = new File(System.getProperty("user.dir")+"\\RealDevice_ChromeDriver\\chromedriver.exe");
				File actualdriverpath = new File("C:\\Users\\s.abhishek\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\node_modules\\appium-chromedriver\\chromedriver\\win\\chromedriver.exe");
																		//P
				FileUtils.copyFile(devicepath, actualdriverpath);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);//P
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
			caps.setCapability(IOSMobileCapabilityType.BROWSER_NAME, "Safari");
			
			System.out.println("Real device browser initiated");

		}else if(Device.equalsIgnoreCase("RealDevice App-iOS")){
			
			File fins = new File(System.getProperty("user.dir")+"\\ApkFiles");
			File fss = new File(fins, "HelloWorld.apk");//P
			caps.setCapability(CapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
			//caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8");
			//caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11 Pro");//P
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);//P
			//caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.1");//P
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, PlatformVersion);//P
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
			caps.setCapability(MobileCapabilityType.APP, fins.getAbsolutePath());
			caps.setCapability(IOSMobileCapabilityType.SAFARI_ALLOW_POPUPS,true);
			//caps.setCapability("autoAcceptAlerts", true);
			caps.setCapability("locationServicesEnabled", true);
			caps.setCapability("locationServicesAuthorized",true);
			
			System.out.println("Real device app initiated");
			
		}

		else {
			
		//	caps.setCapability(AndroidMobileCapabilityType.BROWSER_NAME, "Chrome");
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "gy85a6ssib4ssohy");
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
			caps.setCapability("appPackage", "com.android.chrome");
			caps.setCapability("appActivity", "com.google.android.apps.chrome.Main");
			
		}
		
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		return driver;
		
	}
}