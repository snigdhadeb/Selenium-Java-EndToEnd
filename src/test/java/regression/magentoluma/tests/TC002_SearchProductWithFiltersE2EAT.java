package regression.magentoluma.tests;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import regression.magentoluma.pages.HomePage;
import regression.magentoluma.pages.LoginPage;
import regression.magentoluma.pages.ProductPage;
import regression.magentoluma.pages.ShoppingCartPage;
import util.utilities.JsonDataHandler;
import util.utilities.TestAllureListener;

@Listeners({TestAllureListener.class})
@Story("Luma Products End to End Scenarios")
@Feature("Regression Scenario 2: Search Product with Filters end to end")
public class TC002_SearchProductWithFiltersE2EAT extends BaseTestAT{
	JsonDataHandler testdata = new JsonDataHandler();
	
	@Description("Test to perform e2e flow to purchase products after applying filters ")
	@Test
	public void searchProductWithFilterse2e() {
		testcasename = this.getClass().getSimpleName();
		HomePage hp = new HomePage(driver);
		Assert.assertTrue(hp.isLandedOnHomePage());
		LoginPage loginpage = hp.click_signin();
		Assert.assertTrue(loginpage.isLandedOnLoginPage());
		HomePage homepage = loginpage.enterEmail(test_username).enterPassword(test_password).clickSignIn();
		ProductPage productpage = homepage.findProductFromCategories(testdata.getTestData("main category"), testdata.getTestData("sub category"), testdata.getTestData("product type"));
		ArrayList<String> filters = testdata.getTestDataAsJsonArray("filters");
		for(String filter:filters) {
			productpage.applyFilter(filter);
		}
		ShoppingCartPage shoppingcartpage = productpage.addProductToCart();
		shoppingcartpage.proceedToCheckout();
	}
}
