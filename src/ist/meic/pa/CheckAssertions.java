package ist.meic.pa;

import ist.meic.pa.inspectors.Inspector;
import ist.meic.pa.inspectors.FieldInspector;
import ist.meic.pa.inspectors.MethodInspector;
import ist.meic.pa.inspectors.ConstructorInspector;
import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;


public class CheckAssertions {

	
  private Loader loader;

  public CheckAssertions(Translator translator) throws Exception {
    this.loader = new Loader();
    ClassPool p = new ClassPool(true);
    p.appendClassPath("./build/classes/");
    this.loader.addTranslator(p, translator);
  }

  public void run(String className, String [] arguments) throws Throwable {
    this.loader.run(className, arguments);
  }

  public static void main(String [] args) throws Throwable {

    // Define the assertion strategies to use
    Inspector [] inspectors = { 
    		new MethodInspector(), 
    		new FieldInspector(),
    		new ConstructorInspector()
    };
    
    Translator translator = new AssertionTranslator(inspectors);
    CheckAssertions checkAssertions = new CheckAssertions(translator);
    String[] restArgs = new String[args.length - 1];
    System.arraycopy(args, 1, restArgs, 0, restArgs.length);
    
    if (args[0].equals("junit")) {
    	checkAssertions.run("ist.meic.test.JunitRoot", restArgs);
    } else {
    	checkAssertions.run(args[0], restArgs);
    }
  }

}
