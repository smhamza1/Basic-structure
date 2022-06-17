package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {
	
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public static void setExtent() {
		//htmlReporter= new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/ExtentReport/"+"MyReport_"+BaseClass.getCurrentTime()+".html");
			htmlReporter = new ExtentHtmlReporter( "test-output/ExtentReport/myExtentReport.html");
			htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");
		//htmlReporter.config().setDocumentTitle("Automation Test Report");
		//htmlReporter.config().setReportName("OrangeHRM Test Automation Report");
		//htmlReporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		extent.setSystemInfo("HostName", "MyHost");
		extent.setSystemInfo("ProjectName", "LadyBird");
		extent.setSystemInfo("Tester", "Adeel Qureshi");
		extent.setSystemInfo("OS", "Linux");
		extent.setSystemInfo("Browser", "Chrome");

	}

	public static ExtentTest startTest(String name){
		return extent.createTest(name);
	}

	public static void endReport() {
		extent.flush();
	}
}
