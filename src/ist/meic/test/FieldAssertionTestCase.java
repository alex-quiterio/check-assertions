package ist.meic.test;

import ist.meic.pa.inspectors.FieldInspector;
import ist.meic.test.example.BasicAssertion;

public class FieldAssertionTestCase extends AssertionTestCase {

	public FieldAssertionTestCase() {
		super("FieldAssertion", new FieldInspector());
	}
	public void testBasicAssertions() {
		try {
			Object klass = this.loadNewInstance("BasicAssertion");
			//klass.foo = 2;
			//assertTrue("foo should be 2",klass.foo == 2);
			try {
				//klass.foo = 0;
				fail("foo should not be equal to zero");
			} catch(RuntimeException e) {
				assertFalse("it should raise an exception", true);
			}
		} catch (Throwable e) {
			// Ensure failure to show the error message
			fail("ASD");
		}
		
	}
}
