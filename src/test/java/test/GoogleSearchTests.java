package test;

import base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class GoogleSearchTests extends BaseTest {
    @Test
    public void googleTest() {
        app.navigateTo().open("https://www.google.by");
    }

    @Test(priority = 1)
    public void yandexTest() {
        app.navigateTo().open("https://yandex.by");
    }

    @Test(priority = 2)
    public void yahooTest() {
        app.navigateTo().open("https://en.wikipedia.org");
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        app.navigateTo().showAlert("Hello World2!")
           .closeAlert();
        sleep(1000);
    }
}
