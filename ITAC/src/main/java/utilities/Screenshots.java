package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Screenshots extends GetDriver{

	public Screenshots(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void getScreenshot(String FolderPath, String ScreenshotName) throws IOException {

		Screenshot Expected = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		ImageIO.write(Expected.getImage(), "PNG", new File(FolderPath + "\\" + ScreenshotName + ".png"));

	}

	public static String CreateFolderForScreenShots (String Path) {

		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		Date date = new Date();
		String TimeStamp = dateformat.format(date);

		String BasePath = System.getProperty("user.dir")+"\\Screenshots\\";
		String finalFilePath = BasePath + Path + "\\" + TimeStamp;

		File ScreenShotsPath = new File(finalFilePath);

		if(!ScreenShotsPath.exists()) {
			boolean Successful = ScreenShotsPath.mkdirs();
			if(Successful) {
				System.out.println("Created the folder");
				System.out.println("Created Folder Path: " + ScreenShotsPath.getAbsolutePath());
			}else {
				System.out.println("Couldnt create folder");
			}
		}else {
			System.out.println("Folder Structure already exists");
		}

		return ScreenShotsPath.getAbsolutePath();
	}


	public void screenshot(String FolderPath, String ScreenshotName) throws Exception{

		TakesScreenshot scrShot =((TakesScreenshot)driver);

		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

		File DestFile=new File(FolderPath+"\\"+ScreenshotName+".png");

		FileUtils.copyFile(SrcFile, DestFile);

	}

	public static String CreateFolder (String Path) {

		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		Date date = new Date();
		String TimeStamp = dateformat.format(date);

		String finalFilePath = Path + "\\" + TimeStamp;

		File FolderPath = new File(finalFilePath);

		if(!FolderPath.exists()) {
			boolean Successful = FolderPath.mkdirs();
			if(Successful) {
			//	System.out.println("Created the folder");
			//	System.out.println("Created Folder Path: " + FolderPath.getAbsolutePath() );
			}else {
			//	System.out.println("Couldnt create folder");
			}
		}else {
		//	System.out.println("Folder Structure already exists");
		}

		return FolderPath.getAbsolutePath();
	} 
}

