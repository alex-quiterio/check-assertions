package ist.meic.pa.inspectors;

import ist.meic.pa.annotations.Assertion;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;

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
		for (CtConstructor ctCons: ctClass.getConstructors()) {
			try {
				ctCons.instrument(strategy(ctClass));
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
					if (!cons.getConstructor().hasAnnotation(Assertion.class))
						return;
					Assertion an = (Assertion) cons.getMethod().getAnnotation(Assertion.class);
					expression = an.value();
					errorMessage = String.format(error, expression);
					cons.getMethod().insertAfter(String.format(constructorSnippet, 
							expression,
							errorMessage));
				} catch (ClassNotFoundException e) {
					System.err.println("[Constructor Inspector] Something wrong: " 
							+ e.getMessage());
					e.printStackTrace();
				} catch (NotFoundException e) {
					System.err.println("[Constructor Inspector] Something wrong: " 
							+ e.getMessage());
					e.printStackTrace();
				} catch (CannotCompileException e) {
					System.err.println("[Constructor Inspector] Something wrong: " 
							+ e.getMessage());
					e.printStackTrace();
				}
				
			}
			
		};
	}

}
