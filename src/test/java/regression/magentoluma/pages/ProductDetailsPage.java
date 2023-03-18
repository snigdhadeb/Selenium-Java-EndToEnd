package regression.magentoluma.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.qameta.allure.Step;
import util.utilities.LogUtil;

public class ProductDetailsPage extends BasePage{
	
	WebDriver driver;
	public ProductDetailsPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[@class='swatch-attribute size']/div/div")
	private List <WebElement> available_sizes;
	
	@FindBy(css=".swatch-option.color")
	private List<WebElement> available_colors;
	
	@FindBy(id="qty")
	private WebElement quantity_input;
	
	@FindBy(id="product-addtocart-button")
	private WebElement add_to_cart_button;
	
	@FindBy(css=".page-title span")
	private WebElement product_title;
	
	@FindBy(css=".action.showcart .counter.qty")
	private WebElement items_on_cart_counter;
	
	@FindBy(css=".action.viewcart")
	private WebElement view_and_edit_cart_link;
	
	public boolean isLandedOnProductDeatailsPage(String productname) {
		waitForJStoLoad(driver);
		if(product_title.getText().contains(productname)) {
			LogUtil.log("Product Details page for "+ productname+ " is displayed successfully");
			return true;
		}else
			return false;
	}
	
	@Step("Setting the product size: {0}")
	public ProductDetailsPage setSize(String productsize) {
		for(WebElement size: available_sizes) {
			if(size.getText().equalsIgnoreCase(productsize)) {
				click(size);
				break;
			}
		}
		return this;
	}
	
	@Step("Setting the product color: {0}")
	public ProductDetailsPage setColor(String color) {
		for(WebElement productcolor:available_colors) {
			if(productcolor.getAttribute("option-label").equalsIgnoreCase(color)) {
				click(productcolor);
				break;
			}
		}
		return this;
	}
	
	@Step("Setting product quantity: {0}")
	public ProductDetailsPage setQuantity(String quantity) {
		setText(quantity_input, quantity);
		return this;
	}
	
	@Step("Adding product to the cart")
	public ProductDetailsPage clickAddToCart() {
		click(add_to_cart_button);
		return this;
	}
	
	@Step("Going to the Cart Page")
	public ShoppingCartPage gotoCart() {
		shortWait();
		conditionalWaitonLoad(driver, items_on_cart_counter);
		click(items_on_cart_counter);
		shortWait();
		conditionalWaitonLoad(driver, view_and_edit_cart_link);
		click(view_and_edit_cart_link);
		return new ShoppingCartPage(driver);
	}

}
