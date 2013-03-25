package ist.meic.pa.inspectors;

import ist.meic.pa.annotations.Assertion;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
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
	final String writeRoutine =
			"{" +
				"$0.%s = $1;" +
				"FieldMapper.addField((Object)$0, \"%s\");" +
				"$0.assert_%s();" +
			"}";
	final String readRoutine = 
			"{" +
				"if(FieldMapper.fieldInitialized((Object)$0, \"%s\")) {" +
					"$_ = ($r) $0.%s;" +
				"} else {" +
					"throw new RuntimeException(\"%s\");" +
				"}" +
			"}";

	final String errorMessage = "The assertion %s is false";
	final String errorIMessage = "Error: %s was not initialized";
	
	@Override
	public void inspect(ClassPool pool, CtClass ctClass) {
		for (CtBehavior ctBehavior : ctClass.getDeclaredBehaviors()) {
			try {
				ctBehavior.instrument(strategy(pool, ctClass));
			} catch (CannotCompileException e) {
				System.err.println("[Field Inspector] Something wrong: " 
						+ e.getMessage());
			}
		}
	}
	
	/**
	 * 
	 * @return a strategy defined in order to inject assertions in every field 
	 * with annotation @Assertion
	 */
	private ExprEditor strategy(final ClassPool pool, final CtClass ctClass) {
		return new ExprEditor() {
			// field access purpose
			public void edit(FieldAccess fa) {
				String expression, error;
				Assertion assertion;
				CtClass klass;
				String fieldName = fa.getFieldName();
				try {
					if(!fa.getField().hasAnnotation(Assertion.class)) {
						return;
					} else {
						klass = pool.get(fa.getClassName());
						try {
							// check if method already exists
							klass.getDeclaredMethod("assert_" + fieldName);
						} catch(NotFoundException e) {
							assertion = (Assertion) fa.getField().getAnnotation(Assertion.class);
							expression = assertion.value();
							fieldName = fa.getField().getName();
							error = String.format(errorMessage, expression);
							
							klass = pool.get(fa.getClassName());
							CtMethod m = CtNewMethod.make(String.format(runtimeMethod, 
									fieldName,
									expression,
									error), klass);
							klass.addMethod(m);
						}
						if (fa.isWriter()
								&& fa.getField().hasAnnotation(Assertion.class)) {
								fieldName = fa.getFieldName();
								fa.replace(String.format(writeRoutine, 
										fieldName, fieldName,fieldName, fieldName));
						} else if (fa.isReader() 
								&& fa.getField().hasAnnotation(Assertion.class)) {
								fa.replace(String.format(readRoutine, fieldName,
										fieldName,
										String.format(errorIMessage, fieldName)));
						}
					}
				} catch (NotFoundException e) {
					e.printStackTrace();
				} catch (CannotCompileException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				} catch (ClassNotFoundException e) {
					System.out.println(e.getMessage());
				}
			}
			
		};
	}
}