package regression.magentoluma.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.qameta.allure.Step;
import util.utilities.LogUtil;

public class ProductPage extends BasePage{
	
	public WebDriver driver;
	public ProductPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="#page-title-heading span")
	private WebElement pageheader;
	
	@FindBy(css=".filter-options-title")
	private List<WebElement> available_filters;
	
	@FindBy(xpath="//a[normalize-space(text())='Electronic']")
	private WebElement electronic;
	
	@FindBy(xpath="//span[normalize-space(text())='$40.00']/ancestor::a")
	private WebElement dollar40_45range;
	
	@FindBy(xpath="//a[normalize-space(text())='Outdoor']")
	private WebElement outdoor;
	
	@FindBy(xpath="//strong[@class='product name product-item-name']/a")
	private WebElement aim_analog_watch;
	
	@FindBy(xpath="//div[@class='product-item-inner']//button[@type='submit']")
	private WebElement add_to_cart;
	
	@FindBy(css=".action.showcart .counter.qty")
	private WebElement items_on_cart_counter;
	
	@FindBy(css=".action.viewcart")
	private WebElement view_and_edit_cart_link;
	
	
	public boolean isLandedOnProductPage(String producttype) {
		conditionalWaitonLoad(driver, pageheader);
		if(pageheader.getText().equalsIgnoreCase(producttype)) {
			LogUtil.log(producttype+ "page is displayed successfully");
			return true;
		}	
		return false;
	}

	@Step("Applying filter: {0}")
	public void applyFilter(String filter) {
		switch(filter) {
		case "category":
			try {
				click(selectFilter(filter));
				conditionalWaitonLoad(driver, electronic);
				click(electronic);
				break;
				
			} catch (Exception e) {
				
			}
		case "price":
			try {
				click(selectFilter(filter));
				conditionalWaitonLoad(driver, dollar40_45range);
				click(dollar40_45range);
				break;
			} catch (Exception e) {
				
			}
		case "activity":
			try {
				click(selectFilter(filter));
				conditionalWaitonLoad(driver, outdoor);
				click(outdoor);
				break;
			} catch (Exception e) {
				
			}
		}
		
		
	}
	
	public WebElement selectFilter(String filter) throws Exception {
		shortWait();
		for(WebElement fil:available_filters) {
			if(fil.getText().toLowerCase().equals(filter))
				return fil;
		}
		throw new Exception("No filter available for the product");
		
	}
	
	public ShoppingCartPage addProductToCart() {
		conditionalWaitonLoad(driver, aim_analog_watch);
		actionMouseHover(driver, aim_analog_watch);
		conditionalWaitonLoad(driver, add_to_cart);
		//scrollIntoView(driver, add_to_cart);
		click(add_to_cart);
		shortWait();
		conditionalWaitonLoad(driver, items_on_cart_counter);
		click(items_on_cart_counter);
		shortWait();
		conditionalWaitonLoad(driver, view_and_edit_cart_link);
		click(view_and_edit_cart_link);
		return new ShoppingCartPage(driver);
	}

}
