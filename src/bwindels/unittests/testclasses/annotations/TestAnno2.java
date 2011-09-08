package bwindels.unittests.testclasses.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface TestAnno2 {
	String testValue();
	int[] order();
	TestAnno1[] annos();
	TestAnno1 anno();
}
