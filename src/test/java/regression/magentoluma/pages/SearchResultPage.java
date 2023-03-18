package regression.magentoluma.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.qameta.allure.Step;
import util.utilities.LogUtil;

public class SearchResultPage extends BasePage{
	
	WebDriver driver;
	public SearchResultPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[contains(text(),'Search results for')]")
	private WebElement search_result_header;
	
	@FindBy(css=".product-item-link")
	private List<WebElement> displayed_product_items;
	
	
	
	public boolean isLandedOnSearchResultPage() {
		conditionalWaitonLoad(driver, search_result_header);
		if(search_result_header.getText().contains("Search results for")) {
			LogUtil.log("Search Result page is displayed successfully");
			return true;
		}else
			return false;
	}
	
	@Step("Selecting product {0} from all available product type")
	public ProductDetailsPage selectProduct(String productname) {
		for(WebElement product:displayed_product_items) {
			scrollIntoView(driver, product);
			if(product.getText().trim().equalsIgnoreCase(productname)) {
				click(product);
				break;
			}
		}
		return new ProductDetailsPage(driver);
	}
	


	
	

}
