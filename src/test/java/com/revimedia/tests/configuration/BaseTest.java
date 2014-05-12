package com.revimedia.tests.configuration;

import com.revimedia.testing.configuration.WebDriverFactory;
import com.revimedia.testing.configuration.proxy.BrowserMobProxyLocal2;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

/**
 * Created by Funker on 12.04.14.
 */
public class BaseTest {
    protected WebDriver driver;
    private String url;
    protected static final Logger log = Logger.getLogger(BaseTest.class);

    WebDriverFactory instanceDriver;


    @BeforeClass
    @Parameters(value = {"browser", "version", "url"})
    public void setUp(@Optional("chrome") String browser,
                      @Optional("9") String version,
//                      @Optional("WIN") String platform,
                      @Optional("http://development.stagingrevi.com/auto/mfs/") String url) throws Exception {

        this.url = url;
        log.info("Setting up parameters...");
        WebDriverFactory instanceDriver = new WebDriverFactory();

        //driver = instanceDriver.getDriver(browser, version);
        //with browserMob
        driver = instanceDriver.createDriver(browser, version);

        //driver = instanceDriver.getDriver(browser, version);
        //driver = instanceDriver.getLocalDriver(browser, version);
//        driver = Browser2.getBrowser(browser, version).getDriver();

        //driver = WebDriverFactory.getDriver(browser, version, platform);
        printBrowserParameters();
    }


    @BeforeMethod
    public void openMainPage() {
        BrowserMobProxyLocal2.cleanProxyHar();
        driver.get(url);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        //BrowserMobProxyLocal.stopBrowserMob();
        BrowserMobProxyLocal2.stopProxy();
        driver.quit();
    }

    private void printBrowserParameters() {
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        log.info("-----------Test-Parameters--------------------");
        log.info("Tested URL:        " + this.url);
        log.info("Browser Name:      " + cap.getBrowserName());
        log.info("Browser version:   " + cap.getVersion());
        log.info("Platform:          " + cap.getPlatform());
    }

}
