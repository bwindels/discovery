package bwindels.discovery.impl.jar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

class ZipDirectoryIterator implements Iterator<ZipEntry> {

	private Enumeration<? extends ZipEntry> entries;
	private boolean returnDirs;
	private String[] rootDirs;
	private ZipEntry currentEntry;
	private ZipFile zip;
	
	public ZipDirectoryIterator(File zipFile, String[] rootDirs, boolean returnDirs) throws ZipException, IOException {
		zip = new ZipFile(zipFile);
		this.returnDirs = returnDirs;
		
		boolean rootExists = false;
		
		this.rootDirs = new String[rootDirs.length];
		for (int i = 0;i<rootDirs.length;++i) {
			String rootDir = rootDirs[i];
			if(!rootDir.endsWith("/")) {
				rootDir=rootDir+"/";
			}
			if(rootDir.startsWith("/")) {
				rootDir = rootDir.substring(1);
			}
			this.rootDirs[i] = rootDir;
		}
		entries = zip.entries();
	}

	@Override
	public boolean hasNext() {
		if(entries==null) {
			return false;
		}
		while(entries.hasMoreElements()) {
			ZipEntry e = entries.nextElement();
			boolean foundRootDir = false;
			for (String rootDir : rootDirs) {
				if(e.getName().startsWith(rootDir)) {
					foundRootDir = true;
				}
			}
			//if this file is not a one of the rootDirs directories, skip it
			if(!foundRootDir) {
				continue;
			}
			
			if(e.isDirectory()) {
				if(returnDirs) {
					currentEntry = e;
					return true;
				}
			} else {
				currentEntry = e;
				return true;
			}
		}
		return false;
	}

	@Override
	public ZipEntry next() {
		return currentEntry;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	public InputStream getInputStream(ZipEntry entry) throws IOException {
		return zip.getInputStream(entry);
	}
}