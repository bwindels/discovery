package bwindels.discovery.impl.jar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

public class ZipDirectoryIterable implements Iterable<ZipEntry> {
	
	private ZipDirectoryIterator it;
	
	public ZipDirectoryIterable(File zipFile, String[] rootDirs, boolean returnDirs) throws ZipException, IOException {
		it = new ZipDirectoryIterator(zipFile, rootDirs, returnDirs);
	}

	@Override
	public Iterator<ZipEntry> iterator() {
		return it;
	}

	public InputStream getInputStream(ZipEntry entry) throws IOException {
		return it.getInputStream(entry);
	}
}


