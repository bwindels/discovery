package bwindels.discovery;

public interface TypeDeclaration {
	public String getTypeName();
	public boolean isArray();
	public boolean isPrimitive();
	public Class<?> getTypeClass();
	public int getArrayDimensions();
	public boolean isGeneric();
	public Iterable<TypeDeclaration> getGenericTypeParams();
}