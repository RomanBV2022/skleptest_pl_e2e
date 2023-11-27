package automation.tests.model;

import automation.tests.model.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) {
        super(driver);
    }


    public List<String> getProductList() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//td[contains(@class, 'product-name')]//a"));
        List<String> resultList = elementList.stream().map(WebElement::getText).toList();

        return resultList;
    }

    public HomePage deleteProduct() {
         getDriver().findElement(By.xpath("//span[contains(@class, 'dashicons dashicons-minus')]")).click();
         getDriver().findElement(By.xpath("//input[contains(@name, 'update_cart')]")).click();
        return null;
    }

}
