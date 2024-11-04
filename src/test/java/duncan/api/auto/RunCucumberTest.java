package duncan.api.auto;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.restassured.RestAssured;
import duncan.api.auto.context.TestRunContext;

@CucumberOptions(
		features = "src/test/resources/features", 
		plugin = "json:target/cucumber-reports/cucumber.json",
		tags = "@Books")
class RunCucumberTest extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = false)
	public Object[][] scenarios() {
		return super.scenarios();
	}

	@BeforeSuite
	public static void beforeClass() {
		System.out.println("***************************************************************");
		System.out.println("BEFORE SUITE");
		System.out.println("***************************************************************");
		TestRunContext.getHashMap().put("numberOfTests", 0);
		RestAssured.baseURI = "http://localhost:3000";
	}

	@AfterSuite
	public static void afterClass() {
		System.out.println("***************************************************************");
		System.out.println("AFTER SUITE");
		System.out.println("***************************************************************");
		Integer numberOfTests = (Integer) TestRunContext.getHashMap().get("numberOfTests");
		System.out.println("Number of tests = " + numberOfTests);
	}
}