package Base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utility.ExtentManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseClass {


    public  static Properties properties;
    public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    public Properties loadPropertyFile() throws IOException {
        FileInputStream fileInputStream =new FileInputStream("Configuration/config.properties");
        properties=new Properties();
        properties.load(fileInputStream);
//
        //to set env variables
        for (Map.Entry<Object, Object> each : properties.entrySet()) {

            String pattern = "\\$\\{([A-Za-z0-9.]+)\\}";
            Pattern expr = Pattern.compile(pattern);
            Matcher matcher = expr.matcher(each.getValue().toString());
            while (matcher.find()) {
                String envValue = System.getenv(matcher.group(1));
                if (envValue == null) {
                    envValue = "";
                } else {
                    envValue = envValue.replace("\\", "\\\\");
                }
                Pattern subexpr = Pattern.compile(Pattern.quote(matcher.group(0)));
                each.setValue(subexpr.matcher(each.getValue().toString()).replaceAll(envValue));
            }
        }
        //
        return properties;

    }
    public static WebDriver getDriver() {
        // Get Driver from threadLocalmap
        return driver.get();
    }


    @BeforeSuite(alwaysRun = true, groups = {"SMOKE"})
    public void BeforeSuite() throws IOException {
        ExtentManager.setExtent();
        DOMConfigurator.configure("log4j.xml");
        loadPropertyFile();

    }

    @AfterSuite(alwaysRun = true, groups = {"SMOKE"})
    public void AfterSuite() throws IOException {

        ExtentManager.endReport();

    }


    public void launchBrowser(String browser, String Url) {

        // String browser=properties.getProperty("browser");
        //  String url=System.getenv("login_url");
        String url=Url;
        String driverLocation=properties.getProperty("DriverLocation");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
 //       options.addArguments("user-data-dir=selenium");
        options.addArguments("page_load_strategy\": \"none");
        options.setHeadless(Boolean.parseBoolean(properties.getProperty("headless")));
        //  options.addArguments("--headless"); //!!!should be enabled for Jenkins
        options.addArguments("--disable-dev-shm-usage"); //!!!should be enabled for Jenkins
        options.addArguments("--window-size=1920x1080"); //!!!should be enabled for Jenkins
//
        FirefoxOptions optionsff = new FirefoxOptions();
        optionsff.addArguments("--no-sandbox");
        optionsff.setHeadless(Boolean.parseBoolean(properties.getProperty("headless")));
//        optionsff.addArguments("--headless"); //!!!should be enabled for Jenkins
        optionsff.addArguments("--disable-dev-shm-usage"); //!!!should be enabled for Jenkins
        optionsff.addArguments("--window-size=1920x1080"); //!!!should be enabled for Jenkin

        if(browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver.set(new ChromeDriver(options));

        }
        else if(browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver.set(new FirefoxDriver(optionsff));
        }

        getDriver().manage().window().maximize();
        getDriver().get(url);
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

}
