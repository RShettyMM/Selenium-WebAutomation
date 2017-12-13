package tests;

import functions.HotelApp_BisinessFunction;

//import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * @author RShetty
 *Performs the automation test of the HotelApp 
 *This class extends the HotelApp_BisinessFunction from the functions package 
 */
public class DynamicUIObjectTest extends HotelApp_BisinessFunction
{	
	/**
	 * Initial setup
	 * It calls the function HA_CF_Setup from HotelApp_BisinessFunction class 
	 * @throws Exception: The class Exception and its subclasses are a form of Throwable that indicates 
	 * 			conditions that a reasonable application might want to catch.
	 */
	@BeforeMethod
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
				    
	    String strFile = "./DataPool/HA_HotelSearch.xls";
	    String strLocation = HA_GF_readXL(1, "Location", strFile);
	    
	    //call to searchHotel function 	    
	    HA_BF_SearchHotel(driver, strLocation, "2 - Two", "2 - Two");
	    
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
	    
	    //getting the generated order number from the GUI text box order number 
	    //drawback: its not a dynamic wait i.e it will wait for 10sec even if the task is completed in 5sec 
	    Thread.sleep(10000);
	    //gets the order number 
	    String strOrderNo = driver.findElement(By.id(prop.getProperty("Txt_BookHotel_OrderOn"))).getAttribute("value");
	    System.out.println("Generated order number is: " + strOrderNo);
	    
	    //code added for dynamic UI obj test 
	    //Click My Iteration button 
	    driver.findElement(By.id(prop.getProperty("Btn_BookHotel_MyIteration"))).click();
	    //enter the order no in the search order Id text box
	    driver.findElement(By.id(prop.getProperty("Txt_BookedItinerary_SearchOrderId"))).sendKeys(strOrderNo);
	    //click Go button 
	    driver.findElement(By.id(prop.getProperty("Btn_BookedItinerary_Go"))).click();
	    
	    //driver.findElement(By.xpath(".//*[@value='Cancel 7HLJ7FNDV7']")).click();
	    //here we are clicking the dynamic cancel button by xPath value as it changes at 
	    //run time and get extension order no hence we add the order no string 
	    driver.findElement(By.xpath(".//*[@value='Cancel "+strOrderNo+"']")).click();
	    
	    //Press ok on the confirmation pop-up 
	    Alert javascriptAlert = driver.switchTo().alert();
	    System.out.println(javascriptAlert.getText()); //get the text on alert box
	    javascriptAlert.accept();
	    
	    //Click on logout button 
	    driver.findElement(By.linkText(prop.getProperty("Lnk_BookHotel_Logout"))).click();
	    //Click on Click here to login again 
	    driver.findElement(By.linkText(prop.getProperty("Lnk_Logout_ClickHereToLogoutAgain"))).click(); 
	}
}
