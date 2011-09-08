package bwindels.discovery.impl;

import java.util.LinkedList;
import java.util.List;

import bwindels.discovery.ClassDiscoveryListener;
import bwindels.discovery.TypeRef;
import bwindels.discovery.impl.annotation.Annotation;


public class ProxyDiscoveryListener implements ClassDiscoveryListener {
	
	private List<ClassDiscoveryListener> listeners = new LinkedList<ClassDiscoveryListener>();
	
	public int listenerCount() {
		return listeners.size();
	}
	
	public void addListener(ClassDiscoveryListener l) {
		listeners.add(l);
	}
	
	public void onClass(String typeName, String superClassName,
			String[] interfaces) {
		for (ClassDiscoveryListener listener : listeners) {
			listener.onClass(typeName, superClassName, interfaces);
		}
	}

	public void onClassAnnotation(Annotation a) {
		for (ClassDiscoveryListener listener : listeners) {
			listener.onClassAnnotation(a);
		}
	}

	public void onClassEnd(String typeName) {
		for (ClassDiscoveryListener listener : listeners) {
			listener.onClassEnd(typeName);
		}
	}

	public boolean onField(int access, TypeRef type, String name) {
		boolean result = false;
		for (ClassDiscoveryListener listener : listeners) {
			if(listener.onField(access, type, name)) {
				result = true;
			}
		}
		return result;
	}

	public void onFieldAnnotation(Annotation a) {
		for (ClassDiscoveryListener listener : listeners) {
			listener.onFieldAnnotation(a);
		}
	}

	public boolean onMethod(int access, String typeName, String methodName,
			TypeRef[] types, TypeRef returnType) {
		boolean result = false;
		for (ClassDiscoveryListener listener : listeners) {
			if(listener.onMethod(access, typeName, methodName, types, returnType)) {
				result = true;
			}
		}
		return result;
	}

	public void onMethodAnnotation(Annotation a) {
		for (ClassDiscoveryListener listener : listeners) {
			listener.onMethodAnnotation(a);
		}
	}

	@Override
	public boolean onConstructor(int access, String typeName, TypeRef[] types) {
		boolean result = false;
		for (ClassDiscoveryListener listener : listeners) {
			if(listener.onConstructor(access, typeName, types)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public void onConstructorAnnotation(Annotation a) {
		for (ClassDiscoveryListener listener : listeners) {
			listener.onConstructorAnnotation(a);
		}
	}
}
