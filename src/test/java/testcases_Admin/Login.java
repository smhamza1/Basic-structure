package testcases_Admin;

import Base.BaseClass;
import actions.Action;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobject_Admin.Login_Page_Object;
import utility.Log;

import java.io.*;

//@Listeners(ListenerClass.class)
public class Login extends BaseClass {
    Action action = new Action();
    Login_Page_Object login_page_object;
    @Parameters({"browser","login_url"})
    @BeforeMethod(groups = {"SMOKE"})
    public void setup(String browser, String url) throws IOException {
        launchBrowser(browser, url);
    }

    @AfterMethod(groups = {"SMOKE"})
    public void tearDown() {
        getDriver().quit();
    }


  @Test(groups = {"SMOKE"}, priority = 1)
    public void validlogin() throws Throwable {
      Log.startTestCase("Verify that the system is redirecting to to the Google.com");
      login_page_object=new Login_Page_Object();
Thread.sleep(2000);
action.type(Login_Page_Object.getSearch(),"Pakistan");
Thread.sleep(5000);
      Log.endTestCase("Verify that the system is redirecting to to the Google.com");

  }


}