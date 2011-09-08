package bwindels.discovery.annotation;

import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.Type;

public class Annotation {
	private String type;
	private List<AnnotationParam> params = new LinkedList<AnnotationParam>();
	
	public Annotation() {
		
	}
	
	public Annotation(String type) {
		setType(type);
	}
	
	protected void setType(String t) {
		type = t;
	}
	
	public String getType() {
		return type;
	}
	
	protected void addParam(AnnotationParam p) {
		params.add(p);
	}
	
	protected void clearParams() {
		params.clear();
	}
	
	public Iterable<AnnotationParam> getParams() {
		return params;
	}
	
	protected static Object convertAnnotationValue(Object value) {
		if(value instanceof Type) {
			return new TypeRefImpl(((Type)value).getClassName());
		} else {
			return value;
		}
	}
}
