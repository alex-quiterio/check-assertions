package ist.meic.pa.inspectors;

import javassist.CtClass;
import javassist.CtMethod;

/**
 * Field Inspector implements Inspector Interface in order to arm assertions
 * in every single class attribute with annotation @Assertion on it.
 * When a assertion returns false it throws a runtime Exception with 
 * an explanation of the occurred.
 */
public class FieldInspector implements Inspector {

	@Override
	public void inspect(CtClass ctClass) {
		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			System.out.println(ctMethod.getName());
		}
	}
}