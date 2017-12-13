package tests;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SharedUIMapTest
{
	public WebDriver driver;
	public String baseUrl; 
	public Properties prop;
	
	@SuppressWarnings("deprecation")
	@BeforeMethod
	public void setUp () throws Exception 
	{
		//The obj property class is used to store key/value pairs 
		prop = new Properties();
		//loads the key/value pair into the property obj from the stream 
		prop.load(new FileInputStream("./SharedUIMap/SharedUIMap.properties"));
		
		//setting the Firefox driver 
		//"user.dir" points to the current project folder 
		System.setProperty("webdriver.gecko.driver", 
				System.getProperty("user.dir") + "\\FirefoxDriver\\geckodriver.exe");
		
		//Profiling firefox browser to open the profile Selenium
		//In order to get the plugins like FireBug, FirePath after launching application 
		File file = new File("C:\\Users\\RShetty\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\c0aem1ah.Selenium");
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		FirefoxProfile profile = new FirefoxProfile(file);
		dc.setCapability(FirefoxDriver.PROFILE, profile);
		driver = new FirefoxDriver(dc);
		baseUrl = "http://www.adactin.com/HotelApp/index.php";
	}
	
	@Test
	public void testWebHotelApp() 
	{
		//launch the base url in firefox browser 
		driver.get(baseUrl);
		
		//clear all the content from the username text box 
		//By.xpath(".//*[@id='username']")) - helps to locate element based on xpath 
		driver.findElement(By.xpath(prop.getProperty("Txt_Login_Username"))).clear();
		//provide admin username 
	    driver.findElement(By.xpath(prop.getProperty("Txt_Login_Username"))).sendKeys("RahulShetty");
	    
	    //provide password 
	    driver.findElement(By.xpath(prop.getProperty("Txt_Login_Password"))).clear();
	    driver.findElement(By.xpath(prop.getProperty("Txt_Login_Password"))).sendKeys("Start123.");
	    
	    //Click on login button 
	    driver.findElement(By.xpath(prop.getProperty("Btn_Login_Login"))).click();
	    
	    //checks if the logout button is present 
	    assertTrue(isElementPresent(By.linkText("Logout")));
	    
	    // verify sucessfull login 
	    String sWelcometext = driver.findElement(By.xpath(".//*[@id='username_show']")).getAttribute("value");
	    if (sWelcometext.equalsIgnoreCase("Hello RahulShetty!"))
	    {
	    	System.out.println("Login Test Pass");
	    }
	    else 
	    {
	    	System.out.println("Login Test Fail");
	    }
	    
	    //select Sydney option from the drop down list element Location  
	    new Select(driver.findElement(By.xpath(prop.getProperty("Lst_SearchHotel_Location")))).selectByVisibleText("Sydney");
	    new Select(driver.findElement(By.xpath(prop.getProperty("Lst_SearchHotel_NoOfRooms")))).selectByVisibleText("2 - Two");
	    new Select(driver.findElement(By.xpath(".//*[@id='adult_room']"))).selectByVisibleText("2 - Two");
	    // click the button Submit 
	    driver.findElement(By.id(prop.getProperty("Btn_SearchHotel_Search"))).click();
	    
	    //Insert validation point (to check that the selected location in search hotel page shows same location 
	    //in the select hotel page 
	    //driver.findElement - Helps to find the element based on location 
	    //By.xpath(".//*[@id='radiobutton_1']")) - helps to locate element based on xpath 
	    //getAttribute("value") - gets the desired property value of web element.   
	    String slocation = driver.findElement(By.xpath(".//*[@id='radiobutton_1']")).getAttribute("value");
	    
	    // comparing with the expected value 
	    if (slocation.equalsIgnoreCase("Sydney"))
	    {
	    	System.out.println("The search results are correct");
	    }
	    else 
	    {
	    	System.out.println("The search results are incorrect");
	    }
	    
	    //select the radiobutton 
	    driver.findElement(By.id(prop.getProperty("Rad_SelectHotel_RadioButton_1"))).click();
	    // click continue button 
	    driver.findElement(By.id(prop.getProperty("Btn_SelectHotel_Continue"))).click();
	    //clear all the content in the text box element first_name 
	    driver.findElement(By.id(prop.getProperty("Txt_BookHotel_FirstName"))).clear();
	    driver.findElement(By.id(prop.getProperty("Txt_BookHotel_FirstName"))).sendKeys("test");
	    driver.findElement(By.id(prop.getProperty("Txt_BookHotel_LastName"))).clear();
	    driver.findElement(By.id(prop.getProperty("Txt_BookHotel_LastName"))).sendKeys("test");
	    driver.findElement(By.id(prop.getProperty("Txt_BookHotel_Address"))).clear();
	    driver.findElement(By.id(prop.getProperty("Txt_BookHotel_Address"))).sendKeys("test");
	    driver.findElement(By.id(prop.getProperty("Txt_BookHotel_CCNumber"))).clear();
	    driver.findElement(By.id(prop.getProperty("Txt_BookHotel_CCNumber"))).sendKeys("1212121212121212");
	    
	    //select American Express option from the drop down list element credit card type
	    new Select(driver.findElement(By.id(prop.getProperty("Lst_BookHotel_CCtype")))).selectByVisibleText("American Express");
	    new Select(driver.findElement(By.id(prop.getProperty("Lst_BookHotel_CCExpMonth")))).selectByVisibleText("March");
	    new Select(driver.findElement(By.id(prop.getProperty("Lst_BookHotel_CCExpYear")))).selectByVisibleText("2018");
	    //clear all the content in the text box element ccv number 
	    driver.findElement(By.id(prop.getProperty("Txt_BookHotel_CCCvvNumber"))).clear();
	    // Enter 213 in the CCV Number text box
	    driver.findElement(By.id(prop.getProperty("Txt_BookHotel_CCCvvNumber"))).sendKeys("111");
	    //Click book now button 
	    driver.findElement(By.id(prop.getProperty("Btn_BookHotel_BookNow"))).click();
	    //Click on logout button 
	    driver.findElement(By.linkText(prop.getProperty("Lnk_BookHotel_Logout"))).click();
	    //Click on Click here to login again 
	    driver.findElement(By.linkText(prop.getProperty("Lnk_Logout_ClickHereToLogoutAgain"))).click(); 
	}
	
	
	private boolean isElementPresent(By by) 
	{
	    try 
	    {
	      driver.findElement(by);
	      return true;
	    }
	    catch (NoSuchElementException e)
	    {
	      return false;
	    }
	}
}
