package qiucao.RestAssuredDemo;

import static io.restassured.RestAssured.oauth2;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static io.restassured.RestAssured.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.joda.time.*;

/*
 * Response消息的获取和解析
 */
class Lesson3 {
	
	static Response response;
	/*
	 * 设置RestAssured全局配置
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		RestAssured.baseURI = "https://api.github.com/";
		RestAssured.authentication = oauth2("4d77b9abd8346cc06f47713bfb4ccc44e2fe6ce6");
		
		response = given().
				pathParam("owner","chengxiaqiucao").
				pathParam("repo","RestApiTestingDemo").
				when().
				get("repos/{owner}/{repo}");
	
	}

	@Test
	void get_and_parse_response() {
		String resBody = response.getBody().asString();
		String resBodyInfo = response.getBody().toString();
		
		System.out.println("消息体："+resBody);
		System.out.println("消息体对象："+ resBodyInfo);
		
		response.getBody().prettyPrint();
		
		String resHeaderStatus = response.getHeader("status");
		System.out.println("响应状态："+resHeaderStatus);
		
		System.out.println("cookie信息:" +response.getCookies());
		System.out.println("响应的内容类型："+ response.getContentType());
		
		System.out.println("接口响应时间："+response.getTime());
		System.out.println("接口响应时间(秒)："+response.getTimeIn(TimeUnit.SECONDS));
	
		JsonPath json =  new JsonPath(resBody);
		System.out.println("repo ID:" +json.get("id"));
		json.setRoot("owner");
		System.out.println("owner ID:" +json.get("id"));
		
		//使用Junit自带断言完成响应校验
		//Junit5开始，支持assertAll方法完成批量校验
		assertAll("this is a group assert:",
				() -> assertTrue(resHeaderStatus.contains("Error")),
				() -> assertEquals(json.getInt("id"),"fail"),
				() -> assertTrue(response.getContentType().contains("json"))
				);
		
//		assertTrue(resHeaderStatus.contains("OK"));
//		
//		assertEquals(json.getInt("id"),"fail");
//		
//		assertTrue(response.getContentType().contains("json"));
		
		System.out.println("should print...");
	}
	
	//完成通过RestAssured的内建校验方法实现验证
	@Test
	public void validate_status() {
		response.then().statusCode(200);
	}
	
	@Test
	public void validate_header() {
		response.then().header("Content-Type", containsString("json"));
	}
	
	@Test
	public void validate_res_time() {
		response.then().time(lessThan(3000L));
	}
	
	@Test
	public void validate_body() {
		response.then().body("owner.login", equalTo("chengxiaqiucao"));
	}
	
	//使用json-schema完成响应校验
	@Test
	public void json_schema_validate() {
		String schema = "{\r\n" + 
				"	\"$schema\": \"http://json-schema.org/draft-04/schema#\",\r\n" + 
				"	\"type\": \"object\",\r\n" + 
				"	\"required\": [\"id\",\"name\",\"owner\"],\r\n" + 
				"	\"properties\": {\r\n" + 
				"		\"id\":{\r\n" + 
				"			\"type\": \"number\",\r\n" + 
				"			\"minimum\": 100000000\r\n" + 
				"		},\r\n" + 
				"		\"name\": {\r\n" + 
				"			\"type\": \"string\",\r\n" + 
				"			\"minLength\":5,\r\n" + 
				"			\"maxLength\":20\r\n" + 
				"		},\r\n" + 
				"		\"owner\":{\r\n" + 
				"			\"type\": \"object\",\r\n" + 
				"			\"properties\": {\r\n" + 
				"				\"login\": {\r\n" + 
				"					\"type\": \"string\",\r\n" + 
				"					\"pattern\": \".*qiucccao.*\"\r\n" + 
				"				}\r\n" + 
				"			}\r\n" + 
				"		},\r\n" + 
				"		\"private\": {\r\n" + 
				"			\"type\": \"boolean\"\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"}";
		
//		response.then().assertThat().body(matchesJsonSchema(schema));
		response.then().assertThat().body(matchesJsonSchemaInClasspath("repo-schema.json"));
	}
}
