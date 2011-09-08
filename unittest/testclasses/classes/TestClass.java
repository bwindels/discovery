package testclasses.classes;

import java.util.List;

import testclasses.annotations.TestAnno1;
import testclasses.annotations.TestAnno2;
import testclasses.annotations.TestAnno3;


@TestAnno3(factor=3.141592)
public class TestClass {
	@TestAnno1("name")
	private String name;
	@TestAnno1("foo")
	private int[][] foo;
	
	@TestAnno1("getName")
	public String getName() {
		return name;
	}
	@TestAnno2(anno=@TestAnno1("tata"),annos={@TestAnno1("one"),@TestAnno1("two"),@TestAnno1("three")},order={4,6,102}, testValue = "value")
	protected void setName(String name, int blubber, List<Double> factors) {
		this.name = name;
	}
	@TestAnno1("getFoo")
	private int[][] getFoo() {
		return foo;
	}
	@TestAnno1("setFoo")
	protected void setFoo(int[][] foo) {
		this.foo = foo;
	}
		
	public TestClass() {
		
	}
}
