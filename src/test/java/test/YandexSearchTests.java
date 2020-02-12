package test;

import base.BaseTest;
import org.testng.annotations.Test;

public class YandexSearchTests extends BaseTest {
    @Test
    public void openSite() {
        app.navigateTo().open("https://yandex.by");
        sleep(1100);
    }
}
