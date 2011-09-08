package bwindels.discovery.annotation;

class TypeRefImpl implements TypeRef {

	private String typeName;
	
	public TypeRefImpl(String typeName) {
		super();
		this.typeName = typeName;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

}
