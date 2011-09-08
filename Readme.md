Discovery is a Java library to iterate over all classes in a given subset of the current classpath. Currently it supports filesystem and zipped (war, jar) classpath entries. You plug your code into the library by implementing an interface that follows the visitor pattern.

It uses a type system completely independent from Java's type system so information about the classes under investigation can be disposed of as soon as possible.

## Example

    MyListener l = new MyListener();
    ClassDiscovery s = new ClassDiscovery(new String[]{packageName});
    s.addListener(l);
    s.scan();
   	