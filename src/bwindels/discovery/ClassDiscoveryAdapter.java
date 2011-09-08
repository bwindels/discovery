package bwindels.discovery;

import bwindels.discovery.impl.annotation.AnnotationImpl;


public class ClassDiscoveryAdapter implements ClassDiscoveryListener {

	@Override
	public void onClass(String typeName, String superClassName,
			String[] interfaces) {

	}

	@Override
	public void onClassAnnotation(AnnotationImpl a) {

	}

	@Override
	public void onClassEnd(String typeName) {

	}

	@Override
	public boolean onConstructor(int access, String typeName, TypeDeclaration[] types) {
		return false;
	}

	@Override
	public void onConstructorAnnotation(AnnotationImpl a) {

	}

	@Override
	public boolean onField(int access, TypeDeclaration type, String name) {
		return false;
	}

	@Override
	public void onFieldAnnotation(AnnotationImpl a) {

	}

	@Override
	public boolean onMethod(int access, String typeName, String methodName,
			TypeDeclaration[] types, TypeDeclaration returnType) {
		return false;
	}

	@Override
	public void onMethodAnnotation(AnnotationImpl a) {

	}

}
