package bwindels.discovery.impl.fs;

import java.io.File;
import java.util.Iterator;

public class DirectoryIterable implements Iterable<File> {
	
	private Iterator<File> it;
	
	public DirectoryIterable(File rootDir, boolean returnDirs, boolean includeHiddenFiles) {
		it = new DirectoryIterator(rootDir, returnDirs, includeHiddenFiles);
	}

	@Override
	public Iterator<File> iterator() {
		return it;
	}
}


