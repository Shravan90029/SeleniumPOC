package testsuite.testcases;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import testsuite.POMLib;
import testsuite.utilities.ExcelUtil;
import testsuite.utilities.UtilLib;




public class TC01_CreateNewCase {
	public static Properties prop;
	public static WebDriver driver ;
	public static ExcelUtil eu=new ExcelUtil();
	public static UtilLib ut= new UtilLib();
	public HashMap<String,HashMap<String,Object>> mainmap;
	public static HashMap<String,Object> map;
	public static Calendar startTime,endTime;
	public static String error_message = "",startDateTime="",endDateTime="";
	public static String status = "";
	public static SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
	public POMLib pom;
	public static String launchUrl;
	public static String browser;
	public static String TestCase_Name = "TC01_CreateNewCase";
	private final String filePath = "TestReport/index.html";
	protected static ExtentReports extent;
	protected static ExtentTest test;
	
    
	@BeforeSuite
	public void deleteExistingReport() {
		try{
		new File(filePath).delete();
		}
		catch(Exception e){
			
		}

	}
	
	@BeforeMethod
	public void initiatebrowser() throws Exception {
        prop = ut.LoadConfigFile();
        
        eu.setMap(map);
        map=ExcelUtil.DataEngine("Sheet1", TestCase_Name);
        browser=prop.getProperty("browser");
        pom = new POMLib(map,browser);
        pom.propLoad(prop);
        startTime = Calendar.getInstance();
        startDateTime= formatter.format(startTime.getTime());

       extent = new ExtentReports(filePath, false);
        extent.addSystemInfo("Selenium Version", "2.50.0");
		extent.addSystemInfo("Environment", "Prod");

	}

	@Test
	public void CreateNewCase() throws Exception{
		try{


			
	        launchUrl=prop.getProperty("launchUrl");
			
				test = extent.startTest(TestCase_Name,"Create New Case");
				pom.setTest(test);
				driver=pom.OpenURL(launchUrl);
				
				
					
					AssertJUnit.assertTrue(pom.LoginAsAdmin());
					AssertJUnit.assertTrue(pom.LoginAsUser());
					AssertJUnit.assertTrue(pom.CreateNewCase());
					AssertJUnit.assertTrue(pom.LogOutAsUser());
					AssertJUnit.assertTrue(pom.LogOutAsAdmin());
					
				}
				

				catch(AssertionError e){
					
					String errorMessage=UtilLib.Errormessage+POMLib.ErrorMessage;
					String failedScreenshotPath;

					
						failedScreenshotPath=UtilLib.CaptureScreenshot(TestCase_Name);
					
					test.log(LogStatus.FAIL,errorMessage+test.addScreenCapture(failedScreenshotPath));

					AssertJUnit.assertTrue(errorMessage,false);

				}
				
				

				catch(Exception e){
					e.printStackTrace();
					
					String errorMessage=UtilLib.Errormessage+POMLib.ErrorMessage;
					String failedScreenshotPath;

					
						failedScreenshotPath=UtilLib.CaptureScreenshot(TestCase_Name);
					
					test.log(LogStatus.FAIL,errorMessage+test.addScreenCapture(failedScreenshotPath));

					AssertJUnit.assertTrue(errorMessage,false);

				}
				finally{
					endDateTime=formatter.format(startTime.getTime());
					extent.endTest(test);

					
				}
			}
	
	@AfterClass
	public void tearDown() throws Exception {

		try{
//			ut.CloseDriver();
			extent.flush();

		}catch(Exception e){
			e.printStackTrace();

		}


	}
}
