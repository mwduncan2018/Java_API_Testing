package duncan.api.auto.hooks;

import java.net.MalformedURLException;
import java.util.HashMap;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Hooks {
	private HashMap<String, Object> context;

	public Hooks(HashMap<String, Object> context) {
		this.context = context;
	}

	@Before(value = "", order = 99)
	public void beforeScenario() throws MalformedURLException {
		RequestSpecification httpRequest = RestAssured.given();
		context.put("httpRequest", httpRequest);
	}
	

	@After(value = "", order = 1)
	public void afterScenario() {
		// Delete test data after each scenario
		for (int i = 21; i < 30; i++) {
			RequestSpecification httpRequest = RestAssured.given()
					.header("content-type", "application/json")
					.header("g-token", "ROM831ESV")
					.auth().preemptive().basic("admin", "admin");
			httpRequest.request(Method.DELETE, "/books/" + i);
		}
	}
}