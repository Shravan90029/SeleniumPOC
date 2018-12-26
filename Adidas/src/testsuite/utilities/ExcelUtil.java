package testsuite.utilities;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class ExcelUtil {

	
	  private Map<String,Object> map=new HashMap<String,Object>() ;
	  public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	
	public static HashMap<String, Object> DataEngine(String Sheet, String TestMethodName ) throws IOException{
		HashMap<String, HashMap<String, Object>> Mainmap= new HashMap<String,HashMap<String, Object>>();
		
		FileInputStream fileInputStream = new FileInputStream("Inputdata.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream); 
		XSSFSheet worksheet = workbook.getSheet(Sheet); 
		XSSFRow row = worksheet.getRow(0);
		String abc="";
		try{
		
	
		 String valPointer="";
		
		//Map<String,Map<String,Object>> map= new HashMap<String,Map<String,Object>>();
		
	
	
		
		for(int j=1;j<=worksheet.getLastRowNum();j++){
			XSSFRow rowPointer = worksheet.getRow(j);
			XSSFCell cellPointer= rowPointer.getCell(1); 
		
			if( !cellPointer.getStringCellValue().equalsIgnoreCase(null)){
				//for(int k=0;k<=worksheet.getLastRowNum();k++){
					if( cellPointer.getStringCellValue().toString().equalsIgnoreCase(TestMethodName)){
			
						 HashMap<String,Object>  map= new HashMap<String,Object>();
					for (int i=0;i<row.getLastCellNum();i++){
								try {
									
									XSSFCell cell= row.getCell(i);
									String val=cell.getStringCellValue();
									XSSFCell cell1= rowPointer.getCell((short)i); 
									String val1=cell1.getStringCellValue();
									map.put(val, val1);
									/*System.out.println("hello world");
									abc=rowPointer.getCell(0).getStringCellValue()+"$"+rowPointer.getCell(1).getStringCellValue();
									Mainmap.put(abc, (HashMap<String, Object>) map);
									
									System.out.println(abc+"Mainmap"+Mainmap+Mainmap.size());*/
								
									
								} catch (Exception e) {
									e.printStackTrace();
									e.getMessage();
								}
								
							
						}
								
							
					return map;			
								
								
				}
					
			}
		}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
		
	}
	
	
	public static  HashMap<String, HashMap<String, Object>> DataEngineDbUpdated(String Sheet, String TestMethodName ) throws IOException{
		HashMap<String, HashMap<String, Object>> Mainmap= new HashMap<String,HashMap<String, Object>>();
		
		FileInputStream fileInputStream = new FileInputStream("Inputdata.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream); 
		XSSFSheet worksheet = workbook.getSheet(Sheet); 
		XSSFRow row = worksheet.getRow(0);
		String abc="";
		try{
		
	
		 String valPointer="";
		
		//Map<String,Map<String,Object>> map= new HashMap<String,Map<String,Object>>();
		
	
	
		
		for(int j=1;j<=worksheet.getLastRowNum();j++){
			XSSFRow rowPointer = worksheet.getRow(j);
			XSSFCell cellPointer= rowPointer.getCell(1); 
		
			if( !cellPointer.getStringCellValue().equalsIgnoreCase(null)){
				//for(int k=0;k<=worksheet.getLastRowNum();k++){
					if( cellPointer.getStringCellValue().toString().equalsIgnoreCase(TestMethodName)){
			
						 HashMap<String,Object>  map= new HashMap<String,Object>();
					for (int i=0;i<row.getLastCellNum();i++){
								try {
									
									XSSFCell cell= row.getCell(i);
									String val=cell.getStringCellValue();
									XSSFCell cell1= rowPointer.getCell((short)i); 
									String val1=cell1.getStringCellValue();
									map.put(val, val1);
									/*System.out.println("hello world");
									abc=rowPointer.getCell(0).getStringCellValue()+"$"+rowPointer.getCell(1).getStringCellValue();
									Mainmap.put(abc, (HashMap<String, Object>) map);
									
									System.out.println(abc+"Mainmap"+Mainmap+Mainmap.size());*/
								
									
								} catch (Exception e) {
									e.getMessage();
								}
								
								
						}
								
							
								abc=rowPointer.getCell(0).getStringCellValue()+"$"+rowPointer.getCell(1).getStringCellValue();
								Mainmap.put(abc, (HashMap<String, Object>) map);
								
								
				}
					
			}
		}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return Mainmap;
		
	}
	 public static void main(String[] args){
		 
	 }
}
