package bwindels.reflection;

public interface TypeRef {
	public String getTypeName();
	public boolean isArray();
	public boolean isPrimitive();
	public Class<?> getTypeClass();
	public int getArrayDimensions();
	public boolean isGeneric();
	public Iterable<TypeRef> getGenericTypeParams();
	public TypeRef getSuperType();
	public boolean implementsInterface(TypeRef t);
	public Iterable<TypeRef> getDeclaredInterfaces();
	public boolean inheritsFromType(TypeRef t);
}