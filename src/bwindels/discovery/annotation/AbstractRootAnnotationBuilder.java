package bwindels.discovery.annotation;

import bwindels.discovery.ClassDiscoveryListener;

public abstract class AbstractRootAnnotationBuilder extends AnnotationBuilder {

	public AbstractRootAnnotationBuilder(ClassDiscoveryListener listener) {
		super();
		this.listener = listener;
	}

	private ClassDiscoveryListener listener;

	protected ClassDiscoveryListener getListener() {
		return listener;
	}
}
