package com.qtpselenium.core.hybrid.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.qtpselenium.core.hybrid.Keywords;
import com.qtpselenium.core.hybrid.util.Constants;
import com.qtpselenium.core.hybrid.util.DataUtil;
import com.qtpselenium.core.hybrid.util.ExtentManager;
import com.qtpselenium.core.hybrid.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GmailLogin {
	public ExtentTest test;
	
	ExtentReports rep = ExtentManager.getInstance();
	
	String testName = "GmailTest";
	
	@Test
	public void login(){
		
		test = rep.startTest(testName);
		test.log(LogStatus.INFO, "Staring the Gmail test");
		Xls_Reader xls = new Xls_Reader( Constants.SUITEA_XLS);
		
		Keywords app = new Keywords(test);
		test.log(LogStatus.INFO, "Executing Keywords");
		app.executeKeywords(testName, xls);
		test.log(LogStatus.PASS, "Test pass");
	}
	@AfterTest
	public void quit(){
		rep.endTest(test);
		rep.flush();
	}
	@Test(dataProvider="getData")
	public void loginTest(Hashtable<String,String> data){
		test = rep.startTest(testName);
		test.log(LogStatus.INFO, data.toString());
		
		if(DataUtil.isSkip(xls, testName) || data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		test.log(LogStatus.INFO, "Starting "+testName);
		
		app = new Keywords(test);
		test.log(LogStatus.INFO, "Executing keywords");
		app.executeKeywords(testName, xls,data);
		// add the screenshot
		//app.getGenericKeyWords().reportFailure("xxxx");
		test.log(LogStatus.PASS, "PASS");
		app.getGenericKeyWords().takeScreenShot();
	}

}
