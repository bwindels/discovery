package bwindels.discovery.jar;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;

import bwindels.discovery.ClassFileScanner;
import bwindels.discovery.ClassPathEntryType;


public class JarClassPathEntryType implements ClassPathEntryType {

	@Override
	public boolean handleClassPathEntry(String cpEntry, String[] subdirs,
			ClassFileScanner scanner) throws IOException {
		File jarFile = new File(cpEntry);
		if(jarFile.exists() && jarFile.isFile() && jarFile.getName().toLowerCase().endsWith(".jar")) {
			ZipDirectoryIterable it = new ZipDirectoryIterable(jarFile, subdirs, false);
			for (ZipEntry e : it) {
				if(e.getName().toLowerCase().endsWith(".class")) {
					scanner.scanClassFile(it.getInputStream(e));
				}
			}
			return true;
		} else {
			return false;
		}
	}

}
