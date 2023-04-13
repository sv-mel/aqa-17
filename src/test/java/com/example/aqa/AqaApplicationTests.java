package com.example.aqa;

import com.example.aqa.models.CustomObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AqaApplicationTests {

	RequestSpecification request;

	@BeforeAll
	public static void setUp() {
		RestAssured.baseURI = "http://localhost:8080";
	}

	@BeforeEach
	public void setRequest() {
		request = RestAssured.given();
	}

//	@Test
//	void contextLoads() {
//		RequestSpecification request = RestAssured.given();
//		RestAssured.baseURI = "http://localhost:8080";
//		Response response = request.get("/method");
//		Assertions.assertEquals(200, response.statusCode());
//	}

	@Test
	void shouldGetDefaultString() {
		Response response = request.get("/getString");
//		Assertions.assertEquals(200, response.statusCode());

		assertThat(response)
				.extracting(
						Response::getContentType,
						Response::getStatusCode,
						Response::getStatusLine
				).containsExactly(
						"text/plain;charset=UTF-8",
						200,
						"HTTP/1.1 200 "
				);
		assertThat(response.body().print()).isEqualTo("empty string");

	}
	@Test
	void shouldGetStringWithoutSpaces() {
		final String str = " some string ";
		Response response = request
				.param("str", str)
				.get("/getString");
		assertThat(response)
				.extracting(
						Response::getContentType,
						Response::getStatusCode
				).containsExactly(
						"text/plain;charset=UTF-8",
						200
				);
		assertThat(response.body().print()).isEqualTo(str.trim());
	}

	@Test
	public void shouldGetObject() {
		Response response = request.get("/getObject");

		assertThat(response)
				.extracting(
						Response::getContentType,
						Response::getStatusCode
				).containsExactly(
						"application/json",
						200
				);

		final var body = response.body().peek().as(CustomObject.class);
//		System.out.println(response.body().peek());
		assertThat(body.page).isEqualTo(3);
		assertThat(body.query).isEqualTo("jacket");
		assertThat(body.size).isEqualTo(14);
	}

	@Test
	public void shouldGetLastStrPositive() {

		final String str = " second string ";
		Response response1 = request
				.param("str", str)
				.get("/getString");

		Response response2 = request
				.param("str", str)
				.get("/getLastStr");
		assertThat(response2)
				.extracting(
						Response::getContentType,
						Response::getStatusCode
				).containsExactly(
						"text/plain;charset=UTF-8",
						200
				);
		assertThat(response2.body().print()).isEqualTo(str);
	}

	@Test
	public void shouldGetLastStrNegative() {
		Response response1 = request
				.delete("/deleteLastStr");
		int statusCode = response1.getStatusCode();
		assertThat(statusCode).isEqualTo(200);

		Response response2 = request
				.param("str")
				.get("/getLastStr");
		assertThat(response2)
				.extracting(
						Response::getContentType,
						Response::getStatusCode
				).containsExactly(
						"text/plain;charset=UTF-8",
						200
				);
		assertThat(response2.body().print()).isEqualTo("String is null");
	}
}
