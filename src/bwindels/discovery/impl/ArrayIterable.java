package bwindels.discovery.impl;

import java.util.Iterator;

public class ArrayIterable<T> implements Iterable<T> {
	
	public ArrayIterable(T[] array) {
		this.array = array;
	}

	private T[] array;

	@Override
	public Iterator<T> iterator() {
		return new ArrayIterator();
	}
	
	class ArrayIterator<E> implements Iterator<E> {

		private int index = 0;
		
		@Override
		public boolean hasNext() {
			return index+1<=array.length;
		}

		@Override
		public E next() {
			E val = (E) array[index];
			++index;
			return val;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
}
