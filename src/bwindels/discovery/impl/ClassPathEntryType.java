package bwindels.discovery.impl;

import java.io.IOException;

public interface ClassPathEntryType {
	public boolean handleClassPathEntry(String cpEntry, String[] subdirs, ClassFileScanner scanner) throws IOException;
}
