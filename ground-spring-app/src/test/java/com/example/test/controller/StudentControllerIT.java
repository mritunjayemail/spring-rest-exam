package com.example.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.MainApplication;
import com.example.model.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIT {

	@org.springframework.boot.web.server.LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Before
	public void before() {
		headers.add("Authorization", createHttpAuthenticationHeaderValue("user1", "secret1"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}

	@Test
	public void putExampleTest() {

		Course course = new Course("Course1", "Spring", "10 Steps",
				Arrays.asList("Learn Maven", "Import Project", "First Example", "Second Example"));

		HttpEntity<Course> entity = new HttpEntity<Course>(course, headers);

		ResponseEntity<Course> response = restTemplate.exchange(createURLWithPort("/putExample/111"), HttpMethod.POST,
				entity, Course.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	public void testRetrieveStudentCourse() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/students/Student1/courses/Course1"),
				HttpMethod.GET, entity, String.class);
		System.out.println(response);

		// String expected = "{id:Course1,name:Spring,description:10 Steps}";

		// try {
		// JSONAssert.assertEquals(expected, response.getBody(), false);
		// } catch (JSONException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Test
	public void addCourse() {

		Course course = new Course("Course1", "Spring", "10 Steps",
				Arrays.asList("Learn Maven", "Import Project", "First Example", "Second Example"));

		HttpEntity<Course> entity = new HttpEntity<Course>(course, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/students/Student1/courses"),
				HttpMethod.POST, entity, String.class);

		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

		assertTrue(actual.contains("/students/Student1/courses/"));

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private String createHttpAuthenticationHeaderValue(String userId, String password) {

		String auth = userId + ":" + password;

		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));

		String headerValue = "Basic " + new String(encodedAuth);

		return headerValue;
	}

}
