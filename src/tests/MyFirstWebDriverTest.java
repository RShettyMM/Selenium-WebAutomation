package tests;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.WebDriver;


public class MyFirstWebDriverTest {
		
	  private WebDriver driver;
	  private String baseUrl;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
		Properties prop = new Properties();
		prop.load(new FileInputStream("./SharedUIMap/SharedUIMap.properties"));
			
		System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\RShetty\\eclipse\\geckodriver.exe");
	    driver = new FirefoxDriver();
	    baseUrl = "http://www.adactin.com/HotelApp/index.php";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testIDEVerificationScriptJUnit4() throws Exception {
	    driver.get(baseUrl + "/HotelApp/index.php");
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("RahulShetty");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("Start123.");
	    driver.findElement(By.id("login")).click();
	    assertTrue(isElementPresent(By.linkText("Logout")));
	    new Select(driver.findElement(By.id("location"))).selectByVisibleText("Sydney");
	    new Select(driver.findElement(By.id("room_nos"))).selectByVisibleText("2 - Two");
	    new Select(driver.findElement(By.id("adult_room"))).selectByVisibleText("2 - Two");
	    driver.findElement(By.id("Submit")).click();
	    driver.findElement(By.id("radiobutton_1")).click();
	    driver.findElement(By.id("continue")).click();
	    driver.findElement(By.id("first_name")).clear();
	    driver.findElement(By.id("first_name")).sendKeys("Rahul");
	    driver.findElement(By.id("last_name")).clear();
	    driver.findElement(By.id("last_name")).sendKeys("Shetty");
	    driver.findElement(By.id("address")).clear();
	    driver.findElement(By.id("address")).sendKeys("AlleenStr 38\n78054, Villingen");
	    driver.findElement(By.id("cc_num")).clear();
	    driver.findElement(By.id("cc_num")).sendKeys("1234567891234567");
	    new Select(driver.findElement(By.id("cc_type"))).selectByVisibleText("American Express");
	    new Select(driver.findElement(By.id("cc_exp_month"))).selectByVisibleText("January");
	    new Select(driver.findElement(By.id("cc_exp_year"))).selectByVisibleText("2020");
	    driver.findElement(By.id("cc_cvv")).clear();
	    driver.findElement(By.id("cc_cvv")).sendKeys("213");
	    driver.findElement(By.id("book_now")).click();
	    driver.findElement(By.linkText("Logout")).click();
	    driver.findElement(By.linkText("Click here to login again")).click();
	  }
	

	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }
	
}

/*
public class MyFirstWebDriverTest {
	static private WebDriver driver;
	static private String baseUrl;

	public static void main(String[] args)
	{
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\RShetty\\eclipse\\geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "http://www.adactin.com/HotelApp/index.php";
		driver.get(baseUrl);
		
		driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("RahulShetty");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("Start123.");
	    driver.findElement(By.id("login")).click();
	    assertTrue(isElementPresent(By.linkText("Logout")));
	    new Select(driver.findElement(By.id("location"))).selectByVisibleText("Sydney");
	    new Select(driver.findElement(By.id("room_nos"))).selectByVisibleText("2 - Two");
	    new Select(driver.findElement(By.id("adult_room"))).selectByVisibleText("2 - Two");
	    driver.findElement(By.id("Submit")).click();
	    driver.findElement(By.id("radiobutton_1")).click();
	    driver.findElement(By.id("continue")).click();
	    driver.findElement(By.id("first_name")).clear();
	    driver.findElement(By.id("first_name")).sendKeys("Rahul");
	    driver.findElement(By.id("last_name")).clear();
	    driver.findElement(By.id("last_name")).sendKeys("Shetty");
	    driver.findElement(By.id("address")).clear();
	    driver.findElement(By.id("address")).sendKeys("AlleenStr 38\n78054, Villingen");
	    driver.findElement(By.id("cc_num")).clear();
	    driver.findElement(By.id("cc_num")).sendKeys("1234567891234567");
	    new Select(driver.findElement(By.id("cc_type"))).selectByVisibleText("American Express");
	    new Select(driver.findElement(By.id("cc_exp_month"))).selectByVisibleText("January");
	    new Select(driver.findElement(By.id("cc_exp_year"))).selectByVisibleText("2020");
	    driver.findElement(By.id("cc_cvv")).clear();
	    driver.findElement(By.id("cc_cvv")).sendKeys("213");
	    driver.findElement(By.id("book_now")).click();
	    driver.findElement(By.linkText("Logout")).click();
	    driver.findElement(By.linkText("Click here to login again")).click();
	}
	
	private static boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }
}
*/