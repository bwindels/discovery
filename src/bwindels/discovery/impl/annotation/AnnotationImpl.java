package bwindels.discovery.impl.annotation;

import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.Type;

import bwindels.discovery.Annotation;
import bwindels.discovery.impl.LiteralTypeDeclaration;

public class AnnotationImpl implements Annotation {
	private String type;
	private List<AnnotationParam> params = new LinkedList<AnnotationParam>();
	
	public AnnotationImpl() {
		
	}
	
	public AnnotationImpl(String type) {
		setType(type);
	}
	
	protected void setType(String t) {
		type = t;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	protected void addParam(AnnotationParam p) {
		params.add(p);
	}
	
	protected void clearParams() {
		params.clear();
	}
	
	@Override
	public Iterable<AnnotationParam> getParams() {
		return params;
	}
	
	protected static Object convertAnnotationValue(Object value) {
		if(value instanceof Type) {
			return new LiteralTypeDeclaration(((Type)value).getClassName());
		} else {
			return value;
		}
	}
}
