package tests;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Nicos {
  private WebDriver driver;
  private String baseUrl;
  private String sFirefoxDriverPath;
  private String sFirefoxProfileSeleniumPath;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\RShetty\\eclipse\\geckodriver.exe");
	File file = new File("C:\\Users\\RShetty\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\c0aem1ah.Selenium");
	DesiredCapabilities dc = DesiredCapabilities.firefox();
	FirefoxProfile profile = new FirefoxProfile(file);
	dc.setCapability(FirefoxDriver.PROFILE, profile);
	FirefoxDriver driver = new FirefoxDriver(dc);
	driver.get("https://public.mm-software.com/nicoswebdemo/trunk/");
	/*  
    driver = new FirefoxDriver();
    baseUrl = "https://public.mm-software.com/nicoswebdemo/trunk/";
    sFirefoxDriverPath = "./FirefoxDriver/geckodriver.exe";
    sFirefoxProfileSeleniumPath = "C:\\Users\\RShetty\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\c0aem1ah.Selenium";
    
    //setting the Firefox driver 
  	System.setProperty("webdriver.gecko.driver", sFirefoxDriverPath);
  		
  				
  	//Profiling firefox browser to open the profile Selenium
  	//In order to get the plugins like FireBug, FirePath after launching application 
  	File file = new File(sFirefoxProfileSeleniumPath);
  	DesiredCapabilities dc = DesiredCapabilities.firefox();
  	FirefoxProfile profile = new FirefoxProfile(file);
  	dc.setCapability(FirefoxDriver.PROFILE, profile);
  	driver = new FirefoxDriver(dc);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    */
  }

  @Test
  public void testNicos() throws Exception {
    //driver.get("https://public.mm-software.com/nicoswebdemo/trunk/");
    driver.findElement(By.xpath("//input[@type='text']")).clear();
    driver.findElement(By.xpath("//input[@type='text']")).sendKeys("MM");
    driver.findElement(By.xpath("//input[@type='password']")).clear();
    driver.findElement(By.xpath("//input[@type='password']")).sendKeys("test");
    driver.findElement(By.xpath("//div[@class='qx-button-box']")).click();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}