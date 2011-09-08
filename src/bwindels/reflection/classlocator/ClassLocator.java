package bwindels.reflection.classlocator;

import java.io.InputStream;

import bwindels.reflection.TypeRef;


public interface ClassLocator {
	public InputStream getTypeAsInputStream(TypeRef type);
}
