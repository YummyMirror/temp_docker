package app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver;

public class WebDriverFactory {
    private static final Map<String, Function<ITestContext, WebDriver>> driverMap = new HashMap<>();

    public WebDriverFactory() {
    }

    static WebDriver createAdvanced() {
        WebDriver wd = null;
        DesiredCapabilities caps = null;
        String browser = System.getProperty("BROWSER");
        System.out.println("BROWSER '" + browser + "'");
        if (browser != null) {
            if (browser.equals("chrome")) {
                caps = DesiredCapabilities.chrome();
            }
            if (browser.equals("firefox")) {
                caps = DesiredCapabilities.firefox();
            }
        }
        String hub_host = System.getProperty("HUB_HOST");
        System.out.println("HUB '" + hub_host + "'");
        try {
            if (hub_host != null) {
                wd = new RemoteWebDriver(new URL("http:/" + hub_host + ":4444/wd/hub"), caps);
            } else {
                wd = new RemoteWebDriver(new URL("http:/localhost:4444/wd/hub"), caps);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return wd;
    }

    private static WebDriver dockerDriverCreation(DesiredCapabilities caps) {
        WebDriver wd = null;
        try {
            String hub_host = System.getProperty("HUB_HOST");
            System.out.println("HUB: '" + hub_host + "'");
            if (hub_host != null) {
                wd = new RemoteWebDriver(new URL("http:/" + hub_host + ":4444/wd/hub"), caps);
            } else {
                wd = new RemoteWebDriver(new URL("http:/localhost:4444/wd/hub"), caps);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return wd;
    }

    private static final Function<ITestContext, WebDriver> chromeFunc = context -> {
        chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        return new ChromeDriver(chromeOptions);
    };

    private static final Function<ITestContext, WebDriver> dockerChromeFunc = context -> {
        chromedriver().setup();
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability("name", context.getCurrentXmlTest().getName());
        return dockerDriverCreation(caps);
    };

    private static final Function<ITestContext, WebDriver> dockerFirefoxFunc = context -> {
        firefoxdriver().setup();
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setCapability("name", context.getCurrentXmlTest().getName());
        return dockerDriverCreation(caps);
    };

    private static final Function<ITestContext, WebDriver> firefoxFunc = context -> {
        firefoxdriver().setup();
        return new FirefoxDriver();
    };

    static {
        driverMap.put("chrome", chromeFunc);
        driverMap.put("chrome-docker", dockerChromeFunc);
        driverMap.put("firefox-docker", dockerFirefoxFunc);
        driverMap.put("firefox", firefoxFunc);
    }

    static synchronized WebDriver create(String browser, ITestContext context) {
        ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();
        WebDriver driver = driverMap.get(browser).apply(context);
        if (driver instanceof FirefoxDriver) {
            driver.manage().window().maximize();
        }
        webDriverThreadLocal.set(driver);
        return webDriverThreadLocal.get();
    }
}
