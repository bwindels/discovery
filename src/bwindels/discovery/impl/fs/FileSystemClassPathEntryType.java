package bwindels.discovery.impl.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import bwindels.discovery.impl.ClassFileScanner;
import bwindels.discovery.impl.ClassPathEntryType;


public class FileSystemClassPathEntryType implements ClassPathEntryType {

	@Override
	public boolean handleClassPathEntry(String dir, String[] subdirs, ClassFileScanner scanner) throws IOException {
		if(new File(dir).isDirectory()) {
			for (String subdir : subdirs) {
				File rootDir = new File(new File(dir).getCanonicalPath()+File.separatorChar+subdir);
				if(rootDir.exists()) {
					for (File file : new DirectoryIterable(rootDir, false, true)) {
						if(file.getName().toLowerCase().endsWith(".class")) {
							scanner.scanClassFile(new FileInputStream(file));
						}
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

}
