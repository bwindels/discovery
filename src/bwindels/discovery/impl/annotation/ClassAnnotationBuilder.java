package bwindels.discovery.impl.annotation;

import bwindels.discovery.ClassDiscoveryListener;

public class ClassAnnotationBuilder extends AbstractRootAnnotationBuilder {

	public ClassAnnotationBuilder(ClassDiscoveryListener listener) {
		super(listener);
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
		getListener().onClassAnnotation(getAnnotation());
	}

}
