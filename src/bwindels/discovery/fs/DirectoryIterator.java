package bwindels.discovery.fs;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

public class DirectoryIterator implements Iterator<File> {
	
	private LinkedList<File> directoriesToIterate = new LinkedList<File>();
	private File[] currentListing;
	private int currentListingIndex;
	private File currentResult;
	private boolean returnDirs;
	private boolean includeHiddenFiles;
	
	public DirectoryIterator(File rootDir, boolean returnDirs, boolean includeHiddenFiles) {
		if(!rootDir.isDirectory()) {
			throw new RuntimeException(String.format("%s is not a directory",rootDir.getAbsoluteFile()));
		}
		this.returnDirs = returnDirs;
		this.includeHiddenFiles = includeHiddenFiles;
		directoriesToIterate.add(rootDir);
	}

	@Override
	public boolean hasNext() {
		while(true) {
			//pop when necessary
			if(currentListing==null || currentListing.length==0 || currentListingIndex==(currentListing.length-1)) {
				if(directoriesToIterate.size()==0) {
					return false;
				}
				File currentDir = directoriesToIterate.getFirst();
				directoriesToIterate.removeFirst();
				currentListing = currentDir.listFiles();
				if(currentListing.length==0) {
					continue;
				}
				currentListingIndex = 0;
			} else {
				++currentListingIndex;
			}
			File f = currentListing[currentListingIndex];
			if(f.isHidden() && !includeHiddenFiles) {
				continue;
			}
			if(f.isDirectory()) {
				directoriesToIterate.add(f);
				if(returnDirs) {
					currentResult = f;
					return true;
				}
			} else {
				currentResult = f;
				return true;
			}
		}
	}

	@Override
	public File next() {
		return currentResult;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
