package ist.meic.test;

import ist.meic.pa.FieldMapper;
import junit.framework.TestCase;

public class FieldMapperTestCase extends TestCase {
	
	public FieldMapperTestCase(String msg) {
		super(msg);
	}
	
	public void testFieldMapperInitialization() {
		Object o1 = new Object();
		String field1 = "var1";
		String field2 = "var2";
		
		// Add field1 and field2 into FieldMapper as a reference to
		// every attribute of object 1.
		FieldMapper.addField(o1, field1);
		FieldMapper.addField(o1, field2);
		
		// Verify that field1 and field2 aren't initialized yet
		assertFalse("field1 should not be initialized", 
				FieldMapper.fieldInitialized(o1, field1));
		assertFalse("field2 should not be initialized", 
				FieldMapper.fieldInitialized(o1, field2));
		
		assertTrue("should be return true as sign of good initialization", 
				FieldMapper.initializationComplete(o1, field1));
		assertTrue("should return true because field1 isn't on the list anymore", 
				FieldMapper.fieldInitialized(o1, field1));
		assertFalse("should return false because field2 is on the list", 
				FieldMapper.fieldInitialized(o1, field2));
	}
	
	

}
