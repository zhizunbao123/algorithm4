import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
    private Item[] array;
    private int lastIndex;
		
    public RandomizedQueue() {
		//@SuppressWarnings("unchecked")
		Item[] a = (Item[]) new Object[1];
		array = a;
		lastIndex = -1;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int size() {
		return lastIndex + 1;
	}
	
	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException("Element cannot be null");
		}
		if (lastIndex + 1 == array.length) {
			resize(array.length * 2);
		}
		array[++lastIndex] = item;
	}
	
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("RandomizedQueue is empty.");
		}
		int i = StdRandom.uniform(lastIndex + 1);
		Item removed = array[i];
		array[i] = array[lastIndex];
		array[lastIndex--] = null;
		if (size() > 0 && size() == array.length / 4) {
			resize(array.length / 2);
		}
		return removed;
	}
	
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException("RandomizedQueue is empty.");
		}
		Item sample = null;
		while (sample == null) {
			sample = array[StdRandom.uniform(lastIndex + 1)];
		}
		return sample;
	}
	
	private void resize(int newCapacity) {
		//@SuppressWarnings("unchecked")
		Item[] newArray = (Item[]) new Object[newCapacity];
		int i = 0, j = 0;
		while (i <= lastIndex) {
			newArray[j++] = array[i++];
		}
		array = newArray;
		lastIndex = j - 1;
	}

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new RandomizedIterator();
	}
	
	private class RandomizedIterator implements Iterator<Item> {
		private Item[] copiedArray;
		private int copiedLastIndex;
		
		RandomizedIterator() {
			//@SuppressWarnings("unchecked")
			Item[] a = (Item[]) new Object[lastIndex + 1];
			for (int i = 0; i <= lastIndex; i++) {
				a[i] = array[i];
			}
			copiedArray = a;
			copiedLastIndex = lastIndex;
		}
		
		@Override
		public boolean hasNext() {
			return copiedLastIndex >= 0;
		}
		
		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("no more item");
			}
			int i = StdRandom.uniform(lastIndex + 1);
			Item item = copiedArray[i];
			copiedArray[i] = copiedArray[copiedLastIndex];
			copiedArray[copiedLastIndex--] = null;
			return item;			
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
	}

}
