package bwindels.reflection;

import java.util.LinkedList;
import java.util.List;

import bwindels.reflection.classlocator.ClassLoaderLocator;
import bwindels.reflection.classlocator.ClassLocator;


//TODO optimization: share common type information
public class LiteralTypeRef implements TypeRef {
	private String typeName;
	private int arrayDimensions = 0;
	private List<TypeRef> genericTypeParams = null;
	private ClassLocator _classLocator;
	
	public LiteralTypeRef(String typeName) {
		this(typeName,new ClassLoaderLocator());
	}
	
	public LiteralTypeRef(String typeName, ClassLocator locator) {
		this(locator);
		this.typeName = typeName;
	}
	
	public LiteralTypeRef() {
		this(new ClassLoaderLocator());
	}
	
	public LiteralTypeRef(ClassLocator locator) {
		_classLocator = locator;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public void setArrayDimensions(byte count) {
		this.arrayDimensions = count;
	}
	
	public void addGenericTypeParam(TypeRef tv) {
		if(genericTypeParams==null) {
			genericTypeParams = new LinkedList<TypeRef>();
		}
		genericTypeParams.add(tv);
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public Iterable<TypeRef> getGenericTypeParams() {
		return genericTypeParams;
	}

	@Override
	public boolean isArray() {
		return arrayDimensions!=0;
	}

	@Override
	public boolean isGeneric() {
		return genericTypeParams!=null;
	}

	@Override
	public int getArrayDimensions() {
		return arrayDimensions;
	}

	@Override
	public Class<?> getTypeClass() {
		try {
			return Class.forName(typeName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isPrimitive() {
		return 
			typeName.equals(byte.class.getName()) ||
			typeName.equals(char.class.getName()) ||
			typeName.equals(short.class.getName()) ||
			typeName.equals(int.class.getName()) ||
			typeName.equals(long.class.getName()) ||
			typeName.equals(float.class.getName()) ||
			typeName.equals(double.class.getName());
	}

	@Override
	public TypeRef getSuperType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<TypeRef> getDeclaredInterfaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean implementsInterface(TypeRef t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inheritsFromType(TypeRef t) {
		// TODO Auto-generated method stub
		return false;
	}
}
