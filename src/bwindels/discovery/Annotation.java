package bwindels.discovery;

import bwindels.discovery.impl.annotation.AnnotationParam;

public interface Annotation {

	public abstract String getType();

	public abstract Iterable<AnnotationParam> getParams();

}