package regression.magentoluma.pages;

import java.util.List;

import org.openqa.selenium.By;
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
	
	
	@FindBy(xpath="//ul[@id='ui-id-2']/li/ul")
	private List<WebElement> sub_category_box;
	
	@FindBy(xpath="//a/span")
	private List<WebElement> sub_category_solid_options;
	
	@FindBy(xpath="//a/span[2]")
	private List<WebElement> sub_category_dropdown_options;
	
	@FindBy(xpath="//ul[@id='ui-id-2']/li/a//following-sibling::span")
	private List<WebElement> main_category_dropdown_options;
	
	/*
	 * @FindBy(xpath="//ul[@id='ui-id-2']/li//ul//a/span[2]") private
	 * List<WebElement> sub_category_dropdown_options;
	 */
	
	@FindBy(xpath="//ul[@id='ui-id-2']/li//ul//ul/li/a/span")
	private List<WebElement> sub_sub_category_solid_options;
	
	@FindBy(css="li[class$='ui-menu-item'] a span")
	private List<WebElement> all_menu_submenu_items;
	
	
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
	
	@Step("Selecting {0} -> {1} -> {2}")
	public ProductPage findProductFromCategories(String maincategory, String subcategory, String product) {
		waitForJStoLoad(driver);
		for(int i=0;i<main_category_dropdown_options.size();i++) {
			String text1 = main_category_dropdown_options.get(i).getText();
			if(main_category_dropdown_options.get(i).getText().equalsIgnoreCase(maincategory)) {
				shortWait();
				actionMouseHover(driver,main_category_dropdown_options.get(i));
				conditionalWaitonLoad(driver, sub_category_box.get(i));
				if(!subcategory.equals("")) {
					List<WebElement> eles = sub_category_box.get(i).findElements(By.xpath("/li//a/span[2]"));
					for(WebElement ele:eles) {
						if(ele.getText().equalsIgnoreCase(subcategory)) {
							actionMouseHover(driver, ele);
							break;
						}
					}
				}else {
					shortWait();
					List<WebElement> eles = driver.findElements(By.xpath("(//ul[@id='ui-id-2']/li/ul)["+(i+1)+"]/li//a/span"));
					for(WebElement ele:eles) {
						String text2 = ele.getText();
						if(ele.getText().equalsIgnoreCase(product)) {
							shortWait();
							click(ele);
							break;
						}
					}
				}
				break;
			}
		}
		return new ProductPage(driver);
	}
		
}
