package automation.tests.model.base;

import automation.tests.model.CartPage;
import automation.tests.model.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;


public abstract class BasePage extends BaseModel {
    private Actions actions;

    public BasePage(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//a[@class=\\\"site-title\\\"]\")")
    WebElement homePage;

    @FindBy(xpath = "//li[@class=\"top-cart\"]")
    WebElement myCartButton;

    public HomePage goHomePage() {
        homePage.click();

        return new HomePage(getDriver());
    }


    public CartPage goCartPage() {
        myCartButton.click();

        return new CartPage(getDriver());
    }


    public Actions getActions() {
        if (actions == null) {
            actions = new Actions(getDriver());
        }
        return actions;
    }
}



