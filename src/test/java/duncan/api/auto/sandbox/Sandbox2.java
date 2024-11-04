package duncan.api.auto.sandbox;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Ignore
public class Sandbox2 {
	@Test
	public void test09DeleteTestData() {
		for (int i = 21; i < 100; i++) {
			RestAssured.baseURI = "http://localhost:3000";
			RequestSpecification httpRequest = RestAssured.given()
					.header("content-type", "application/json")
					.header("g-token", "ROM831ESV")
					.auth().preemptive().basic("admin", "admin");
			Response response = httpRequest.request(Method.DELETE, "/books/" + i);
		}
	}
}