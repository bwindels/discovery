package bwindels.discovery.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import bwindels.discovery.TypeDeclaration;


public class TypeUtil {
	private static final byte ParseStageArray = 1;
	private static final byte ParseStageTypeIdentifier = 2;
	private static final byte ParseStageClassName = 3;
	
	public static TypeDeclaration parseTypeName(String t) {
		return parseTypeDescriptor(new StringReader(t));
	}
	
	public static TypeDeclaration parseTypeDescriptor(Reader t) {
		LiteralTypeDeclaration type = new LiteralTypeDeclaration();
		StringBuffer buf = null;
		int stage = ParseStageArray;
		int c;
		int index = 0;
		byte arrayCount = 0;
		
		try {
			while((c=t.read())!=-1) {
				if(c=='+') {
					continue;
				}
				switch(stage) {
				case ParseStageArray:
					if(c=='[') {
						++arrayCount;
						continue;
					} else {
						type.setArrayDimensions(arrayCount);
						stage = ParseStageTypeIdentifier;
					}
				case ParseStageTypeIdentifier:
					boolean primitiveType = true;
					if(c=='V') {
						return null;
					}
					else if(c=='Z') {
						type.setTypeName(boolean.class.getName());
					} else if(c=='C') {
						type.setTypeName(char.class.getName());
					} else if(c=='B') {
						type.setTypeName(byte.class.getName());
					} else if(c=='S') {
						type.setTypeName(short.class.getName());
					} else if(c=='I') {
						type.setTypeName(int.class.getName());
					} else if(c=='F') {
						type.setTypeName(float.class.getName());
					} else if(c=='J') {
						type.setTypeName(long.class.getName());
					} else if(c=='D') {
						type.setTypeName(double.class.getName());
					} else if(c=='L') {
						primitiveType = false;
						stage = ParseStageClassName;
						buf = new StringBuffer();
					}
					if(primitiveType) {
						return type;
					}
					break;
				case ParseStageClassName:
					if(c==';') {
						type.setTypeName(buf.toString());
						return type;
					} else if(c=='<') {
						int nextc;
						do {
							TypeDeclaration tv = parseTypeDescriptor(t);
							type.addGenericTypeParam(tv);
							t.mark(1);
							nextc = t.read();
							t.reset();
						} while(nextc!='>');
						//skip '>'
						t.read();
					} else if(c=='/') {
						buf.append('.');
					} else {
						buf.append((char)c);
					}
					break;
				}
				++index;
			}
		} catch (IOException e) {}
		throw new RuntimeException("invalid format");
	}
	
	public static String parseClassName(String typeDescriptor) {
		return typeDescriptor.substring(1, typeDescriptor.length()-1).replace('/', '.');
	}
	
	public static String parseInternalClassname(String typeDescriptor) {
		return typeDescriptor.replace('/', '.');
	}
	
	public static TypeDeclaration[] parseSignature(Reader r) {
		List<TypeDeclaration> values = new LinkedList<TypeDeclaration>();
		try {
			//skip '('
			r.read();
			while(true) {
				r.mark(1);
				int nextc = r.read();
				if(nextc!=')') {
					r.reset();
					values.add(parseTypeDescriptor(r));
				} else {
					break;
				}
			}
			return values.toArray(new TypeDeclaration[]{});
		} catch (IOException e) {
			return null;
		}
		
	}
}
