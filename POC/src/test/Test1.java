package test;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;


public class Test1 {
	public static WebDriver driver; 
	
	@SuppressWarnings("deprecation")
	@Test
	public void CreateNewCase() throws Exception{
		try {
			//DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			System.setProperty("webdriver.gecko.driver", "D:/SeleniumProjects/geckodriver-v0.23.0-win64/geckodriver.exe");
			  DesiredCapabilities ffCaps; 
			   @SuppressWarnings("unused")
			Proxy proxy = new Proxy();
			   ffCaps = DesiredCapabilities.firefox(); 
			  // System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			
  
			   ffCaps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			 //driver = new FirefoxDriver(ffCaps);
			   System.setProperty("webdriver.chrome.driver", "D:/SeleniumProjects/chromedriver_win32/chromedriver.exe");
			driver = new ChromeDriver();
			//driver = new FirefoxDriver(new FirefoxOptions());//FirefoxDriver(DesiredCapabilities.firefox());
			driver.get("https://login.salesforce.com/?locale=in");
			driver.findElement(By.xpath("//input[@id='username']")).sendKeys("shravanece061@gmail.com");
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Infosys1234$");
		
			// UtilLibrary.clickOnElement("Login Button", "//input[@id='Login']",driver);
			driver.findElement(By.xpath("//input[@id='Login']")).click();;
			
			//xpath: //*[@class='featured-box']//*[text()='Testing']
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
