package regression.magentoluma.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.netty.util.internal.ThreadLocalRandom;
import io.qameta.allure.Step;
import util.utilities.JsonDataHandler;

public class BasePage {
	protected static WebDriverWait wait;
	protected JsonDataHandler utilities = new JsonDataHandler();
	
	public BasePage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
		wait = new WebDriverWait(driver,Duration.ofSeconds(5));
	}
	
	/**
	* @author
	* Description : This function will click on the element provided as an argument
	* @param WebElement
	*/
	@Step("Clicking on element {0}")
	public static void click(WebElement elementToClick)
	{
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(elementToClick)));
		wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
		elementToClick.click();
	}
	
	/**
	* @author
	* Description : This function will check for an alert.
	* @param WebElement
	* @return boolean
	*/
	@Step("Checking for Alert")
	public static boolean isAlertPresent()
	{
		try{
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		}
		catch(Exception e) 
		{
			return false;
		}
	}
	
	/**
	* @author
	* Description : This function will accept an alert.
	* @param WebElement
	* @return Alert
	*/
	@Step("Accepting the alert")
	public static Alert acceptAlert(WebDriver driver)
	{
		Alert alert;
		try{
			wait.until(ExpectedConditions.alertIsPresent());
			alert=driver.switchTo().alert();
			driver.switchTo().alert().accept();
		}
		catch(NoAlertPresentException e)
		{
			alert =null;
		}
		return alert;
	}
	
	/**
	* @author
	* Description : This function will select a dropdown value based on the index number.
	* @param 1. WebElement, 2. Index no
	* @return void
	*/
	@Step("Selecting {1} from the dropdown")
	public void selectDropdownIndex(WebElement dropdown, int value)
	{
		Select select = new Select(dropdown);
		select.selectByIndex(value);
	}
	
	/**
	* @author
	* Description : This function will select a dropdown value based on the visible text.
	* @param 1. WebElement, 2. Visible text as String
	* @return void
	*/
	@Step("Selecting {1} from the dropdown")
	public void selectDropdown(WebElement dropdown, String value)
	{
		Select select = new Select(dropdown);
		select.selectByVisibleText(value.trim());
	}
	
	/**
	* @author
	* Description : This function will return the selected value in a dropdown.
	* @param WebElement
	* @return String
	*/
	@Step("Getting the selected element from the dropdown: {0}")
	public String getSelectedOptionFromDropdown(WebElement dropdown)
	{
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(dropdown)));
		wait.until(ExpectedConditions.visibilityOf(dropdown));
		Select comboBox = new Select(dropdown);
		return comboBox.getFirstSelectedOption().getText();
	}
	
	/**
	* @author
	* Description : This function will enter text in an edit box.
	* @param 1. WebElement 2. Text to be entered
	* @return void
	*/
	@Step("Entering: {1} into edit field: {0}")
	public void setText(WebElement ele, String strText)
	{
		try{
			ele.click();
			ele.clear();
			ele.sendKeys(strText);
		}
		catch(ElementNotInteractableException en)
		{
			en.printStackTrace();
		}
		catch(StaleElementReferenceException s)
		{
			s.printStackTrace();
		}
	}
	
	/**
	* @author
	* Description : This function will generate a random future date.
	* @param
	* @return
	*/
	public String futureDateMonth()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 30);
		String newDate = dateFormat.format(cal.getTime());
		return newDate;
	}
	
	/**
	* @author
	* Description : This function will generate a random integer in range.
	* @param 1. Lower range 2. Upper range
	* @return integer
	*/
	public int getRandomNumberInRange(int min, int max)
	{
		int finalreturnvalue=0;
		if(min>=max)
		{
			throw new IllegalArgumentException("max must be greater than min");
		}
		finalreturnvalue=ThreadLocalRandom.current().nextInt(min,max+1);
		return finalreturnvalue;
	}
	
	/**
	* @author
	* Description : This function will select a random dropdown option.
	* @param WebElement
	* @return void
	*/
	public void selectRandomDropdownValue(WebElement dropdown)
	{
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(dropdown)));
		Select select = new Select(dropdown);
		int size = select.getOptions().size();
		int ranValue = ThreadLocalRandom.current().nextInt(1,size);
		select.selectByIndex(ranValue);
	}
	
	/**
	* @author
	* Description : This function will wait for 20 secs for a given webelement.
	* @param 1. RemoteWebDriver instance, 2. WebElement
	* @return void
	*/
	public void conditionalWaitonLoad(WebDriver driver, WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	/**
	* @author
	* Description : This function will wait for 2 seconds.
	* @param
	* @return
	*/
	@Step("Short wait of 2 seconds")
	public static void shortWait()
	{
		long now = System.currentTimeMillis();
		long expectedTimeInMilli = now+2000;
		while(now < expectedTimeInMilli)
		{
			now = System.currentTimeMillis();
		}
	}
	
	/**
	* @author
	* Description : This function will wait for 10 seconds.
	* @param 
	* @return
	*/
	@Step("Short wait of 10 seconds for Windows Print Popup")
	public static void longWait()
	{
		long now = System.currentTimeMillis();
		long expectedTimeInMilli = now+10000;
		while (now < expectedTimeInMilli)
		{
			now = System.currentTimeMillis();
		}
	}
	
	/**
	* @author
	* Description : This function will generate the current date.
	* @param
	* @return void
	*/
	@Step("Generating current date")
	public static String currentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}
	
	/**
	* @author
	* Description : This function will generate random string with 5 characters.
	* @param
	* @return
	*/
	@Step("Generating Random String")
	public String generateRandomString()
	{
		char[] randomCharacters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		String outputString = "";
		StringBuilder stringBuilder = new StringBuilder();
		for (int i=0;i<5;i++)
		{
			char singleChar = randomCharacters[ThreadLocalRandom.current().nextInt(randomCharacters.length)];
			stringBuilder.append(singleChar);
			outputString = stringBuilder.toString();
		}
		return outputString;
	}
	
	/**
	* @author
	* Description : This function will generate a 10 digit random number which doesn't start with 0.
	* @param 
	* @return 10 digit number as String
	*/
	@Step("Generating random 10 digit number")
	public String generateTenDigitNumberAsString()
	{
		LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();
		LinkedList<Integer> list = new LinkedList<Integer>();
		while(set.size() < 10)
		{
			set.add(getRandomNumberInRange(0,9));
		}
		Iterator<Integer> it = set.iterator();
		while(it.hasNext())
		{
			list.add(it.next());
		}
		if(list.get(0)==0)
		{
			list.remove(0);
			list.add(1,0);
		}
		StringBuilder builder = new StringBuilder();
		for(int i:list)
		{
			builder.append(i);
		}
		return builder.toString();
	}
	
	/**
	* @author
	* Description : This function will wait for Jquery and Js to load completely.
	* @param 
	* @return boolean
	*/
	public static boolean waitForJStoLoad(WebDriver driver) {

	    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));

	    // wait for jQuery to load
	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        try {
	        	return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active"))==0;
	        }
	        catch (Exception e) {
	          return true;
	        }
	      }
	    };

	    // wait for Javascript to load
	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	    	  return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	      }
	    };

	  return wait.until(jQueryLoad) && wait.until(jsLoad);
	}
	
	public static void scrollIntoView(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public static void actionClick(WebDriver driver, WebElement element) {
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Actions action = new Actions(driver);
		action.moveToElement(element).click(element).build().perform();
	}
}
