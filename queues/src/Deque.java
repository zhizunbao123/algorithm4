import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 */

/**
 * @author lei
 *
 */
public class Deque<Item> implements Iterable<Item> {

	/**
	 * 
	 */
	private final Node head;
	private final Node tail;
	private int size;
	
	private class Node {
		Item item;
		Node prev;
		Node next;
		
		Node(Item item) {
			this.item = item;
		}
	}
	
	public Deque() {
		head = new Node(null);
		tail = new Node(null);
		head.next = tail;
		tail.prev = head;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void addFirst(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("null element");
		}
		Node node = new Node(item);
		node.next = head.next;
		node.prev = head;
		head.next.prev = node;
		head.next = node;
		size++;
	}
	
	public void addLast(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("null element");
		}
		Node node = new Node(item);
		node.prev = tail.prev;
		tail.prev.next = node;
		tail.prev = node;
		node.next = tail;
		size++;
	}
	
	public Item removeFirst() {
		if (size == 0) {
			throw new NoSuchElementException("deque is empty");
		}
		Node node = head.next;
		node.next.prev = head;
		head.next = node.next;
		size--;
		return node.item;
	}
	
	public Item removeLast() {
		if (size == 0) {
			throw new NoSuchElementException("deque is empty");
		}
		Node node = tail.prev;
		tail.prev = node.prev;
		node.prev.next = tail;
		size--;
		return node.item;
	}
	
	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new HeadIterator();
	}
	
	private class HeadIterator implements Iterator<Item> {
		private Node cur = head;
		
		@Override
		public boolean hasNext() {
			return cur.next != tail;
		}
		
		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more item");
			}
			cur = cur.next;
			return cur.item;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove not supported");
		}
	}

	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	*/
}
