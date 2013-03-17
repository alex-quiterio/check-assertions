package ist.meic.pa.test;

import ist.meic.pa.annotations.Assertion;

public class DummyClass {
	
	@Assertion("foo>0")
	int foo=1;
	
	@Assertion("bar%2==0")
	long bar;
	
	@Assertion("baz>foo")
	int baz;
	
	@Assertion("quux.length()>1")
	String quux;

	
	public DummyClass() {
		
	}
	
	@Assertion("($1>=0) && ($_>$1)")
	public int fooBar(int x) {
		return ++x;
	}

	public static void main(String [] args) {
		DummyClass dum = new DummyClass();
		dum.bar=2;
		dum.baz=3;
		dum.bar+=2;
		dum.quux="foo";
		dum.bar++;
	}
}