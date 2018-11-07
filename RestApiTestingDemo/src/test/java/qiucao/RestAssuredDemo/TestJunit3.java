package qiucao.RestAssuredDemo;

/*
 * Junit3引入的是Framework包
 */
import junit.framework.TestCase;

/*
 * Junit3 需要继承TestCase类
 */
public class TestJunit3 extends TestCase {

	/*
	 * (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 * setup方法是在每个case执行之前执行的动作
	 */
	protected void setUp() throws Exception {
		super.setUp();
		System.out.println("-------------");
	}

	/*
	 * (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 * teardown方法是在每个case执行之后执行的动作
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		System.out.println("**************");
	}
	
	public void Case1() {
		assertTrue(true);
	}
	
	/*
	 * Junit3的测试用例必须以test开头 
	 */
	public void testCase2() {
		assertTrue(false);
	}

}
