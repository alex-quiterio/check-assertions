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
		
		assertFalse("should return false because field1 isn't initialized", 
				FieldMapper.fieldInitialized(o1, field1));
		
		FieldMapper.addField(o1, field1);
		
		// Verify that field2 isn't initialized
		assertFalse("field2 should not be initialized", 
				FieldMapper.fieldInitialized(o1, field2));
	
		assertTrue("should return true because field1 already was initialized", 
				FieldMapper.fieldInitialized(o1, field1));
	}
	
	

}
