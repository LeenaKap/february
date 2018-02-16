package com.qtpselenium.core.hybrid;

import com.qtpselenium.core.hybrid.util.Constants;
import com.qtpselenium.core.hybrid.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

//import org.testng.annotations.Test;

public class Keywords {
	//gives log for every step
	ExtentTest test;
	Generickeywords app;
	
	public Keywords(ExtentTest test){
		this.test = test;
	}
	
 public void executeKeywords(String  testUnderExecution , Xls_Reader xls){
	 
		/*Generickeywords app = new Generickeywords();
		app.openBrowser("Chrome");
		app.navigate("url");
		app.click("gmaillink_xpath");
		app.input("email_id", "leena.kapgate@gmail.com");
		app.click("nextbutton_id");*/
	 //String = "GmailTest";
	 app = new Generickeywords(test);
	 int rows = xls.getRowCount("Keywords");
	 for(int rNum=2;rNum<=rows;rNum++){
		 
		 String tcid = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COL, rNum);
		 if(tcid.equals(testUnderExecution)){
		 String keyword = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.Keyword_COL, rNum);
		 String object = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.Object_COL, rNum);
		 String data = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.Data_COL, rNum);
		 test.log(LogStatus.INFO, tcid+"--" + keyword +"--" + object +"--"+ data +"--");
		 
		 if(keyword.equals("openBrowser"))
			 app.openBrowser(data);
		 else if(keyword.equals("navigate"))
			 app.navigate(object);
		 else if(keyword.equals("click"))
			 app.click(object);
		 else if(keyword.equals("input"))
			 app.input(object, data);
		 else if(keyword.equals("closeBrowser"))
			 app.closeBrowser();
	 }
	}
 }
public Generickeywords getGenerickeywords(){
	return app;
	
}
}
