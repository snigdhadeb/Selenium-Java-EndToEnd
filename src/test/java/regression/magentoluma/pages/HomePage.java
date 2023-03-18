package regression.magentoluma.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.qameta.allure.Step;
import util.utilities.LogUtil;

public class HomePage extends BasePage{
	
	WebDriver driver;
	public HomePage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//a[normalize-space(text())='Sign In']")
	private WebElement signin_link;
	
	@FindBy(id="search")
	private WebElement searchbar;
	
	@FindBy(css="#search_autocomplete li")
	private WebElement search_autocomplete_list;
	
	
	@Step("Clicking on Sign In Link")
	public LoginPage click_signin() {
		click(signin_link);
		return new LoginPage(driver);
	}
	
	@Step("Searching for product Type: {0} in the searchbar")
	public SearchResultPage searchProduct(String producttype) {
		click(searchbar);
		setText(searchbar, producttype);
		conditionalWaitonLoad(driver, search_autocomplete_list);
		click(search_autocomplete_list);
		return new SearchResultPage(driver);
	}

	public boolean isLandedOnHomePage() {
		if(driver.getTitle().equals("Home Page")) {
			LogUtil.log("Home Page is displayed successfully");
			return true;
		}else
			return false;
	}

}
