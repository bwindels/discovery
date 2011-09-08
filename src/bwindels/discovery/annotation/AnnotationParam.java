package bwindels.discovery.annotation;

public class AnnotationParam {
	private String name;
	private Object value;
	
	public AnnotationParam(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
	public AnnotationParam(String name) {
		super();
		this.name = name;
	}

	

	public String getName() {
		return name;
	}
	public Object getValue() {
		return value;
	}

	protected void setValue(Object value) {
		this.value = value;
	}
}
