package bwindels.discovery.annotation;

class EnumValueImpl implements EnumValue {
	private String type;
	private String value;
	
	public EnumValueImpl(String type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}
}
