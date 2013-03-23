package ist.meic.test;

import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;
import ist.meic.pa.AssertionTranslator;
import ist.meic.pa.inspectors.Inspector;
import junit.framework.TestCase;

public abstract class AssertionTestCase extends TestCase {
	

	protected Translator translator;
	protected Loader loader;
	protected AssertionTestCase(String msg, Inspector inspector) {
		super(msg);	
		this.translator = 
				new AssertionTranslator(new Inspector[] { inspector });
	}
	
	protected void loadClass(String className) throws Throwable {
		String klassName = "ist.meic.test.example." + className;
		ClassPool pool = ClassPool.getDefault();
		pool.insertClassPath("ist.meic.test.example");
		this.translator.start(pool);
		this.translator.onLoad(pool, klassName);
	}
}
