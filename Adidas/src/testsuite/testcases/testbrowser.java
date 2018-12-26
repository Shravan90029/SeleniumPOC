package testsuite.testcases;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;



public class testbrowser {
	@Test
	public void test() {File file = new File("drivers\\IEDriverServer.exe");


	DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
	//Ignoring security domains so that WebDriver can work on IE
	ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	//Specifying the path of the IE Driver executable (here the driver is placed at D:\Selenium)
	System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
	//To make sure the driver works on all IE supported Platforms
	ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	//driver = new InternetExplorerDriver(ieCapabilities);

	
	WebDriver driver = new InternetExplorerDriver(ieCapabilities);
	driver.get("https://adidas--uatfinal.cs87.my.salesforce.com/");
	}
	

}
