package com.qtpselenium.core.hybrid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
//Baseclass
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

import com.qtpselenium.core.hybrid.util.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Generickeywords {
	
public WebDriver driver;
public Properties prop;
public ExtentTest test;

public Generickeywords(ExtentTest test){
	this.test = test;
	prop = new Properties();
	try {
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//project.properties");
		prop.load(fs);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	public String openBrowser(String browserType){
		test.log(LogStatus.INFO, "Opening Browser");
		if(browserType.equals("Mozilla"))
			System.setProperty("webdriver.firefox.marionette", "C:\\Program Files (x86)\\geckodriver.exe");
			//driver=new FirefoxDriver();
			else if(browserType.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Leena\\Downloads\\chromedriver_win32\\chromedriver.exe");
			driver=new ChromeDriver();
		}else if(browserType.equals("IEDriver")){
			System.setProperty("webdriver.ie.driver", "Downloads\\iedriver_exe");
			driver=new InternetExplorerDriver();
			
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			return Constants.PASS;
		}
		
	}

   public void navigate(String urlKey){
	   test.log(LogStatus.INFO, "Navigating to "+ prop.getProperty(urlKey));
	   driver.get(prop.getProperty(urlKey));
	   //driver.get(url);
	   return Constants.PASS;
		
	}
   
   public void click(String locatorKey){
	   test.log(LogStatus.INFO,"Clicking on "+ prop.getProperty(locatorKey));
	   WebElement e = getElement(locatorKey);
	   e.click();
	   return Constants.PASS;
   }
	   
	  
   
   public void input(String locatorKey , String data){
	   test.log(LogStatus.INFO,"Typing in "+ prop.getProperty(locatorKey));
	   WebElement e = getElement(locatorKey);
	   e.sendKeys(data);
   }
	  

	public String closeBrowser() {
		test.log(LogStatus.INFO,"Closing browser");
		driver.quit();
		return Constants.PASS;

   /***************************Validation keywords*********************************/
	public String verifyText(String locatorKey,String expectedText){
		WebElement e = getElement(locatorKey);
		String actualText = e.getText();
		if(actualText.equals(expectedText))
			return Constants.PASS;
		else
			return Constants.FAIL;

   }
	public String verifyElementPresent(String locatorKey){
		boolean result= isElementPresent(locatorKey);
		if(result)
			return Constants.PASS;
		else
			return Constants.FAIL+" - Could not find Element "+ locatorKey;
	}
	
	public String verifyElementNotPresent(String locatorKey){
		boolean result= isElementPresent(locatorKey);
		if(!result)
			return Constants.PASS;
		else
			return Constants.FAIL+" - Could find Element "+ locatorKey;
	}
	public boolean isElementPresent(String locatorKey){
		List<WebElement> e = null;
		
		if(locatorKey.endsWith("_id"))
			e = driver.findElements(By.id(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_xpath"))
			e = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_name"))
			e = driver.findElements(By.name(prop.getProperty(locatorKey)));
		
		if(e.size()==0)
			return false;
		else 
			return true;
	}

  	   
   }
   
   
  
   /*****************Utility function********************/
   
   
   //centralize function which is responsible to extact any webelements from the webpage
   
   public WebElement getElement(String locatorKey){
	   WebElement e = null;
	   try {
		if(locatorKey.endsWith("_id"))
			  e = driver.findElement(By.id(prop.getProperty(locatorKey)));
		   else if(locatorKey.endsWith("_xpath"))
			   e = driver.findElement(By.id(prop.getProperty(locatorKey)));
		   else if(locatorKey.endsWith("_name"))
			  e = driver.findElement(By.id(prop.getProperty(locatorKey)));
	} catch (Exception e1) {
		e1.printStackTrace();
		
		Assert.fail("Failure in Element Extraction "+ locatorKey);
	}
	   
	   return e;
   }

/************Reporting function***************************************/
   
   public void reportFailure(String failureMessage ){
	   takeScreenShot();
	   test.log(LogStatus.FAIL, failureMessage);
   }

private void takeScreenShot(){
//decide name - time stamp
		Date d = new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ","_")+".png";
		String path=Constants.SCREENSHOT_PATH+screenshotFile;
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// embed
		test.log(LogStatus.INFO, test.addScreenCapture(path));
	}

{
	
}
   }
	   