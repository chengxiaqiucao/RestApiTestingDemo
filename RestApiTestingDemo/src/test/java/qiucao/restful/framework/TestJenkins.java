package qiucao.restful.framework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

/*
 * 通过RestAPI接口来访问和使用Jenkins的基本功能
 */
class TestJenkins {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		RestAssured.baseURI = "http://localhost:8080/";
		RestAssured.authentication = preemptive().basic("qiucao", "qiucao");
	}

	/*
	 * 获取最近一次测试执行的结果
	 */
	@Test
	void GetLastBuildTestResult() {
		given()
		.when()
			.get("job/RestDemo/lastBuild/testReport/api/json")
		.then()
			.log().all()
			.statusCode(200);
	}
	
	/*
	 * 执行一次自动化job
	 */
	@Test
	public void RunJob() {
		given()
		.when()
			.post("job/RestDemo/build")
		.then()
			.log().all()
			.statusCode(201);
	}

}
