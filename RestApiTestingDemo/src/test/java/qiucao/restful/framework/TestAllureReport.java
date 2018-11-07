package qiucao.restful.framework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

import io.restassured.RestAssured;
import io.qameta.allure.*;

import static io.restassured.RestAssured.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/*
 * Allure测试报告定制demo
 */
@Epic(value = "Wiremock的测试Demo")
@Feature("Wiremock在Junit5中的使用")
@Owner("城下秋草")
class TestAllureReport {
	
	static WireMockServer wiremockServer = new WireMockServer(9088,9089);

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		wiremockServer.start();
		RestAssured.baseURI = "http://localhost:9088";
	}
	
	@AfterEach
	public void teardown(TestInfo testinfo) {
		System.out.println("---------"+testinfo.getDisplayName()+" finished!---------");
		
	}

	@Test
	@DisplayName("基本侦听规则设置")
	@Story("wiremock基本功能测试")
	@Description("这是一个针对<b>wiremock</b>的基本功能验证的简单demo测试用例")
	@Severity(SeverityLevel.CRITICAL)
	void test001() {
		test001_step1();
		test001_step2();
	}
	
	@Step("定义wiremock响应")
	void test001_step1() {
		wiremockServer.stubFor(get(urlEqualTo("/Rest/Mock"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("user", "qiucao")
						.withBody("basic test"))
				);
	}
	
	@Step("验证响应状态")
	void test001_step2() {
		given()
		.when()
			.get("/Rest/Mock")
		.then()
			.statusCode(200)
			.log().all();
	}
	
	@Test
	@DisplayName("响应规则简化设置")
	@Story("wiremock基本功能测试")
	@Severity(SeverityLevel.MINOR)
	void test002() {
		wiremockServer.stubFor(get(urlEqualTo("/Rest/simple"))
				.willReturn(ok("这是一个简化写法"))
				);
		
		given()
		.when()
			.get("/Rest/simple")
		.then()
			.log().all();		
	}
	
	@Test
	@DisplayName("重定向")
	@Story("wiremock基本功能测试")
	@Severity(SeverityLevel.NORMAL)
	void test003() {
		wiremockServer.stubFor(post(urlEqualTo("/Rest/redirect"))
				.willReturn(temporaryRedirect("/Test/newplace"))
				);
		
		given()
		.when()
			.post("/Rest/redirect")
		.then()
			.log().all();		
	}
	
	@Test
	@DisplayName("ServerError")
	@Story("wiremock基本功能测试")
	@Severity(SeverityLevel.BLOCKER)
	@Issues({@Issue("bug004"),@Issue("bug005")})
	void test004() {
		wiremockServer.stubFor(post(urlEqualTo("/Rest/ServerErr"))
				.willReturn(serverError())
				);
		
		given()
		.when()
			.post("/Rest/ServerErr")
		.then()
			.statusCode(200)
			.log().all();		
	}
	
	
	@Test
	@DisplayName("优先级规则的应用")
	@Story("wiremock高级功能测试")
	@Severity(SeverityLevel.CRITICAL)
	@Issue("bug001")
	void test005() {
		test005_step1();
		test005_step2();
		test005_step3();
		test005_step4();
	}
	
	@Step("匹配所有的请求设置")
	void test005_step1() {
//		匹配所有的请求
		wiremockServer.stubFor(any(anyUrl())
				.atPriority(10)
				.willReturn(notFound())
				);
	}
	
	@Step("匹配符合路径请求设置")
	void test005_step2() {
//		匹配符合路径的请求
		wiremockServer.stubFor(get(urlMatching("/Rest/.*"))
				.atPriority(5)
				.willReturn(aResponse()
						.withStatus(402)
						.withBody("没有访问权限"))
				);
	}
	
	@Step("匹配准确路径设置")
	void test005_step3() {
//		匹配符合路径的请求
		wiremockServer.stubFor(get(urlEqualTo("/Rest/Test"))
				.atPriority(1)
				.willReturn(ok("测试地址"))
				);
	}
	
	@Step("验证优先级匹配功能")
	void test005_step4() {
		given()
		.when()
			.get("/test/none")
		.then()
			.log().status();	
		
		given()
		.when()
			.get("/Rest/none")
		.then()
			.log().body();	
		
		given()
		.when()
			.get("/Rest/Test")
		.then()
			.log().body();	
	}
	
	@Test
	@DisplayName("场景的使用方法")
	@Story("wiremock高级功能测试")
	@Severity(SeverityLevel.NORMAL)
	@TmsLink("700")
	public void test006() {
		wiremockServer.stubFor(get(urlEqualTo("/Rest/user"))
				.willReturn(ok("{\"user\":\"qiucao\"}"))
				);
		
		wiremockServer.stubFor(delete(urlEqualTo("/Rest/user"))
				.inScenario("get user")
				.whenScenarioStateIs(Scenario.STARTED)
				.willReturn(aResponse().withStatus(204))
				.willSetStateTo("deleted")
				);
		
		wiremockServer.stubFor(get(urlEqualTo("/Rest/user"))
				.inScenario("get user")
				.whenScenarioStateIs("deleted")
				.willReturn(notFound())
				);
		
		given()
		.when()
			.get("/Rest/user")
		.then()
			.log().all();
		
		given()
		.when()
			.delete("/Rest/user")
		.then()
			.log().all();
		
		given()
		.when()
			.get("/Rest/user")
		.then()
			.log().all();	
		
	}
	
	/*
	 * Wiremock的消息录制功能
	 */
	@Test
	@Story("wiremock高级功能测试")
	@Severity(SeverityLevel.CRITICAL)
	@Link(name = "77.html", type = "qiucao")
	public void test007() {
		RestAssured.baseURI = "https://localhost:9089";
		RestAssured.useRelaxedHTTPSValidation();
		
		wiremockServer.startRecording("https://api.github.com");
		
		given()
		.when()
			.get("/users/rest-assured")
		.then()
			.statusCode(200)
			.log().all();
		
		wiremockServer.stopRecording();
		
		getResponse();
		
	}
	
	/*
	 * 添加Allure附件，附件类型可以是文本类型或者图像类型image/png
	 */
	@Attachment(type = "application/json")
	public String getResponse() {
		String resp;
		resp = given()
		.when()
			.get("/users/rest-assured")
		.then()
			.statusCode(200)
			.extract().asString();
		
		return resp;
	}
	
	/*
	 * 从文件中返回消息响应内容
	 */
	@Test
	@DisplayName("从文件获取响应内容")
	@Story("wiremock基本功能测试")
	@Severity(SeverityLevel.MINOR)
	@Link(name = "288.html", type = "qiucao")
	public void test008() {
		wiremockServer.stubFor(get(urlEqualTo("/Rest/file"))
				.willReturn(aResponse()
						.withStatus(200)
						.withBodyFile("filebody.json")));
		
		given()
		.when()
			.get("/Rest/file")
		.then()
			.log().all();
	}
	
	@AfterAll
	static void stopAll() {
		wiremockServer.stop();
	}

}
