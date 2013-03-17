package ist.meic.pa.inspectors;

import ist.meic.pa.annotations.Assertion;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

/**
 * Field Inspector implements Inspector Interface in order to arm assertions
 * in every single class attribute with annotation @Assertion on it.
 * When a assertion returns false it throws a runtime Exception with 
 * an explanation of the occurred.
 */
public class FieldInspector implements Inspector {
	
	final String runtimeMethod =
			"public void assert_%s() throws RuntimeException {" +  
				"if(!(%s)) {" +
					"throw new RuntimeException(\"%s\");" +
				"}" + 
			"}";
	final String defaultTemplate =
			"{" + 
				"$0.%s = $1;" + 
				"$0.assert_%s();" +
			"}";
	final String errorMessage = "The assertion %s is false";
	
	private boolean initialized = false;
	
	@Override
	public void inspect(CtClass ctClass) {
		if (this.initialized == false) {
			injectGuardMethods(ctClass);
			this.inspectorInitialized();
		}
		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			try {
				ctMethod.instrument(strategy(ctClass));
			} catch (CannotCompileException e) {
				System.out.println("Something wrong" + e.getMessage());
			}
		}
	}
	
	@Override
	public void inspectorInitialized() {
		this.initialized = true;
	}
	
	/**
	 * 
	 * @return a strategy defined in order to inject assertions in every field 
	 * attribute of classes
	 */
	private ExprEditor strategy(final CtClass ctClass) {
		return new ExprEditor() {
			public void edit(FieldAccess fa) {
				String fieldName;
				try {
					if (fa.isWriter()
							&& fa.getField().hasAnnotation(Assertion.class)) {
							fieldName = fa.getFieldName();
							fa.replace(String.format(defaultTemplate, fieldName, fieldName));
					}
				} catch (NotFoundException e) {
					e.printStackTrace();
				} catch (CannotCompileException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	
	private void injectGuardMethods(CtClass ctClass) {
		Assertion assertion;
		String expression, fieldName, error;
		
		for(CtField field : ctClass.getFields()) {
			if (field.hasAnnotation(Assertion.class)) {
				try {
					assertion = (Assertion) field.getAnnotation(Assertion.class);
					expression = assertion.value();
					fieldName = field.getName();
					error = String.format(errorMessage, expression);
					CtMethod m = CtNewMethod.make(String.format(runtimeMethod, 
							fieldName,
							expression,
							error), ctClass);
					ctClass.addMethod(m);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (CannotCompileException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	
}