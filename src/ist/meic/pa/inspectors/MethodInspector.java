package ist.meic.pa.inspectors;

import ist.meic.pa.annotations.Assertion;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

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
		String expression, error; 
		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			try {
				if (ctMethod.hasAnnotation(Assertion.class)) {
					expression = getAssertionChain(ctMethod, ctClass);
					error = String.format(errorMessage, expression);
					ctMethod.insertBefore(templateBefore);
					ctMethod.insertAfter(String.format(templateAfter, 
						expression, 
						error
					));
				}
			} catch (CannotCompileException e) {
				System.err.println("[Method Inspector] Something wrong: " 
						+ e.getMessage());
			}
		}
	}
	
	private String getAssertionChain(CtMethod m, CtClass c) {
		Assertion an;
		String OPERAND = "&&";
		String assertionChain = "";
		String template = "(%s) %s ";
		CtMethod currentMethod = m;
		CtClass  currentClass  = c;
		while (currentClass != null) {
			try {
				currentMethod = currentClass.getDeclaredMethod(currentMethod.getName(), 
						currentMethod.getParameterTypes());
				an = (Assertion) currentMethod.getAnnotation(Assertion.class);
				assertionChain += String.format(template, an.value(), OPERAND);
				currentClass = currentClass.getSuperclass();
			} catch (NotFoundException e) {
				currentClass = null;
				continue;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return "true";
			}
		}
		return assertionChain  + "true";
	}
}