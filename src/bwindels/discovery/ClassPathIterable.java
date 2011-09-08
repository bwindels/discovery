package bwindels.discovery;

import java.io.File;

public class ClassPathIterable extends ArrayIterable<String> {

	public ClassPathIterable() {
		super(entries());
	}

	private static String[] entries() {
		String classPath = System.getProperty("java.class.path");
		String[] classPathEntries = classPath.split(Character.toString(File.pathSeparatorChar));
		return classPathEntries;
	}
}
