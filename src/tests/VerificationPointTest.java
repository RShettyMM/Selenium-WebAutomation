package tests;

import java.io.File;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VerificationPointTest
{
	public WebDriver driver;
	public String baseUrl; 
	
	@SuppressWarnings("deprecation")
	@BeforeMethod
	public void setUp()
	{
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
		driver.findElement(By.xpath(".//*[@id='username']")).clear();
		//provide admin username 
	    driver.findElement(By.xpath(".//*[@id='username']")).sendKeys("RahulShetty");
	    
	    //provide password 
	    driver.findElement(By.xpath(".//*[@id='password']")).clear();
	    driver.findElement(By.xpath(".//*[@id='password']")).sendKeys("Start123.");
	    
	    //Click on login button 
	    driver.findElement(By.xpath(".//*[@id='login']")).click();
	    
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
	    new Select(driver.findElement(By.xpath(".//*[@id='location']"))).selectByVisibleText("Sydney");
	    new Select(driver.findElement(By.xpath(".//*[@id='room_nos']"))).selectByVisibleText("2 - Two");
	    new Select(driver.findElement(By.xpath(".//*[@id='adult_room']"))).selectByVisibleText("2 - Two");
	    // click the button Submit 
	    driver.findElement(By.id("Submit")).click();
	    
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
	    driver.findElement(By.id("radiobutton_1")).click();
	    // click continue button 
	    driver.findElement(By.id("continue")).click();
	    //clear all the content in the text box element first_name 
	    driver.findElement(By.id("first_name")).clear();
	    // enter first name in the textbox 
	    driver.findElement(By.id("first_name")).sendKeys("Rahul");
	    driver.findElement(By.id("last_name")).clear();
	    driver.findElement(By.id("last_name")).sendKeys("Shetty");
	    driver.findElement(By.id("address")).clear();
	    driver.findElement(By.id("address")).sendKeys("AlleenStr 38\n78054, Villingen");
	    driver.findElement(By.id("cc_num")).clear();
	    driver.findElement(By.id("cc_num")).sendKeys("1234567891234567");
	    //select American Express option from the drop down list element credit card type
	    new Select(driver.findElement(By.id("cc_type"))).selectByVisibleText("American Express");
	    new Select(driver.findElement(By.id("cc_exp_month"))).selectByVisibleText("January");
	    new Select(driver.findElement(By.id("cc_exp_year"))).selectByVisibleText("2020");
	    //clear all the content in the text box element ccv number 
	    driver.findElement(By.id("cc_cvv")).clear();
	    // Enter 213 in the CCV Number text box
	    driver.findElement(By.id("cc_cvv")).sendKeys("213");
	    //Click book now button 
	    driver.findElement(By.id("book_now")).click();
	    //Click on logout button 
	    driver.findElement(By.linkText("Logout")).click();
	    //Click on Click here to login again 
	    driver.findElement(By.linkText("Click here to login again")).click();
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
