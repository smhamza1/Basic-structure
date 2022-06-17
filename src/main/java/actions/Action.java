package actions;

import Base.BaseClass;
import Interface.ActionInterface;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


public class Action extends BaseClass implements ActionInterface {
    BufferedWriter Bwrite;

    @Override
    public boolean type(WebElement ele, String text) {
        boolean flag = false;
        try {
            flag = ele.isDisplayed();
            //  ele.clear();
            ele.sendKeys(text);
            ele.click();
            // logger.info("Entered text :"+text);
            flag = true;
        } catch (Exception e) {
            System.out.println("Location Not found");
            flag = false;
        } finally {
            if (flag) {
                System.out.println("Successfully entered value");
            } else {
                System.out.println("Unable to enter value");
                //  getDriver().close();
            }

        }
        return flag;
    }

    public String getCurrentURL() {
        boolean flag = false;

        String text = getDriver().getCurrentUrl();
        if (flag) {
            System.out.println("Current URL is: \"" + text + "\"");
        }
        return text;
    }


//   @Override
//    public boolean click1(WebElement ele) {
//        boolean flag = false;
//        try {
//            ele.click();
//            flag = true;
//        } catch (Exception e) {
//            return false;
//        } finally {
//            if (flag) {
//                System.out.println("Able to click on");
//            } else {
//                System.out.println("Click Unable to click on");
//            }
//        }
//return flag;
//    }

    @Override
    public void click(WebDriver driver, WebElement ele) {

        Actions act = new Actions(driver);
        // fluentWait(driver,ele,60);
        act.moveToElement(ele).click().build().perform();
//        act.moveToElement(ele).click();


    }

