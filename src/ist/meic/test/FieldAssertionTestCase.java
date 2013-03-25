package ist.meic.test;

import ist.meic.test.example.BasicAssertion;

public class FieldAssertionTestCase extends AssertionTestCase {

	public FieldAssertionTestCase() {
		super("FieldAssertion");
	}
	
	public void testBasicAssertionsOnFoo() {
		try {
			BasicAssertion instance = new BasicAssertion();
			instance.foo = 1;
			assertTrue("foo should be equal to 1", instance.foo == 1);
			instance.foo = 2;
			assertTrue("foo should be equal to 2", instance.foo == 2);
			try {
				instance.foo = 0;
				fail("foo should not be equal to zero");	
			} catch(RuntimeException e) {
				assertTrue("foo should raise an exception", true);
			}
		} catch(RuntimeException e) {
			fail("foo should not raise an exception");
		}
	}
	
	public void testBasicAssertionsOnBar() {
		try {
			BasicAssertion instance = new BasicAssertion();
			instance.bar = 0;
			assertTrue("bar should be equal to 0", instance.bar== 0);
			instance.bar = 2;
			assertTrue("bar should be equal to 2", instance.bar == 2);
			instance.bar *= 2;
			assertTrue("bar should be equal to 4", instance.bar == 4);
			try {
				instance.bar += 1;
				fail("bar should not be equal to 5");	
			} catch(RuntimeException e) {
				assertTrue("bar should raise an exception", true);
			}
		} catch(RuntimeException e) {
			fail("bar should not raise an exception");
		}
	}
	
	public void testBasicAssertionsOnBaz() {
		try {
			BasicAssertion instance = new BasicAssertion();
			instance.foo = 1;
			instance.baz = 2;
			assertTrue("bar should be equal to 0", instance.baz == 2);
			assertTrue("bar should be equal to 2", instance.foo == 1);
			try {
				instance.baz -= 1;
				fail("baz should raise an exception");	
			} catch(RuntimeException e) {
				assertTrue("bar should raise an exception", true);
			}
		} catch(RuntimeException e) {
			fail("baz should not raise an exception");
		}
	}
	
	public void testBasicAssertionsOnGreatRelax() {
		try {
			BasicAssertion instance = new BasicAssertion();
			instance.greatRelax++;			
		} catch(RuntimeException e) {
			assertTrue("greatRelax should raise an exception", true);
		}
	}
	
}
