package bwindels.unittests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bwindels.discovery.ClassDiscovery;
import bwindels.discovery.ClassDiscoveryListener;
import bwindels.discovery.ClassPathIterable;
import bwindels.discovery.TypeRef;
import bwindels.discovery.impl.annotation.Annotation;
import bwindels.discovery.impl.annotation.AnnotationParam;
import bwindels.unittests.testclasses.annotations.TestAnno1;
import bwindels.unittests.testclasses.annotations.TestAnno2;
import bwindels.unittests.testclasses.annotations.TestAnno3;
import bwindels.unittests.testclasses.classes.TestClass;


import junit.framework.TestCase;

public class DiscoveryTestCase extends TestCase implements ClassDiscoveryListener {
	
	private Map<String, Boolean> methodAnnotations = new HashMap<String, Boolean>();
	private Map<String, Boolean> fieldAnnotations = new HashMap<String, Boolean>();
	private Map<String, Boolean> typeAnnotations = new HashMap<String, Boolean>();
	private Map<String, Boolean> fields = new HashMap<String, Boolean>();
	private Map<String, Boolean> methods = new HashMap<String, Boolean>();
	private Map<String, Boolean> types = new HashMap<String, Boolean>();
	
	private String currentField, currentMethod;
	private boolean constructorVisited = false;
	
	@Override
	public void onClass(String typeName, String superClassName,
			String[] interfaces) {
		types.put(typeName, true);
	}

	@Override
	public void onClassAnnotation(Annotation a) {
		typeAnnotations.put(a.getType(),true);
		assertTrue(a.getType().equals(TestAnno3.class.getName()));
		Iterator<AnnotationParam> it = a.getParams().iterator();
		AnnotationParam p = it.next();
		assertTrue(p.getValue().getClass().equals(Double.class));
		assertTrue(p.getName().equals("factor"));
		assertTrue(p.getValue().equals(3.141592));
		assertFalse(it.hasNext());
	}

	@Override
	public void onClassEnd(String typeName) {
		assertTrue(typeName.equals(TestClass.class.getName()));
	}

	@Override
	public boolean onField(int access, TypeRef type, String name) {
		fields.put(name, true);
		currentField = name;
		assertTrue((access & Private)!=0);
		if(name.equals("name")) {
			assertTrue(type.getTypeName().equals(String.class.getName()) && !type.isArray() && !type.isGeneric());
		} else if(name.equals("foo")) {
			assertTrue(type.getTypeName().equals(int.class.getName()) && type.getArrayDimensions()==2 && !type.isGeneric());
		} else {
			assertTrue(String.format("invalid field: %s", name),false);
		}
		return true;
	}

	@Override
	public void onFieldAnnotation(Annotation a) {
		fieldAnnotations.put(currentField, true);
		assertTrue(currentField.equals("name") || currentField.equals("foo"));
		assertTrue(a.getType().equals(TestAnno1.class.getName()));
		Iterator<AnnotationParam> it = a.getParams().iterator();
		AnnotationParam p = it.next();
		assertTrue(p.getName().equals("value"));
		assertTrue(p.getValue().equals(currentField));
		assertFalse(it.hasNext());
	}

	@Override
	public boolean onMethod(int access, String typeName, String methodName,
			TypeRef[] types, TypeRef returnType) {
		methods.put(methodName, true);
		currentMethod = methodName;
		if(methodName.equals("getName")) {
			assertTrue((access & Public)!=0);
			assertTrue(types.length==0);
			assertTrue(returnType.getTypeName().equals(String.class.getName()));
		} else if(methodName.equals("setName")) {
			assertTrue((access & Protected)!=0);
			assertTrue(types[0].getTypeName().equals(String.class.getName()));
			assertTrue(returnType==null);
			assertTrue(types.length==3);
			assertTrue(types[0].getTypeName().equals(String.class.getName()));
			assertTrue(types[1].getTypeName().equals(int.class.getName()));
			assertTrue(types[2].getTypeName().equals(List.class.getName()));
			assertTrue(types[2].isGeneric());
			Iterator<TypeRef> it = types[2].getGenericTypeParams().iterator();
			assertTrue(it.next().getTypeName().equals(Double.class.getName()));
			assertFalse(it.hasNext());
		} else if(methodName.equals("getFoo")) {
			assertTrue((access & Private)!=0);
			assertTrue(types.length==0);
			assertTrue(returnType.getTypeName().equals(int.class.getName()));
			assertTrue(returnType.getArrayDimensions()==2);
		} else if(methodName.equals("setFoo")) {
			assertTrue((access & Protected)!=0);
			assertTrue(returnType==null);
			assertTrue(types.length==1);
			assertTrue(types[0].getTypeName().equals(int.class.getName()));
			assertTrue(types[0].getArrayDimensions()==2);
		} else {
			assertTrue(String.format("not a valid method: %s",methodName),false);
		}
		return true;
	}

