package ist.meic.pa.inspectors;

import ist.meic.pa.annotations.Assertion;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;

public class ConstructorInspector implements Inspector {

	final String constructorSnippet =
			"{" +
				"if(!(%s)) {" +
					"throw new RuntimeException(\"%s\");" +
				"}" + 
			"}";
	final String error = "The assertion %s is false";	
	@Override
	public void inspect(CtClass ctClass) {
		Assertion assertion;
		String expression, errorMessage;
		for (CtConstructor ctCons: ctClass.getDeclaredConstructors()) {
			if (ctCons.hasAnnotation(Assertion.class)) {
				try {
					assertion = (Assertion) ctCons.getAnnotation(Assertion.class);
					expression = assertion.value();
					errorMessage = String.format(error, expression);
					ctCons.insertAfter(String.format(constructorSnippet, 
							expression,
							errorMessage));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (CannotCompileException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
