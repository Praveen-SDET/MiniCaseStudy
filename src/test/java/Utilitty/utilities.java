package Utilitty;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class utilities {
public static String getScreenshot(WebDriver driver) {
		
		TakesScreenshot screen = (TakesScreenshot)driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		String srcFile =System.getProperty("user.dir")+"/screenshot/" + System.currentTimeMillis() +".png";
		File dest = new File(srcFile);
		try {
			FileUtils.copyFile(src, dest);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		
		return srcFile;
		
	}

}
