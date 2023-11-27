package automation.tests.model.tests;

import automation.tests.model.CartPage;
import automation.tests.model.HomePage;
import automation.tests.model.runner.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ViewHomeTest extends BaseTest {


    @Test(dependsOnMethods = "addProductToCart")
    public void viewHome() {

        String homePage = new HomePage(getDriver())
                .getTitleOfPage();
        Assert.assertEquals(homePage, "Generic Shop – Just another web shop");
    }

    @Test
    public void addProductToCart() {
        final String productName = "Jennifer Scarf";
        Integer homePage = new HomePage(getDriver())
                .addProductToCart(35);

        Assert.assertTrue(new CartPage(getDriver()).getProductList().contains(productName));
    }

    @Test(dependsOnMethods = "addProductToCart")
    public void deleteProductFromCart() {
        final String productName = "Jennifer Scarf";
        HomePage homePage = new HomePage(getDriver())
                .goCartPage()
                .deleteProduct();
    }

}
