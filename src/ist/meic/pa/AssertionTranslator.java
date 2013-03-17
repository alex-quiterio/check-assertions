package ist.meic.pa;

import ist.meic.pa.inspectors.Inspector;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.Translator;

public class AssertionTranslator implements Translator {

	private Inspector [] inspectors;
	
	/**
	 * AssertionTranslator Constructor inject partial assertions 
	 * on code with annotation @Assert
	 * @param inspectors - a list of inspectors with different
	 * strategies to create assertions
	 */
	public AssertionTranslator(Inspector [] inspectors) {
		this.inspectors = inspectors;
	}

	@Override
	public void start(ClassPool pool) 
			throws NotFoundException, CannotCompileException {
		
	}
	
	/**
	 *	@param pool - an array of classes to be evaluated 
	 *	@param className - the target class used as argument 
	 *	for each inspector
	 */
	@Override
	public void onLoad(ClassPool pool, String className) 
			throws NotFoundException, CannotCompileException {
		for(Inspector inspector : this.inspectors) {
			inspector.inspect(pool.get(className));
		}
	}
}
