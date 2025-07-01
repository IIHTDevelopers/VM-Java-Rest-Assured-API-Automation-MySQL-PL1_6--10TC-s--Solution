package testcases;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.ApiUtil;
import testcases.TestCodeValidator;
import coreUtilities.utils.FileOperations;
import org.apache.poi.xssf.usermodel.*;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Set;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import static org.testng.Assert.assertEquals;

@SuppressWarnings("unused")
public class RestAssured_TestCases {

	private static String baseUrl;
	private static String username;
	private static String password;
	private static String cookieValue = null;
	private ApiUtil apiUtil;
	private int employeeStatus;
	private TestCodeValidator testCodeValidator;
	private String apiUtilPath = System.getProperty("user.dir") + "\\src\\main\\java\\rest\\ApiUtil.java";

	
	@BeforeClass
	public void loginWithSeleniumAndGetCookie() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		apiUtil = new ApiUtil();
		baseUrl = apiUtil.getBaseUrl();
		username = apiUtil.getUsername();
		password = apiUtil.getPassword();

		driver.get(baseUrl + "/web/index.php/auth/login");
		Thread.sleep(3000); // Wait for page load

		// Login to the app
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		Thread.sleep(9000); // Wait for login

		// Extract cookie named "orangehrm"
		Set<Cookie> cookies = driver.manage().getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("orangehrm")) {
				cookieValue = cookie.getValue();
				break;
			}
		}

		driver.quit();
		testCodeValidator = new TestCodeValidator();

		if (cookieValue == null) {
			throw new RuntimeException("orangehrm cookie not found after login");
		}
	}

	@Test(priority = 1, groups = {
			"PL1" }, description = "1. Send a GET request to the '/web/index.php/api/v2/admin/localization' endpoint with a valid cookie\n"
					+ "2. Do not pass any request body (null)\n"
					+ "3. Print and verify the response status code and response body\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void getLocalizationValid() throws IOException {

		String endpoint = "/web/index.php/api/v2/admin/localization";
		Response response = apiUtil.getLocalizationValid(endpoint, cookieValue, null);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"getLocalizationValid", List.of("cookie", "get", "response"));

		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());

		Assert.assertTrue(isImplementationCorrect, "getLocalizationValid must be implementated using the Rest assured  methods only!");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 2, groups = {
			"PL1" }, description = "1. Send a GET request to the '/web/index.php/api/v2/admin/i18n/languages?limit=50&offset=0&sortOrder=ASC&activeOnly=true' endpoint with a valid cookie\n"
					+ "2. Do not include any request body (null)\n"
					+ "3. Print the response status code and response body for logging\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void getActiveLanguages() throws IOException {
		Response response = apiUtil.getActiveLanguages("/web/index.php/api/v2/admin/i18n/languages?limit=50&offset=0&sortOrder=ASC&activeOnly=true",
				cookieValue, null);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"getActiveLanguages", List.of("given", "cookie", "get", "response"));

		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());

		Assert.assertTrue(isImplementationCorrect, "getActiveLanguages must be implementated using the Rest assured  methods only!");
		assertEquals(response.getStatusCode(), 200);
	}
	

	@Test(priority = 3, groups = {
			"PL1" }, description = "1. Send a GET request to the '/web/index.php/api/v2/dashboard/shortcuts' endpoint with a valid cookie\n"
					+ "2. Do not provide any request body (null)\n"
					+ "3. Print the response status code and body for verification\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void getDashboardShortcuts() throws IOException {
		Response response = apiUtil.getDashboardShortcuts("/web/index.php/api/v2/admin/modules", cookieValue, null);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"getDashboardShortcuts", List.of("given", "cookie", "get", "response"));

		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());

		Assert.assertTrue(isImplementationCorrect, "getDashboardShortcuts must be implementated using the Rest assured  methods only!");
		assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 4, groups = {
			"PL1" }, description = "1. Send a GET request to the '/web/index.php/api/v2/auth/openid-providers?limit=50&offset=0' endpoint with a valid cookie\n"
					+ "2. Do not include any request body (null)\n"
					+ "3. Log the date used, response status code, and response body\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void getAuthProviders() throws IOException {
		
		Response response = apiUtil.getAuthProviders(
				"/web/index.php/api/v2/auth/openid-providers?limit=50&offset=0", cookieValue, null);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"getAuthProviders", List.of("given", "cookie", "get", "response"));

		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());

		Assert.assertTrue(isImplementationCorrect, "getAuthProviders must be implementated using the Rest assured  methods only!");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	@Test(priority = 5, groups = {
			"PL1" }, description = "1. Send a GET request to the '/web/index.php/api/v2/admin/oauth-clients?limit=50&offset=0' endpoint with a valid cookie\n"
					+ "2. Do not provide any request body (null)\n"
					+ "3. Print the response status code and response body for validation\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void getAdminOAuthList() throws IOException {

		Response response = apiUtil.getAdminOAuthList("/web/index.php/api/v2/admin/oauth-clients?limit=50&offset=0", cookieValue,
				null);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"getAdminOAuthList", List.of("given", "cookie", "get", "response"));

		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());

		Assert.assertTrue(isImplementationCorrect, "getAdminOAuthList must be implementated using the Rest assured  methods only!");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 6, groups = {
			"PL1" }, description = 
					"1. Construct the endpoint '/web/index.php/api/v2/pim/employees?limit=50&offset=0&model=detailed&includeEmployees=onlyCurrent&sortField=employee.firstName&sortOrder=ASC' \n"
					+ "2. Send a GET request with the constructed endpoint, valid cookie, and request body\n"
					+ "3. Print the response status code and body for verification\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void getEmployeeList() throws IOException {

		Response response = apiUtil.getEmployeeList("/web/index.php/api/v2/pim/employees?limit=50&offset=0&model=detailed&includeEmployees=onlyCurrent&sortField=employee.firstName&sortOrder=ASC" , cookieValue,
				null);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"getEmployeeList", List.of("given", "cookie", "get", "response"));

		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());

		Assert.assertTrue(isImplementationCorrect, "getEmployeeList must be implementated using the Rest assured  methods only!");
		assertEquals(response.getStatusCode(), 200);
	}

	
	@Test(priority = 7, groups = {
	"PL1" }, description = "1. fetch the language package number thast doesnt already exist in the list. "
			+ "2. Send a PUT request to the '/web/index.php/api/v2/admin/i18n/languages/{{lang_package_no}}' endpoint with a valid cookie and request body\\n"
			+ "3. Print the request body, response status code, and response body for debugging\n"
			+ "4. Assert that the response status code is 200, indicating successful creation")
	public void putLanguagePackage() throws IOException {
			int id=getMissingLanguageIdLessThan450();
			System.out.println("language id is "+ id);
			Response response=apiUtil.putLanguagePackage("/web/index.php/api/v2/admin/i18n/languages/"+id,cookieValue);
				
			boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
					"putLanguagePackage", List.of("given", "cookie", "put", "response"));
			
			System.out.println("Response Body: " + response.getBody().asString());
			System.out.println("Status Code: " + response.getStatusCode());
			Assert.assertTrue(isImplementationCorrect, "putLanguagePackage must be implementated using the Rest assured  methods only!");
			// Assert that the status code is 200 or 201 (depends on API behavior)
			assertEquals(response.getStatusCode(), 200, "Expected status code 200 for successful POST.");
	
	}
	
	@Test(priority = 8, groups = {
	"PL1" }, description = "1. fetch the language package number that doesnt already exist in the list and send its id as req body. "
			+ "2. Send a DELETE request to the '/web/index.php/api/v2/admin/i18n/languages' endpoint with a valid cookie and request body\\n"
			+ "3. Print the request body, response status code, and response body for debugging\n"
			+ "4. Assert that the response status code is 200, indicating successful creation")
	public void deleteLangById() throws IOException {
			
			int id=getfirstlangid();
			String requestBody = "{ \"ids\": [" + id + "] }";
			
			Response response =apiUtil.deleteLangById("/web/index.php/api/v2/admin/i18n/languages",cookieValue,requestBody);
			
			boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
					"pl1usertescase1_08", List.of("given", "cookie", "delete", "response"));
			
			System.out.println("Request Body: " + requestBody);
			System.out.println("Status Code: " + response.getStatusCode());
			System.out.println("Response Body: " + response.getBody().asString());
			
	
			Assert.assertTrue(isImplementationCorrect, "deleteLangById must be implementated using the Rest assured  methods only!");
			// Assert that the status code is 200 or 201 (depends on API behavior)
			assertEquals(response.getStatusCode(), 200, "Expected status code 200 for successful POST.");
			
		}
	
	@Test(priority = 9, groups = {
	"PL1" }, description = "1. Create a req body with boolean entries ,. "
			+ "2. Send a PUT request to the '/web/index.php/api/v2/admin/modules' endpoint with a valid cookie and request body\\n"
			+ "3. Print the request body, response status code, and response body for debugging\n"
			+ "4. Assert that the response status code is 200, indicating successful creation")
	public void putModulesSettings() throws IOException {
			String requestBody = "{"
			        + "\"admin\": true,"
			        + "\"buzz\": true,"
			        + "\"claim\": true,"
			        + "\"directory\": true,"
			        + "\"leave\": false,"
			        + "\"maintenance\": true,"
			        + "\"mobile\": true,"
			        + "\"performance\": true,"
			        + "\"pim\": true,"
			        + "\"recruitment\": true,"
			        + "\"time\": true"
			        + "}";
			
			Response response =apiUtil.putModulesSettings("/web/index.php/api/v2/admin/modules",cookieValue,requestBody);
			
			System.out.println(requestBody);
			boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
					"putModulesSettings", List.of("given", "cookie", "put", "response"));
			
			System.out.println("Request Body: " + requestBody);
			System.out.println("Status Code: " + response.getStatusCode());
			System.out.println("Response Body: " + response.getBody().asString());
			
	
			Assert.assertTrue(isImplementationCorrect, "putModulesSettings must be implementated using the Rest assured  methods only!");
			// Assert that the status code is 200 or 201 (depends on API behavior)
			assertEquals(response.getStatusCode(), 200, "Expected status code 200 for successful POST.");
	
		}

	@Test(priority = 10, groups = {
	"PL1" }, description = "1. Create a req body with required fields({name: \"ABCDE\", url: \"WWW.GOOGLE.COM\", clientId: \"1\", clientSecret: \"123\"}) and making the name unique. "
			+ "2. Send a POST request to the '/web/index.php/api/v2/auth/openid-providers' endpoint with a valid cookie and request body\\n"
			+ "3. Print the request body, response status code, and response body for debugging\n"
			+ "4. Assert that the response status code is 200, indicating successful creation")
	public void postOpenIdProvide() throws IOException {
			String uniqueName = "ABCDE_" + UUID.randomUUID().toString().substring(0, 8); // or use full UUID
			String requestBody = "{"
			        + "\"name\": \"" + uniqueName + "\","
			        + "\"url\": \"WWW.GOOGLE.COM\","
			        + "\"clientId\": \"1\","
			        + "\"clientSecret\": \"123\""
			        + "}";

		
		Response response =apiUtil.postOpenIdProvide("/web/index.php/api/v2/auth/openid-providers",cookieValue,requestBody);
		
		System.out.println(requestBody);
		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"postOpenIdProvide", List.of("given", "cookie", "post", "response"));
		
		System.out.println("Request Body: " + requestBody);
		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());
		

		Assert.assertTrue(isImplementationCorrect, "GetEmpLeaveInfo must be implementated using the Rest assured  methods only!");
		// Assert that the status code is 200 or 201 (depends on API behavior)
		assertEquals(response.getStatusCode(), 200, "Expected status code 200 for successful POST.");


		
	}
	

