package utility;

import Base.BaseClass;
import actions.Action;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.IAlterSuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ListenerClass extends ExtentManager implements ITestListener, IAlterSuiteListener {

	Action action= new Action();

	//
	@Override
	public void alter(List<XmlSuite> suites) {
		//to set suite prameters from properties
		XmlSuite suite = suites.get(0);
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("Configuration/config.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for (Map.Entry<String, String> each : suite.getParameters().entrySet()) {

			String pattern = "\\$\\{([A-Za-z0-9.]+)\\}";
			Pattern expr = Pattern.compile(pattern);
			Matcher matcher = expr.matcher(each.getValue());
			while (matcher.find()) {
				String envValue = properties.getProperty(matcher.group(1));
				Matcher matchEnv = expr.matcher(envValue);
				while (matchEnv.find()) {
					envValue = System.getenv(matchEnv.group(1));
				}
				if (envValue == null) {
					envValue = "";
				} else {
					envValue = envValue.replace("\\", "\\\\");
				}
				Pattern subexpr = Pattern.compile(Pattern.quote(matcher.group(0)));
				each.setValue(subexpr.matcher(each.getValue()).replaceAll(envValue));
			}
		}
	}


	//
	
	public void onTestStart(ITestResult result) {
			test = extent.createTest(result.getName());
			//System.out.println(result.getName());
	}

	public void onTestSuccess(ITestResult result) {

			if (result.getStatus() == ITestResult.SUCCESS) {
				test.log(Status.PASS, "Pass Test case is: " + result.getName());
				System.out.println("Pass Test case is: " + result.getName());
			}

	}

	public void onTestFailure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println(result.getStatus());
			System.out.println(ITestResult.FAILURE);

			try {
				test.log(Status.FAIL,
						MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
				test.log(Status.FAIL,
						MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
				String imgPath = action.screenShot(BaseClass.getDriver(), result.getName());

				test.fail("ScreenShot is Attached", MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//System.out.println("----"+ e.getMessage() + "-----");
			}

		}
	}

	public void onTestSkipped(ITestResult result) {
		if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Skipped Test case is: " + result.getName());
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();
	}
}
