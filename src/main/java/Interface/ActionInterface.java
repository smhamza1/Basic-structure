package Interface;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

public interface ActionInterface {
    boolean type(WebElement ele, String text);
    String getCurrentURL();
    //public boolean click1(WebElement ele);
    public void scrollByVisibilityOfElement(WebDriver driver, WebElement ele);
    public void selectItemFromInput(List<WebElement> CountryList, String text);
    public void fluentWaitForList(WebDriver driver, List<WebElement> element, int timeOut);
    public void click(WebDriver driver, WebElement ele);
    public String screenShot(WebDriver driver, String filename);
    public boolean isDisplayed(WebDriver driver, WebElement ele);
    public boolean findElement(WebDriver driver, WebElement ele);
    public String getCurrentURL(WebDriver driver);
    public void fluentWait(WebDriver driver,WebElement element, int timeOut);
    public String getTitle(WebDriver driver, WebElement element);
    public void explicitWait(WebDriver driver, WebElement element, int timeOut);
    public String Gmail() throws Throwable;

    public void saveCookies() throws IOException;
    public void loadCookies() throws IOException;


}
