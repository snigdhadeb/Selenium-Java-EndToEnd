package util.utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;
import regression.magentoluma.tests.BaseTestAT;

public class TestAllureListener implements ITestListener {


	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult iTestResult) {
		WebDriver driver = BaseTestAT.driver;
		if(driver!=null) {
			saveScreenshotPNG(driver);
		}
		saveTextLog(getTestMethodName(iTestResult)+"failed and screenshot is taken!");
		System.out.println(getTestMethodName(iTestResult)+" failed");
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}
	
	/**
	* @author
	* Description : This function will capture screenshot
	* @param
	* @return byte[]
	*/
	
	@Attachment(value="Page Screenshot", type="image/png")
	public byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	}
	
	/**
	* @author
	* Description : This function will add the error log when a test case is failed
	* @param
	* @return error message as String
	*/
	@Attachment(value="{0}", type="text/plain")
	public static String saveTextLog(String message) {
		return message;
	}

}
