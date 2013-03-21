package ist.meic.test;

import javassist.Translator;
import ist.meic.pa.AssertionTranslator;
import ist.meic.pa.inspectors.Inspector;
import junit.framework.TestCase;

public abstract class AssertionTestCase extends TestCase {
	
	protected Inspector inspector;
	protected Translator translator;
	protected AssertionTestCase(String msg, String className) {
		super(msg);	
		this.translator = 
				new AssertionTranslator(new Inspector[] { this.inspector });
	}
}
