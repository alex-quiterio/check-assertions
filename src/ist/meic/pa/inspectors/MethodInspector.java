package ist.meic.pa.inspectors;

import javassist.CtClass;
import javassist.CtMethod;

/**
 * Method Inspector implements Inspector Interface in order to arm assertions
 * on the final step of their execution. If an assertion fails it returns a
 * Runtime Exception explaining what was wrong with execution of the method
 */
public class MethodInspector implements Inspector {

	@Override
	public void inspect(CtClass ctClass) {
		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
		}
	}
	
	public void inspectorInitialized() {
		
	}
}