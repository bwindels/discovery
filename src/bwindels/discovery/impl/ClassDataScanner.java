package bwindels.discovery.impl;

import java.io.Reader;
import java.io.StringReader;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import bwindels.discovery.ClassDiscoveryListener;
import bwindels.discovery.TypeRef;
import bwindels.discovery.impl.annotation.Annotation;
import bwindels.discovery.impl.annotation.AnnotationBuilder;
import bwindels.discovery.impl.annotation.AnnotationParam;
import bwindels.discovery.impl.annotation.ClassAnnotationBuilder;
import bwindels.discovery.impl.annotation.FieldAnnotationBuilder;
import bwindels.discovery.impl.annotation.MethodAnnotationBuilder;


public class ClassDataScanner implements ClassVisitor {

	private ClassDiscoveryListener listener;
	private ClassAnnotationBuilder classAnnotationBuilder;
	private MethodAnnotationBuilder methodAnnotationBuilder;
	private FieldAnnotationBuilder fieldAnnotationBuilder;
	
	private FieldScanner fieldScanner = new FieldScanner();
	private MethodScanner methodScanner = new MethodScanner();
	private boolean lastMethodIsConstructor = false;
	
	private String currentTypeName;
	
	public ClassDataScanner(ClassDiscoveryListener listener) {
		super();
		this.listener = listener;
		classAnnotationBuilder = new ClassAnnotationBuilder(listener);
		methodAnnotationBuilder = new MethodAnnotationBuilder(listener);
		fieldAnnotationBuilder = new FieldAnnotationBuilder(listener);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		currentTypeName = TypeUtil.parseInternalClassname(name);
		String superTypeName = null;
		if(superName!=null) {
			superTypeName = TypeUtil.parseInternalClassname(superName);
		}
		String[] translatedInterfaces = new String[interfaces.length];
		for(int i=0;i<interfaces.length;++i) {
			translatedInterfaces[i]=TypeUtil.parseInternalClassname(interfaces[i]);
		}
		listener.onClass(currentTypeName, superTypeName, translatedInterfaces);
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		String typeDescriptor = desc;
		if(signature!=null) {
			typeDescriptor = signature;
		}
		if(listener.onField(access, TypeUtil.parseTypeName(typeDescriptor), name)) {
			return fieldScanner;
		} else {
			return null;
		}
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		Reader sig = new StringReader(signature==null?desc:signature);
		TypeRef[] arguments = TypeUtil.parseSignature(sig);
		TypeRef returnType = TypeUtil.parseTypeDescriptor(sig);
		boolean getAnnotations;
		if(name.equals("<init>")) {
			getAnnotations = listener.onConstructor(access, currentTypeName, arguments);
			lastMethodIsConstructor = true;
		} else {
			getAnnotations = listener.onMethod(access, currentTypeName, name, arguments, returnType);
			lastMethodIsConstructor = false;
		}
		return (getAnnotations?methodScanner:null);
	}
	
	@Override
	public void visitEnd() {
		listener.onClassEnd(currentTypeName);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean arg1) {
		classAnnotationBuilder.setAnnotation(new Annotation(TypeUtil.parseClassName(desc)));
		return classAnnotationBuilder;
	}
	
	class FieldScanner implements FieldVisitor {

		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean arg1) {
			fieldAnnotationBuilder.setAnnotation(new Annotation(TypeUtil.parseClassName(desc)));
			return fieldAnnotationBuilder;
		}

		@Override
		public void visitAttribute(Attribute arg0) {
		}

		@Override
		public void visitEnd() {
		}
		
	}
	
	class MethodScanner implements MethodVisitor {

		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean arg1) {
			methodAnnotationBuilder.setAnnotation(new Annotation(TypeUtil.parseClassName(desc)));
			methodAnnotationBuilder.setConstructor(lastMethodIsConstructor);
			return methodAnnotationBuilder;
		}

		@Override
		public AnnotationVisitor visitAnnotationDefault() {return null;
		}

		@Override
		public void visitAttribute(Attribute arg0) {
		}

		@Override
		public void visitCode() {
		}

		@Override
		public void visitEnd() {
		}

		@Override
		public void visitFieldInsn(int arg0, String arg1, String arg2,
				String arg3) {
		}

		@Override
		public void visitFrame(int arg0, int arg1, Object[] arg2, int arg3,
				Object[] arg4) {
		}

		@Override
		public void visitIincInsn(int arg0, int arg1) {
		}

		@Override
		public void visitInsn(int arg0) {
		}

		@Override
		public void visitIntInsn(int arg0, int arg1) {
		}

		@Override
		public void visitJumpInsn(int arg0, Label arg1) {
		}

		@Override
		public void visitLabel(Label arg0) {
		}

		@Override
		public void visitLdcInsn(Object arg0) {
		}

		@Override
		public void visitLineNumber(int arg0, Label arg1) {
		}

		@Override
		public void visitLocalVariable(String arg0, String arg1, String arg2,
				Label arg3, Label arg4, int arg5) {
		}

		@Override
		public void visitLookupSwitchInsn(Label arg0, int[] arg1, Label[] arg2) {
		}

		@Override
		public void visitMaxs(int arg0, int arg1) {
		}

		@Override
		public void visitMethodInsn(int arg0, String arg1, String arg2,
				String arg3) {
		}

		@Override
		public void visitMultiANewArrayInsn(String arg0, int arg1) {
		}

		@Override
		public AnnotationVisitor visitParameterAnnotation(int arg0,
				String arg1, boolean arg2) {return null;
		}

		@Override
		public void visitTableSwitchInsn(int arg0, int arg1, Label arg2,
				Label[] arg3) {
		}

		@Override
		public void visitTryCatchBlock(Label arg0, Label arg1, Label arg2,
				String arg3) {
		}

		@Override
		public void visitTypeInsn(int arg0, String arg1) {
		}

		@Override
		public void visitVarInsn(int arg0, int arg1) {
		}
		
	}

	@Override
	public void visitAttribute(Attribute arg0) {
		

	}

	@Override
	public void visitInnerClass(String arg0, String arg1, String arg2, int arg3) {
		

	}

	@Override
	public void visitOuterClass(String arg0, String arg1, String arg2) {
		

	}

	@Override
	public void visitSource(String arg0, String arg1) {
		

	}

}
