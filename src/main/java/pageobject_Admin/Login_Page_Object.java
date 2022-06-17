package pageobject_Admin;

import Base.BaseClass;
import actions.Action;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login_Page_Object extends BaseClass {
    Action action=new Action();

    public static WebElement getSearch() {
        return Search;
    }

    @FindBy(name="q")
    private static WebElement Search;


    public Login_Page_Object() {
        PageFactory.initElements(getDriver(), this);
    }



}
