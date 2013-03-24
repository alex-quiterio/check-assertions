package ist.meic.test;

import ist.meic.test.example.BasicAssertion;

public class FieldAssertionTestCase extends AssertionTestCase {

	public FieldAssertionTestCase() {
		super("FieldAssertion");
	}
	
	public void testBasicAssertions() {
		try {
			BasicAssertion instance = new BasicAssertion();
			instance.foo = 1;
			instance.foo = 2;
			instance.foo = 0;
			fail("foo should not be equal to zero");
		} catch(RuntimeException e) {
			assertTrue("it should raise an exception", true);
		}
	}
}
