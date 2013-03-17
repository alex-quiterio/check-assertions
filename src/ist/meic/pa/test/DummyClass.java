package ist.meic.pa.test;

import ist.meic.pa.annotations.Assertion;


public class DummyClass {
	
	@Assertion("foo>0")
	int foo=1;
	
	@Assertion("bar%2==0")
	long bar;
	
	@Assertion("false")
	int baz;
	
	@Assertion("quux.length()>1")
	String quux;
	
	public DummyClass() {
		// TODO Auto-generated constructor stub
	}
	
	@Assertion("($1>=0) && ($_>$1)")
	public int fooBar(int x) {
		return ++x;
	}

	
	
	public static void main(String [] args) {
		DummyClass dumm = new DummyClass();
		
		dumm.bar = 4;
		dumm.baz = 1;
		dumm.fooBar(2);
		System.out.println("HELLO");
		
	}
}
