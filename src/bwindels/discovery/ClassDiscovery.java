package bwindels.discovery;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import org.objectweb.asm.ClassReader;

import bwindels.discovery.impl.ClassDataScanner;
import bwindels.discovery.impl.ClassFileScanner;
import bwindels.discovery.impl.ClassPathEntryType;
import bwindels.discovery.impl.ProxyDiscoveryListener;
import bwindels.discovery.impl.fs.FileSystemClassPathEntryType;
import bwindels.discovery.impl.jar.JarClassPathEntryType;



public class ClassDiscovery implements ClassFileScanner {
	private String[] packages;
	private ClassDataScanner classDataScanner;
	private ProxyDiscoveryListener listeners = new ProxyDiscoveryListener();
	private List<ClassPathEntryType> classPathScanners = new LinkedList<ClassPathEntryType>();
	private Iterable<String> classPathEntries;
	
	public ClassDiscovery(String[] packages) {
		this(packages, new ClassPathIterable());
	}
	
	public ClassDiscovery(String[] packages, Iterable<String> classPathEntries) {
		this.packages = packages;
		this.classPathEntries = classPathEntries;
		classPathScanners.add(new JarClassPathEntryType());
		classPathScanners.add(new FileSystemClassPathEntryType());
	}
	
	public ClassDiscovery(Iterable<String> packages, Iterable<String> classPathEntries) {
		this(convertPackages(packages),classPathEntries);
	}
	
	private static String[] convertPackages(Iterable<String> packages) {
		int packageCount = 0;
		for (String p : packages) {
			++packageCount;
		}
		String[] packagesArray = new String[packageCount];
		int index = 0;
		for (String p : packages) {
			packagesArray[index] = p;
			++index;
		}
		return packagesArray;
	}
	
	public void addListener(ClassDiscoveryListener l) {
		listeners.addListener(l);
	}
	
	public void scan() {
		if(listeners.listenerCount()==0) {
			return;
		}
		for (String cpe : classPathEntries) {
			scanLocation(cpe.trim());
		}
	}
	
	public void scanLocation(String loc) {
		if(listeners.listenerCount()==0) {
			return;
		}
		String[] subdirs;
		if(packages==null) {
			subdirs = new String[]{""};
		} else {
			subdirs = new String[packages.length];
			for(int i=0;i<subdirs.length;++i) {
				subdirs[i]=packages[i].replace('.', File.separatorChar)+File.separatorChar;
			}
		}
		try {
			boolean handled = false;
			for (ClassPathEntryType cpet : classPathScanners) {
				if(cpet.handleClassPathEntry(loc, subdirs, this)) {
					handled = true;
					break;
				}
			}
			if(!handled) {
				throw new RuntimeException(String.format("Unrecognized class path entry: %s",loc));
			}
		} catch(IOException e) {
			//this really should not fail, so if it does, let the application crash
			throw new RuntimeException(e);
		}
	}
	
	public void scanClassFile(InputStream is) {
		if(listeners.listenerCount()==0) {
			return;
		}
		if(classDataScanner==null) {
			classDataScanner = new ClassDataScanner(listeners);
		}
		try {
			ClassReader reader = new ClassReader(is);
			reader.accept(classDataScanner, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG);
			is.close();
			
		} catch (IOException e) {
			//this really should not fail, so if it does, let the application crash
			throw new RuntimeException(e);
		}
	}
}
