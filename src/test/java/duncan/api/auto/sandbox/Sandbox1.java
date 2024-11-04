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
public class Sandbox1 {
	@Test
	public void test01SendRequestWithoutHeader() {
		RestAssured.baseURI = "http://localhost:3000";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/books");
		Assert.assertEquals(response.getStatusCode(), 403);
	}	
	@Test
	public void test02SendRequestWithHeader() {
		RestAssured.baseURI = "http://localhost:3000";
		RequestSpecification httpRequest = RestAssured.given()
				.header("content-type", "application/json")
				.header("g-token", "ROM831ESV");
		Response response = httpRequest.request(Method.GET, "/books");
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test
	public void test03SendRequestWithBasicAuth() {
		RestAssured.baseURI = "http://localhost:3000";
		RequestSpecification httpRequest = RestAssured.given()
				.header("content-type", "application/json")
				.header("g-token", "ROM831ESV")
				.auth().basic("admin", "admin");
		Response response = httpRequest.request(Method.GET, "/books");
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test
	public void test04GetSingleObjectJsonResponse() {
		RestAssured.baseURI = "http://localhost:3000";
		RequestSpecification httpRequest = RestAssured.given()
				.header("content-type", "application/json")
				.header("g-token", "ROM831ESV")
				.auth().basic("admin", "admin");
		Response response = httpRequest.request(Method.GET, "/books/1");
		String json = response.getBody().asString();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(JsonPath.from(json).get("title"), "Don't Waste Your Life");
		Assert.assertEquals(JsonPath.from(json).get("author"), "John Piper");
		Assert.assertEquals(JsonPath.from(json).get("isbn"), "1593281056");
	}
	@Test
	public void test05GetSingleObjectJsonResponseByQueryParameter() {
		RestAssured.baseURI = "http://localhost:3000";
		RequestSpecification httpRequest = RestAssured.given()
				.header("content-type", "application/json")
				.header("g-token", "ROM831ESV")
				.auth().basic("admin", "admin")
				.queryParam("author", "Sam Harris");
		Response response = httpRequest.request(Method.GET, "/books");
		String json = response.getBody().asString();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(JsonPath.from("{\"Object\":" + json + "}").get("Object[0].author"), "Sam Harris");
	}
	@Test
	public void test06GetArrayJsonResponse() {
		RestAssured.baseURI = "http://localhost:3000";
		RequestSpecification httpRequest = RestAssured.given()
				.header("content-type", "application/json")
				.header("g-token", "ROM831ESV")
				.auth().basic("admin", "admin");
		Response response = httpRequest.request(Method.GET, "/books");
		String json = response.getBody().asString();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(JsonPath.from("{\"Object\":" + json + "}").get("Object[19].title"), "The Moral Landscape");
		Assert.assertEquals(JsonPath.from("{\"Object\":" + json + "}").get("Object[19].author"), "Sam Harris");
		Assert.assertEquals(JsonPath.from("{\"Object\":" + json + "}").get("Object[19].isbn"), "9780593064863");
	}
	@SuppressWarnings("unchecked")
	@Test
	public void test07Post() {
		JSONObject book = new JSONObject();
		{
			book.put("title", "Java Automation");
			book.put("author", "Duncan");
			book.put("isbn", "1234432123455432");
			book.put("releaseDate", "2024-01-01");
		}
		
		RestAssured.baseURI = "http://localhost:3000";
		RequestSpecification httpRequest = RestAssured.given()
				.header("content-type", "application/json")
				.header("g-token", "ROM831ESV")
				.auth().basic("admin", "admin")
				.body(book.toJSONString());
		Response response = httpRequest.request(Method.POST, "/books");
		Assert.assertEquals(response.getStatusCode(), 201);
	}
	@Test
	public void test00Delete() {
		RestAssured.baseURI = "http://localhost:3000";
		RequestSpecification httpRequest = RestAssured.given()
				.header("content-type", "application/json")
				.header("g-token", "ROM831ESV")
				.auth().preemptive().basic("admin", "admin");
		Response response = httpRequest.request(Method.DELETE, "/books/24");
		Assert.assertEquals(response.getStatusCode(), 204);
	}
}
