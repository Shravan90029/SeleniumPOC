package testsuite;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import bsh.util.Util;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import testsuite.utilities.UtilLib;

public class POMLib {
	private static WebDriver driver; 
	public static Properties prop;
	private static WebElement el;
	public int shortWait = 3000;
	public int normalWait = 6000;
	public int longWait = 10000;
	public String brower="";
	private Map<String,Object> map;
	protected static ExtentTest test;	

	public static String	browser;	
	public static String	launchUrl;
	public static String ErrorMessage=null;
	static WebDriverWait wait;


	public static ExtentTest getTest() {
		return test;
	}

	public static void setTest(ExtentTest test) {
		POMLib.test = test;
		UtilLib.setTest(test);
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public  void propLoad(Properties prop){
		this.prop=prop;
	}


	@SuppressWarnings("static-access")
	public POMLib(Map map,String browser) {

		this.map=map;

		POMLib.browser = browser;
		;

	}
	public WebDriver OpenURL(String launchUrl) throws Exception {
		driver=UtilLib.getDriver(browser, launchUrl);
		return driver;
	}
	public boolean LoginAsAdmin(){
		try{
			driver.findElement(By.xpath(prop.getProperty("Adidas_username"))).isDisplayed();
		}
		catch(Exception e){
		try{
			
			driver.findElement(By.xpath(prop.getProperty("UserName_clearICon"))).click();

		}
		catch(Exception e1){

		}
		}
		UtilLib.inputDataInTextbox("Username", prop.getProperty("Adidas_username"), map.get("Username").toString());
		UtilLib.inputDataInTextbox("Password", prop.getProperty("Adidas_password"), map.get("Password").toString());
		UtilLib.clickOnElement("Log In to Sandbox", prop.getProperty("Adidas_LoginButton"));
		if(driver.getTitle().equalsIgnoreCase("Verify Your Identity | Salesforce")){
			System.out.println("Verification required");
			test.log(LogStatus.FAIL, "Verification required: Verification Id sent in mail");
			if(ErrorMessage!=null){
			ErrorMessage=ErrorMessage+"\nVerification required: Verification Id sent in mail";
			}
			else{
				ErrorMessage="Verification required: Verification Id sent in mail";
			}
			return false;
		}
		else if(driver.getTitle().equalsIgnoreCase("Login | Salesforce")){

			System.out.println("Login unsuccessful");
			test.log(LogStatus.FAIL, "Login unsuccessful");
			if(ErrorMessage!=null){
			ErrorMessage=ErrorMessage+"\nLogin unsuccessful";
			}
			else{
				ErrorMessage="Login unsuccessful";
			}
			
			return false;
		}
		else{
			return true;
		}
	}

	public boolean LoginAsUser(){
		UtilLib.inputDataInTextbox("Search Salesforce", prop.getProperty("Adidas_SearchSalesforce"),"Achiyong,Benjamin");
		driver.findElement(By.xpath(prop.getProperty("Adidas_SearchSalesforce"))).sendKeys(Keys.ENTER);

		UtilLib.clickOnElement("Benjamin Achiyong search result link", prop.getProperty("Adidas_SearchSalesforceResult"));

		driver.switchTo().defaultContent();
		UtilLib.clickOnElement("User Action arrow", prop.getProperty("Adidas_ActionArrow"));
		UtilLib.clickOnElement("User Detail", prop.getProperty("Adidas_ActionArrow_UserDetail"));
		
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(prop.getProperty("UserDetail_LoginButton"))));
		driver.switchTo().activeElement().click();
		driver.findElement(By.xpath(prop.getProperty("UserDetail_LoginButton"))).click();
		test.log(LogStatus.PASS, "Login Button clicked successfully ");
		System.out.println("Login Button clicked successfully ");
		UtilLib.WaitTime(2000);
		
