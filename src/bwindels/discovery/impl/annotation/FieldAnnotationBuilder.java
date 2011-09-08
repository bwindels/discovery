package bwindels.discovery.impl.annotation;

import bwindels.discovery.ClassDiscoveryListener;

public class FieldAnnotationBuilder extends AbstractRootAnnotationBuilder {

	public FieldAnnotationBuilder(ClassDiscoveryListener listener) {
		super(listener);
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
		getListener().onFieldAnnotation(getAnnotation());
	}

	
}
