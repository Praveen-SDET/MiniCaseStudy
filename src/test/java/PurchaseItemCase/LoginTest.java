package PurchaseItemCase;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import Utilitty.utilities;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {
	WebDriver driver;

	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
  @BeforeTest
  public void setup() throws IOException {
//	  WebDriverManager.chromedriver().setup();
//		driver = new ChromeDriver();
		
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.demoblaze.com/index.html");
		reports = new ExtentReports();
		spark = new ExtentSparkReporter("target//SparkReporter.html");
		reports.attachReporter(spark);
		
		
  }
  @Test(priority=1,groups="particular")
  public void Signup() throws InterruptedException  {
	  extentTest=reports.createTest("Signup test");
	  driver.findElement(By.cssSelector("#login2")).click();
	  Thread.sleep(2000);
	  driver.findElement(By.cssSelector("#loginusername")).sendKeys("Ramann");
	  driver.findElement(By.id("loginpassword")).sendKeys("Hanu");
	  driver.findElement(By.xpath("(//div/button[@type='button'])[9]")).click();
	  Thread.sleep(3000);
	  
  }
  @Test(priority=2,groups="particular",dataProvider="SinData")
  public void Addsingle(String item) throws InterruptedException {   
	  extentTest=reports.createTest("Add single item test");
	  driver.findElement(By.xpath("//div/a[contains(text(),'Phones')]")).click();
	  Thread.sleep(2000);
//	  
	  driver.findElement(By.linkText(item)).click();   // String item = "//a[text(),'"+catagory"']";
	  Thread.sleep(3000);
	//  driver.findElement(By.xpath("//div/a[contains(text(),'Add to cart')]")).click();
	  
	  WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
	  WebElement btn = driver.findElement(By.xpath("//div/a[contains(text(),'Add to cart')]"));
		wait.until(ExpectedConditions.elementToBeClickable(btn));
		btn.click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
		Thread.sleep(2000);
		
			
			
//			Thread.sleep(3000);
		
  }
  @Test(priority=3)
  public void delIt() throws InterruptedException {
	  driver.findElement(By.xpath("//li/a[contains(text(),'Cart')]")).click();
		Thread.sleep(3000);
		String availitem = driver.findElement(By.cssSelector(".panel-title")).getText();
		int bef = Integer.parseInt(availitem);
	  driver.findElement(By.xpath("(//td/a[contains(text(),'Delete')])[1]")).click();
	  
	  String aftitem = driver.findElement(By.cssSelector(".panel-title")).getText();
	//  driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		int aft = Integer.parseInt(availitem);
		if(bef==aft) {
			System.out.println("Item is not Deleted");
		}
		else { System.out.println("Item is deleted");}
	  
		driver.findElement(By.xpath("(//li/a[@class='nav-link'])[1]")).click();
  }
  @DataProvider(name="SinData")
	public Object[][] getsinData() throws IOException, CsvValidationException{
		String path =System.getProperty("user.dir")+("//src//test//resources//Resources_CX//SingleItem.csv");
		String[] cols1;
		CSVReader reader = new CSVReader(new FileReader(path));
		ArrayList<Object> dataList1 = new ArrayList<Object>();
		while((cols1=reader.readNext()) !=null) {
			Object[] record1 = {cols1[0]};
			dataList1.add(record1);
		}
		return dataList1.toArray(new Object[dataList1.size()][]);
		
	}
 
  @DataProvider(name="Finddata")
	public Object[][] getData() throws IOException, CsvValidationException{
		String path =System.getProperty("user.dir")+("//src//test//resources//Resources_CX//details_Cx.csv");
		String[] cols;
		CSVReader reader = new CSVReader(new FileReader(path));
		ArrayList<Object> dataList = new ArrayList<Object>();
		while((cols=reader.readNext()) !=null) {
			Object[] record = {cols[0]};
			dataList.add(record);
		}
		return dataList.toArray(new Object[dataList.size()][]);
		
	}
  @Test(dataProvider="Finddata",priority=4)
  public void multiselect(String first) throws InvalidFormatException, IOException, InterruptedException {
	  
	  extentTest=reports.createTest("Add multiple item test");
	  
		  driver.findElement(By.xpath("//li/a[contains(text(),'Home')]")).click();
		  Thread.sleep(2000);
		  driver.findElement(By.linkText(first)).click();
		  Thread.sleep(3000);
		  WebDriverWait wait1 = new WebDriverWait(driver,Duration.ofSeconds(20));
		  WebElement btn1 = driver.findElement(By.xpath("//div/a[contains(text(),'Add to cart')]"));
			wait1.until(ExpectedConditions.elementToBeClickable(btn1));
			btn1.click();
			wait1.until(ExpectedConditions.alertIsPresent());
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			Thread.sleep(2000);
  }
  
  @Test(priority=5,dependsOnMethods="multiselect")
  public void orderplace() throws InterruptedException {
	  
	  extentTest=reports.createTest("Order placed Test");
	  driver.findElement(By.xpath("//li/a[contains(text(),'Cart')]")).click();
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//div/button[contains(text(),'Place Order')]")).click();
	  Thread.sleep(3000);
	  driver.findElement(By.cssSelector("#name")).sendKeys("Ajith");
	  driver.findElement(By.cssSelector("#country")).sendKeys("Japan");
	  driver.findElement(By.cssSelector("#city")).sendKeys("Masko");
	  driver.findElement(By.cssSelector("#card")).sendKeys("13245");
	  driver.findElement(By.cssSelector("#month")).sendKeys("March");
	  driver.findElement(By.cssSelector("#year")).sendKeys("2045");
	  
	  driver.findElement(By.xpath("//div/button[contains(text(),'Purchase')]")).click();
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//div/button[contains(text(),'OK')]")).click(); 
  }
  
  
  @AfterTest
  public void finishExtent() {
	  reports.flush();
  }
  @AfterMethod
  public void teardown(ITestResult result) {
	  if(ITestResult.FAILURE==result.getStatus()) {
		  extentTest.log(Status.FAIL, result.getThrowable().getMessage());
		  String strPath =utilities.getScreenshot(driver);
		  extentTest.addScreenCaptureFromPath(strPath);
	  }
  }
  
}
