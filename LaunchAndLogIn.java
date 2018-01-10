package WebAutomation;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameters;
import com.opera.core.systems.internal.input.KeyEvent;

public class LaunchAndLogIn {
	private Keywords keywords;
	private WebDriver driver;
	private String url;
	private String username;
	private String password;
	private String browser;

	@Test
	public void LogIn() throws InterruptedException, IOException, AWTException {
		keywords = new Keywords();
		
		Thread.sleep(5000);
		driver.manage().window().maximize();
		Thread.sleep(5000);
		System.out.println(keywords.locator("Home.LogIn"));
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(keywords.locator("Home.LogIn"))));
		// driver.findElement(By.xpath(keywords.locator("Home.LogIn"))).click();
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(keywords.locator("Home.username"))));
		driver.findElement(By.xpath(keywords.locator("Home.username"))).sendKeys(username);
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(keywords.locator("Home.password"))));
		driver.findElement(By.xpath(keywords.locator("Home.password"))).sendKeys(password);
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(keywords.locator("Home.LogInLink"))));
		driver.findElement(By.xpath(keywords.locator("Home.LogInLink"))).click();
		System.out.println("You are successfully logged in to the application");
		Thread.sleep(5000);
		Reporter.log("Application launched successfully!");
		driver.findElement(By.xpath(keywords.locator("Home.MainSearchTextbox"))).sendKeys("mixer grinder 750w");
		Thread.sleep(2000);
		driver.findElement(By.xpath(keywords.locator("Home.MainSearchIcon"))).click();
		
		Thread.sleep(2000);
		List<WebElement> DrpdownList = driver.findElements(By.xpath(keywords.locator("Home.priceDrpDowns")));
		Select selMinPrice = new Select(DrpdownList.get(0));
		selMinPrice.selectByValue("2000");
		Thread.sleep(2000);
		Select selMaxPrice = new Select(DrpdownList.get(1));
		selMaxPrice.selectByValue("5000");
		Thread.sleep(2000);
		Reporter.log("Item is Searched");
		System.out.println("Mixer is Searched and flitered for the range 2000 to 5000");
		WebElement FirstMixer = driver.findElement(By.xpath(keywords.locator("Home.FirstItemName")));
		String title = FirstMixer.getAttribute("title");
		WebElement NumberofRatings = driver.findElement(By.xpath("//*[@class='_38sUEc']"));
		String numRatings = NumberofRatings.getText();
		WebElement price = driver.findElement(By.xpath("//*[@class='_1vC4OE']"));
		String actualPrice = price.getText();
		System.out.println("First Item is : "+title);
		System.out.println("Number of people rated for this Item : "+numRatings);
		System.out.println("First Item price is : "+actualPrice);
	}

	@org.testng.annotations.Parameters("browser")
	@BeforeTest
	
	public void beforeTest() throws IOException {
		String driverpath = "D:////RFM2_XBTA//CrossBrowser//CrossBrowser_WorkSpace//CrossBrowser_2.14_old//E-CommercePro//SetUp//chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", driverpath);
		driver = new ChromeDriver();

//		if(browser.equalsIgnoreCase("chrome")){
//		String driverpath = "D:////RFM2_XBTA//CrossBrowser//CrossBrowser_WorkSpace//CrossBrowser_2.14_old//E-CommercePro//SetUp//chromedriver.exe";
//		System.setProperty("webdriver.chrome.driver", driverpath);
//		driver = new ChromeDriver();
//		}else{
//		String driverpath = "D:////RFM2_XBTA//CrossBrowser//CrossBrowser_WorkSpace//CrossBrowser_2.14_old//E-CommercePro//SetUp//IEDriverServer.exe";
//		System.setProperty("webdriver.ie.driver", driverpath);
//		driver = new InternetExplorerDriver();
//		}
		String filepath = "D:////RFM2_XBTA//CrossBrowser//CrossBrowser_WorkSpace//CrossBrowser_2.14_old//E-CommercePro//properties.properties";
		File file = new File(filepath);
		FileInputStream fis = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fis);
		url = (String) prop.get("URL");
		username = (String) prop.get("UserName");
		password = (String) prop.get("Password");
		driver.get(url);
		// url = keywords.prortyreader("URL");
		// username = keywords.prortyreader("UserName");
		// password = keywords.prortyreader("Password");

	}

	@AfterTest
	public void afterTest() throws InterruptedException, IOException {

		try {
			WebElement link = driver.findElement(By.linkText("Hi Shivashankar!"));
			Actions builder = new Actions(driver);
			builder.moveToElement(link).perform();
			Thread.sleep(2000);
			WebElement web = driver.findElement(By.linkText("Log Out"));
			web.click();
			System.out.println("Successfully Logged Out");
			Reporter.log("Application is logged out!");
			Thread.sleep(5000);
		} catch (Exception e) {
		} finally {
			driver.quit();
		}
	}
}
