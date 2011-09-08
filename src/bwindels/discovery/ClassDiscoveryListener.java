package bwindels.discovery;

import bwindels.discovery.impl.annotation.Annotation;

public interface ClassDiscoveryListener {
	public static final int Public =	0x0001;
	public static final int Private =	0x0002;
	public static final int Protected =	0x0004;
	public static final int Static =	0x0008;
	public static final int Final =		0x0100;
	
	/**
	 * Called whenever a 
	 */
	public void onClass(String typeName, String superClassName, String[] interfaces);
	public void onClassAnnotation(Annotation a);
	public boolean onField(int access, TypeRef type, String name);
	public void onFieldAnnotation(Annotation a);
	public boolean onConstructor(int access, String typeName, TypeRef[] types);
	public void onConstructorAnnotation(Annotation a);
	public boolean onMethod(int access, String typeName, String methodName, TypeRef[] types, TypeRef returnType);
	public void onMethodAnnotation(Annotation a);
	public void onClassEnd(String typeName);
}
