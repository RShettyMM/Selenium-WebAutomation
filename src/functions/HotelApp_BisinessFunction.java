package functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import jxl.*;
//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;


/**
 * @author RShetty
 * HotelApp_BisinessFunction contains all the function which can be reused 
 * or are used most often 
 *
 */
public class HotelApp_BisinessFunction 
{
	public static Properties prop;
	public static WebDriver driver;
	
	public static String sAppURL;
	public static String sSharedUIMapPath;
	public static String sFirefoxDriverPath;
	public static String sFirefoxProfileSeleniumPath;
	
	
	/**
	 * Performs the login activity for the hotel app ("Login Page") and verifies if login is successful
	 * @param driver : WebDriver 
	 * @param sUserName
	 * @param sPassword
	 * @throws InterruptedException: Thrown when a thread is waiting, sleeping, or otherwise occupied, 
	 *			 and the thread is interrupted, either before or during the activity
	 */
	public void HA_BF_Login (WebDriver driver, String sUserName, String sPassword ) throws InterruptedException
	{
		

		// Provide user name.
		driver.findElement(By.xpath(prop.getProperty("Txt_Login_Username"))).clear();
		driver.findElement(By.xpath(prop.getProperty("Txt_Login_Username"))).sendKeys(sUserName);

		// Provide Password.
		driver.findElement(By.xpath(prop.getProperty("Txt_Login_Password"))).clear();
		driver.findElement(By.xpath(prop.getProperty("Txt_Login_Password"))).sendKeys(sPassword);

		 // Click on Login button.
		driver.findElement(By.xpath(prop.getProperty("Btn_Login_Login"))).click();
		Thread.sleep(4000);

		//Verify for welcome message.
		try
		{

			WebElement welcomeTxt = driver.findElement(By.xpath(prop.getProperty("Lbl_SearchHotel_WelcomeMessage")));
			String text = welcomeTxt.getAttribute("value");

			if(text.contains("Hello "+ sUserName))
			{
				System.out.println("Login Test Pass for: "+ sUserName);
				return;
			}	
		}
		catch(Exception e)
		{
			//print out the programs execution stack 
			e.printStackTrace();
			System.out.println("Login Test Fail for: "+sUserName);
			// to show the results as fail.
			assert false;  	
		}
	}
	
	
	/**
	 * Actions related to the "Search Hotel" page 
	 * @param driver
	 * @param sLocation: Select the location from the dropdown list 
	 * @param sNoOfRooms: Select the No of rooms from the dropdown list
	 * @param AdultsPerRoom: Select the Adults per room from the dropdown list 
	 * @throws InterruptedException
	 */
	public void HA_BF_SearchHotel (WebDriver driver, String sLocation, String sNoOfRooms, String AdultsPerRoom ) throws InterruptedException 
	{
		//select Sydney option from the drop down list element Location  
	    new Select(driver.findElement(By.xpath(prop.getProperty("Lst_SearchHotel_Location")))).selectByVisibleText(sLocation);
	    new Select(driver.findElement(By.xpath(prop.getProperty("Lst_SearchHotel_NoOfRooms")))).selectByVisibleText(sNoOfRooms);
	    new Select(driver.findElement(By.xpath(".//*[@id='adult_room']"))).selectByVisibleText(AdultsPerRoom);
	    // click the button Submit 
	    driver.findElement(By.id(prop.getProperty("Btn_SearchHotel_Search"))).click();
	}
	
	
	/**
	 * This function is used initial setup before running the test (Setting firefox driver, firefox profile)
	 * @throws InterruptedException
	 * @throws FileNotFoundException: Signals that an attempt to open the file denoted by a specified pathname has failed. 
	 * @throws IOException: Signals that an I/O exception of some sort has occurred
	 */
	@SuppressWarnings("deprecation")
	public void HA_BF_Setup() throws InterruptedException, FileNotFoundException, IOException 
	{
		
		//The obj property class is used to store key/value pairs 
		prop = new Properties();
		//loads the key/value pair into the property obj from the stream
		prop. load(new FileInputStream("./Configration/HA_Configuration.properties"));
				
		sAppURL = prop.getProperty("AppUrl");
		sSharedUIMapPath = prop.getProperty("SharedUIMap");
		sFirefoxDriverPath = prop.getProperty("FirefoxDriver");
		sFirefoxProfileSeleniumPath = prop.getProperty("FirefoxProfileSelenium");
				
		prop.load(new FileInputStream(sSharedUIMapPath));
				
		//setting the Firefox driver 
		System.setProperty("webdriver.gecko.driver", sFirefoxDriverPath);
		
				
		//Profiling firefox browser to open the profile Selenium
		//In order to get the plugins like FireBug, FirePath after launching application 
		File file = new File(sFirefoxProfileSeleniumPath);
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		FirefoxProfile profile = new FirefoxProfile(file);
		dc.setCapability(FirefoxDriver.PROFILE, profile);
		driver = new FirefoxDriver(dc);
	}
	
	
	/**
	 * This generic function is used to read data from specified cell of Excel sheet once you give Excelsheet name and path
	 * @param row: row no or row from which you wish to fetch the data from 
	 * @param column: name of the column header from which you want to read data 
	 * @param strFilePath: path of the XL file containing the data 
	 * @return string containing data from the required cell
	 */
	public static String HA_GF_readXL (int row, String column, String strFilePath) 
	{
			Cell c= null;
			int reqCol=0;
			WorkbookSettings ws = null;
			Workbook workbook = null;
			Sheet sheet = null;
			FileInputStream fs = null;
	try{
		fs = new FileInputStream(new File(strFilePath));
		ws = new WorkbookSettings();
		ws.setLocale(new Locale("en", "EN"));
				
		// opening the work book and sheet for reading data
		workbook = Workbook.getWorkbook(fs, ws);
		sheet = workbook.getSheet(0);
		
		// Sanitise given data
		String col = column.trim();
		
		//loop for going through the given row
		for(int j=0; j<sheet.getColumns(); j++)
		{
			Cell cell = sheet.getCell(j,0);
			if((cell.getContents().trim()).equalsIgnoreCase(col))
			{	
				reqCol= cell.getColumn();
				//System.out.println("column No:"+reqCol);
				c = sheet.getCell(reqCol, row);
				fs.close();
				return c.getContents();
			}
		}
	}
	catch(BiffException be)
	{		
		System.out.println("The given file should have .xls extension.");
	}
	catch(Exception e)
	{
		e.printStackTrace();		
	}
	System.out.println("NO MATCH FOUND IN GIVEN FILE: PROBLEM IS COMING FROM DATA FILE");
	
	return null;
	}
	
	
	/**
	 * It fetches Rowcount from the excel sheet using two parameters 
	 * @param strFilePath: path of the XL file containing the data 
	 * @param sColumn: name of the column header from which you want to read data
	 * @return an integer containing the row count within the datasheet 
	 */
	public static int HA_GF_XLRowCount (String strFilePath, String sColumn)
	{
		int k;
		for (k = 1; k < 999; k++)
		{
		
		String sParamVal = 	HA_GF_readXL(k, sColumn, strFilePath);
			if (sParamVal.equals("ENDOFROW"))
				{
					break;
				}
		
		}
		
		return k;		
	}
	
	
	/**
	 * Function to dynamically wait for web element presence
	 * if the web element is found the function returns back to the main script 
	 * if it is not found it waits for the iSleep time and again searches for the element   
	 * the iSleeptime repeats until the element is found or the wait time exceeds iTimeOut  
	 * @param driver
	 * @param by: element id, Xpath, text etc
	 * @param iTimeOut: total amount of time to wait for 
	 * @throws InterruptedException
	 */
	public void HA_GF_WaitForElementPresent(WebDriver driver , By by, int iTimeOut) throws InterruptedException
	{
		int iTotal = 0;
		int iSleepTime = 1000;
		while(iTotal < iTimeOut)
		{
			List<WebElement> oWebElements = driver.findElements(by);
			if(oWebElements.size()>0)
				return;
			else
			{						
				Thread.sleep(iSleepTime);
				iTotal = iTotal + iSleepTime;
				System.out.println(String.format("Waited for %d milliseconds.[%s]", iTotal, by));          			
			}
		}
	}
	
	
	/**
	 * Function to dynamically wait for webelement to achieve its property value
	 * @param driver
	 * @param by
	 * @param ExpPropertyVal
	 * @param PropertName
	 * @param iTimeOut
	 * @throws InterruptedException
	 */
	public void HA_GF_WaitForPropertyValue(WebDriver driver , By by, String ExpPropertyVal, String PropertName,  int iTimeOut) throws InterruptedException
	{
		int iTotal = 0;
		int iSleepTime = 5000;
		while(iTotal < iTimeOut)
		{
			List<WebElement> oWebElements = driver.findElements(by);
			if(oWebElements.size()>0)
				
				for (WebElement weOption : oWebElements)
				{
					if(weOption.getAttribute(PropertName).equalsIgnoreCase(ExpPropertyVal))
					{
						return;
					}
					else
							
					{
						Thread.sleep(iSleepTime);
						iTotal = iTotal + iSleepTime;
						System.out.println(String.format("Waited for %d milliseconds.[%s]", iTotal, by));          
					}
								
				}
				else
				{
					Thread.sleep(iSleepTime);
					iTotal = iTotal + iSleepTime;
					System.out.println(String.format("Waited for %d milliseconds.[%s]", iTotal, by));          
				}
		}
	}
}
