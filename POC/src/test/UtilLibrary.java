package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

public class UtilLibrary {
	  public static  String Errormessage="";
	  public  WebDriver driver ;
	public static  boolean clickOnElement(String elementName,String xpath,WebDriver driver){
		  try{
		   
		   if( driver.findElement(By.xpath(xpath)).isDisplayed()){
		   
		    WebElement  click=driver.findElement(By.xpath(xpath));
		    
	
		    System.out.println(elementName+" clicked successfully ");
		    // 
		    return true;
		  }
		   return false;
		  }
		  catch(Exception e){
		   Errormessage=Errormessage+"\n"+e.getLocalizedMessage();
		   System.out.println(elementName+" not found");
		
		   return false;
		  }
}
}
