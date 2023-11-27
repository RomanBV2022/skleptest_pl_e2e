package automation.tests.model;

import automation.tests.model.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getTitleOfPage() {

        return getDriver().getTitle();
    }


    public Integer addProductToCart(int number) {
        getDriver().findElement(By.xpath("//a[contains(@href,"   + number + ")]")).click();
        CartPage cartPage = new CartPage(getDriver())
                .goCartPage();

        return null;
    }

    public AllProductPage goAllProductsPage() {
        final WebElement categoryDropdown = getDriver().findElement(By.xpath("//a[@href='/']"));
        Select select = new Select(categoryDropdown);

        getActions().moveToElement(categoryDropdown).click()
                        .scrollToElement(categoryDropdown.findElement(By.xpath("//a[@href='/all-products']")))
                .click()
                .perform();

        return this.goAllProductsPage();
    }

}
