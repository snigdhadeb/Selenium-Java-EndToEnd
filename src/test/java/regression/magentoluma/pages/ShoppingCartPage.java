package regression.magentoluma.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.qameta.allure.Step;

public class ShoppingCartPage extends BasePage{
	
	WebDriver driver;
	public ShoppingCartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//ul[@class='checkout methods items checkout-methods-items']//button[@title='Proceed to Checkout']")
	private WebElement proceed_to_checkout_button;
	
	@Step("Proceeding for Checkout")
	public CheckOutPage proceedToCheckout() {
		conditionalWaitonLoad(driver, proceed_to_checkout_button);
		click(proceed_to_checkout_button);
		return new CheckOutPage(driver);
	}

}
