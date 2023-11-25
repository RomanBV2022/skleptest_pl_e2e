package automation.tests.model.tests;

import automation.tests.model.HomePage;
import automation.tests.model.runner.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class ViewHomeTest extends BaseTest {


    @Test
    public void viewHome() {

        String homePage = new HomePage(getDriver())
                .getTitleOfPage();
        Assert.assertEquals(homePage, "Generic Shop – Just another web shop");
    }
    @Ignore
    @Test
    public void viewHome2() {

    }

}
