package bwindels.discovery.impl.annotation;

import org.objectweb.asm.AnnotationVisitor;

import bwindels.discovery.impl.TypeUtil;


public class AnnotationBuilder implements AnnotationVisitor {

	private Annotation annotation;
	private ArrayBuilder arrayBuilder;
	@Override
	public void visit(String name, Object value) {
		annotation.addParam(new AnnotationParam(name, Annotation.convertAnnotationValue(value)));
	}

	@Override
	public AnnotationVisitor visitAnnotation(String name, String desc) {
		Annotation a = new Annotation(TypeUtil.parseClassName(desc));
		annotation.addParam(new AnnotationParam(name, a));
		AnnotationBuilder ab = new AnnotationBuilder();
		ab.setAnnotation(a);
		return ab;
	}

	@Override
	public AnnotationVisitor visitArray(String name) {
		AnnotationParam p = new AnnotationParam(name);
		annotation.addParam(p);
		if(arrayBuilder==null) {
			arrayBuilder = new ArrayBuilder();
		}
		arrayBuilder.setParam(p);
		return arrayBuilder;
	}

	@Override
	public void visitEnd() {}

	@Override
	public void visitEnum(String name, String desc, String value) {
		annotation.addParam(new AnnotationParam(name,new EnumValueImpl(TypeUtil.parseClassName(desc), value)));
	}

	public Annotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}
}
