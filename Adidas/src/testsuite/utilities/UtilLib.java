
package testsuite.utilities;
import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.firefox.internal.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

















import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class UtilLib {

	public static WebDriver driver; 
	static WebDriverWait wait;
	static Properties propFile = new Properties();
	public static ExtentTest test;
	public static String Errormessage=null;
	public static String browserType;

	
	//<Method Name>getDriver</Method Name>
	//<Description>initialize the driver object to launch scripts in selected browser type</Description>
	//<Arguments>browser,launchUrl</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static WebDriver getDriver (String browser,String launchUrl) throws Exception
	{

		browserType=browser;

		if(browser.equalsIgnoreCase("firefox")){
			/*ProfilesIni profileini = new ProfilesIni() {
		        @Override
		        public FirefoxProfile getProfile(String profileName) {
		                File appData = locateAppDataDirectory(Platform.getCurrent());
		                Map<String, File> profiles = readProfiles(appData);
		                File profileDir = profiles.get(profileName);
		                if (profileDir == null)
		                  return null;
		                return new FirefoxProfile(profileDir);
		         }
		    };*/

			ProfilesIni profileini= new ProfilesIni();
  			FirefoxProfile firefoxProfile=profileini.getProfile("default");	
			
			
			
			
			/*firefoxProfile.setPreference("BrowserId", "dBktIekPR36nl6KQhyydEg");
		    firefoxProfile.setPreference("network.proxy.type", 2);
	        firefoxProfile.setPreference("network.proxy.autoconfig_url", "http://infypacsrv/chdsez.pac");
	        firefoxProfile.setPreference("network.proxy.no_proxies_on", "localhost");*/
	        driver = new FirefoxDriver(firefoxProfile);
	       
			


//			driver = new FirefoxDriver(firefoxProfile);


		}
		//Incase browser is chrome
		else if(browser.equalsIgnoreCase("chrome"))
		{
			
			DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
			Proxy proxy = new Proxy();
			proxy.setProxyType(Proxy.ProxyType.PAC);

			chromeCapabilities.setCapability(CapabilityType.PROXY,proxy);


			File fileChromeDriver = new File("C:\\tools\\chromedriver.exe");
			System.out.println("Chrome Driver Path: "+ fileChromeDriver.getAbsolutePath());
			System.setProperty("webdriver.chrome.driver",fileChromeDriver.getAbsolutePath());


			driver = new ChromeDriver(chromeCapabilities);

		}

		else if(browser.equalsIgnoreCase("IE")){
			File file = new File("drivers\\IEDriverServer.exe");


			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

			driver = new InternetExplorerDriver(ieCapabilities);

		}else {
			//Throw an exception for no matching browsers    
			throw new Exception("Invalid Browser:"+browser+"\n Please select a valid browser");
		}



//		driver.manage().window().maximize();
		
		driver.get(launchUrl);	
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		wait=  new WebDriverWait( driver , 30 );
		return driver;

	}


	//<Method Name>setTest</Method Name>
	//<Description>setter method for extent report</Description>
	//<Arguments>test</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static void setTest(ExtentTest test) {
		UtilLib.test = test;
	}





	//<Method Name>LoadConfigFile</Method Name>
	//<Description>Loads the property files inside a property object and returns the object</Description>
	//<Arguments>None</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public Properties LoadConfigFile() throws Exception
	{
		System.out.println("Executing the function LoadConfigFile...");
		String strApplicationConfigPath=getConfigPath();
		strApplicationConfigPath=strApplicationConfigPath +"ApplicationDetails.properties";

		propFile.load(new FileInputStream(strApplicationConfigPath));

		String strObjectConfigPath=getConfigPath();
		strObjectConfigPath=strObjectConfigPath +"ObjectProperties.properties";

		propFile.load(new FileInputStream(strObjectConfigPath));
		System.out.println("End of the function LoadConfigFile...");
		return propFile;
	}



	//<Method Name>getConfigPath</Method Name>
	//<Description>Returns the path of property files with respect to working directory</Description>
	//<Arguments>None</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static String getConfigPath()
	{
		String workingdirectory = "resources/";

		return workingdirectory;
	}


	//<Method Name>switchtoFrame</Method Name>
	//<Description>Switch to a frame based on an element present in it</Description>
	//<Arguments>elementname,xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean switchtoFrame(String elementname, String xpath)
	{
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		int count=0;
		List<WebElement> frames=driver.findElements(By.xpath("//iframe"));

		for(WebElement frame:frames){
			driver.switchTo().frame(frame);
			try{
			
				count++;
				driver.findElement(By.xpath(xpath)).getTagName();
				if(!browserType.equalsIgnoreCase("firefox")){
					UtilLib.WaitTime(2000);
				}
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				return true;
			}

			catch(Exception e) {

			}
			driver.switchTo().defaultContent();
		}
		test.log(LogStatus.INFO,"Frame having element"+elementname+"not found");
		Errormessage=Errormessage+"\n"+"Frame having element"+elementname+"not found";
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return false;
	}


	//<Method Name>switchtoFrame</Method Name>
	//<Description>Switch to a frame across windows based on FrameId</Description>
	//<Arguments>frame_id</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>
	public static boolean switchToFrameInsideWindow(String frame_id){
		Set<String> windowHandles = driver.getWindowHandles();

		for(String handle : windowHandles){  
			driver.switchTo().window(handle);  
			try{

				driver.switchTo().frame(frame_id);

				return true;  
			} 
			catch(Exception e){

			}
			driver.switchTo().defaultContent();

		}
		test.log(LogStatus.INFO,"Frame with frame_id "+frame_id+"not found");
		Errormessage=Errormessage+"\n"+"Frame with frame_id "+frame_id+"not found";
		return false;
	}





	//<Method Name>inputDataInTextbox</Method Name>
	//<Description>Verifies the Element present in the page and enters passed data in it</Description>
	//<Arguments>elementName,xpath,data</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>
	public static boolean inputDataInTextbox(String elementName,String xpath,String data){
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			if( driver.findElement(By.xpath(xpath)).isDisplayed()){
				driver.findElement(By.xpath(xpath)).clear();
				driver.findElement(By.xpath(xpath)).sendKeys(data);

				if(driver.findElement(By.xpath(xpath)).getAttribute("value").equalsIgnoreCase(data)){
					test.log(LogStatus.PASS,data+" successfully entered into the field "+elementName+".  ");
					System.out.println(data+" successfully entered into the field "+elementName);
					return true;
				}
				else{
					test.log(LogStatus.INFO,data+"  could not be entered into the field"+elementName+".  ");
					Errormessage=Errormessage+"\n"+data+" could not be entered into the field "+elementName;
					System.out.println(data+" could not be entered into the field "+elementName);
					return false;
				}
			}
			return false;
		}
		catch(TimeoutException e){
			System.out.println(elementName+" not found in page");
			Errormessage=Errormessage+"\n"+elementName+" not found in page";
			test.log(LogStatus.INFO,elementName+" not found in page");
			return false;
		}
		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}
	}

	//<Method Name>inputDataInTextbox1</Method Name>
	//<Description>Verifies the Element present in the page and enters passed data in it</Description>
	//<Arguments>elementName,xpath,data</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean inputDataInTextbox1(String elementName,String xpath,String data){
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			if( driver.findElement(By.xpath(xpath)).isDisplayed()){
				driver.findElement(By.xpath(xpath)).clear();
				driver.findElement(By.xpath(xpath)).sendKeys(data);

				if(driver.findElement(By.xpath(xpath)).getAttribute("value").equalsIgnoreCase(data)){
					test.log(LogStatus.PASS,data+" successfully entered into the field "+elementName);
					System.out.println(data+" successfully entered into the field "+elementName);
					return true;
				}
				else{
					return false;
				}
			}
			return false;
		}
		catch(TimeoutException e){

			return false;
		}
		catch(Exception e){

			return false;
		}
	}


	//<Method Name>clickOnElement</Method Name>
	//<Description>Verifies the Element present in the page and clicks on it</Description>
	//<Arguments>elementName,xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean clickOnElement(String elementName,String xpath){
		try{


			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

			if( driver.findElement(By.xpath(xpath)).isDisplayed()){

				driver.findElement(By.xpath(xpath)).click();

				System.out.println(elementName+" clicked successfully. ");
				test.log(LogStatus.PASS,elementName+" clicked successfully.   ");
				if(elementName.toLowerCase().contains("save")||elementName.toLowerCase().contains("next")||elementName.toLowerCase().contains("search")){
					waitForProcessing(propFile.getProperty("Safety_processing_img"));
					try{

						driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

						driver.switchTo().defaultContent();

						driver.findElement(By.xpath(propFile.getProperty("Safety_AlertMsg"))).getTagName();



						return false;
					}
					catch(Exception e){
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						driver.switchTo().frame(0);

					}
				}
				return true;
			}
			return false;
		}
		catch(TimeoutException e){
			Errormessage=Errormessage+"\n"+elementName+" not found in page";
			System.out.println(elementName+" not found in page");
			test.log(LogStatus.INFO,elementName+" not found in page ");
			return false;
		}

		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}
	}


	//<Method Name>CaptureScreenshot</Method Name>
	//<Description>Captures the screenshot with current time stamp and copies to the created folder</Description>
	//<Arguments>TestcaseName</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>
	public static  String CaptureScreenshot(String TestcaseName){

		if(driver!=null){

			String ResultFolderName = "TestReport/";
			File file = new File(ResultFolderName);
			try{
				file.mkdirs();

			}catch(Exception e){
				e.printStackTrace();
			} 
			DateFormat screenshotName1 = new SimpleDateFormat("dd-MMMM-yyyy_HH-mm-ss");
			Date screenshotDate = new Date();
			String picName = TestcaseName+"_"+screenshotName1.format(screenshotDate);

			String folderPath = ResultFolderName+"/"+picName+".png";

			Augmenter augmenter= new Augmenter();
			driver=augmenter.augment(driver);
			File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);




			//File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(folderPath));
			} catch (IOException e) {
				e.printStackTrace();
			}

			String SreenshotPath = picName+".png";
			return SreenshotPath;
		}
		return "";
	}



	//<Method Name>selectValueFromDropdown</Method Name>
	//<Description>selects an option from Dropdown based on the visible text</Description>
	//<Arguments>elementName,xpath,visibleText</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean selectValueFromDropdown(String elementName,String xpath,String visibleText){
		try{


			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			if( driver.findElement(By.xpath(xpath)).isDisplayed()){
				Select dropdown = new Select(driver.findElement(By.xpath(xpath)));
				dropdown.selectByVisibleText(visibleText);
				WaitTime(1000);

				System.out.println(visibleText+" selected from dropdown" +elementName);
				test.log(LogStatus.PASS, visibleText+" selected from dropdown" +elementName);




			}
			return true;
		}
		catch(TimeoutException e){
			Errormessage=Errormessage+"\n"+elementName+" not found in page";
			System.out.println(elementName+" not found in page");
			test.log(LogStatus.INFO,elementName+" not found in page ");
			return false;
		}

		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}


	}


	//<Method Name>waitForProcessing</Method Name>
	//<Description>waits until processing bar disappear</Description>
	//<Arguments>xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static void waitForProcessing(String xpath){
		WebDriverWait wait = new WebDriverWait(driver, 50);
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		try{
			if(!browserType.equalsIgnoreCase("firefox")){
				WaitTime(3000);
			}
			driver.findElement(By.xpath(xpath)).isDisplayed();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
			if(!browserType.equalsIgnoreCase("firefox")){
				WaitTime(1000);
			}

		}
		catch(Exception e){

		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	//<Method Name>longwaitForProcessing</Method Name>
	//<Description>waits until processing bar disappear</Description>
	//<Arguments>xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static void longwaitForProcessing(String xpath){
		WebDriverWait longwait = new WebDriverWait(driver, 150);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try{
			if(!browserType.equalsIgnoreCase("firefox")){

				WaitTime(3000);
			}
			driver.findElement(By.xpath(xpath)).isDisplayed();
			longwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
			if(!browserType.equalsIgnoreCase("firefox")){
				WaitTime(1000);
			}

		}
		catch(Exception e){

		}

	}


	//<Method Name>WaitTime</Method Name>
	//<Description>waits for some specific milliseconds of time</Description>
	//<Arguments>milliseconds</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static void WaitTime(int milliseconds) {
		try{
			long stoptime = System.currentTimeMillis()+milliseconds;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		catch(Exception e){

		}
	}


	//<Method Name>verifyFieldIsPrepopulated</Method Name>
	//<Description>verifies whether an element is present and pre-populated</Description>
	//<Arguments>ElementName,xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean verifyFieldIsPrepopulated(String ElementName, String xpath) {
		String value;

		try{

			driver.findElement(By.xpath(xpath)).isDisplayed();

			if(driver.findElement(By.xpath(xpath)).getTagName().equalsIgnoreCase("input")){

				value= driver.findElement(By.xpath(xpath)).getAttribute("value");
			}
			else if(driver.findElement(By.xpath(xpath)).getTagName().equalsIgnoreCase("select")){

				value= new Select(driver.findElement(By.xpath(xpath))).getFirstSelectedOption().getText();

			}
			else{
				System.out.println(driver.findElement(By.xpath(xpath)).getTagName());
				value= driver.findElement(By.xpath(xpath)).getText();
			}

			if(!(value.length()==0 || value== null)){
				test.log(LogStatus.PASS,ElementName+" is prepopulated with "+value);
				System.out.println(ElementName+" is prepopulated with "+value);
				return true;
			}
			else{
				test.log(LogStatus.INFO,ElementName+" is not prepopulated");
				Errormessage=Errormessage+"\n"+ElementName+" is not prepopulated";
				System.out.println(ElementName+" is not prepopulated");
				return false;
			}


		}
		catch(NoSuchElementException e){
			Errormessage=Errormessage+"\n"+ElementName+" not found in page";
			System.out.println(ElementName+" not found in page");
			test.log(LogStatus.INFO,ElementName+" not found in page ");
			return false;
		}
		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}
	}


	//<Method Name>verifyFieldIsPrepopulatedAndNonEditable</Method Name>
	//<Description>verifies whether an element is present and pre-populated and non-editable</Description>
	//<Arguments>ElementName,xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean verifyFieldIsPrepopulatedAndNonEditable(String ElementName, String xpath) {
		String value;

		try{
			driver.findElement(By.xpath(xpath)).isDisplayed();
			if(!driver.findElement(By.xpath(xpath)).isEnabled()){

				if(driver.findElement(By.xpath(xpath)).getTagName().equalsIgnoreCase("input")){
					value= driver.findElement(By.xpath(xpath)).getAttribute("value");
				}
				else if(driver.findElement(By.xpath(xpath)).getTagName().equalsIgnoreCase("select")){

					value= new Select(driver.findElement(By.xpath("xpath"))).getFirstSelectedOption().getText();

				}
				else{
					value= driver.findElement(By.xpath(xpath)).getText();
				}

				if(!(value.length()==0 || value== null)){
					test.log(LogStatus.PASS,ElementName+" is prepopulated with "+value+" and is non editable");
					System.out.println(ElementName+" is prepopulated with "+value+" and is non editable");
					return true;
				}
				else{
					Errormessage=Errormessage+"\n"+ElementName+" is not prepopulated";
					test.log(LogStatus.INFO,ElementName+" is not prepopulated");
					System.out.println(ElementName+" is not prepopulated");
					return false;
				}
			}
			else{
				Errormessage=Errormessage+"\n"+ElementName+" is editable";
				test.log(LogStatus.INFO,ElementName+"  is editable");
				System.out.println(ElementName+" is editable");
				return false;
			}


		}
		catch(NoSuchElementException e){
			Errormessage=Errormessage+"\n"+ElementName+" not found in page";
			System.out.println(ElementName+" not found in page");
			test.log(LogStatus.INFO,ElementName+" not found in page ");
			return false;
		}
		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}
	}


	//<Method Name>verifyFieldIsNonEditable</Method Name>
	//<Description>verifies whether a field is present and is non-editable</Description>
	//<Arguments>ElementName,xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean verifyFieldIsNonEditable(String ElementName, String xpath) {
		try{
			driver.findElement(By.xpath(xpath)).isDisplayed();
			if(!driver.findElement(By.xpath(xpath)).isEnabled()){
				test.log(LogStatus.PASS,ElementName+" is non editable");
				System.out.println(ElementName+" is non editable");
				return true;
			}
			else{
				Errormessage=Errormessage+"\n"+ElementName+" is editable";
				test.log(LogStatus.INFO,ElementName+" is editable");
				System.out.println(ElementName+" is editable");
				return false;
			}
		}
		catch(NoSuchElementException e){
			Errormessage=Errormessage+"\n"+ElementName+" not found in page";
			System.out.println(ElementName+" not found in page");
			test.log(LogStatus.INFO,ElementName+" not found in page ");
			return false;
		}
		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}
	}


	//<Method Name>verifyFieldIsEditable</Method Name>
	//<Description>verifies whether a field is present and is editable</Description>
	//<Arguments>ElementName,xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean verifyFieldIsEditable(String ElementName, String xpath) {
		try{
			driver.findElement(By.xpath(xpath)).isDisplayed();
			if(driver.findElement(By.xpath(xpath)).isEnabled()){
				test.log(LogStatus.PASS,ElementName+" is editable");
				System.out.println(ElementName+" is  editable");
				return true;
			}
			else{
				Errormessage=Errormessage+"\n"+ElementName+" is non editable";
				test.log(LogStatus.INFO,ElementName+" is non editable");
				System.out.println(ElementName+" is non editable");
				return false;
			}
		}
		catch(NoSuchElementException e){
			Errormessage=Errormessage+"\n"+ElementName+" not found in page";
			System.out.println(ElementName+" not found in page");
			test.log(LogStatus.INFO,ElementName+" not found in page ");
			return false;
		}
		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}


	}


	//<Method Name>getValue</Method Name>
	//<Description>retrieve and returns the value stored at a particular xpath</Description>
	//<Arguments>xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static String getValue(String xpath) {
		String value;
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));


			driver.findElement(By.xpath(xpath)).isDisplayed();


			if(driver.findElement(By.xpath(xpath)).getTagName().equalsIgnoreCase("input")){
				value= driver.findElement(By.xpath(xpath)).getAttribute("value");
			}
			else if(driver.findElement(By.xpath(xpath)).getTagName().equalsIgnoreCase("select")){

				value= new Select(driver.findElement(By.xpath(xpath))).getFirstSelectedOption().getText();

			}
			else{
				value= driver.findElement(By.xpath(xpath)).getText();
			}

			return value;
		}
		catch(TimeoutException e){
			Errormessage=Errormessage+"\n"+"Xpath "+xpath+" not found";
			System.out.println("Xpath "+xpath+" not found");
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return null;
		}

	}


	//<Method Name>verifyFieldIsPrepopulatedwithValue</Method Name>
	//<Description>verifies an element is pre-populated with the specified value</Description>
	//<Arguments>ElementName,xpath,expectedValue</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean verifyFieldIsPrepopulatedwithValue(String ElementName,
			String xpath, String expectedValue) {
		String value;

		try{
			driver.findElement(By.xpath(xpath)).isDisplayed();

			if(driver.findElement(By.xpath(xpath)).getTagName().equalsIgnoreCase("input")){
				value= driver.findElement(By.xpath(xpath)).getAttribute("value");
			}
			else if(driver.findElement(By.xpath(xpath)).getTagName().equalsIgnoreCase("select")){

				value= new Select(driver.findElement(By.xpath(xpath))).getFirstSelectedOption().getText();

			}
			else{
				value= driver.findElement(By.xpath(xpath)).getText();
			}

			if(value.equalsIgnoreCase(expectedValue)){
				test.log(LogStatus.PASS,ElementName+" is prepopulated with "+expectedValue);
				System.out.println(ElementName+" is prepopulated with "+expectedValue);
				return true;
			}
			else{
				Errormessage=Errormessage+"\n"+ElementName+" is not prepopulated with "+expectedValue;
				test.log(LogStatus.INFO,ElementName+" is not prepopulated with "+expectedValue);
				System.out.println(ElementName+" is not prepopulated with "+expectedValue);
				return false;
			}


		}
		catch(NoSuchElementException e){
			Errormessage=Errormessage+"\n"+ElementName+" not found in page";
			System.out.println(ElementName+" not found in page");
			test.log(LogStatus.INFO,ElementName+" not found in page ");
			return false;
		}
		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}

	}


	//<Method Name>getTodaysDate</Method Name>
	//<Description>returns today's date in specified format</Description>
	//<Arguments>format</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>


	public static String getTodaysDate(String format) {
		String timeStamp = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
		return timeStamp;
	}


	//<Method Name>getFutureDate</Method Name>
	//<Description>returns the date after 30 days from today's date in specified format</Description>
	//<Arguments>format</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static String getFutureDate(String format) {
		Calendar cal =Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 30); 
		String timeStamp = new SimpleDateFormat(format).format(cal.getTime());
		return timeStamp;
	}



	//<Method Name>verifyFieldPresent</Method Name>
	//<Description>verifies whether the field is present or not</Description>
	//<Arguments>FieldName,xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public boolean verifyFieldPresent(String FieldName, String xpath) {
		try{
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			driver.findElement(By.xpath(xpath)).getTagName();
			test.log(LogStatus.PASS,FieldName+" present in the page");	
			System.out.println(FieldName+" present in the page");	
			return true;


		}
		catch(NoSuchElementException e){
			Errormessage=Errormessage+"\n"+FieldName+" not found in page";
			System.out.println(FieldName+" not found in page");
			test.log(LogStatus.INFO,FieldName+" not found in page ");
			return false;
		}
		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}

	}



	//<Method Name>switchtoFrame</Method Name>
	//<Description>Switch to a frame across windows based on element present in it</Description>
	//<Arguments>elementname,xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean switchToFrameInsideWindow(String elementname, String xpath) {
		Set<String> windowHandles = driver.getWindowHandles();

		for(String handle : windowHandles){  
			driver.switchTo().window(handle);  
			try{

				switchtoFrame(elementname,xpath);

				return true; 
			} 
			catch(Exception e){

			}
			driver.switchTo().defaultContent();

		}
		test.log(LogStatus.INFO,"Frame having element "+elementname+" not found ");
		Errormessage=Errormessage+"\n"+"Frame having element "+elementname+" not found ";
		return false;

	}



	//<Method Name>switchtoFrame</Method Name>
	//<Description>Switch to a frame across windows based on element present in it</Description>
	//<Arguments>elementname,xpath</Arguments>
	//<Author>Rashmi Kumari</Author>
	//<Version>1.0</Version>

	public static boolean switchToWindow(String elementname, String xpath) {
		Set<String> windowHandles = driver.getWindowHandles();

		for(String handle : windowHandles){  
			driver.switchTo().window(handle);  
			try{

				driver.findElement(By.xpath(xpath)).getTagName();

				return true;


			} 
			catch(Exception e){

			}
			driver.switchTo().defaultContent();

		}
		test.log(LogStatus.INFO,elementname+" not found ");
		Errormessage=Errormessage+"\n"+elementname+" not found";
		return false;

	}





	public static boolean switchToAlertWindow(String xpath) {

		try{
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath(xpath)).getTagName();
			return true;
		}
		catch(Exception e){
			return false;
		}



	}

	/*public static boolean switchToAlertWindow(String xpath) {

		Set<String> windowHandles = driver.getWindowHandles();

		for(String handle : windowHandles){  
			driver.switchTo().window(handle);  
			try{

				driver.findElement(By.xpath(xpath)).getTagName();

				return true;


			} 
			catch(Exception e){

			}
			driver.switchTo().defaultContent();

		}

		return false;

	}*/

	public static boolean clickAndVerify(String ElementToClick, String xpathToClick,
			String ElementToVerify, String xpathToVerify) {

		try{

			try{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToClick)));
			}
			catch(TimeoutException e){
				Errormessage=Errormessage+"\n"+ElementToClick+" not found in page ";
				System.out.println(ElementToClick+" not found in page ");
				test.log(LogStatus.INFO,ElementToClick+" not found in page ");
				return false;
			}

			if( driver.findElement(By.xpath(xpathToClick)).isDisplayed()){

				driver.findElement(By.xpath(xpathToClick)).click();
				test.log(LogStatus.PASS,ElementToClick+" clicked successfully ");
				System.out.println(ElementToClick+" clicked successfully ");
				WaitTime(5000);

				try{
					driver.switchTo().defaultContent();
					driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					driver.findElement(By.xpath(propFile.getProperty("Safety_AlertMsg"))).getTagName();



					return false;
				}
				catch(Exception e){
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					driver.switchTo().frame(0);

				}
				longwaitForProcessing(propFile.getProperty("Safety_processing_img"));

				try{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToVerify)));
				}
				catch(TimeoutException e){
					Errormessage=Errormessage+"\n"+ElementToVerify+" not displayed in page after clicking on "+ElementToClick;
					System.out.println(ElementToVerify+" not displayed in pageafter clicking on "+ElementToClick);
					test.log(LogStatus.INFO,ElementToVerify+" not displayed in page after clicking on "+ElementToClick);
					return false;

				}
				if( driver.findElement(By.xpath(xpathToVerify)).isDisplayed()){


					System.out.println(ElementToVerify+" displayed in page");
					return true;
				}
				return false;
			}
			return false;
		}
		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}

	}



	public void CloseDriver() {
		try{
			if(driver!=null){
				driver.quit();
			}
		}
		catch(Exception e){

		}
	}
	public static boolean selectIndexFromDropdown(String ElementName, String xpath,
			int index) {
		try{


			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			if( driver.findElement(By.xpath(xpath)).isDisplayed()){
				Select dropdown = new Select(driver.findElement(By.xpath(xpath)));
				dropdown.selectByIndex(index);
				WaitTime(4000);

			}
			System.out.println(index+"th indesx selected from dropdown" +ElementName);
			test.log(LogStatus.PASS, index+"th indesx selected from dropdown" +ElementName);
			return true;
		}
		catch(TimeoutException e){
			Errormessage=Errormessage+"\n"+ElementName+" not displayed in page ";
			test.log(LogStatus.INFO,ElementName+" not displayed in page ");
			return false;
		}

		catch(Exception e){
			Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
			e.printStackTrace();
			test.log(LogStatus.INFO,e.getLocalizedMessage());
			return false;
		}

	}

	public static boolean waitForSaveMessage(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		try{
			driver.findElement(By.xpath(xpath)).isDisplayed();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

			return true;
		}
		catch(Exception e){
			Errormessage=Errormessage+"Saved message not displyed";
			return false;

		}

	}

	public static boolean isChecked(String xpath) {
		if( driver.findElement(By.xpath(xpath)).isSelected()){
			return true;
		}
		else{
			return false;
		}
	}

	public static void ReinitializeErrorMessage() {
		Errormessage="";

	}


	public static boolean suppressAlert(String xpath1, String xpath2) {
		try{
			driver.findElement(By.xpath(xpath1)).isDisplayed();
			driver.findElement(By.xpath(xpath1)).click();

			return true;
		}
		catch(Exception e){

		}
		try{
			driver.findElement(By.xpath(xpath2)).isDisplayed();
			driver.findElement(By.xpath(xpath2)).click();

			return true;
		}
		catch(Exception e){
			return false;
		}
	}


	public static void switchToFrame(String frameId) {
		driver.switchTo().frame(frameId);

	}


	public static void switchToDefaultWindow() {
		driver.switchTo().defaultContent();

	}


	public static boolean switchToFrameUsingIndex(int index) {
		try{
			driver.switchTo().frame(index);
			return true;
		}
		catch(Exception e){
			return false;
		}

	}


}
