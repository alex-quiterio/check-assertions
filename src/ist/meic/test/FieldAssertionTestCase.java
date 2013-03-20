package ist.meic.test;

import junit.framework.TestCase;

public class FieldAssertionTestCase extends TestCase {

	public void testSomething() {
		String list = null;
		assertTrue("The list should be null", list == null);
	}
}
