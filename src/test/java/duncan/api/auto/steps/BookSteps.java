package duncan.api.auto.steps;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BookSteps {
	private HashMap<String, Object> context;

	public BookSteps(HashMap<String, Object> context) {
		this.context = context;
	}

	@Given("G-Token is not provided")
	public void G_Token_is_not_provided() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
	}

	@Given("G-Token is provided")
	public void G_Token_is_provided() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		httpRequest.header("g-token", "ROM831ESV");
	}

	@SuppressWarnings("unchecked")
	@Given("a book")
	public void a_book() {
		JSONObject book = new JSONObject();
		{
			book.put("title", "Java Automation");
			book.put("author", "Duncan");
			book.put("isbn", "1234432123455432");
			book.put("releaseDate", "2024-01-01");
		}
		context.put("book", book);
	}

	@SuppressWarnings("unchecked")
	@Given("a book has been added")
	public void a_book_has_been_added() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		JSONObject book = new JSONObject();
		{
			book.put("title", "Java Automation");
			book.put("author", "Duncan");
			book.put("isbn", "1234432123455432");
			book.put("releaseDate", "2024-01-01");
		}
		httpRequest.header("content-type", "application/json");
		httpRequest.header("g-token", "ROM831ESV");
		httpRequest.body(book);
		Response response = httpRequest.request(Method.POST, "/books");
		context.put("response", response);		
	}

	@Given("Basic Auth credentials are provided")
	public void Basic_Auth_credentials_are_provided() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		httpRequest.auth().preemptive().basic("admin", "admin");
	}

	@Given("Basic Auth credentials are not provided")
	public void Basic_Auth_credentials_are_not_provided() {
	}

	@When("all books are requested")
	public void all_books_are_requested() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		Response response = httpRequest.request(Method.GET, "/books");
		context.put("response", response);
	}

	@When("a book is requested by ID")
	public void a_book_is_requested_by_ID() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		Response response = httpRequest.request(Method.GET, "/books/1");
		context.put("response", response);
	}

	@When("a book is requested by an invalid ID")
	public void a_book_is_requested_by_an_invalid_ID() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		Response response = httpRequest.request(Method.GET, "/books/999999999999999999999");
		context.put("response", response);
	}

	@When("a book is requested by title")
	public void a_book_is_requested_by_title() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		httpRequest.queryParam("title", "The Screwtape Letters");
		Response response = httpRequest.request(Method.GET, "/books");
		context.put("response", response);
	}

	@When("a book is requested by a title that does not exist")
	public void a_book_is_requested_by_a_title_that_does_not_exist() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		httpRequest.queryParam("title", "This Title Does Not Exist 1234");
		Response response = httpRequest.request(Method.GET, "/books");
		context.put("response", response);
	}

	@When("a book is requested by author")
	public void a_book_is_requested_by_author() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		httpRequest.queryParam("title", "Greg Bahnsen");
		Response response = httpRequest.request(Method.GET, "/books");
		context.put("response", response);
	}

	@When("a book is requested by author that does not exist")
	public void a_book_is_requested_by_author_that_does_not_exist() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		httpRequest.queryParam("title", "This Author Does Not Exist 1234");
		Response response = httpRequest.request(Method.GET, "/books");
		context.put("response", response);
	}

	@When("a book is added")
	public void a_book_is_added() {
		JSONObject book = (JSONObject) context.get("book");
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		httpRequest.header("content-type", "application/json");
		httpRequest.header("g-token", "ROM831ESV");
		httpRequest.body(book);
		Response response = httpRequest.request(Method.POST, "/books");
		context.put("response", response);
	}

	@When("the book is deleted")
	public void the_book_is_deleted() {
		RequestSpecification httpRequest = (RequestSpecification) context.get("httpRequest");
		Response response = httpRequest.request(Method.DELETE, "/books/21");
		context.put("response", response);
	}

	@Then("403 is returned")
	public void _403_is_returned() {
		Response response = (Response) context.get("response");
		Assert.assertEquals(response.statusCode(), 403);
	}

	@Then("200 is returned")
	public void _200_is_returned() {
		Response response = (Response) context.get("response");
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Then("the ISBN of the returned book is 1593281056")
	public void the_ISBN_of_the_returned_book_is_1593281056() {
		Response response = (Response) context.get("response");
		String json = response.getBody().asString();
		Assert.assertEquals(JsonPath.from(json).get("isbn"), "1593281056");
	}

	@Then("404 is returned")
	public void _404_is_returned() {
		Response response = (Response) context.get("response");
		Assert.assertEquals(response.statusCode(), 404);
	}

	@Then("the ISBN of the returned book is 0060652934")
	public void the_ISBN_of_the_returned_book_is_0060652934() {
		Response response = (Response) context.get("response");
		String json = response.getBody().asString();
		Assert.assertEquals(JsonPath.from("{\"Object\":" + json + "}").get("Object[0].isbn"), "0060652934");
	}

	@Then("the response contains no books")
	public void the_response_contains_no_books() {
		Response response = (Response) context.get("response");
		String json = response.getBody().asString();
		Assert.assertEquals(json, "[]");
	}

	@Then("201 is returned")
	public void _201_is_returned() {
		Response response = (Response) context.get("response");
		Assert.assertEquals(response.statusCode(), 201);
	}

	@Then("the response contains the added book")
	public void the_response_contains_the_added_book() {
		Response response = (Response) context.get("response");
		String json = response.getBody().asString();
		Assert.assertEquals(JsonPath.from(json).get("title"), "Java Automation");
		Assert.assertEquals(JsonPath.from(json).get("author"), "Duncan");
		Assert.assertEquals(JsonPath.from(json).get("isbn"), "1234432123455432");

	}

	@Then("204 is returned")
	public void _204_is_returned() {
		Response response = (Response) context.get("response");
		Assert.assertEquals(response.statusCode(), 204);
	}

	@Then("401 is returned")
	public void _401_is_returned() {
		Response response = (Response) context.get("response");
		Assert.assertEquals(response.statusCode(), 401);
	}
}