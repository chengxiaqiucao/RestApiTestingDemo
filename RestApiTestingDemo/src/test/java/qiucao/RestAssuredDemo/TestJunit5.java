package qiucao.RestAssuredDemo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Junit5 =  platform+jupiter+vintage
 * 平台组件+junit执行引擎+Junit3/4版本的兼容引擎
 */
class TestJunit5 {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCase1() {
		fail("Not yet implemented");
	}
	
	@Test
	void testCase2() {
		assertTrue(true);
	}

}
