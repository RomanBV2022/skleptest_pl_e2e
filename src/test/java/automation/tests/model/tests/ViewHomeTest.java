package automation.tests.model.tests;

import automation.tests.model.CartPage;
import automation.tests.model.HomePage;
import automation.tests.model.runner.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ViewHomeTest extends BaseTest {


    @Test()
    public void testViewHome() {

        String homePage = new HomePage(getDriver())
                .getTitleOfPage();
        Assert.assertEquals(homePage, "Generic Shop – Just another web shop");
    }

    @Test
    public void testAddProductToCart() {
        final String productName = "Jennifer Scarf";
        Integer homePage = new HomePage(getDriver())
                .addProductToCart(35);

        Assert.assertTrue(new CartPage(getDriver()).getProductList().contains(productName));
    }

    @Test(dependsOnMethods = "addProductToCart")
    public void testDeleteProductFromCart() {
        final String productName = "Jennifer Scarf";
        HomePage homePage = new HomePage(getDriver())
                .goCartPage()
                .deleteProduct();
    }

    @Test
    public void testCheckHeaderItem() {
        List<String> items = new HomePage(getDriver())
                .showHeaderElements();

        List<String> itemList = List.of("SHOP", "MOST WANTED", "CATERGRIES", "ABOUT US", "CONTACT", "BLOG");

        Assert.assertEquals(items, itemList);

    }

}
