package tests;

import functions.HotelApp_BisinessFunction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;



/**
 * @author RShetty
 *Performs the automation test of the HotelApp 
 *This class extends the HotelApp_BisinessFunction from the functions package 
 */
public class JUnit_FW_Final_HotelApp_Automation extends HotelApp_BisinessFunction
{	
	/**
	 * Initial setup
	 * It calls the function HA_CF_Setup from HotelApp_BisinessFunction class 
	 * @throws Exception: The class Exception and its subclasses are a form of Throwable that indicates 
	 * 			conditions that a reasonable application might want to catch.
	 */
	@Before
	public void setUp () throws Exception 
	{
		HA_BF_Setup();
	}
	
	/**
	 * Testing of hotel app (it contains all the test cases)
	 * @throws Exception
	 */
	@Test
	public void testWebHotelApp() throws Exception  
	{
		//launch the base url in firefox browser 
		driver.get(sAppURL);
				   
		//Call to login function 
		HA_BF_Login(driver, "RahulShetty", "Start123.");
				
	    //checks if the logout button is present 
	    assertTrue(isElementPresent(By.linkText("Logout")));
	    
	    // verify successful login 
	    String sWelcometext = driver.findElement(By.xpath(".//*[@id='username_show']")).getAttribute("value");
	    if (sWelcometext.equalsIgnoreCase("Hello RahulShetty!"))
	    {
	    	System.out.println("Login Test Pass");
	    }
	    else 
	    {
	    	System.out.println("Login Test Fail");
	    }
	      
	    //call to searchHotel function 	    
	    HA_BF_SearchHotel(driver, "Sydney", "2 - Two", "2 - Two");
	    
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
	
	
	/**
	 * Checks for the particular element or object exists 
	 * @param by: id, linkText, Title etc 
	 * @return true or False 
	 */
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