	@Override
	public void onMethodAnnotation(Annotation a) {
		methodAnnotations.put(currentMethod, true);
		if(currentMethod.equals("setName")) {
			assertTrue(a.getType().equals(TestAnno2.class.getName()));
			int paramCount = 0;
			for (AnnotationParam p : a.getParams()) {
				++paramCount;
				if(p.getName().equals("anno")) {
					assertTrue(p.getValue() instanceof Annotation);
					Annotation av = (Annotation)p.getValue();
					assertTrue(av.getType().equals(TestAnno1.class.getName()));
					Iterator<AnnotationParam> avpit = av.getParams().iterator();
					AnnotationParam avp = avpit.next();
					assertTrue(avp.getName().equals("value"));
					assertTrue(avp.getValue().equals("tata"));
					assertFalse(avpit.hasNext());
				} else if(p.getName().equals("annos")) {
					assertTrue(p.getValue() instanceof Annotation[]);
					Annotation[] arrayValues = (Annotation[])p.getValue();
					assertTrue(arrayValues.length==3);
					String[] refValues = {"one","two","three"};
					int counter = 0;
					for (Annotation av : arrayValues) {
						assertTrue(av.getType().equals(TestAnno1.class.getName()));
						Iterator<AnnotationParam> avpit = av.getParams().iterator();
						AnnotationParam avp = avpit.next();
						assertTrue(avp.getName().equals("value"));
						assertTrue(avp.getValue().equals(refValues[counter]));
						assertFalse(avpit.hasNext());
						++counter;
					}
				} else if(p.getName().equals("order")) {
					assertTrue(p.getValue() instanceof int[]);
					int[] arrayValues = (int[])p.getValue();
					assertTrue(arrayValues.length==3);
					int[] refValues = {4,6,102};
					for (int index = 0; index<arrayValues.length; ++index) {
						assertTrue(refValues[index]==arrayValues[index]);
					}
				} else if(p.getName().equals("testValue")) {
					assertTrue(p.getValue() instanceof String);
					assertTrue(p.getValue().equals("value"));
				} else {
					assertTrue(String.format("invalid annotation param: %s",p.getName()),false);
				}
			}
			assertTrue(paramCount==4);
		} else {
			assertTrue(a.getType().equals(TestAnno1.class.getName()));
			Iterator<AnnotationParam> it = a.getParams().iterator();
			AnnotationParam p = it.next();
			assertTrue(p.getName().equals("value"));
			assertTrue(p.getValue().equals(currentMethod));
		}
	}
	
	@Override
	public boolean onConstructor(int access, String typeName, TypeRef[] types) {
		constructorVisited = true;
		return true;
	}

	@Override
	public void onConstructorAnnotation(Annotation a) {
		
	}
	
	public void testClassDiscovery() {
		String packageName = this.getClass().getPackage().getName()+".testclasses.classes";
		ClassDiscovery s = new ClassDiscovery(new String[]{packageName});
		s.addListener(this);
		s.scan();
		
		assertTrue(fields.size()==2);
		assertTrue(fields.get("name")==true);
		assertTrue(fields.get("foo")==true);
		
		assertTrue(fieldAnnotations.size()==2);
		assertTrue(fieldAnnotations.get("name")==true);
		assertTrue(fieldAnnotations.get("foo")==true);
		assertTrue(methods.size()==4);
		assertTrue(methods.get("getFoo")==true);
		assertTrue(methods.get("setFoo")==true);
		assertTrue(methods.get("getName")==true);
		assertTrue(methods.get("setName")==true);
		
		assertTrue(methodAnnotations.size()==4);
		assertTrue(methodAnnotations.get("getFoo")==true);
		assertTrue(methodAnnotations.get("setFoo")==true);
		assertTrue(methodAnnotations.get("getName")==true);
		assertTrue(methodAnnotations.get("setName")==true);
		assertTrue(constructorVisited);
	}	
}
