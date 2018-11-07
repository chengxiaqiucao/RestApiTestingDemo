package qiucao.restful.framework;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.restassured.RestAssured;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.*;
/*
 * 在Junit4中使用Wiremock来实现服务端响应模拟功能
 */
public class WiremockForJunit4 {
	
	/*
	 * 参数：port,https-port
	 * options().port(8088).httpsPort(8089)
	 */
	@ClassRule
	public static WireMockRule wiremockrule = new WireMockRule(8088,8089);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		RestAssured.baseURI = "http://localhost:8088";
		
		wiremockrule.stubFor(get(urlEqualTo("/rest/mock"))
				.willReturn(aResponse()
						.withBody("从Junit4中返回的信息")
						.withStatus(200)));
	}

	@Test
	public void test() {

		
		given()
		.when()
			.get("/rest/mock")
		.then()
			.log().all();
	}

}
