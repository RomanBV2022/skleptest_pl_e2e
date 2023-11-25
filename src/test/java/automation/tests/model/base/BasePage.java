package automation.tests.model.base;



import automation.tests.model.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public abstract class BasePage extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage goHomePage() {
        getDriver().findElement(By.xpath("//a[@class=\"site-title\"]")).click();

        return new HomePage(getDriver());
    }



}
