package app;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import page.Navigation;

import static app.WebDriverFactory.create;
import static app.WebDriverFactory.createAdvanced;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ApplicationManager {
    private WebDriver wd;
    private Navigation navigation;

    public void init(String browser, ITestContext context) {
        wd = createAdvanced();
//        wd = create(browser, context);
    }

    public void stop() {
        if (nonNull(wd)) {
            wd.quit();
            wd = null;
        }
    }

    public Navigation navigateTo() {
        if (isNull(navigation)) {
            navigation = new Navigation(wd);
        }
        return navigation;
    }
}
