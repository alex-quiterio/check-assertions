package ist.meic.test.example;

import ist.meic.pa.annotations.Assertion;

public class BasicAssertion {
	@Assertion("foo>0")
	public int foo=1;
	
	@Assertion("bar%2==0")
	public long bar;
	
	@Assertion("baz>foo")
	public int baz;
	
	@Assertion("quux.length()>1")
	public String quux;
}
