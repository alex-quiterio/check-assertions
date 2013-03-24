package ist.meic.pa.inspectors;

import javassist.ClassPool;
import javassist.CtClass;

public interface Inspector {
	
	/**
	 * inspect - Every class that implements Inspector
	 * needs to define its strategy for class inspection
	 * @param ctClass - the target class of evaluation
	 */
	public abstract void inspect(ClassPool pool, CtClass ctClass);
}