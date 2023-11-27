package automation.tests.model.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {


    final  static  String SITE_LOGO = "Generic Shop";

    private static void goToHomePage(BaseTest baseTest, boolean goToHomePage) {
        if (goToHomePage) {
            baseTest.getDriver().findElement(By.linkText(SITE_LOGO)).click();
        }
    }


    public static List<String> getTextOfWebElements(List<WebElement> elements) {
        List<String> textOfWebElements = new ArrayList<>();

        for (WebElement element : elements) {
            textOfWebElements.add(element.getText());
        }
        return textOfWebElements;
    }
}

