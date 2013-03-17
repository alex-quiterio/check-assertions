package ist.meic.pa;

import ist.meic.pa.inspectors.FieldInspector;
import ist.meic.pa.inspectors.Inspector;
import ist.meic.pa.inspectors.MethodInspector;
import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;


public class CheckAssertions {

	
  private Loader loader;

  public CheckAssertions(Translator translator) throws Exception {
    this.loader = new Loader();
    this.loader.addTranslator(ClassPool.getDefault(), translator);
  }

  public void run(String className, String [] arguments) throws Throwable {
    this.loader.run(className, arguments);
  }

  public static void main(String [] args) throws Throwable {

    // Define the strategies to make the assertion check
    Inspector [] inspectors = { new FieldInspector(), new MethodInspector() };
    
    Translator translator = new AssertionTranslator(inspectors);
    CheckAssertions checkAssertions = new CheckAssertions(translator);
    String[] restArgs = new String[args.length - 1];
    System.arraycopy(args, 1, restArgs, 0, restArgs.length);
    checkAssertions.run(args[0], restArgs);
  }

}
