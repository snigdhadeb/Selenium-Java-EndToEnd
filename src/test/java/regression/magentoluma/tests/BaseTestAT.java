package regression.magentoluma.tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import io.qameta.allure.Step;
import util.utilities.JsonDataHandler;
import util.utilities.LogUtil;

public class BaseTestAT {
	private static Properties prop=null;
	public static WebDriver driver=null;
	public static String testcasename;
	public static String test_type;
	public static String test_env;
	protected static String test_username;
	protected static String test_password;
	
	
	/**
	* @author: Snigdhadeb Samanta
	* Description : This method reads the complete Json data and stores it in a JsonObject
	* @param 
	* @throws FileNotFoundException 
	*/
	@BeforeSuite
	@Step("*** Reading the testdata ***")
	public void setJsonObject() {
		JsonDataHandler.jsonObject = JsonDataHandler.readJsonFile();
	}
	
	
	/**
	* @author: Snigdhadeb Samanta
	* Description : This method reads global data from properties file which will be used to configure testcases
	* @param 
	* @throws FileNotFoundException 
	*/
	@BeforeSuite
	@Step("*** Reading the config file ***")
	public void readProperty() {
		prop = new Properties();
		try {
			FileReader reader = new FileReader(".//src//test//java//util//resources//config.properties");
			try {
				prop.load(reader);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* @author: Snigdhadeb Samanta
	* Description : This method sets the test-set-up instance variables
	* @param 
	* @throws 
	*/
	@BeforeSuite
	@Step("*** Setting the global variables ***")
	public void setTestSetUp() {
		test_type = prop.getProperty("TEST_TYPE");
		test_env = prop.getProperty("TEST_ENV");
		test_username = prop.getProperty("CUSTOMER_USERNAME");
		test_password = prop.getProperty("CUSTOMER_PASSWORD");
	}
	
	/**
	* @author: Snigdhadeb Samanta
	* Description : This method sets the test-set-up instance variables
	* @param browsername set from <parameter> in testng xml file
	* @throws 
	*/
	@BeforeClass
	@Parameters("browser")
	public static void setDriver(String browsername) {
		switch(test_env.toLowerCase()) {
		case "local":
			setupLocalBrowser(browsername.toLowerCase());
			break;
		case "grid":
			setUpGrid();
			break;
		default: 
			new Exception("Test Env is not set");
		}
		
	}


	

	/**
	* @author: Snigdhadeb Samanta
	* Description : This method sets the local browsers
	* @param : browsername coming from <parameters> in Testng xml file
	* @throws 
	*/
	@Step("*** Now Setting up local browser: {0} ***")
	public static void setupLocalBrowser(String browsername) {
		switch(browsername) {
		case "chrome":
			if(driver==null) {
				ChromeOptions option = new ChromeOptions();
				option.addArguments("--incognito");
				option.addArguments("--remote-allow-origins=*");
				//option.addArguments("--headless");
				option.setAcceptInsecureCerts(true);
				option.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				option.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
				driver = new ChromeDriver(option);
				driver.get(prop.getProperty("CUSTOMER_URL"));
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				LogUtil.log(browsername+" is launched");
				break;
			}
		case "firefox":
			if(driver==null) {
				FirefoxOptions option = new FirefoxOptions();
				option.addArguments("-private");
				//option.addArguments("-headless");
				option.setAcceptInsecureCerts(true);
				option.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				option.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
				driver = new FirefoxDriver(option);
				driver.get(prop.getProperty("CUSTOMER_URL"));
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				LogUtil.log(browsername+" is launched");
				break;
			}
		case "edge":
			if(driver==null) {
				EdgeOptions option = new EdgeOptions();
				option.addArguments("-inprivate");
				//option.addArguments("headless");
				option.setAcceptInsecureCerts(true);
				option.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				option.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
				driver = new EdgeDriver(option);
				driver.get(prop.getProperty("CUSTOMER_URL"));
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				LogUtil.log(browsername+" is launched");
				break;
			}
				
		default:
				new Exception("No Browser Found");
		}
		
	}
	
	
	/**
	* @author: Snigdhadeb Samanta
	* Description : This method sets Selenium Grid
	* @param : 
	* @throws 
	*/
	private static void setUpGrid() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	@AfterClass
	@Step("*** Closing the Browser ***")
	public void tearDown() {
		driver.close();
		driver=null;
	}
}
