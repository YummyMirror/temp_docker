package page;

import base.BasePage;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

public class Navigation extends BasePage {
    public Navigation(WebDriver wd) {
        super(wd);
    }

    @Override
    public void open(String url) {
        super.open(url);
    }

    public Navigation showAlert(String text) {
        js.executeScript("alert('" + text + "');");
        return this;
    }

    public void closeAlert() {
        wait.until(alertIsPresent()).accept();
    }
}
