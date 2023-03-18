package regression.magentoluma.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.qameta.allure.Step;
import util.utilities.LogUtil;

public class LoginPage extends BasePage{
	
	WebDriver driver;
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".page-title")
	private WebElement page_header;
	
	@FindBy(id="email")
	private WebElement input_email;
	
	@FindBy(id="pass")
	private WebElement input_password;
	
	@FindBy(id="send2")
	private WebElement signin_button;
	
	public boolean isLandedOnLoginPage() {
		conditionalWaitonLoad(driver, page_header);
		if(page_header.getText().equals("Customer Login")) {
			LogUtil.log("Login page is displayed successfully");
			return true;
		}else
			return false;
	}
	
	@Step("Entering email: {0}")
	public LoginPage enterEmail(String emailid) {
		setText(input_email, emailid);
		return this;
	}
	
	@Step("Entering password: {0}")
	public LoginPage enterPassword(String password) {
		setText(input_password, password);
		return this;
	}
	
	@Step("Clicking on Sign-in Button")
	public HomePage clickSignIn() {
		click(signin_button);
		return new HomePage(driver);
	}
	

}
