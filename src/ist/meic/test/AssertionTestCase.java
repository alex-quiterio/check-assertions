package ist.meic.test;

import javassist.ClassPool;
import ist.meic.test.example.*;
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
		this.loader = new Loader(ClassPool.getDefault());
	}
	
	protected Object loadNewInstance(String className) throws Throwable {
		String klassName = "ist.meic.test.example." + className;
		Class<?> klass = loader.loadClass(klassName);
		return klass.newInstance();
	}
}
