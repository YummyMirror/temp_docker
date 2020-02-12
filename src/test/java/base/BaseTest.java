package base;

import app.ApplicationManager;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected ApplicationManager app = new ApplicationManager();

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome-docker") String browser, ITestContext context) {
        app.init(browser, context);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    protected void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
