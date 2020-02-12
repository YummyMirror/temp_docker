package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

public class BasePage {
    protected WebDriver wd;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver wd) {
        this.wd = wd;
        this.wait = new WebDriverWait(wd, 10);
        this.js = (JavascriptExecutor) wd;
    }

    protected void open(String url) {
        wd.navigate().to(url);
        wait.until(urlContains(url));
    }
}
