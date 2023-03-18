package regression.magentoluma.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import regression.magentoluma.pages.CheckOutPage;
import regression.magentoluma.pages.HomePage;
import regression.magentoluma.pages.LoginPage;
import regression.magentoluma.pages.ProductDetailsPage;
import regression.magentoluma.pages.SearchResultPage;
import regression.magentoluma.pages.ShoppingCartPage;
import util.utilities.JsonDataHandler;
import util.utilities.TestAllureListener;

@Listeners({TestAllureListener.class})
@Story("Luma Products End to End Scenarios")
@Feature("Regression Scenario 1: Direct Search Product end to end")
public class TC001_SearchProductE2EAT extends BaseTestAT{
	JsonDataHandler testdata = new JsonDataHandler();
	
	@Description("Test to perform e2e flow for Hotel Booking")
	@Test
	public void searchProducte2e() {
		testcasename = this.getClass().getSimpleName();
		HomePage hp = new HomePage(driver);
		Assert.assertTrue(hp.isLandedOnHomePage());
		LoginPage loginpage = hp.click_signin();
		Assert.assertTrue(loginpage.isLandedOnLoginPage());
		HomePage homepage = loginpage.enterEmail(test_username).enterPassword(test_password).clickSignIn();
		SearchResultPage searchresultpage = homepage.searchProduct(testdata.getTestData("producttype"));
		Assert.assertTrue(searchresultpage.isLandedOnSearchResultPage());
		ProductDetailsPage productdetailspage = searchresultpage.selectProduct(testdata.getTestData("productname"));
		Assert.assertTrue(productdetailspage.isLandedOnProductDeatailsPage(testdata.getTestData("productname")));
		ShoppingCartPage shoppingcartpage = productdetailspage.setSize(testdata.getTestData("size"))
		                  					.setColor(testdata.getTestData("color"))
		                  					.setQuantity(testdata.getTestData("quantity"))
		                  					.clickAddToCart().gotoCart();
		CheckOutPage checkoutpage = shoppingcartpage.proceedToCheckout();
	}
}