/*
	@Test(priority = 7, groups = {
			"PL1" }, description = "1. Generate a unique employment status name using the current timestamp\n"
					+ "2. Create a JSON request body with the generated name\n"
					+ "3. Send a POST request to the '/web/index.php/api/v2/admin/employment-statuses' endpoint with a valid cookie and request body\n"
					+ "4. Print the request body, response status code, and response body for debugging\n"
					+ "5. Assert that the response status code is 200, indicating successful creation")
	public void pl1usertescase1_07() throws IOException {

		// Generate a unique name using current timestamp
		String uniqueName = "status_" + System.currentTimeMillis();
		String requestBody = "{ \"name\": \"" + uniqueName + "\" }";

		Response response = apiUtil.pl1usertescase1_07("/web/index.php/api/v2/admin/employment-statuses", cookieValue,
				requestBody);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"pl1usertescase1_07", List.of("given", "cookie", "post", "response"));

		// Print debug info
		System.out.println("Request Body: " + requestBody);
		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());
		JsonPath jsonPath = response.jsonPath();
		employeeStatus = jsonPath.getInt("data.id");
		String statusName = jsonPath.getString("data.name");

		Assert.assertTrue(isImplementationCorrect, "GetEmpLeaveInfo must be implementated using the Rest assured  methods only!");
		// Assert that the status code is 200 or 201 (depends on API behavior)
		assertEquals(response.getStatusCode(), 200, "Expected status code 200 for successful POST.");
		assertEquals(uniqueName, statusName, "The status name does not match the expected!");
	}

	@Test(priority = 9, groups = {
			"PL1" }, description = "Precondition: Retrieve the employment status ID using getemploymentstatusid()\n"
					+ "1. Construct the request body with the retrieved ID in the format: {\"ids\": [id]}\n"
					+ "2. Send a DELETE request to the '/web/index.php/api/v2/admin/employment-statuses' endpoint with a valid cookie and the request body\n"
					+ "3. Print the request body, response status code, and response body for debugging\n"
					+ "4. Assert that the response status code is 200, indicating the request was successful")
	public void pl1usertescase1_08() throws IOException {

//		int id = getemploymentstatusid();
		String requestBody = "{\"ids\": [" + employeeStatus + "]}";// during post request id is not working.

		Response response = apiUtil.pl1usertescase1_08("/web/index.php/api/v2/admin/employment-statuses", cookieValue,
				requestBody);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"pl1usertescase1_08", List.of("given", "cookie", "delete", "response"));

		System.out.println("Request Body: " + requestBody);
		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());

		// Assert that the status code is 200 or 201 (depends on API behavior)
		assertEquals(response.getStatusCode(), 200, "Expected status code 200 for success.");
		Assert.assertTrue(isImplementationCorrect, "GetEmpLeaveInfo must be implementated using the Rest assured  methods only!");
	}

	@Test(priority = 8, groups = {
			"PL1" }, description = "Precondition: Retrieve the employment status ID using getemploymentstatusid()\n"
					+ "1. Construct the endpoint '/web/index.php/api/v2/admin/employment-statuses/{id}' using the retrieved ID\n"
					+ "2. Create a JSON request body to update the employment status name to 'abcdef'\n"
					+ "3. Send a PUT request to the constructed endpoint with a valid cookie and the request body\n"
					+ "4. Print the request body, response status code, and response body for debugging\n"
					+ "5. Assert that the response status code is 200, indicating successful update")
	public void pl1usertescase1_09() throws IOException {
		int id = getemploymentstatusid();
		String requestBody = "{\"name\": \"abcdef\"}";
		Response response = apiUtil.pl1usertescase1_09(
				"/web/index.php/api/v2/admin/employment-statuses/" + employeeStatus, cookieValue, requestBody);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"pl1usertescase1_09", List.of("given", "cookie", "put", "response"));

		System.out.println("Request Body: " + requestBody);
		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());

		assertEquals(response.getStatusCode(), 200, "Expected status code 200 for success.");
		Assert.assertTrue(isImplementationCorrect, "GetEmpLeaveInfo must be implementated using the Rest assured  methods only!");
	}

	@Test(priority = 10, groups = {
			"PL1" }, description = "1. Generate a unique job category name using a random UUID substring\n"
					+ "2. Create a JSON request body with the generated name\n"
					+ "3. Send a POST request to the '/web/index.php/api/v2/admin/job-categories' endpoint with a valid cookie and the request body\n"
					+ "4. Print the request body, response status code, and response body for debugging\n"
					+ "5. Assert that the response status code is 200, indicating successful creation of the job category")
	public void pl1usertescase1_10() throws IOException {
		String randomName = "name_" + UUID.randomUUID().toString().substring(0, 5);
		String requestBody = "{ \"name\": \"" + randomName + "\" }";

		Response response = apiUtil.pl1usertescase1_10("/web/index.php/api/v2/admin/job-categories", cookieValue,
				requestBody);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath,
				"pl1usertescase1_10", List.of("given", "cookie", "post", "response"));

		System.out.println("Request Body: " + requestBody);
		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());

		assertEquals(response.getStatusCode(), 200, "Expected status code 200 for success.");
		Assert.assertTrue(isImplementationCorrect, "GetEmpLeaveInfo must be implementated using the Rest assured  methods only!");
	}

	/*------------Helper Methods------------*/
	
	public int getfirstlangid() {
	    String url = "https://opensource-demo.orangehrmlive.com/web/index.php/api/v2/admin/i18n/languages?limit=50&offset=0&sortOrder=ASC&activeOnly=true";

	    Response response = RestAssured
	            .given()
	            .cookie("orangehrm", cookieValue)
	            .header("Content-Type", "application/json")
	            .get(url)
	            .then()
	            .extract()
	            .response();

	    // Extract the first language ID from the JSON response
	    int firstLangId = response.jsonPath().getInt("data[0].id");
	    return firstLangId;
	}


	public int getMissingLanguageIdLessThan450() {
	    String url = "https://opensource-demo.orangehrmlive.com/web/index.php/api/v2/admin/i18n/languages?limit=50&offset=0&sortOrder=ASC&activeOnly=true";

	    Response response = RestAssured
	            .given()
	            .cookie("orangehrm", cookieValue)
	            .header("Content-Type", "application/json")
	            .get(url)
	            .then()	         
	            .extract()
	            .response();

	    List<Integer> existingIds = response.jsonPath().getList("data.id");
//	    System.out.println("Existing IDs: " + existingIds);

	    if (existingIds == null || existingIds.isEmpty()) {
	        System.out.println("No IDs found in response!");
	        return -1;
	    }

	    Set<Integer> idSet = new HashSet<>(existingIds);

	    for (int i = 1; i < 450; i++) {
	        if (!idSet.contains(i)) {
	            System.out.println("Missing ID Found: " + i);
	            return i;
	        }
	    }

	    System.out.println("No missing ID found under 450.");
	    return -1;
	}

	
	public int getPayGradeid() {
		String endpoint = "/web/index.php/api/v2/admin/pay-grades?limit=50&offset=0";
		Response response = RestAssured.given().cookie("orangehrm", cookieValue).get(baseUrl + endpoint);

		if (response.statusCode() == 200) {
			int firstId = response.jsonPath().getInt("data[0].id");
			System.out.println("First Job Title ID: " + firstId);
			return firstId;
		} else {
			System.out.println("Failed to fetch job titles. Status code: " + response.statusCode());
			return -1;
		}
	}

	public int getemploymentstatusid() {
		String endpoint = "/web/index.php/api/v2/admin/employment-statuses?limit=50&offset=0";

		Response response = RestAssured.given().cookie("orangehrm", cookieValue).get(baseUrl + endpoint);

		if (response.statusCode() == 200) {
			int firstId = response.jsonPath().getInt("data[0].id");
			System.out.println("First Job Title ID: " + firstId);
			return firstId;
		} else {
			System.out.println("Failed to fetch job titles. Status code: " + response.statusCode());
			return -1;
		}

	}

//	public int getFirstEmploymentStatus() {
//		String endpoint = "/web/index.php/api/v2/admin/employment-statuses?limit=50&offset=0";
//
//		Response response = RestAssured.given().cookie("orangehrm", cookieValue).get(baseUrl + endpoint);
//
//		if (response.statusCode() == 200) {
//			int firstId = response.jsonPath().getInt("data[0].id");
//			System.out.println("First Job Title ID: " + firstId);
//			return firstId;
//		} else {
//			System.out.println("Failed to fetch job titles. Status code: " + response.statusCode());
//			return -1;
//		}
//
//	}
//
//	public int getFirstJobTitleId() {
//		String endpoint = "/web/index.php/api/v2/admin/job-titles?limit=50&offset=0&sortField=jt.jobTitleName&sortOrder=ASC";
//		Response response = RestAssured.given().cookie("orangehrm", cookieValue).get(baseUrl + endpoint);
//
//		if (response.statusCode() == 200) {
//			int firstId = response.jsonPath().getInt("data[0].id");
//			System.out.println("First Job Title ID: " + firstId);
//			return firstId;
//		} else {
//			System.out.println("Failed to fetch job titles. Status code: " + response.statusCode());
//			return -1;
//		}
//
//	}
}
