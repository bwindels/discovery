package bwindels.discovery.impl.annotation;

import java.lang.reflect.Array;
import java.util.LinkedList;

import org.objectweb.asm.AnnotationVisitor;

import bwindels.discovery.impl.TypeUtil;


public class ArrayBuilder implements AnnotationVisitor {

	private AnnotationParam param;
	private LinkedList<Object> values = new LinkedList<Object>();
	private AnnotationBuilder nestedAnnotationBuilder;
	@Override
	public void visit(String name, Object value) {
		values.add(AnnotationImpl.convertAnnotationValue(value));
	}

	@Override
	public AnnotationVisitor visitAnnotation(String name, String desc) {
		AnnotationImpl a = new AnnotationImpl(TypeUtil.parseClassName(desc));
		values.add(a);
		if(nestedAnnotationBuilder==null) {
			nestedAnnotationBuilder = new AnnotationBuilder();
		}
		nestedAnnotationBuilder.setAnnotation(a);
		return nestedAnnotationBuilder;
	}

	@Override
	public AnnotationVisitor visitArray(String arg0) {return null;}

	@Override
	public void visitEnd() {
		if(!values.isEmpty()) {
			Class<? extends Object> c = values.getFirst().getClass();
			Object array = Array.newInstance(c, values.size());
			int index = 0;
			for (Object v : values) {
				Array.set(array, index, v);
				++index;
			}
			param.setValue(array);
		} else {
			param.setValue(null);
		}
	}

	@Override
	public void visitEnum(String arg0, String arg1, String arg2) {}

	protected AnnotationParam getParam() {
		return param;
	}

	protected void setParam(AnnotationParam param) {
		this.param = param;
		values.clear();
	}
}
