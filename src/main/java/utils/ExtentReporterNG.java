package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	static ExtentReports extent;

	public static ExtentReports getReporterObject() {

		String path = System.getProperty("user.dir") + "\\reports\\index.html";

		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Demo Project");
		reporter.config().setDocumentTitle("Test Result");

		extent = new ExtentReports();
		extent.attachReporter(reporter);

		extent.setSystemInfo("Tester", "Test");
		extent.createTest(path);

		return extent;

	}

}
