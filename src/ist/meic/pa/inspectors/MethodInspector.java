package ist.meic.pa.inspectors;

import ist.meic.pa.annotations.Assertion;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * Method Inspector implements Inspector Interface in order to arm assertions
 * on the final step of their execution. If an assertion fails it returns a
 * Runtime Exception explaining what was wrong with execution of the method
 */
public class MethodInspector implements Inspector {

	final String templateBefore = 
			"{" + 
				"FieldMapper.stack.push($args);" +
			"}";
	final String templateAfter = 
			"{" + 
				"$args = (Object []) FieldMapper.stack.pop();" +
				" if(!(%s)) {" +
					" throw new RuntimeException(\"%s\");" + 
				"}" + 
			"}";
	final String errorMessage = "The Assertion %s is false";
	
	@Override
	public void inspect(CtClass ctClass) {
		Assertion assertion;
		String expression, error; 
		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			try {
				if (ctMethod.hasAnnotation(Assertion.class)) {
					assertion = (Assertion) ctMethod.getAnnotation(Assertion.class);
					expression = assertion.value();
					error = String.format(errorMessage, expression);
					ctMethod.insertBefore(templateBefore);
					ctMethod.insertAfter(String.format(
							templateAfter, 
							expression, 
							error
					));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (CannotCompileException e) {
				e.printStackTrace();
			}
		}
	}
}