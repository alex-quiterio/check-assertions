# Check Assertions on Java Classes

Although Java is a strongly typed language, its type system is not sufficiently
powerful to be usable in some interesting situations.
One such situation occurs with the initialization of fields, also known as
instance variables. Although the Java language requires that local variables
must be initialized before being used, it does not have a similar requirement for
instance variables, instead defining its automatic initialization to default values.
This behavior allows code to run with objects that were not explicitly initialized.
Another situation occurs with subtypes of the available types, for example,
a field that can only contain positive integers. Since the Java language does not
provide a predefined type for positive integers, the programmer must overcome
this limitation by implementing a new (reference) type. Unfortunately, the
elements of this new type cannot be operated with the primitive arithmetic
operations, such as + or *. 

On the other hand, if the programmer prefers to use the primitive operations, 
he can declare the field as, for example, type int but then there is nothing that 
prevents that field to be assigned with a negative integer.

For a more complex example, consider a pair of fields where one of the fields
must not be smaller than the other. This might be useful, for instance, to
implement ranges. Again, it is not possible to declare this relation in Java in a
way that the type system can enforce.
Finally, consider method declarations: it might be useful to establish con-
straints for the parameters or return types of methods so that it becomes pos-
sible to express some interesting properties such as the fact that, for positive
arguments, the result must be positive.
In spite of Java’s type system limitations, it is possible to augment a Java
program with annotations that express additional constraints that must be
checked during program execution. It might then be possible to automati-
cally verify those constraints whenever a field is initialized, used, or updated,
or whenever a method is called.


## Goals with Check Assertions Project

Implement, in Java, a set of extensions that can be applicable to the above
mentioned scenarios. You must implement, at the very least:


* A mechanism for annotating a field declaration with an assertion that
must be enforced each time the field is assigned.

* A mechanism that prevents an annotated field of an object to be read
without having been initialized by the program (that is, without consid-
ering the automatic initialization done by the language).

* A mechanism for annotating a method declaration with an assertion that
must be enforced immediately before the method returns.


**An simple example to define assertions in class attributes:**

```java
public class Test {
	@Assertion("foo>0")
	int foo=1;
	
	@Assertion("bar%2==0")
	long bar;
	
	@Assertion("baz>foo")
	int baz;
	
	@Assertion("quux.length()>1")
	String quux;
}

```