		if(UtilLib.getValue(prop.getProperty("UserPageHeader")).contains("Logged in as Benjamin Achiyong")){
			System.out.println("Logged in as Benjamin Achiyong");
			test.log(LogStatus.PASS, "Logged in as Benjamin Achiyong");
			return true;
		}
		else{
			if(ErrorMessage!=null){
				ErrorMessage=ErrorMessage+"\nLogin as Benjamin Achiyong unsuccessful";
				return false;
			}
			else{
				ErrorMessage="Login as Benjamin Achiyong unsuccessful";
				return false;
			}
		}
	}


	public boolean CreateNewCase(){
		UtilLib.clickOnElement("Back to E-Commerce EMEA Button", prop.getProperty("BackToEcommerceEMEA"));
		int size=driver.findElements(By.xpath(prop.getProperty("Cases_Tab"))).size();
		
		if(size>1){
			for(int i=size-1;i>=2;i--){
				Actions actions = new Actions(driver);
				actions.moveToElement(driver.findElement(By.xpath(prop.getProperty("Cases_Tab_generic")+i+prop.getProperty("Cases_Tab_generic1")))).perform();
				driver.findElement(By.xpath(prop.getProperty("CloseButtonGeneric")+i+"]/a[1]")).click();
			}
		}
		UtilLib.clickOnElement("Cases", prop.getProperty("CasesButton"));
		UtilLib.WaitTime(2000);
		driver.switchTo().frame("ext-comp-1005");
		UtilLib.clickOnElement("New Case Button", prop.getProperty("NewCaseButton"));
		driver.switchTo().defaultContent();

		UtilLib.switchtoFrame("New Case_Case Notes", prop.getProperty("NewCase_CaseNotes"));
		if(!UtilLib.inputDataInTextbox("Case Notes", prop.getProperty("NewCase_CaseNotes"), map.get("CaseNotes").toString())){
			return false;
		}
		UtilLib.inputDataInTextbox("Subject", prop.getProperty("NewCase_Subject"), map.get("Subject").toString());
		UtilLib.selectValueFromDropdown("Contact Reason", prop.getProperty("NewCase_ContactReason"), "Company Information");
		UtilLib.selectValueFromDropdown("Disposition", prop.getProperty("NewCase_Disposition"), "Marketing");
		UtilLib.selectValueFromDropdown("Case Type", prop.getProperty("NewCase_CaseType"), "Normal");
		UtilLib.selectValueFromDropdown("Brand", prop.getProperty("NewCase_Brand"), "adidas");
		UtilLib.selectValueFromDropdown("Sub-Brand", prop.getProperty("NewCase_SubBrand"), "adidas Inline");
		UtilLib.selectValueFromDropdown("Country", prop.getProperty("NewCase_Country"), "Austria");
		UtilLib.selectValueFromDropdown("Case Language", prop.getProperty("NewCase_CaseLanguage"), "English");
		UtilLib.clickOnElement("Anonymous Consumer checkbox", prop.getProperty("NewCase_AnonymousCustomerChkbox"));
		UtilLib.clickOnElement("Create Case Button", prop.getProperty("NewCase_CreateCaseButton"));
		driver.switchTo().defaultContent();
		List<WebElement> elements=driver.findElements(By.xpath(prop.getProperty("Cases_Tab_CaseID")));
		

		WebElement ele=elements.get(elements.size()-1);

		String CaseID=ele.getText();
		System.out.println("Case created with Case ID:"+CaseID);
		test.log(LogStatus.PASS,"Case created with Case ID:"+CaseID);
		if(CaseID==null){
			if(ErrorMessage!=null){
				ErrorMessage=ErrorMessage+"\nCase Id not generated";
				return false;
			}
			else{
				ErrorMessage="Case Id not generated";
				return false;
			}
		}

		return true;

	}
	public boolean LogOutAsUser(){
		UtilLib.clickOnElement("User Navigations Button", prop.getProperty("UserNavigations"));
		UtilLib.clickOnElement("Logout Button", prop.getProperty("UserLogOut"));
		UtilLib.WaitTime(5000);
		return true;

	}
	public static boolean LogOutAsAdmin(){

		UtilLib.clickOnElement("User Navigations Button for admin", prop.getProperty("UserNavigations"));
		UtilLib.clickOnElement("Logout Button for admin", prop.getProperty("AdminLogOut"));
		UtilLib.WaitTime(5000);
		return true;
	}
	public boolean CustomerServiceCase(){
		if(!driver.getTitle().equalsIgnoreCase("Customer Service | FAQ")){
			ErrorMessage=ErrorMessage+"\nCustomer Service page not opened";
			return false;
		}
		else{
			UtilLib.inputDataInTextbox("First Name", prop.getProperty("CustServFirstName"), map.get("CustomerFirstName").toString());
			UtilLib.inputDataInTextbox("Last Name", prop.getProperty("CustServLastName"), map.get("CustomerLastName").toString());
			UtilLib.inputDataInTextbox("Email Id", prop.getProperty("CustServEmailId"), map.get("CustomerEmailId").toString());
			UtilLib.inputDataInTextbox("Phone Number", prop.getProperty("CustServPhnNo"), map.get("CustomerPhoneNumber").toString());

			UtilLib.selectValueFromDropdown("Reason", prop.getProperty("CustServReason"), "Company Information");
			UtilLib.inputDataInTextbox("Subject", prop.getProperty("CustServSubject"), map.get("Subject").toString());
			UtilLib.inputDataInTextbox("Question", prop.getProperty("CustServQuestion"), map.get("Question").toString());
			WebElement we= driver.findElement(By.xpath(prop.getProperty("CustServSendButton")));
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",we);
			driver.switchTo().activeElement().click();
			driver.findElement(By.xpath(prop.getProperty("CustServSendButton"))).click();
			test.log(LogStatus.PASS, "Send Customer case Button clicked successfully");
			System.out.println("Send Customer case Button clicked successfully");
			UtilLib.waitForProcessing(prop.getProperty("CustomerCaseSubmissionWait"));
			UtilLib.clickOnElement("My Issue is not Resolved Button", prop.getProperty("CustIssueNotResolved"));
			try{
				
				if(driver.getCurrentUrl().toLowerCase().contains("thankyou")){
				System.out.println("Case submitted successfully");
				test.log(LogStatus.PASS,"Case submitted successfully");
				return true;
				}
				else{
					System.out.println("Case submission failed");
					test.log(LogStatus.PASS,"Case submission failed");
					return false;
				}
			}
			catch(Exception e){
				System.out.println("Case submission failed");
				ErrorMessage=ErrorMessage+"Case submission failed";
				return false;
			}
		}

	}

	public boolean GetConsumerCase(){
		try{
			AssertJUnit.assertTrue(UtilLib.clickOnElement("Back to E-Commerce EMEA Button", prop.getProperty("BackToEcommerceEMEA")));

		int size=driver.findElements(By.xpath(prop.getProperty("Cases_Tab"))).size();
		if(size>1){
			for(int i=size-1;i>=2;i--){
				Actions actions = new Actions(driver);
				actions.moveToElement(driver.findElement(By.xpath(prop.getProperty("Cases_Tab_generic")+i+prop.getProperty("Cases_Tab_generic1")))).perform();
				driver.findElement(By.xpath(prop.getProperty("CloseButtonGeneric")+i+"]/a[1]")).click();
			}
		}
		AssertJUnit.assertTrue(UtilLib.clickOnElement("Cases", prop.getProperty("CasesButton")));
		UtilLib.WaitTime(2000);
		driver.switchTo().frame("ext-comp-1005");
		UtilLib.clickOnElement("Get Case Button", prop.getProperty("GetCaseButton"));
		UtilLib.WaitTime(15000);
		driver.switchTo().defaultContent();
		UtilLib.waitForProcessing(prop.getProperty("CaseLoading"));
		UtilLib.WaitTime(2000);
		String CaseID= driver.findElement(By.xpath(prop.getProperty("Cases_Tab_case1"))).getText();
		if(CaseID!=null ||CaseID.length()!=0){
		System.out.println("Case Number "+CaseID+" opened successfully");
		test.log(LogStatus.PASS,"Case Number "+CaseID+" opened successfully");
		return true;
		}
		else{
			System.out.println("Case could not be opened");
			test.log(LogStatus.FAIL,"Case could not be opened");
			return false;
		}
		}
		catch(AssertionError e){
			return false;
		}
		catch(Exception e){
			return false;
		}
		
	}


}
