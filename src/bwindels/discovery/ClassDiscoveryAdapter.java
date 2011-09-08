package bwindels.discovery;

import bwindels.discovery.annotation.Annotation;
import bwindels.reflection.TypeRef;

public class ClassDiscoveryAdapter implements ClassDiscoveryListener {

	@Override
	public void onClass(String typeName, String superClassName,
			String[] interfaces) {

	}

	@Override
	public void onClassAnnotation(Annotation a) {

	}

	@Override
	public void onClassEnd(String typeName) {

	}

	@Override
	public boolean onConstructor(int access, String typeName, TypeRef[] types) {
		return false;
	}

	@Override
	public void onConstructorAnnotation(Annotation a) {

	}

	@Override
	public boolean onField(int access, TypeRef type, String name) {
		return false;
	}

	@Override
	public void onFieldAnnotation(Annotation a) {

	}

	@Override
	public boolean onMethod(int access, String typeName, String methodName,
			TypeRef[] types, TypeRef returnType) {
		return false;
	}

	@Override
	public void onMethodAnnotation(Annotation a) {

	}

}
