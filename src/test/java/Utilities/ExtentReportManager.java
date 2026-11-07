package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ExtentReportManager implements ITestListener {

    ExtentReports extent;
    ExtentTest test;

    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String repName = "ApiReport-" + timeStamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/" + repName);
        spark.config().setReportName("Petstore API Automation");
        spark.config().setDocumentTitle("API Test Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Tester", "Pooja");
        extent.setSystemInfo("Run time", new SimpleDateFormat("dd MMM yyyy, HH:mm:ss").format(new Date()));
    }

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getName());
        test.log(Status.PASS, "Test passed");
    }

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.log(Status.FAIL, "Test failed: " + result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName());
        test.log(Status.SKIP, "Test skipped");
    }

    public void onFinish(ITestContext context) {
        extent.flush();   // writes the report file
    }
}