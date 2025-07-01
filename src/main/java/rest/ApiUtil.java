package rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

public class ApiUtil {
	private static final Set<Integer> usedNumbers = new HashSet<>();
	private static final Random random = new Random();
	private static String BASE_URL;
	Properties prop;

	/**
	 * Retrieves the base URL from the configuration properties file.
	 *
	 * <p>
	 * This method loads the properties from the file located at
	 * <code>{user.dir}/src/main/resources/config.properties</code> and extracts the
	 * value associated with the key <code>base.url</code>. The value is stored in
	 * the static variable <code>BASE_URL</code> and returned.
	 *
	 * @return the base URL string if successfully read from the properties file;
	 *         {@code null} if an I/O error occurs while reading the file.
	 */
	public String getBaseUrl() {
		prop = new Properties();
		try (FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties")) {
			prop.load(fis);
			BASE_URL = prop.getProperty("base.url");
			return BASE_URL;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves the username from the configuration properties file.
	 *
	 * <p>
	 * This method reads the properties from the file located at
	 * <code>{user.dir}/src/main/resources/config.properties</code> and returns the
	 * value associated with the key <code>username</code>.
	 *
	 * @return the username as a {@code String} if found in the properties file;
	 *         {@code null} if an I/O error occurs while reading the file.
	 */
	public String getUsername() {
		prop = new Properties();
		try (FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties")) {
			prop.load(fis);
			return prop.getProperty("username");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getPassword() {
		prop = new Properties();
		try (FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties")) {
			prop.load(fis);
			return prop.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves the password from the configuration properties file.
	 *
	 * <p>
	 * This method loads the properties from the file located at
	 * <code>{user.dir}/src/main/resources/config.properties</code> and returns the
	 * value associated with the key <code>password</code>.
	 *
	 * @return the password as a {@code String} if found in the properties file;
	 *         {@code null} if an I/O error occurs while reading the file.
	 */
	public static String generateUniqueName(String base) {
		int uniqueNumber;
		do {
			uniqueNumber = 1000 + random.nextInt(9000);
		} while (usedNumbers.contains(uniqueNumber));

		usedNumbers.add(uniqueNumber);
		return base + uniqueNumber;
	}

	/**
	 * Sends a GET request to the specified endpoint with a session cookie and an optional request body.
	 *
	 * <p>
	 * This method uses RestAssured to construct a GET request, sets the 
	 * <code>Content-Type</code> header to <code>application/json</code>, and includes 
	 * a cookie named <code>orangehrm</code> for authentication or session tracking. 
	 * If a request body is provided, it is attached to the request (though sending a body 
	 * with a GET request is generally discouraged and may not be supported by all servers).
	 *
	 * @param endpoint    the relative endpoint to which the GET request is sent,
	 *                    appended to the base URL
	 * @param cookieValue the value of the <code>orangehrm</code> cookie used for session
	 *                    or authorization purposes
	 * @param body        a map representing the request body parameters, or <code>null</code> 
	 *                    if no body is to be sent
	 * @return the {@link io.restassured.response.Response} returned from the GET request
	 */

	public Response getLocalizationValid(String endpoint, String cookieValue, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		return request.get(BASE_URL + endpoint).then().extract().response();
	}

	/**
	 * Sends a GET request to the specified endpoint with a session cookie and an optional request body.
	 *
	 * <p>
	 * This method uses RestAssured to construct a GET request, sets the 
	 * <code>Content-Type</code> header to <code>application/json</code>, and includes 
	 * a cookie named <code>orangehrm</code> for session or authentication purposes. 
	 * If a request body is provided, it is attached to the request. Note that attaching 
	 * a body to a GET request is non-standard and may be ignored by some servers or cause unexpected behavior.
	 *
	 * @param endpoint    the relative endpoint to which the GET request is sent,
	 *                    appended to the base URL
	 * @param cookieValue the value of the <code>orangehrm</code> cookie used for session
	 *                    or authorization purposes
	 * @param body        a map representing the request body parameters, or <code>null</code> 
	 *                    if no body is to be sent
	 * @return the {@link io.restassured.response.Response} returned from the GET request
	 */

	public Response getActiveLanguages(String endpoint, String cookieValue, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		return request.get(BASE_URL + endpoint).then().extract().response();
	}

	/**
	 * Sends a GET request to the specified endpoint with a session cookie and an optional request body.
	 *
	 * <p>
	 * This method constructs a GET request using RestAssured, sets the 
	 * <code>Content-Type</code> header to <code>application/json</code>, and includes 
	 * a cookie named <code>orangehrm</code> for session or authorization purposes. 
	 * If a request body is provided, it is attached to the request. Note that using a 
	 * request body with a GET request is unconventional and may not be supported by all servers.
	 *
	 * @param endpoint    the relative endpoint to which the GET request is sent,
	 *                    appended to the base URL
	 * @param cookieValue the value of the <code>orangehrm</code> cookie used for session
	 *                    or authentication purposes
	 * @param body        a map representing the request body parameters, or <code>null</code> 
	 *                    if no body is to be sent
	 * @return the {@link io.restassured.response.Response} returned from the GET request
	 */

	public Response getDashboardShortcuts(String endpoint, String cookieValue, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		return request.get(BASE_URL + endpoint).then().extract().response();
	}

	/**
	 * Sends a GET request to the specified endpoint with a session cookie and an optional request body.
	 *
	 * <p>
	 * This method constructs a GET request using RestAssured, sets the 
	 * <code>Content-Type</code> header to <code>application/json</code>, and includes 
	 * a cookie named <code>orangehrm</code> for session or authentication purposes. 
	 * If a request body is provided, it is attached to the request. Including a body 
	 * in a GET request is not standard practice and may be ignored or rejected by some servers.
	 *
	 * @param endpoint    the relative endpoint to which the GET request is sent,
	 *                    appended to the base URL
	 * @param cookieValue the value of the <code>orangehrm</code> cookie used for session
	 *                    or authentication purposes
	 * @param body        a map representing the request body parameters, or <code>null</code> 
	 *                    if no body is to be sent
	 * @return the {@link io.restassured.response.Response} returned from the GET request
	 */

	public Response getAuthProviders(String endpoint, String cookieValue, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		return request.get(BASE_URL + endpoint).then().extract().response();
	}

	/**
	 * Sends a GET request to the specified endpoint with a session cookie and an optional request body.
	 *
	 * <p>
	 * This method constructs a GET request using RestAssured, sets the 
	 * <code>Content-Type</code> header to <code>application/json</code>, and includes 
	 * a cookie named <code>orangehrm</code> for session or authorization purposes. 
	 * If a request body is provided, it is attached to the request. Note that 
	 * sending a body with a GET request is non-standard and may be ignored or 
	 * unsupported by certain servers or APIs.
	 *
	 * @param endpoint    the relative endpoint to which the GET request is sent,
	 *                    appended to the base URL
	 * @param cookieValue the value of the <code>orangehrm</code> cookie used for session
	 *                    or authentication purposes
	 * @param body        a map representing the request body parameters, or <code>null</code> 
	 *                    if no body is to be sent
	 * @return the {@link io.restassured.response.Response} returned from the GET request
	 */

	public Response getAdminOAuthList(String endpoint, String cookieValue, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		return request.get(BASE_URL + endpoint).then().extract().response();
	}
	
	/**
	 * Sends a GET request to the specified endpoint with a session cookie and an optional request body.
	 *
	 * <p>
	 * This method constructs a GET request using RestAssured, sets the 
	 * <code>Content-Type</code> header to <code>application/json</code>, and includes 
	 * a cookie named <code>orangehrm</code> for session or authentication purposes. 
	 * If a request body is provided, it is attached to the request. Although technically 
	 * possible, sending a body with a GET request is against standard HTTP semantics 
	 * and may be ignored or rejected by some servers.
	 *
	 * @param endpoint    the relative endpoint to which the GET request is sent,
	 *                    appended to the base URL
	 * @param cookieValue the value of the <code>orangehrm</code> cookie used for session
	 *                    or authorization purposes
	 * @param body        an object representing the request body, or <code>null</code> 
	 *                    if no body is to be sent
	 * @return the {@link io.restassured.response.Response} returned from the GET request
	 */

	
	public Response getEmployeeList(String endpoint, String cookieValue, Object body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		return request.get(BASE_URL + endpoint).then().extract().response();
	}

	/**
	 * Sends a PUT request to the specified endpoint with a session cookie.
	 *
	 * <p>
	 * This method constructs a PUT request using RestAssured, sets the 
	 * <code>Content-Type</code> header to <code>application/json</code>, and includes 
	 * a cookie named <code>orangehrm</code> for session or authorization purposes. 
	 * No request body is sent with this request.
	 *
	 * @param endpoint    the relative endpoint to which the PUT request is sent,
	 *                    appended to the base URL
	 * @param cookieValue the value of the <code>orangehrm</code> cookie used for session
	 *                    or authentication purposes
	 * @return the {@link io.restassured.response.Response} returned from the PUT request
	 */

	public Response putLanguagePackage(String endpoint, String cookieValue) {

		return RestAssured.given().cookie("orangehrm", cookieValue)
				.header("Content-Type", "application/json")
				.put(BASE_URL + endpoint) // Sending PUT request to the specified endpoint
				.then().extract().response(); // Extracting the response
	}
	
	/**
	 * Sends a DELETE request to the specified endpoint with a session cookie and a request body.
	 *
	 * <p>
	 * This method constructs a DELETE request using RestAssured, sets the 
	 * <code>Content-Type</code> header to <code>application/json</code>, and includes 
	 * a cookie named <code>orangehrm</code> for session or authorization purposes. 
	 * A request body is also attached to the request, which is supported by some servers 
	 * but not universally accepted for DELETE requests.
	 *
	 * @param endpoint     the relative endpoint to which the DELETE request is sent,
	 *                     appended to the base URL
	 * @param cookieValue  the value of the <code>orangehrm</code> cookie used for session
	 *                     or authentication purposes
	 * @param requestBody  the JSON string representing the request body to be sent with the DELETE request
	 * @return the {@link io.restassured.response.Response} returned from the DELETE request
	 */

	
	public Response deleteLangById(String endpoint, String cookieValue,String requestBody) {
		return RestAssured.given().cookie("orangehrm", cookieValue)
				.header("Content-Type", "application/json")
				.body(requestBody)
				.delete(BASE_URL + endpoint) // Sending PUT request to the specified endpoint
				.then().extract().response(); // Extracting the response
	
	}
	
	/**
	 * Sends a PUT request to the specified endpoint with a session cookie and a request body.
	 *
	 * <p>
	 * This method constructs a PUT request using RestAssured, sets the 
	 * <code>Content-Type</code> header to <code>application/json</code>, and includes 
	 * a cookie named <code>orangehrm</code> for session or authentication purposes. 
	 * A request body is attached to the request in JSON string format.
	 *
	 * @param endpoint     the relative endpoint to which the PUT request is sent,
	 *                     appended to the base URL
	 * @param cookieValue  the value of the <code>orangehrm</code> cookie used for session
	 *                     or authorization purposes
	 * @param requestBody  the JSON string representing the request body to be sent with the PUT request
	 * @return the {@link io.restassured.response.Response} returned from the PUT request
	 */

	
	public Response putModulesSettings(String endpoint, String cookieValue,String requestBody) {
		return RestAssured.given().cookie("orangehrm", cookieValue)
				.header("Content-Type", "application/json")
				.body(requestBody)
				.put(BASE_URL + endpoint) // Sending PUT request to the specified endpoint
				.then().extract().response(); // Extracting the response
	
	}
	/**
	 * Sends a POST request to the specified endpoint with a session cookie and a request body.
	 *
	 * <p>
	 * This method constructs a POST request using RestAssured, sets the 
	 * <code>Content-Type</code> header to <code>application/json</code>, and includes 
	 * a cookie named <code>orangehrm</code> for session or authentication purposes. 
	 * A request body is attached to the request in JSON string format.
	 *
	 * @param endpoint     the relative endpoint to which the POST request is sent,
	 *                     appended to the base URL
	 * @param cookieValue  the value of the <code>orangehrm</code> cookie used for session
	 *                     or authorization purposes
	 * @param requestBody  the JSON string representing the request body to be sent with the POST request
	 * @return the {@link io.restassured.response.Response} returned from the POST request
	 */

	
	public Response postOpenIdProvide(String endpoint, String cookieValue,String requestBody) {
		return RestAssured.given().cookie("orangehrm", cookieValue)
				.header("Content-Type", "application/json")
				.body(requestBody)
				.post(BASE_URL + endpoint) // Sending PUT request to the specified endpoint
				.then().extract().response(); // Extracting the response
	
	}

	/**
	 * Executes a DELETE request to the specified endpoint with a cookie and JSON
	 * string body.
	 *
	 * <p>
	 * This method constructs a DELETE request using RestAssured, setting the
	 * <code>Content-Type</code> header to <code>application/json</code> and
	 * attaching a cookie named <code>orangehrm</code>. The provided JSON string is
	 * used as the request payload.
	 *
	 * @param endpoint    the relative API endpoint to which the DELETE request is
	 *                    sent
	 * @param cookieValue the value of the <code>orangehrm</code> cookie used for
	 *                    session or authentication
	 * @param body        the request payload in JSON string format
	 * @return the {@link io.restassured.response.Response} object representing the
	 *         server's response
	 */
	public Response pl1usertescase1_08(String endpoint, String cookieValue, String body) {

		return RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type", "application/json")
				.body(body) // Adding the request payload as a JSON string
				.delete(BASE_URL + endpoint) // Sending POST request to the specified endpoint
				.then().extract().response(); // Extracting the response
	}

	/**
	 * Sends a PUT request to the specified endpoint with a cookie and an optional
	 * request body.
	 *
	 * <p>
	 * This method uses RestAssured to create and send a PUT request to the given
	 * endpoint. It sets the <code>Content-Type</code> header to
	 * <code>application/json</code> and includes a cookie named
	 * <code>orangehrm</code>. If a body object is provided, it is added to the
	 * request payload.
	 *
	 * @param endpoint    the API endpoint to which the PUT request will be sent,
	 *                    relative to the base URL
	 * @param cookieValue the value of the <code>orangehrm</code> cookie used for
	 *                    authentication or session tracking
	 * @param body        the request payload as an object (can be null)
	 * @return the {@link io.restassured.response.Response} object containing the
	 *         result of the POST request
	 */
	public Response pl1usertescase1_09(String endpoint, String cookieValue, Object body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		return request.put(BASE_URL + endpoint).then().extract().response();
	}

	/**
	 * Performs a POST request to the specified endpoint using a cookie and a
	 * JSON-formatted string body.
	 *
	 * <p>
	 * This method uses RestAssured to send a POST request to the given endpoint. It
	 * sets the <code>Content-Type</code> header to <code>application/json</code>,
	 * includes a cookie named <code>orangehrm</code>, and attaches the provided
	 * JSON string as the request payload.
	 *
	 * @param endpoint    the relative API endpoint for the POST request, appended
	 *                    to the base URL
	 * @param cookieValue the value of the <code>orangehrm</code> cookie for
	 *                    authentication or session handling
	 * @param body        a JSON string representing the request body
	 * @return the {@link io.restassured.response.Response} object containing the
	 *         response from the server
	 */
	public Response pl1usertescase1_10(String endpoint, String cookieValue, String body) {

		return RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type", "application/json")
				.body(body) // Adding the request payload as a JSON string
				.post(BASE_URL + endpoint) // Sending POST request to the specified endpoint
				.then().extract().response(); // Extracting the response
	}

}