package bwindels.reflection.classlocator;

import java.io.InputStream;

import bwindels.reflection.TypeRef;


public class ClassLoaderLocator implements ClassLocator {

	private ClassLoader _classLoader;
	
	public ClassLoaderLocator(ClassLoader classLoader) {
		super();
		_classLoader = classLoader;
	}
	
	public ClassLoaderLocator() {
		this(ClassLoader.getSystemClassLoader());
	}

	@Override
	public InputStream getTypeAsInputStream(TypeRef type) {
		if(type.isArray() || type.isPrimitive()) {
			throw new RuntimeException("invalid type, can't be array or primitive: "+type);
		}
		String name = '/'+type.getTypeName().replace('.', '/')+".class";
		InputStream is = getClass().getResourceAsStream(name);
		return is;
	}

}
