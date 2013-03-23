package ist.meic.pa.inspectors;

import ist.meic.pa.annotations.Assertion;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;

public class ConstructorInspector implements Inspector {

	final String constructorSnippet =
			
				"if(!(%s)) {" +
					"throw new RuntimeException(\"%s\");" +
				"}" +  
			"}";
	final String error = "The assertion %s is false";	
	@Override
	public void inspect(CtClass ctClass) {
		for (CtBehavior ctBehavior : ctClass.getDeclaredBehaviors()) {
			try {
				ctBehavior.instrument(strategy(ctClass));
			} catch (CannotCompileException e) {
				System.err.println("[Constructor Inspector] Something wrong: " 
						+ e.getMessage());
			}
		}
	}
	
	private ExprEditor strategy(final CtClass ctClass) {
		return new ExprEditor() {
			public void edit(ConstructorCall cons) {
				String expression, errorMessage;
				try {
					Assertion an = (Assertion) cons.getMethod().getAnnotation(Assertion.class);
					expression = an.value();
					errorMessage = String.format(error, expression);
					cons.getMethod().insertBefore(String.format(constructorSnippet, 
							expression,
							errorMessage));
				} catch (ClassNotFoundException e) {
					System.err.println("[Constructor Inspector] Something wrong: " 
							+ e.getMessage());
				} catch (NotFoundException e) {
					System.err.println("[Constructor Inspector] Something wrong: " 
							+ e.getMessage());
				} catch (CannotCompileException e) {
					System.err.println("[Constructor Inspector] Something wrong: " 
							+ e.getMessage());
				}
				
			}
			
		};
	}

}
