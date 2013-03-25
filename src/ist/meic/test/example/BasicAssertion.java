package ist.meic.test.example;

import ist.meic.pa.annotations.Assertion;

public class BasicAssertion {
	@Assertion("foo>0")
	public int foo;
	
	@Assertion("bar%2==0")
	public long bar;
	
	@Assertion("baz>foo")
	public int baz;
	
	@Assertion("quux.length()>1")
	public String quux;
	
	@Assertion("true")
	public int greatRelax;

}
