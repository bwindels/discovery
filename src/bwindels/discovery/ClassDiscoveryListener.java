package bwindels.discovery;

import bwindels.discovery.impl.annotation.AnnotationImpl;


public interface ClassDiscoveryListener {
	public static final int Public =	0x0001;
	public static final int Private =	0x0002;
	public static final int Protected =	0x0004;
	public static final int Static =	0x0008;
	public static final int Final =		0x0100;
	
	/**
	 * Called for each class found before calling any of the other methods in this interface related to the same class
	 */
	public void onClass(String typeName, String superClassName, String[] interfaces);
	/**
	 * Called for each Annotation found on a class
	 */
	public void onClassAnnotation(AnnotationImpl a);
	/**
	 * Called for each field in each class
	 * @return Whether you want to look for annotations on this field
	 */
	public boolean onField(int access, TypeDeclaration type, String name);
	/**
	 * Called for each Annotation found on a method in a class, only if you returned true in onField for that method
	 */
	public void onFieldAnnotation(AnnotationImpl a);
	/**
	 * Called for each constructor in each class
	 * @return Whether you want to look for annotations on this constructor
	 */
	public boolean onConstructor(int access, String typeName, TypeDeclaration[] types);
	/**
	 * Called for each Annotation found on a constructor in a class, only if you returned true in onConstructor for that constructor
	 */
	public void onConstructorAnnotation(AnnotationImpl a);
	/**
	 * Called for each method in each class
	 * @return Whether you want to look for annotations on this method
	 */
	public boolean onMethod(int access, String typeName, String methodName, TypeDeclaration[] types, TypeDeclaration returnType);
	/**
	 * Called for each Annotation found on a method in a class, only if you returned true in onMethod for that method
	 */
	public void onMethodAnnotation(AnnotationImpl a);
	/**
	 * Called for each class found after calling all of the other methods in this interface related to the same class
	 */
	public void onClassEnd(String typeName);
}
