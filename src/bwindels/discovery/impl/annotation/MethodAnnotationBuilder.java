package bwindels.discovery.impl.annotation;

import bwindels.discovery.ClassDiscoveryListener;

public class MethodAnnotationBuilder extends AbstractRootAnnotationBuilder {

	private boolean isConstructor = false;
	
	public MethodAnnotationBuilder(ClassDiscoveryListener listener) {
		super(listener);
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
		if(isConstructor()) {
			getListener().onConstructorAnnotation(getAnnotation());
		} else {
			getListener().onMethodAnnotation(getAnnotation());
		}
	}

	public boolean isConstructor() {
		return isConstructor;
	}

	public void setConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
	}

}