    public void scrollByVisibilityOfElement(WebDriver driver, WebElement ele) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", ele);

    }


    @Override
    public String screenShot(WebDriver driver, String filename) {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/ScreenShots/" + filename + "_" + dateName + ".png";

        try {
            FileUtils.copyFile(source, new File(destination));
        } catch (Exception e) {
            e.getMessage();
        }
        // This new path for jenkins
        String newImageString = "http://localhost:8082/job/MyStoreProject/ws/MyStoreProject/ScreenShots/" + filename + "_"
                + dateName + ".png";
        //  return newImageString;
        return destination;
    }

    @Override
    public boolean isDisplayed(WebDriver driver, WebElement ele) {
        boolean flag = false;
        flag = findElement(driver, ele);
        if (flag) {
            flag = ele.isDisplayed();
            if (flag) {
                System.out.println("The element is Displayed");
            } else {
                System.out.println("The element is not Displayed");
            }
        } else {
            System.out.println("Not displayed ");
        }
        return flag;
    }

    public boolean findElement(WebDriver driver, WebElement ele) {
        boolean flag = false;
        try {
            ele.isDisplayed();
            flag = true;
        } catch (Exception e) {
            // System.out.println("Location not found: "+locatorName);
            flag = false;
        } finally {
            if (flag) {
                System.out.println("Successfully Found element at");

            } else {
                System.out.println("Unable to locate element at");
            }
        }
        return flag;
    }


    @Override
    public String getCurrentURL(WebDriver driver) {
        boolean flag = false;

        String text = driver.getCurrentUrl();
        if (flag) {
            System.out.println("Current URL is: \"" + text + "\"");
        }
        return text;
    }

    @Override
    public void fluentWait(WebDriver driver, WebElement element, int timeOut) {
        Wait<WebDriver> wait = null;
        try {
            wait = new FluentWait<WebDriver>((WebDriver) driver)
                    .withTimeout(Duration.ofSeconds(timeOut))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(Exception.class);
            wait.until(ExpectedConditions.visibilityOf(element));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void fluentWaitForList(WebDriver driver, List<WebElement> element, int timeOut) {
        Wait<WebDriver> wait = null;
        try {
            wait = new FluentWait<WebDriver>((WebDriver) driver)
                    .withTimeout(Duration.ofSeconds(timeOut))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(Exception.class);
            wait.until(ExpectedConditions.visibilityOfAllElements(element));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getTitle(WebDriver driver, WebElement element) {
        boolean flag = false;
        String text = "";

        try {
            text = element.getText();
            flag = true;
        } catch (Exception e) {
            System.out.println(e);
        }
        if (flag) {
            System.out.println("Title is: \"" + text + "\"");
        }
        return text;
    }


    @Override
    public void explicitWait(WebDriver driver, WebElement element, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.visibilityOf(element));
    }


    //Step-1 -> Enable IMAP from gmail.
    //Step-2 -> Disable 2 step verification from gmail.
    //Step-3 -> Allow less secure app from gmail.
    @Override
    public String Gmail() throws Throwable {
        String username = "ladybirdd009@gmail.com";
        String password = "yztbonnonqevplly";
        Properties properties = new Properties();
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.ssl.enable", "true");
        props.setProperty("mail.imaps.port", "993");
        props.setProperty("mail.imaps.timeout", "10000");
        props.setProperty("mail.imaps.connectiontimeout", "10000");
        Session session = Session.getInstance(props, null);
        Store store = session.getStore();
        store.connect("imap.gmail.com", username, password);
        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        System.out.println("Connected!");
        Message message = null;
        Message[] messages = inbox.getMessages();
        for (int i = messages.length - 1; i >= messages.length - 2; i--) {

            message = messages[i];
//            System.out.println("---------------------------------");
//            System.out.println("Email Number " + (i + 1));
//            System.out.println("Subject: " + message.getSubject());
//            System.out.println("From: " + message.getFrom()[0]);
//            System.out.println("Date: " + message.getReceivedDate());
            Date d1 = messages[messages.length - 1].getReceivedDate();
            Date d2 = messages[messages.length - 2].getReceivedDate();
            System.out.println(d1 + "----" + "----" + d2);

            if (message.getSubject().equalsIgnoreCase("Password reset for Perfect Day Care")) {
                if (d1.after(d2)) {
                    //
                    message = messages[messages.length - 1];
//                        if(message.getSubject().contains("Password reset for Perfect Day Care")) {
//                            System.out.println("---------------------------------");
//                            System.out.println("Email Number " + (i + 1));
//                            System.out.println("Subject: " + message.getSubject());
//                            System.out.println("From: " + message.getFrom()[0]);

                    if (message.isMimeType("text/plain")) {
                        System.out.println(message.getContent().toString());
                    } else if (message.isMimeType("multipart/*")) {
                        String result = "";
                        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                        int count = mimeMultipart.getCount();
                        for (int j = 0; j < count; j++) {
                            BodyPart bodyPart = mimeMultipart.getBodyPart(j);
                            if (bodyPart.isMimeType("text/plain")) {
                                result = result + "\n" + bodyPart.getContent();
                                break;  //without break same text appears twice in my tests
                            } else if (bodyPart.isMimeType("text/html")) {
                                String html = (String) bodyPart.getContent();
                                result = result + "\n" + Jsoup.parse(html).text();

                            }
                        } //System.out.println(result);

                        String s1 = result.replaceAll("[^0-9]+", "");
                        System.out.println("------- result ----------" + s1);
                        return s1;
                    }
                    System.out.println("-");

                }
            }
        }
        return "";
    }

    @Override
    public void saveCookies() throws IOException {
        // create file named Cookies to store Login Information
        File file = new File("Cookies.data");
        file.delete();
        file.createNewFile();
        FileWriter fileWrite = new FileWriter(file);
        BufferedWriter Bwrite = new BufferedWriter(fileWrite);
    }

    @Override
    public void loadCookies() throws IOException {
        // loop for getting the cookie information
        for (Cookie ck : getDriver().manage().getCookies()) {
            Bwrite.write((ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";" + ck.getExpiry() + ";" + ck.isSecure()));
            Bwrite.newLine();
            System.out.println("----"+ck.getValue());
        }
    }


    public void selectItemFromInput(List<WebElement> CountryList, String text) {
        List<WebElement> op = CountryList;
        System.out.println("size---" + op.size());
        System.out.println("data---" + op.get(0).getText());
        for (int i = 0; i < op.size(); i++) {

            if(op.get(i).getText().toString().matches(text)){
                op.get(i).click();
               break;
            }else
            {

            }

        }

    }

    public String searchResult(List<WebElement> CountryList, String text) {
        List<WebElement> op = CountryList;
        System.out.println("size---" + op.size());
        String ex="";

        for (int i = 0; i < op.size(); i++) {

         //  if(Integer.parseInt(op.get(i).getText()) == Integer.parseInt(text)){
            if(op.get(i).getText().contains(text)){
                System.out.println("data---" + op.get(i).getText());
                ex=op.get(i).getText();
               break;
            }else {
                System.out.println("Not Found");
                ex=op.get(i).getText();
            }


        }
        return ex;
      //  Assert.assertEquals(ex,text);

    }



    //
}





