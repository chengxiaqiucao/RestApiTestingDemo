package qiucao.RestAssuredDemo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Junit4引入org.junit库
 * 支持通过注解来标记case和执行
 */
public class TestJunit4 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("--------------");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("***************");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("@@@@@@@@");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("=========");
	}

	@Test
	public void testcase1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void anotherCase2() {
		assertTrue(true);
	}

}
